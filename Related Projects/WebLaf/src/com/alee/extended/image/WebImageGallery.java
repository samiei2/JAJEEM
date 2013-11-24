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

package com.alee.extended.image;

import com.alee.laf.StyleConstants;
import com.alee.laf.scroll.WebScrollBarUI;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.hotkey.Hotkey;
import com.alee.utils.ImageUtils;
import com.alee.utils.LafUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.swing.WebTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * User: mgarin Date: 05.09.11 Time: 15:45
 */

public class WebImageGallery extends JComponent
{
    private int spacing = 20;
    private int imageLength = 200;
    private int borderWidth = 3;
    private float fadeHeight = 0.7f;
    private int opacity = 125;

    private Color light = new Color ( 128, 128, 128 );
    private Color selectedLight = new Color ( 255, 255, 255 );
    private Color transparent = new Color ( 128, 128, 128, 0 );
    private Color selectedTransparent = new Color ( 255, 255, 255, 0 );

    private int maxWidth = 0;
    private int maxHeight = 0;
    private List<ImageIcon> images = new ArrayList<ImageIcon> ();
    private List<BufferedImage> reflections = new ArrayList<BufferedImage> ();
    private List<String> descriptions = new ArrayList<String> ();
    //    private List<Integer> sizes = new ArrayList<Integer> (  );

    private int preferredColumnCount = 4;
    private boolean scrollOnSelection = true;
    private int selectedIndex = -1;

    private int oldSelectedIndex = -1;
    private float progress = 0f;
    private WebTimer reflectionMover = null;

    private WebScrollPane view;

