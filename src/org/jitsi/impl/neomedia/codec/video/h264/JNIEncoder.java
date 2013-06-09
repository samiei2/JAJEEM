/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.neomedia.codec.video.h264;

import java.awt.*;
import java.util.*;

import javax.media.*;
import javax.media.format.*;

import net.sf.fmj.media.*;

import org.jitsi.impl.neomedia.*;
import org.jitsi.impl.neomedia.codec.*;
import org.jitsi.impl.neomedia.format.*;
import org.jitsi.service.configuration.*;
import org.jitsi.service.libjitsi.*;
import org.jitsi.service.neomedia.codec.*;
import org.jitsi.service.neomedia.control.*;
import org.jitsi.service.neomedia.event.*;
import org.jitsi.util.*;

/**
 * Implements an H.264 encoder.
 *
 * @author Damian Minkov
 * @author Lyubomir Marinov
 * @author Sebastien Vincent
 */
public class JNIEncoder
    extends AbstractCodec
    implements RTCPFeedbackListener
{
    /**
     * The available presets we can use with the encoder.
     */
    public static final String[] AVAILABLE_PRESETS =
    {
        "ultrafast",
        "superfast",
        "veryfast",
        "faster",
        "fast",
        "medium",
        "slow",
        "slower",
        "veryslow"
    };

    /**
     * The name of the baseline H.264 (encoding) profile.
     */
    public static final String BASELINE_PROFILE = "baseline";

    /**
     * The frame rate to be assumed by <tt>JNIEncoder</tt> instances in the
     * absence of any other frame rate indication.
     */
    static final int DEFAULT_FRAME_RATE = 15;

    /**
     * The default value of the {@link #PRESET_PNAME}
     * <tt>ConfigurationService</tt> property.
     */
    public static final String DEFAULT_PRESET = AVAILABLE_PRESETS[0];

    /**
     * The name of the <tt>ConfigurationService</tt> property which specifies
     * the H.264 (encoding) profile to be used in the absence of negotiation.
     * Though it seems that RFC 3984 "RTP Payload Format for H.264 Video"
     * specifies the baseline profile as the default, we have till the time of
     * this writing defaulted to the main profile and we do not currently want
     * to change from the main to the base profile unless we really have to.
     */
    public static final String DEFAULT_PROFILE_PNAME
        = "net.java.sip.communicator.impl.neomedia.codec.video.h264."
            + "defaultProfile";

    /**
     * Key frame every 150 frames.
     */
    static final int IFRAME_INTERVAL = 150;

    /**
     * The logger used by the <tt>JNIEncoder</tt> class and its instances for
     * logging output.
     */
    private static final Logger logger = Logger.getLogger(JNIEncoder.class);

    /**
     * The name of the main H.264 (encoding) profile.
     */
    public static final String MAIN_PROFILE = "main";

    /**
     * The name of the high H.264 (encoding) profile.
     */
    public static final String HIGH_PROFILE = "high";

    /**
     * The default value of the {@link #DEFAULT_PROFILE_PNAME}
     * <tt>ConfigurationService</tt> property.
     */
    public static final String DEFAULT_DEFAULT_PROFILE = MAIN_PROFILE;

    /**
     * The name of the format parameter which specifies the packetization mode
     * of H.264 RTP payload.
     */
    public static final String PACKETIZATION_MODE_FMTP = "packetization-mode";

    /**
     * Minimum interval between two PLI request processing (in milliseconds).
     */
    private static final long PLI_INTERVAL = 3000;

    /**
     * Name of the code.
     */
    private static final String PLUGIN_NAME = "H.264 Encoder";

    /**
     * A preset is a collection of options that will provide a certain encoding
     * speed to compression ratio. A slower preset will provide
     * better compression (compression is quality per size).
     */
    public static final String PRESET_PNAME
        = "org.jitsi.impl.neomedia.codec.video.h264.preset";

    /**
     * The list of <tt>Formats</tt> supported by <tt>JNIEncoder</tt> instances
     * as output.
     */
    static final Format[] SUPPORTED_OUTPUT_FORMATS
        = {
            new ParameterizedVideoFormat(
                    Constants.H264,
                    PACKETIZATION_MODE_FMTP, "0"),
            new ParameterizedVideoFormat(
                    Constants.H264,
                    PACKETIZATION_MODE_FMTP, "1")
        };

    /**
     * Additional codec settings.
     */
    private Map<String, String> additionalSettings = null;

    /**
     * The codec we will use.
     */
    private long avctx;

    /**
     * The encoded data is stored in avpicture.
     */
    private long avFrame;

    /**
     * Force encoder to send a key frame.
     * First frame have to be a keyframe.
     */
    private boolean forceKeyFrame = true;

    /**
     * Next interval for an automatic keyframe.
     */
    @SuppressWarnings("unused")
    private int framesSinceLastIFrame = IFRAME_INTERVAL + 1;

    /**
     * The <tt>KeyFrameControl</tt> used by this <tt>JNIEncoder</tt> to
     * control its key frame-related logic.
     */
    private KeyFrameControl keyFrameControl;

    private KeyFrameControl.KeyFrameRequestee keyFrameRequestee;

    /**
     * The time in milliseconds of the last request for a key frame from the
     * remote peer to this local peer.
     */
    private long lastKeyFrameRequestTime = System.currentTimeMillis();

    /**
     * The packetization mode to be used for the H.264 RTP payload output by
     * this <tt>JNIEncoder</tt> and the associated packetizer. RFC 3984 "RTP
     * Payload Format for H.264 Video" says that "[w]hen the value of
     * packetization-mode is equal to 0 or packetization-mode is not present,
     * the single NAL mode, as defined in section 6.2 of RFC 3984, MUST be
     * used."
     */
    private String packetizationMode;

    /**
     * The raw frame buffer.
     */
    private long rawFrameBuffer;

    /**
     * Length of the raw frame buffer. Once the dimensions are known, this is
     * set to 3/2 * (height*width), which is the size needed for a YUV420 frame.
     */
    private int rawFrameLen;

    /**
     * Peer that receive stream from latest ffmpeg/x264 aware peer does not
     * manage to decode the first keyframe and must wait for the next periodic
     * intra refresh to display the video to the user.
     *
     * Temporary solution for this problem: send the two first frames as
     * keyframes to display video stream.
     */
    private boolean secondKeyFrame = true;

    /**
     * Initializes a new <tt>JNIEncoder</tt> instance.
     */
    public JNIEncoder()
    {
        inputFormats
            = new Format[]
            {
                new YUVFormat(
                        /* size */ null,
                        /* maxDataLength */ Format.NOT_SPECIFIED,
                        Format.byteArray,
                        /* frameRate */ Format.NOT_SPECIFIED,
                        YUVFormat.YUV_420,
                        /* strideY */ Format.NOT_SPECIFIED,
                        /* strideUV */ Format.NOT_SPECIFIED,
                        /* offsetY */ Format.NOT_SPECIFIED,
                        /* offsetU */ Format.NOT_SPECIFIED,
                        /* offsetV */ Format.NOT_SPECIFIED)
            };

        inputFormat = null;
        outputFormat = null;
    }

    /**
     * Closes this <tt>Codec</tt>.
     */
    @Override
    public synchronized void close()
    {
        if (opened)
        {
            opened = false;
            super.close();

            if (avctx != 0)
            {
                FFmpeg.avcodec_close(avctx);
                FFmpeg.av_free(avctx);
                avctx = 0;
            }

            if (avFrame != 0)
            {
                FFmpeg.avcodec_free_frame(avFrame);
                avFrame = 0;
            }
            if (rawFrameBuffer != 0)
            {
                FFmpeg.av_free(rawFrameBuffer);
                rawFrameBuffer = 0;
            }

            if (keyFrameRequestee != null)
            {
                if (keyFrameControl != null)
                    keyFrameControl.removeKeyFrameRequestee(keyFrameRequestee);
                keyFrameRequestee = null;
            }
        }
    }

    /**
     * Gets the matching output formats for a specific format.
     *
     * @param inputFormat input format
     * @return array for formats matching input format
     */
    private Format[] getMatchingOutputFormats(Format inputFormat)
    {
        VideoFormat inputVideoFormat = (VideoFormat) inputFormat;

        String[] packetizationModes
            = (this.packetizationMode == null)
                ? new String[] { "0", "1" }
                : new String[] { this.packetizationMode };
        Format[] matchingOutputFormats = new Format[packetizationModes.length];
        Dimension size = inputVideoFormat.getSize();
        float frameRate = inputVideoFormat.getFrameRate();

        for (int index = packetizationModes.length - 1; index >= 0; index--)
        {
            matchingOutputFormats[index]
                = new ParameterizedVideoFormat(
                        Constants.H264,
                        size,
                        /* maxDataLength */ Format.NOT_SPECIFIED,
                        Format.byteArray,
                        frameRate,
                        ParameterizedVideoFormat.toMap(
                                PACKETIZATION_MODE_FMTP,
                                packetizationModes[index]));
        }
        return matchingOutputFormats;
    }

    /**
     * Gets the name of this <tt>Codec</tt>.
     *
     * @return codec name
     */
    @Override
    public String getName()
    {
        return PLUGIN_NAME;
    }

    /**
     * Returns the list of formats supported at the output.
     *
     * @param in input <tt>Format</tt> to determine corresponding output
     * <tt>Format</tt>s
     * @return array of formats supported at output
     */
    @Override
    public Format[] getSupportedOutputFormats(Format in)
    {
        Format[] supportedOutputFormats;

        // null input format
        if (in == null)
            supportedOutputFormats = SUPPORTED_OUTPUT_FORMATS;
        // mismatch input format
        else if (!(in instanceof VideoFormat)
                || (null == AbstractCodec2.matches(in, inputFormats)))
            supportedOutputFormats = new Format[0];
        else
            supportedOutputFormats = getMatchingOutputFormats(in);
        return supportedOutputFormats;
    }

    /**
     * Notifies this <tt>JNIEncoder</tt> that the remote peer has requested a
     * key frame from this local peer.
     *
     * @return <tt>true</tt> if this <tt>JNIEncoder</tt> has honored the request
     * for a key frame; otherwise, <tt>false</tt>
     */
    private boolean keyFrameRequest()
    {
        if (System.currentTimeMillis()
                > (lastKeyFrameRequestTime + PLI_INTERVAL))
        {
            lastKeyFrameRequestTime = System.currentTimeMillis();
            forceKeyFrame = true;
        }
        return true;
    }

    /**
     * Opens this <tt>Codec</tt>.
     */
    @Override
    public synchronized void open()
        throws ResourceUnavailableException
    {
        if (opened)
            return;

        VideoFormat inputVideoFormat = (VideoFormat) inputFormat;
        VideoFormat outputVideoFormat = (VideoFormat) outputFormat;

        /*
         * An Encoder translates raw media data in (en)coded media data.
         * Consequently, the size of the output is equal to the size of the
         * input.
         */
        Dimension size = null;

        if (inputVideoFormat != null)
            size = inputVideoFormat.getSize();
        if ((size == null) && (outputVideoFormat != null))
            size = outputVideoFormat.getSize();
        if (size == null)
        {
            throw new ResourceUnavailableException(
                    "The input video frame width and height are not set.");
        }

        int width = size.width, height = size.height;

        boolean useIntraRefresh = true;
        /*
         * XXX We do not currently negotiate the profile so, regardless of the
         * many AVCodecContext properties we have set above, force the default
         * profile configuration.
         */
        ConfigurationService cfg = LibJitsi.getConfigurationService();
        String profile
            = (cfg == null)
                ? null
                : cfg.getString(DEFAULT_PROFILE_PNAME, DEFAULT_DEFAULT_PROFILE);

        if(additionalSettings != null)
        {
            for(Map.Entry<String, String> e : additionalSettings.entrySet())
            {
                String k = e.getKey();
                String v = e.getValue();

                if("h264.intrarefresh".equals(k))
                {
                    if("false".equals(v))
                        useIntraRefresh = false;
                }
                else if("h264.profile".equals(k))
                {
                    if(MAIN_PROFILE.equals(v)
                       || BASELINE_PROFILE.equals(v)
                       || HIGH_PROFILE.equals(v))
                        profile = v;
                }
            }
        }

        long avcodec = FFmpeg.avcodec_find_encoder(FFmpeg.CODEC_ID_H264);

        avctx = FFmpeg.avcodec_alloc_context3(avcodec);

        FFmpeg.avcodeccontext_set_pix_fmt(avctx, FFmpeg.PIX_FMT_YUV420P);
        FFmpeg.avcodeccontext_set_size(avctx, width, height);

        FFmpeg.avcodeccontext_set_qcompress(avctx, 0.6f);

        int bitRate = NeomediaServiceUtils
                .getMediaServiceImpl()
                    .getDeviceConfiguration()
                        .getVideoBitrate() * 1000;
        int frameRate = Format.NOT_SPECIFIED;

        /* Allow the outputFormat to request a certain frameRate. */
        if (outputVideoFormat != null)
            frameRate = (int) outputVideoFormat.getFrameRate();
        /* Otherwise, output in the frameRate of the inputFormat. */
        if ((frameRate == Format.NOT_SPECIFIED) && (inputVideoFormat != null))
            frameRate = (int) inputVideoFormat.getFrameRate();
        if (frameRate == Format.NOT_SPECIFIED)
            frameRate = DEFAULT_FRAME_RATE;

        // average bit rate
        FFmpeg.avcodeccontext_set_bit_rate(avctx, bitRate);
        // so to be 1 in x264
        FFmpeg.avcodeccontext_set_bit_rate_tolerance(avctx,
                (bitRate / frameRate));
        FFmpeg.avcodeccontext_set_rc_max_rate(avctx, bitRate);
        FFmpeg.avcodeccontext_set_sample_aspect_ratio(avctx, 0, 0);
        FFmpeg.avcodeccontext_set_thread_count(avctx, 1);

        // time_base should be 1 / frame rate
        FFmpeg.avcodeccontext_set_time_base(avctx, 1, frameRate);
        FFmpeg.avcodeccontext_set_ticks_per_frame(avctx, 2);
        FFmpeg.avcodeccontext_set_quantizer(avctx, 30, 31, 4);

        // avctx.chromaoffset = -2;

        FFmpeg.avcodeccontext_set_mb_decision(avctx,
            FFmpeg.FF_MB_DECISION_SIMPLE);

        FFmpeg.avcodeccontext_set_rc_eq(avctx, "blurCplx^(1-qComp)");

        FFmpeg.avcodeccontext_add_flags(avctx,
                FFmpeg.CODEC_FLAG_LOOP_FILTER);
        if(useIntraRefresh)
        {
            /*
             * The flag is ignored in newer FFmpeg versions and we set the
             * "intra-refresh" option for them. Anyway, the flag is set for the
             * older FFmpeg versions.
             */
            FFmpeg.avcodeccontext_add_flags2(avctx,
                FFmpeg.CODEC_FLAG2_INTRA_REFRESH);
        }
        FFmpeg.avcodeccontext_set_me_method(avctx, 7);
        FFmpeg.avcodeccontext_set_me_subpel_quality(avctx, 2);
        FFmpeg.avcodeccontext_set_me_range(avctx, 16);
        FFmpeg.avcodeccontext_set_me_cmp(avctx, FFmpeg.FF_CMP_CHROMA);
        FFmpeg.avcodeccontext_set_scenechange_threshold(avctx, 40);
        FFmpeg.avcodeccontext_set_rc_buffer_size(avctx, 10);
        FFmpeg.avcodeccontext_set_gop_size(avctx, IFRAME_INTERVAL);
        FFmpeg.avcodeccontext_set_i_quant_factor(avctx, 1f / 1.4f);

        FFmpeg.avcodeccontext_set_refs(avctx, 1);
        //FFmpeg.avcodeccontext_set_trellis(avctx, 2);

        FFmpeg.avcodeccontext_set_keyint_min(avctx, 0);

        if ((null == packetizationMode) || "0".equals(packetizationMode))
        {
            FFmpeg.avcodeccontext_set_rtp_payload_size(avctx,
                    Packetizer.MAX_PAYLOAD_SIZE);
        }

        try
        {
            FFmpeg.avcodeccontext_set_profile(avctx,
                getProfileForConfig(profile));
        }
        catch (UnsatisfiedLinkError ule)
        {
            logger.warn("The FFmpeg JNI library is out-of-date.");
        }

        String preset
            = (cfg == null)
                ? DEFAULT_PRESET
                : cfg.getString(PRESET_PNAME, DEFAULT_PRESET);

        if (FFmpeg.avcodec_open2(
                    avctx,
                    avcodec,
                    /*
                     * XXX crf=0 means lossless coding which is not supported by
                     * the baseline and main profiles. Consequently, we cannot
                     * specify it because we specify either the baseline or the
                     * main profile. Otherwise, x264 will detect the
                     * inconsistency in the specified parameters/options and
                     * FFmpeg will fail.
                     */
                    //"crf" /* constant quality mode, constant ratefactor */, "0",
                    "intra-refresh", useIntraRefresh ? "1" : "0",
                    "partitions", "b8x8,i4x4,p8x8",
                    "preset", preset,
                    "thread_type", "slice",
                    "tune", "zerolatency")
                < 0)
        {
            throw new ResourceUnavailableException(
                    "Could not open codec."
                        + " (size= " + width + "x" + height + ")");
        }

        rawFrameLen = (width * height * 3) / 2;
        rawFrameBuffer = FFmpeg.av_malloc(rawFrameLen);
        avFrame = FFmpeg.avcodec_alloc_frame();

        int sizeInBytes = width * height;

        FFmpeg.avframe_set_data(
                avFrame,
                rawFrameBuffer,
                sizeInBytes,
                sizeInBytes / 4);
        FFmpeg.avframe_set_linesize(avFrame, width, width / 2, width / 2);

        /*
         * Implement the ability to have the remote peer request key frames from
         * this local peer.
         */
        if (keyFrameRequestee == null)
        {
            keyFrameRequestee
                = new KeyFrameControl.KeyFrameRequestee()
                {
                    public boolean keyFrameRequest()
                    {
                        return JNIEncoder.this.keyFrameRequest();
                    }
                };
        }
        if (keyFrameControl != null)
            keyFrameControl.addKeyFrameRequestee(-1, keyFrameRequestee);

        opened = true;
        super.open();
    }

    /**
     * Processes/encodes a buffer.
     *
     * @param inBuffer input buffer
     * @param outBuffer output buffer
     * @return <tt>BUFFER_PROCESSED_OK</tt> if buffer has been successfully
     * processed
     */
    @Override
    public synchronized int process(Buffer inBuffer, Buffer outBuffer)
    {
        if (isEOM(inBuffer))
        {
            propagateEOM(outBuffer);
            reset();
            return BUFFER_PROCESSED_OK;
        }
        if (inBuffer.isDiscard())
        {
            outBuffer.setDiscard(true);
            reset();
            return BUFFER_PROCESSED_OK;
        }

        Format inFormat = inBuffer.getFormat();

        if ((inFormat != inputFormat) && !inFormat.equals(inputFormat))
            setInputFormat(inFormat);

        if (inBuffer.getLength() < 10)
        {
            outBuffer.setDiscard(true);
            reset();
            return BUFFER_PROCESSED_OK;
        }

        // copy data to avframe
        FFmpeg.memcpy(
                rawFrameBuffer,
                (byte[]) inBuffer.getData(), inBuffer.getOffset(),
                rawFrameLen);

        if (/* framesSinceLastIFrame >= IFRAME_INTERVAL || */ forceKeyFrame)
        {
            FFmpeg.avframe_set_key_frame(avFrame, true);
            framesSinceLastIFrame = 0;

            /* send keyframe for the first two frames */
            if(secondKeyFrame)
            {
                secondKeyFrame = false;
                forceKeyFrame = true;
            }
            else
            {
                forceKeyFrame = false;
            }
        }
        else
        {
            framesSinceLastIFrame++;
            FFmpeg.avframe_set_key_frame(avFrame, false);
        }

        /*
         * Do not always allocate a new data array for outBuffer, try to reuse
         * the existing one if it is suitable.
         */
        Object outData = outBuffer.getData();
        byte[] out;

        if (outData instanceof byte[])
        {
            out = (byte[]) outData;
            if (out.length < rawFrameLen)
                out = null;
        }
        else
            out = null;
        if (out == null)
        {
            out = new byte[rawFrameLen];
            outBuffer.setData(out);
        }

        // encode data
        int outputLength
            = FFmpeg.avcodec_encode_video(avctx, out, out.length, avFrame);

        outBuffer.setLength(outputLength);
        outBuffer.setOffset(0);
        outBuffer.setTimeStamp(inBuffer.getTimeStamp());
        return BUFFER_PROCESSED_OK;
    }

    /**
     * Notifies this <tt>RTCPFeedbackListener</tt> that an RTCP feedback message
     * has been received
     *
     * @param event an <tt>RTCPFeedbackEvent</tt> which specifies the details of
     * the notification event such as the feedback message type and the payload
     * type
     */
    public void rtcpFeedbackReceived(RTCPFeedbackEvent event)
    {
        /*
         * If RTCP message is a Picture Loss Indication (PLI) or a Full
         * Intra-frame Request (FIR) the encoder will force the next frame to be
         * a keyframe.
         */
        if (event.getPayloadType() == RTCPFeedbackEvent.PT_PS)
        {
            switch (event.getFeedbackMessageType())
            {
                case RTCPFeedbackEvent.FMT_PLI:
                case RTCPFeedbackEvent.FMT_FIR:
                    if (logger.isTraceEnabled())
                    {
                        logger.trace("Scheduling a key-frame, because we" +
                                " received an RTCP PLI or FIR.");
                    }
                    keyFrameRequest();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Sets additional settings for the codec.
     *
     * @param settings additional settings
     */
    public void setAdditionalCodecSettings(Map<String, String> settings)
    {
        additionalSettings = settings;
    }

    /**
     * Sets the <tt>Format</tt> of the media data to be input to this
     * <tt>Codec</tt>.
     *
     * @param format the <tt>Format</tt> of media data to set on this
     * <tt>Codec</tt>
     * @return the <tt>Format</tt> of media data set on this <tt>Codec</tt> or
     * <tt>null</tt> if the specified <tt>format</tt> is not supported by this
     * <tt>Codec</tt>
     */
    @Override
    public Format setInputFormat(Format format)
    {
        // mismatch input format
        if (!(format instanceof VideoFormat)
                || (null == AbstractCodec2.matches(format, inputFormats)))
            return null;

        YUVFormat yuvFormat = (YUVFormat) format;

        if (yuvFormat.getOffsetU() > yuvFormat.getOffsetV())
            return null;

        inputFormat = AbstractCodec2.specialize(yuvFormat, Format.byteArray);

        // Return the selected inputFormat
        return inputFormat;
    }

    /**
     * Sets the <tt>KeyFrameControl</tt> to be used by this
     * <tt>JNIEncoder</tt> as a means of control over its key frame-related
     * logic.
     *
     * @param keyFrameControl the <tt>KeyFrameControl</tt> to be used by this
     * <tt>JNIEncoder</tt> as a means of control over its key frame-related
     * logic
     */
    public void setKeyFrameControl(KeyFrameControl keyFrameControl)
    {
        if (this.keyFrameControl != keyFrameControl)
        {
            if ((this.keyFrameControl != null) && (keyFrameRequestee != null))
                this.keyFrameControl.removeKeyFrameRequestee(keyFrameRequestee);

            this.keyFrameControl = keyFrameControl;

            if ((this.keyFrameControl != null) && (keyFrameRequestee != null))
                this.keyFrameControl.addKeyFrameRequestee(-1, keyFrameRequestee);
        }
    }

    /**
     * Sets the <tt>Format</tt> in which this <tt>Codec</tt> is to output media
     * data.
     *
     * @param format the <tt>Format</tt> in which this <tt>Codec</tt> is to
     * output media data
     * @return the <tt>Format</tt> in which this <tt>Codec</tt> is currently
     * configured to output media data or <tt>null</tt> if <tt>format</tt> was
     * found to be incompatible with this <tt>Codec</tt>
     */
    @Override
    public Format setOutputFormat(Format format)
    {
        // mismatch output format
        if (!(format instanceof VideoFormat)
                || (null
                        == AbstractCodec2.matches(
                                format,
                                getMatchingOutputFormats(inputFormat))))
            return null;

        VideoFormat videoFormat = (VideoFormat) format;
        /*
         * An Encoder translates raw media data in (en)coded media data.
         * Consequently, the size of the output is equal to the size of the
         * input.
         */
        Dimension size = null;

        if (inputFormat != null)
            size = ((VideoFormat) inputFormat).getSize();
        if ((size == null) && format.matches(outputFormat))
            size = ((VideoFormat) outputFormat).getSize();

        Map<String, String> fmtps = null;

        if (format instanceof ParameterizedVideoFormat)
            fmtps = ((ParameterizedVideoFormat) format).getFormatParameters();
        if (fmtps == null)
            fmtps = new HashMap<String, String>();
        if (packetizationMode != null)
            fmtps.put(PACKETIZATION_MODE_FMTP, packetizationMode);

        outputFormat
            = new ParameterizedVideoFormat(
                    videoFormat.getEncoding(),
                    size,
                    /* maxDataLength */ Format.NOT_SPECIFIED,
                    Format.byteArray,
                    videoFormat.getFrameRate(),
                    fmtps);

        // Return the selected outputFormat
        return outputFormat;
    }

    /**
     * Sets the packetization mode to be used for the H.264 RTP payload output
     * by this <tt>JNIEncoder</tt> and the associated packetizer.
     *
     * @param packetizationMode the packetization mode to be used for the H.264
     * RTP payload output by this <tt>JNIEncoder</tt> and the associated
     * packetizer
     */
    public void setPacketizationMode(String packetizationMode)
    {
        /*
         * RFC 3984 "RTP Payload Format for H.264 Video" says that "[w]hen the
         * value of packetization-mode is equal to 0 or packetization-mode is
         * not present, the single NAL mode, as defined in section 6.2 of RFC
         * 3984, MUST be used."
         */
        if ((packetizationMode == null) || "0".equals(packetizationMode))
            this.packetizationMode = "0";
        else if ("1".equals(packetizationMode))
            this.packetizationMode = "1";
        else
            throw new IllegalArgumentException("packetizationMode");
    }

    /**
     * Checks the configuration and returns the profile to use.
     * @param profile the profile setting.
     * @return the profile FFmpeg to use.
     */
    private static int getProfileForConfig(String profile)
    {
        if(BASELINE_PROFILE.equalsIgnoreCase(profile))
            return FFmpeg.FF_PROFILE_H264_BASELINE;
        else if(HIGH_PROFILE.equalsIgnoreCase(profile))
            return FFmpeg.FF_PROFILE_H264_HIGH;
        else
            return FFmpeg.FF_PROFILE_H264_MAIN;
    }
}
