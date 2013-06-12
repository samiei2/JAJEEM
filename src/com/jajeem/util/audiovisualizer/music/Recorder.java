/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

package com.jajeem.util.audiovisualizer.music;

import java.util.List;
import com.jajeem.util.audiovisualizer.music.data.DataFrame;

/**
 *
 * @author michael
 */
public interface Recorder extends Component {

    void addFrame(DataFrame frame);

    DataFrame getLastFrame();

    List<DataFrame> popFrames();

}
