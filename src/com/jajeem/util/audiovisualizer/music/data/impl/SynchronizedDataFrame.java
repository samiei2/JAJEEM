/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.music.data.impl;

import com.jajeem.util.audiovisualizer.music.data.ACF;
import com.jajeem.util.audiovisualizer.music.data.AMDF;
import com.jajeem.util.audiovisualizer.music.data.DataFrame;
import com.jajeem.util.audiovisualizer.music.data.FFT;
import com.jajeem.util.audiovisualizer.music.data.PS;
import com.jajeem.util.audiovisualizer.music.data.PitchInfo;
import com.jajeem.util.audiovisualizer.music.data.QualityInfo;
import com.jajeem.util.audiovisualizer.music.data.SDF;
import com.jajeem.util.audiovisualizer.music.data.SM;
import com.jajeem.util.audiovisualizer.music.data.SNAC;
import com.jajeem.util.audiovisualizer.music.data.WACF;
import com.jajeem.util.audiovisualizer.music.data.WSNAC;
import com.jajeem.util.audiovisualizer.music.data.Wav;

/**
 *
 * @author michael
 */
public class SynchronizedDataFrame implements DataFrame {

    private DataFrame frame;

    public SynchronizedDataFrame(DataFrame frame)
    {
        this.frame = frame;
    }

    public long getSessionID() {
        return frame.getSessionID();
    }

    public long getTime() {
        return frame.getTime();
    }

    public Wav getWav() {
        return frame.getWav();
    }

    public synchronized PS getPS() {
        return frame.getPS();
    }

    public synchronized FFT getFFT() {
        return frame.getFFT();
    }

    public synchronized WACF getWACF() {
        return frame.getWACF();
    }

    public synchronized ACF getACF() {
        return frame.getACF();
    }

    public synchronized SNAC getSNAC() {
        return frame.getSNAC();
    }

    public synchronized WSNAC getWSNAC() {
        return frame.getWSNAC();
    }

    public synchronized SDF getSDF() {
        return frame.getSDF();
    }

    public synchronized AMDF getAMDF() {
        return frame.getAMDF();
    }

    public int compareTo(Long o) {
        return frame.compareTo(o);
    }

    public synchronized PS getWPS() {
        return frame.getWPS();
    }

    public synchronized FFT getWFFT() {
        return frame.getWFFT();
    }

    public synchronized SM getSM() {
        return frame.getSM();
    }

    public synchronized PitchInfo getPitchInfo() {
        return frame.getPitchInfo();
    }

    public synchronized QualityInfo getQualityInfo() {
        return frame.getQualityInfo();
    }

}
