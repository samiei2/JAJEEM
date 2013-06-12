/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.music.data;

/**
 *
 * @author michael
 */
public interface QualityInfo {

    double getPrimaryCorrelation();
    double getSmoothedCorrelation();

    double getSpectralFlux();
    double getSmoothedFlux();

    double getSpectralCentroidFrequency();
    double getSmoothedSpectralCentroidFrequency();
}