    public WebImageGallery ()
    {
        super ();

        SwingUtils.setOrientation ( this );
        setFocusable ( true );
        setFont ( new JLabel ().getFont ().deriveFont ( Font.BOLD ) );

        MouseAdapter mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( SwingUtilities.isLeftMouseButton ( e ) )
                {
                    WebImageGallery.this.requestFocusInWindow ();
                    for ( int i = 0; i < images.size (); i++ )
                    {
                        if ( getImageRect ( i ).contains ( e.getPoint () ) )
                        {
                            setSelectedIndex ( i );
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseWheelMoved ( MouseWheelEvent e )
            {
                int index = getSelectedIndex ();
                int maxIndex = images.size () - 1;
                int wheelRotation = e.getWheelRotation ();
                int newIndex;
                if ( wheelRotation > 0 )
                {
                    newIndex = index + wheelRotation;
                    while ( newIndex > maxIndex )
                    {
                        newIndex -= images.size ();
                    }
                }
                else
                {
                    newIndex = index + wheelRotation;
                    while ( newIndex < 0 )
                    {
                        newIndex += images.size ();
                    }
                }
                setSelectedIndex ( newIndex );
            }
        };
        addMouseListener ( mouseAdapter );
        addMouseWheelListener ( mouseAdapter );

        addKeyListener ( new KeyAdapter ()
        {
            @Override
            public void keyPressed ( KeyEvent e )
            {
                if ( images.size () > 0 )
                {
                    int si = getSelectedIndex ();
                    if ( Hotkey.LEFT.isTriggered ( e ) )
                    {
                        setSelectedIndex ( si == -1 || si == 0 ? images.size () - 1 : si - 1 );
                    }
                    else if ( Hotkey.RIGHT.isTriggered ( e ) )
                    {
                        setSelectedIndex ( si == -1 || si == images.size () - 1 ? 0 : si + 1 );
                    }
                    else if ( Hotkey.HOME.isTriggered ( e ) )
                    {
                        setSelectedIndex ( 0 );
                    }
                    else if ( Hotkey.END.isTriggered ( e ) )
                    {
                        setSelectedIndex ( images.size () - 1 );
                    }
                }
            }
        } );
    }

    public List<ImageIcon> getImages ()
    {
        return images;
    }

    public int getPreferredColumnCount ()
    {
        return preferredColumnCount;
    }

    public void setPreferredColumnCount ( int preferredColumnCount )
    {
        this.preferredColumnCount = preferredColumnCount;
    }

    public WebScrollPane getView ()
    {
        return getView ( true );
    }

    public WebScrollPane getView ( boolean withBorder )
    {
        if ( view == null )
        {
            view = new WebScrollPane ( WebImageGallery.this, withBorder )
            {
                @Override
                public Dimension getPreferredSize ()
                {
                    int columns = Math.min ( images.size (), preferredColumnCount );
                    return new Dimension ( spacing * ( columns + 1 ) + columns * maxWidth,
                            WebImageGallery.this.getPreferredSize ().height + WebScrollBarUI.LENGTH );
                }
            };
            view.setHorizontalScrollBarPolicy ( WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
            view.setVerticalScrollBarPolicy ( WebScrollPane.VERTICAL_SCROLLBAR_NEVER );

            InputMap im = view.getInputMap ( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
            im.put ( KeyStroke.getKeyStroke ( "UP" ), "none" );
            im.put ( KeyStroke.getKeyStroke ( "DOWN" ), "none" );
            im.put ( KeyStroke.getKeyStroke ( "LEFT" ), "none" );
            im.put ( KeyStroke.getKeyStroke ( "RIGHT" ), "none" );
        }
        return view;
    }

    public int getImageLength ()
    {
        return imageLength;
    }

    public void setImageLength ( int imageLength )
    {
        this.imageLength = imageLength;
    }

    public boolean isScrollOnSelection ()
    {
        return scrollOnSelection;
    }

    public void setScrollOnSelection ( boolean scrollOnSelection )
    {
        this.scrollOnSelection = scrollOnSelection;
    }

    public int getSelectedIndex ()
    {
        return selectedIndex;
    }

    public void setSelectedIndex ( int selectedIndex )
    {
        if ( this.selectedIndex == selectedIndex )
        {
            return;
        }

        this.oldSelectedIndex = this.selectedIndex;
        this.selectedIndex = selectedIndex;
        repaint ();
        if ( scrollOnSelection )
        {
            Rectangle rect = getImageRect ( selectedIndex );
            SwingUtils.scrollSmoothly ( getView (), rect.x + rect.width / 2 - WebImageGallery.this.getVisibleRect ().width / 2, rect.y );
        }
        moveReflection ();
    }

    private void moveReflection ()
    {
        if ( reflectionMover != null && reflectionMover.isRunning () )
        {
            reflectionMover.stop ();
        }

        progress = 0f;
        reflectionMover = new WebTimer ( "WebImageGallery.reflectionMoveTimer", StyleConstants.fastAnimationDelay, new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                if ( progress < 1f )
                {
                    progress += 0.08f;
                    progress = Math.min ( progress, 1f );
                    WebImageGallery.this.repaint ();
                }
                else
                {
                    reflectionMover.stop ();
                }
            }
        } );
        reflectionMover.start ();
    }

    public Rectangle getImageRect ( int index )
    {
        int iconWidth = images.get ( index ).getIconWidth ();
        int iconHeight = images.get ( index ).getIconHeight ();
        Dimension ps = getPreferredSize ();
        int x = ( getWidth () > ps.width ? ( getWidth () - ps.width ) / 2 : 0 ) + spacing +
                ( maxWidth + spacing ) * index + maxWidth / 2;
        int y = getHeight () / 2 - spacing / 2 - iconHeight / 2;
        return new Rectangle ( x - iconWidth / 2, y - iconHeight / 2, iconWidth, iconHeight );
    }

    public void addImage ( ImageIcon image )
    {
        addImage ( 0, image );
    }

    public void addImage ( int index, ImageIcon image )
    {
        try
        {
            ImageIcon previewIcon = ImageUtils.createPreviewIcon ( image, imageLength );
            int rwidth = previewIcon.getIconWidth ();
            int rheight = previewIcon.getIconHeight ();

            BufferedImage reflection = ImageUtils.createCompatibleImage ( rwidth, rheight, Transparency.TRANSLUCENT );
            Graphics2D g2d = reflection.createGraphics ();
            LafUtils.setupAntialias ( g2d );
            g2d.drawImage ( previewIcon.getImage (), 0, 0, null );
            g2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.DST_IN ) );
            g2d.setPaint ( new GradientPaint ( 0, rheight * ( 1f - fadeHeight ), new Color ( 0, 0, 0, 0 ), 0, rheight,
                    new Color ( 0, 0, 0, opacity ) ) );
            g2d.fillRect ( 0, 0, rwidth, rheight );
            g2d.dispose ();

            images.add ( index, previewIcon );
            descriptions.add ( index, image.getIconWidth () + " x " + image.getIconHeight () + " px" );
            reflections.add ( index, reflection );
        }
        catch ( Throwable e )
        {
            // Out of memory
        }

        recalcualteMaxSizes ();
        updateContainer ();
    }

    public void removeImage ( ImageIcon image )
    {
        if ( images.contains ( image ) )
        {
            removeImage ( images.indexOf ( image ) );
        }
    }

    public void removeImage ( int index )
    {
        if ( index >= 0 && index < images.size () )
        {
            boolean wasSelected = getSelectedIndex () == index;

            images.remove ( index );
            descriptions.remove ( index );
            reflections.remove ( index ).flush ();
            recalcualteMaxSizes ();
            updateContainer ();

            if ( wasSelected && images.size () > 0 )
            {
                setSelectedIndex ( index < images.size () ? index : index - 1 );
            }
        }
    }

