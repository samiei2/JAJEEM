/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.utils.swing;

import java.awt.*;

/**
 * This interface provides a set of methods that should be added into components that support custom window methods.
 *
 * @param <W> window type
 * @author Mikle Garin
 * @see SwingMethods
 * @see com.alee.utils.WindowUtils
 */

public interface WindowMethods<W extends Window> extends SwingMethods
{
    /**
     * Sets window opaque if that option is supported by the underlying system.
     *
     * @param opaque whether should make window opaque or not
     */
    public W setWindowOpaque ( boolean opaque );

    /**
     * Returns whether window is opaque or not.
     *
     * @return true if window is opaque, false otherwise
     */
    public boolean isWindowOpaque ();

    /**
     * Sets window opacity if that option is supported by the underlying system.
     *
     * @param opacity new window opacity
     */
    public W setWindowOpacity ( float opacity );

    /**
     * Returns window opacity.
     *
     * @return window opacity
     */
    public float getWindowOpacity ();

    /**
     * Centers window relative to screen center.
     *
     * @return processed window
     */
    public W center ();

    /**
     * Centers window relative to the specified component center.
     *
     * @param relativeTo component used to center window
     * @return processed window
     */
    public W center ( Component relativeTo );

    /**
     * Changes window size and centers it relative to screen center.
     *
     * @param width  new window width
     * @param height new window height
     * @return processed window
     */
    public W center ( int width, int height );

    /**
     * Changes window size and centers it relative to the specified component center.
     *
     * @param relativeTo component used to center window
     * @param width      new window width
     * @param height     new window height
     * @return processed window
     */
    public W center ( Component relativeTo, int width, int height );

    /**
     * Packs window to its preffered height and specified width.
     *
     * @param width
     * @return processed window
     */
    public W packToWidth ( int width );

    /**
     * Packs window to its preffered width and specified height.
     *
     * @param height
     * @return processed window
     */
    public W packToHeight ( int height );

    /**
     * Packs and centers window relative to old position.
     *
     * @return processed window
     */
    public W packAndCenter ();

    /**
     * Packs and centers window relative to old position.
     *
     * @param animate whether should animate window size changes or not
     * @return processed window
     */
    public W packAndCenter ( boolean animate );
}