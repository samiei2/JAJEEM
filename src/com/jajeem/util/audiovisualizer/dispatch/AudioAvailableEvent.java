/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.dispatch;

import com.jajeem.util.audiovisualizer.buffer.DataReference;

/**
 *
 * @author michael
 */
public class AudioAvailableEvent extends AudioEvent {

    private DataReference<Double> audio;
    public AudioAvailableEvent(DataReference<Double> audio)
    {
        super();

        this.audio = audio;
    }

    public DataReference<Double> getAudioData()
    {
        return audio;
    }
}
