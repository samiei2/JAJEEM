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

package com.alee.extended.painter;

import com.alee.utils.LafUtils;
import com.alee.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

/**
 * Titled border painter.
 *
 * @param <E> component type
 * @author Mikle Garin
 * @see BorderPainter
 * @see AbstractPainter
 * @see Painter
 */

public class TitledBorderPainter<E extends JComponent> extends BorderPainter<E> implements SwingConstants
{
    /**
     * todo 1. Left/Right title position
     * todo 2. Icon for title text
     * todo 3. Take title position into account when calculating preferred size & border
     */

    /**
     * Title side offset.
     */
    protected int titleOffset = 4;

    /**
     * Gap between title and border line.
     */
    protected int titleBorderGap = 3;

    /**
     * Title position relative to border.
     */
    protected TitlePosition titlePosition = TitlePosition.onLine;

    /**
     * Title foreground.
     */
    protected Color foreground = null;

    /**
     * Painter background.
     */
    protected Color background = null;

    /**
     * Whether to clip background under the title or not.
     */
    protected boolean clipTitleBackground = true;

    /**
     * Title text alignment.
     */
    protected int titleAlignment;

    /**
     * Title text display side.
     */
    protected int titleSide;

    /**
     * Title text.
     */
    protected String titleText;

    /**
     * Runtime variables.
     */
    protected int w;
    protected int h;
    protected boolean emptyTitle;
    protected FontMetrics fontMetrics;
    protected int titleAreaHeight;
    protected int titleWidth;
    protected int titleX;
    protected int titleY;
    protected double borderCenter;
    protected double borderPosition;
    protected Shape borderShape;
    protected boolean doClip;

    public TitledBorderPainter ()
    {
        this ( null );
    }

    public TitledBorderPainter ( final String titleText )
    {
        this ( titleText, LEADING );
    }

    public TitledBorderPainter ( final String titleText, final int titleAlignment )
    {
        this ( titleText, titleAlignment, TOP );
    }

    public TitledBorderPainter ( final String titleText, final int titleAlignment, final int titleSide )
    {
        super ();
        this.titleText = titleText;
        this.titleAlignment = titleAlignment;
        this.titleSide = titleSide;
    }

    public int getTitleOffset ()
    {
        return titleOffset;
    }

    public void setTitleOffset ( final int titleOffset )
    {
        this.titleOffset = titleOffset;
        fireUpdate ();
    }

    public int getTitleBorderGap ()
    {
        return titleBorderGap;
    }

    public void setTitleBorderGap ( final int titleBorderGap )
    {
        this.titleBorderGap = titleBorderGap;
        fireUpdate ();
    }

    public int getTitleSide ()
    {
        return titleSide;
    }

    public void setTitleSide ( final int titleSide )
    {
        this.titleSide = titleSide;
        fireUpdate ();
    }

    public int getTitleAlignment ()
    {
        return titleAlignment;
    }

    public void setTitleAlignment ( final int titleAlignment )
    {
        this.titleAlignment = titleAlignment;
        fireRepaint ();
    }

    public TitlePosition getTitlePosition ()
    {
        return titlePosition;
    }

    public void setTitlePosition ( final TitlePosition titlePosition )
    {
        this.titlePosition = titlePosition;
        fireRepaint ();
    }

    public Color getForeground ()
    {
        return foreground;
    }

    public void setForeground ( final Color foreground )
    {
        this.foreground = foreground;
        fireRepaint ();
    }

    public Color getBackground ()
    {
        return background;
    }

    public void setBackground ( final Color background )
    {
        this.background = background;
        fireRepaint ();
    }

    public boolean isClipTitleBackground ()
    {
        return clipTitleBackground;
    }

    public void setClipTitleBackground ( final boolean clipTitleBackground )
    {
        this.clipTitleBackground = clipTitleBackground;
        fireRepaint ();
    }

    public String getTitleText ()
    {
        return titleText;
    }

    public void setTitleText ( final String titleText )
    {
        this.titleText = titleText;
        fireUpdate ();
    }

