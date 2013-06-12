/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.music.data.calc;

/**
 *
 * @author michael
 */
public interface Smoother {

    double getValue();

    double newValue(double value);

}
