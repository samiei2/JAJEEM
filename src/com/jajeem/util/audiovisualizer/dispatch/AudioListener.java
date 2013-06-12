/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.dispatch;

/**
 *
 * @author michael
 */
public interface AudioListener<T extends AudioEvent> {

    void eventFired(T event, Object userData);
    
}