    @Override
    public Insets getMargin ( final E c )
    {
        final Insets m = super.getMargin ( c );
        if ( !isEmptyTitle () )
        {
            switch ( titleSide )
            {
                case TOP:
                {
                    m.top += getTitleAreaHeight ( c );
                    break;
                }
                case LEFT:
                {
                    m.left += getTitleAreaHeight ( c );
                    break;
                }
                case BOTTOM:
                {
                    m.bottom += getTitleAreaHeight ( c );
                    break;
                }
                case RIGHT:
                {
                    m.right += getTitleAreaHeight ( c );
                    break;
                }
            }
        }
        return m;
    }

    @Override
    public Dimension getPreferredSize ( final E c )
    {
        if ( isEmptyTitle () )
        {
            return super.getPreferredSize ( c );
        }
        else
        {
            final int titleAreaHeight = getTitleAreaHeight ( c );
            final int titleWidth = c.getFontMetrics ( c.getFont () ).stringWidth ( titleText );
            final int border = Math.max ( width, round );
            final int title = Math.max ( titleAreaHeight, border );
            switch ( titleSide )
            {
                case TOP:
                case BOTTOM:
                {
                    return new Dimension ( border * 2 + titleWidth + titleOffset * 2 +
                            titleBorderGap * 2, title + border );
                }
                case LEFT:
                case RIGHT:
                {
                    return new Dimension ( title + border, border * 2 + titleWidth + titleOffset * 2 +
                            titleBorderGap * 2 );
                }
            }
            return null;
        }
    }

    @Override
    public void paint ( final Graphics2D g2d, final Rectangle bounds, final E c )
    {
        // Initializing values
        w = c.getWidth ();
        h = c.getHeight ();
        emptyTitle = isEmptyTitle ();
        fontMetrics = emptyTitle ? null : c.getFontMetrics ( c.getFont () );
        titleWidth = emptyTitle ? 0 : fontMetrics.stringWidth ( titleText );
        titleAreaHeight = getTitleAreaHeight ( c );
        titleX = getTitleX ( c );
        titleY = getTitleY ();
        borderCenter = ( double ) width / 2;
        borderPosition = getBorderPosition ();
        borderShape = getBorderShape ();
        doClip = clipTitleBackground && !emptyTitle && titlePosition.equals ( TitlePosition.onLine );

        // Drawing border and background

        final Object aa = LafUtils.setupAntialias ( g2d );
        final Stroke os = LafUtils.setupStroke ( g2d, stroke, stroke != null );

        // Drawing background when title is not on the border line
        if ( background != null && !doClip )
        {
            g2d.setPaint ( background );
            g2d.fill ( borderShape );
        }

        // Properly clipping border line for text space
        final Shape clipShape = doClip ? getBorderClipShape () : null;
        final Shape oldClip = LafUtils.subtractClip ( g2d, clipShape, doClip );

        // Drawing clipped by text background
        if ( background != null && doClip )
        {
            g2d.setPaint ( background );
            g2d.fill ( borderShape );
        }

        // Drawing clipped border
        g2d.setPaint ( color );
        g2d.draw ( borderShape );

        LafUtils.restoreClip ( g2d, oldClip, doClip );
        LafUtils.restoreStroke ( g2d, os, stroke != null );
        LafUtils.restoreAntialias ( g2d, aa );

        // Drawing text
        if ( !emptyTitle )
        {
            g2d.setPaint ( foreground != null ? foreground : c.getForeground () );
            switch ( titleSide )
            {
                case TOP:
                case BOTTOM:
                {
                    Map hints = SwingUtils.setupTextAntialias ( g2d );
                    g2d.drawString ( titleText, titleX, titleY );
                    SwingUtils.restoreTextAntialias ( g2d, hints );
                    break;
                }
                case LEFT:
                case RIGHT:
                {
                    // todo
                    break;
                }
            }
        }
    }

