/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.music.data.impl;

import com.jajeem.util.audiovisualizer.music.data.DataFrame;
import com.jajeem.util.audiovisualizer.music.data.FFT;
import com.jajeem.util.audiovisualizer.music.data.Wav;

/**
 *
 * @author michael
 */
public class DataFactory {

    public static DataFrame newDataFrame(Wav wav, long time, long sessionID) {
        return new SynchronizedDataFrame(new DefaultDataFrame(wav, time, sessionID));
    }

    public static DataFrame newDataFrame(FFT fft, long time, long sessionID) {
        return new SynchronizedDataFrame(new DefaultDataFrame(fft, time, sessionID));
    }

    public static Wav newWav(double[] data, double sampleRate) {
        return new DefaultWav(data, sampleRate);
    }

    public static FFT newFFT(double[] complexData, int n, double sampleRate) {
        return new jtFFT(n, sampleRate, complexData);
    }
}