    private void updateContainer ()
    {
        if ( getParent () instanceof JComponent )
        {
            ( ( JComponent ) getParent () ).revalidate ();
        }
        repaint ();
    }

    private void recalcualteMaxSizes ()
    {
        for ( ImageIcon icon : images )
        {
            maxWidth = Math.max ( maxWidth, icon.getIconWidth () );
            maxHeight = Math.max ( maxHeight, icon.getIconHeight () );
        }
    }

    @Override
    protected void paintComponent ( final Graphics g )
    {
        super.paintComponent ( g );

        final int height = getHeight ();
        final int width = getWidth ();

        final Graphics2D g2d = ( Graphics2D ) g;
        LafUtils.setupAntialias ( g2d );

        g2d.setPaint ( new GradientPaint ( 0, 0, Color.black, 0, height, Color.darkGray ) );
        g2d.fillRect ( 0, 0, width, height );

        final Rectangle vr = getVisibleRect ();
        final Dimension ps = getPreferredSize ();
        final Composite oldComposite = g2d.getComposite ();
        for ( int i = 0; i < images.size (); i++ )
        {
            if ( !getImageRect ( i ).intersects ( vr ) )
            {
                continue;
            }

            final ImageIcon icon = images.get ( i );
            final BufferedImage bi = ImageUtils.getBufferedImage ( icon );
            final int imageWidth = icon.getIconWidth ();
            final int imageHeight = icon.getIconHeight ();

            final int x = ( getWidth () > ps.width ? ( getWidth () - ps.width ) / 2 : 0 ) + spacing +
                    ( maxWidth + spacing ) * i + maxWidth / 2;
            final int y = height / 2 - spacing / 2 - imageHeight / 2;
            final int y2 = height / 2 + spacing / 2 + imageHeight / 2;

            // Initial image

            float add = selectedIndex == i ? progress * 0.4f : ( oldSelectedIndex == i ? 0.4f - progress * 0.4f : 0 );
            g2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 0.6f + add ) );

            g2d.drawImage ( bi, x - imageWidth / 2, y - imageHeight / 2, null );

            g2d.setPaint ( selectedIndex == i ? Color.WHITE : Color.GRAY );
            Area gp = new Area ( new RoundRectangle2D.Double ( x - imageWidth / 2 - borderWidth, y - imageHeight / 2 - borderWidth,
                    imageWidth + borderWidth * 2, imageHeight + borderWidth * 2, borderWidth * 2, borderWidth * 2 ) );
            gp.subtract ( new Area ( new Rectangle ( x - imageWidth / 2, y - imageHeight / 2, imageWidth, imageHeight ) ) );
            g2d.fill ( gp );

            g2d.setComposite ( oldComposite );

            // Info text

            if ( selectedIndex == i || oldSelectedIndex == i )
            {
                final float opacity = selectedIndex == i ? progress : 1f - progress;
                g2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, opacity ) );
                g2d.setPaint ( Color.WHITE );

                final String infoText = descriptions.get ( i );
                final Point ts = LafUtils.getTextCenterShear ( g2d.getFontMetrics (), infoText );
                g2d.drawString ( infoText, x + ts.x, getHeight () / 2 + spacing / 2 + ts.y );
                g2d.setComposite ( oldComposite );
            }

            // Reflection

            final int rwidth = imageWidth + borderWidth * 2;
            final int rheight = imageHeight + borderWidth * 2;

            final int addition = selectedIndex == i ? Math.round ( progress * spacing ) :
                    ( oldSelectedIndex == i ? spacing - Math.round ( progress * spacing ) : 0 );
            if ( reflections.get ( i ) != null )
            {
                g2d.drawImage ( reflections.get ( i ), x - imageWidth / 2, y2 + imageHeight / 2 + addition, imageWidth, -imageHeight,
                        null );
            }

            gp = new Area ( new RoundRectangle2D.Double ( x - rwidth / 2, y2 - rheight / 2 + addition, rwidth, rheight, borderWidth * 2,
                    borderWidth * 2 ) );
            gp.subtract ( new Area (
                    new Rectangle ( x - rwidth / 2 + borderWidth, y2 - rheight / 2 + addition + borderWidth, rwidth - borderWidth * 2,
                            rheight - borderWidth * 2 ) ) );
            g2d.setPaint ( new GradientPaint ( 0, y2 - imageHeight / 2 + addition, selectedIndex == i ? selectedLight : light, 0,
                    y2 - imageHeight / 2 + addition + imageHeight * fadeHeight, selectedIndex == i ? selectedTransparent : transparent ) );
            g2d.fill ( gp );
        }
    }

    @Override
    public Dimension getPreferredSize ()
    {
        return new Dimension ( spacing * ( images.size () + 1 ) + maxWidth * images.size (), spacing * 3 + maxHeight * 2 );
    }
}
