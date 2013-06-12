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
public interface Engine extends Component, AudioListener<AudioAvailableEvent>
{

    public void start();
    
    /**
     * Causes the engine to do one full processing
     * iteration.
     */
    public void update();
}
