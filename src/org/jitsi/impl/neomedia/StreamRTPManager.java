/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.neomedia;

import java.io.IOException;
import java.util.Vector;

import javax.media.Format;
import javax.media.format.UnsupportedFormatException;
import javax.media.protocol.DataSource;
import javax.media.rtp.GlobalReceptionStats;
import javax.media.rtp.GlobalTransmissionStats;
import javax.media.rtp.RTPConnector;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.RemoteListener;
import javax.media.rtp.SendStream;
import javax.media.rtp.SendStreamListener;
import javax.media.rtp.SessionListener;

import org.jitsi.service.neomedia.RTPTranslator;

import com.sun.media.rtp.RTPSessionMgr;

/**
 * Implements the <tt>RTPManager</tt> interface as used by a
 * <tt>MediaStream</tt>.
 * 
 * @author Lyubomir Marinov
 */
public class StreamRTPManager {
	/**
	 * The <tt>RTPManager</tt> this instance is to delegate to when it is not
	 * attached to an <tt>RTPTranslator</tt>.
	 */
	private final RTPManager manager;

	/**
	 * The <tt>RTPTranslator</tt> which this instance is attached to and which
	 * forwards the RTP and RTCP flows of the <tt>MediaStream</tt> associated
	 * with this instance to other <tt>MediaStream</tt>s.
	 */
	private final RTPTranslatorImpl translator;

	/**
	 * Initializes a new <tt>StreamRTPManager</tt> instance which is,
	 * optionally, attached to a specific <tt>RTPTranslator</tt> which is to
	 * forward the RTP and RTCP flows of the associated <tt>MediaStream</tt> to
	 * other <tt>MediaStream</tt>s.
	 * 
	 * @param translator
	 *            the <tt>RTPTranslator</tt> to attach the new instance to or
	 *            <tt>null</tt> if the new instance is to not be attached to any
	 *            <tt>RTPTranslator</tt>
	 */
	public StreamRTPManager(RTPTranslator translator) {
		this.translator = (RTPTranslatorImpl) translator;

		manager = (this.translator == null) ? RTPManager.newInstance() : null;
	}

	public void addFormat(Format format, int payloadType) {
		if (translator == null) {
			manager.addFormat(format, payloadType);
		} else {
			translator.addFormat(this, format, payloadType);
		}
	}

	public void addReceiveStreamListener(ReceiveStreamListener listener) {
		if (translator == null) {
			manager.addReceiveStreamListener(listener);
		} else {
			translator.addReceiveStreamListener(this, listener);
		}
	}

	public void addRemoteListener(RemoteListener listener) {
		if (translator == null) {
			manager.addRemoteListener(listener);
		} else {
			translator.addRemoteListener(this, listener);
		}
	}

	public void addSendStreamListener(SendStreamListener listener) {
		if (translator == null) {
			manager.addSendStreamListener(listener);
		} else {
			translator.addSendStreamListener(this, listener);
		}
	}

	public void addSessionListener(SessionListener listener) {
		if (translator == null) {
			manager.addSessionListener(listener);
		} else {
			translator.addSessionListener(this, listener);
		}
	}

	public SendStream createSendStream(DataSource dataSource, int streamIndex)
			throws IOException, UnsupportedFormatException {
		if (translator == null) {
			return manager.createSendStream(dataSource, streamIndex);
		} else {
			return translator.createSendStream(this, dataSource, streamIndex);
		}
	}

	public void dispose() {
		if (translator == null) {
			manager.dispose();
		} else {
			translator.dispose(this);
		}
	}

	/**
	 * Gets a control of a specific type over this instance. Invokes
	 * {@link #getControl(String)}.
	 * 
	 * @param controlType
	 *            a <tt>Class</tt> which specifies the type of the control over
	 *            this instance to get
	 * @return a control of the specified <tt>controlType</tt> over this
	 *         instance if this instance supports such a control; otherwise,
	 *         <tt>null</tt>
	 */
	@SuppressWarnings("unchecked")
	public <T> T getControl(Class<T> controlType) {
		return (T) getControl(controlType.getName());
	}

	/**
	 * Gets a control of a specific type over this instance.
	 * 
	 * @param controlType
	 *            a <tt>String</tt> which specifies the type (i.e. the name of
	 *            the class) of the control over this instance to get
	 * @return a control of the specified <tt>controlType</tt> over this
	 *         instance if this instance supports such a control; otherwise,
	 *         <tt>null</tt>
	 */
	public Object getControl(String controlType) {
		if (translator == null) {
			return manager.getControl(controlType);
		} else {
			return translator.getControl(this, controlType);
		}
	}

	public GlobalReceptionStats getGlobalReceptionStats() {
		if (translator == null) {
			return manager.getGlobalReceptionStats();
		} else {
			return translator.getGlobalReceptionStats(this);
		}
	}

	public GlobalTransmissionStats getGlobalTransmissionStats() {
		if (translator == null) {
			return manager.getGlobalTransmissionStats();
		} else {
			return translator.getGlobalTransmissionStats(this);
		}
	}

	public long getLocalSSRC() {
		if (translator == null) {
			return ((RTPSessionMgr) manager).getLocalSSRC();
		} else {
			return translator.getLocalSSRC(this);
		}
	}

	@SuppressWarnings("rawtypes")
	public Vector getReceiveStreams() {
		if (translator == null) {
			return manager.getReceiveStreams();
		} else {
			return translator.getReceiveStreams(this);
		}
	}

	@SuppressWarnings("rawtypes")
	public Vector getSendStreams() {
		if (translator == null) {
			return manager.getSendStreams();
		} else {
			return translator.getSendStreams(this);
		}
	}

	public void initialize(RTPConnector connector) {
		if (translator == null) {
			manager.initialize(connector);
		} else {
			translator.initialize(this, connector);
		}
	}

	public void removeReceiveStreamListener(ReceiveStreamListener listener) {
		if (translator == null) {
			manager.removeReceiveStreamListener(listener);
		} else {
			translator.removeReceiveStreamListener(this, listener);
		}
	}

	public void removeRemoteListener(RemoteListener listener) {
		if (translator == null) {
			manager.removeRemoteListener(listener);
		} else {
			translator.removeRemoteListener(this, listener);
		}
	}

	public void removeSendStreamListener(SendStreamListener listener) {
		if (translator == null) {
			manager.removeSendStreamListener(listener);
		} else {
			translator.removeSendStreamListener(this, listener);
		}
	}

	public void removeSessionListener(SessionListener listener) {
		if (translator == null) {
			manager.removeSessionListener(listener);
		} else {
			translator.removeSessionListener(this, listener);
		}
	}
}