    protected int getTitleX ( final E c )
    {
        final boolean ltr = c.getComponentOrientation ().isLeftToRight ();
        if ( titleAlignment == LEFT || titleAlignment == LEADING && ltr ||
                titleAlignment == TRAILING && !ltr )
        {
            return Math.max ( width, round ) + titleOffset + titleBorderGap;
        }
        else if ( titleAlignment == RIGHT || titleAlignment == TRAILING && ltr ||
                titleAlignment == LEADING && !ltr )
        {
            return w - Math.max ( width, round ) - titleOffset - titleBorderGap -
                    titleWidth;
        }
        else
        {
            return w / 2 - titleWidth / 2;
        }
    }

    protected int getTitleY ()
    {
        final int fontDescent = fontMetrics != null ? fontMetrics.getDescent () : 0;
        switch ( titleSide )
        {
            case TOP:
            {
                return titlePosition.equals ( TitlePosition.aboveLive ) ? titleAreaHeight - width - fontDescent :
                        titleAreaHeight - fontDescent;
            }
            case BOTTOM:
            {
                return titlePosition.equals ( TitlePosition.belowLine ) ? h - width - fontDescent : h - fontDescent;
            }
            case LEFT:
            {
                // todo
                break;
            }
            case RIGHT:
            {
                // todo
                break;
            }
        }
        return 0;
    }

    protected double getBorderPosition ()
    {
        if ( emptyTitle )
        {
            return borderCenter;
        }
        else
        {
            if ( titlePosition.equals ( TitlePosition.onLine ) )
            {
                return ( double ) titleAreaHeight / 2;
            }
            else if ( titlePosition.equals ( TitlePosition.aboveLive ) )
            {
                return titleAreaHeight - borderCenter;
            }
            else
            {
                return borderCenter;
            }
        }
    }

    protected int getTitleAreaHeight ( final E c )
    {
        if ( isEmptyTitle () )
        {
            return width;
        }
        else
        {
            int height = c.getFontMetrics ( c.getFont () ).getHeight ();
            return titlePosition.equals ( TitlePosition.onLine ) ? height : height + width;
        }
    }

    protected boolean isEmptyTitle ()
    {
        return titleText == null;
    }

    protected Shape getBorderShape ()
    {
        Rectangle2D rect = null;
        switch ( titleSide )
        {
            case TOP:
            {
                rect = new Rectangle2D.Double ( borderCenter, borderPosition, w - borderCenter * 2, h - borderPosition - borderCenter );
                break;
            }
            case LEFT:
            {
                rect = new Rectangle2D.Double ( borderPosition, borderCenter, w - borderPosition - borderCenter, h - borderCenter * 2 );
                break;
            }
            case BOTTOM:
            {
                rect = new Rectangle2D.Double ( borderCenter, borderCenter, w - borderCenter * 2, h - borderPosition - borderCenter );
                break;
            }
            case RIGHT:
            {
                rect = new Rectangle2D.Double ( borderCenter, borderCenter, w - borderPosition - borderCenter, h - borderCenter * 2 );
                break;
            }
        }
        return round > 0 ?
                new RoundRectangle2D.Double ( rect.getX (), rect.getY (), rect.getWidth (), rect.getHeight (), round * 2, round * 2 ) :
                rect;
    }

    protected Shape getBorderClipShape ()
    {
        if ( emptyTitle )
        {
            return null;
        }
        else
        {
            switch ( titleSide )
            {
                case TOP:
                {
                    return new RoundRectangle2D.Double ( titleX - titleBorderGap, borderPosition - titleAreaHeight / 2,
                            titleWidth + titleBorderGap * 2, titleAreaHeight, 3, 3 );
                }
                case BOTTOM:
                {
                    return new RoundRectangle2D.Double ( titleX - titleBorderGap, h - borderPosition - titleAreaHeight / 2,
                            titleWidth + titleBorderGap * 2, titleAreaHeight, 3, 3 );
                }
                case LEFT:
                {
                    // todo
                    return null;
                }
                case RIGHT:
                {
                    // todo
                    return null;
                }
            }
        }
        return null;
    }
}