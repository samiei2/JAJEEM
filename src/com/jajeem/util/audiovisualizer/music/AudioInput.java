/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.music;

import com.jajeem.util.audiovisualizer.dispatch.AudioAvailableEvent;
import com.jajeem.util.audiovisualizer.dispatch.AudioListener;

/**
 *
 * @author michael
 */
public interface AudioInput extends Component {

    public void addListener(AudioListener<AudioAvailableEvent> listener);

    public long samplesToNanoTime(long samples);
    public double samplesToTime(long samples);
    public double timeToSamples(double time);
}
