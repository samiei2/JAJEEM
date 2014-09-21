package com.jajeem.ui.combobox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ListUI;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.ScrollPaneUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import com.alee.extended.layout.AbstractLayoutManager;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.combobox.WebComboBoxStyle;
import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.laf.list.WebListUI;
import com.alee.laf.scroll.WebScrollBarUI;
import com.alee.laf.scroll.WebScrollPaneUI;
import com.alee.laf.text.WebTextFieldUI;
import com.alee.utils.LafUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.swing.WebDefaultCellEditor;

public class JajeemComboBoxUI extends BasicComboBoxUI{
	private ImageIcon expandIcon = WebComboBoxStyle.expandIcon;
    private ImageIcon collapseIcon = WebComboBoxStyle.collapseIcon;
    private int iconSpacing = WebComboBoxStyle.iconSpacing;
    private boolean drawBorder = WebComboBoxStyle.drawBorder;
    private int round = WebComboBoxStyle.round;
    private int shadeWidth = WebComboBoxStyle.shadeWidth;
    private boolean drawFocus = WebComboBoxStyle.drawFocus;
    private boolean mouseWheelScrollingEnabled = WebComboBoxStyle.mouseWheelScrollingEnabled;

    private MouseWheelListener mouseWheelListener = null;
    private WebButton arrow = null;
	private ImageIcon expand;
	private ImageIcon collapse;
    
    public JajeemComboBoxUI(){
    	
    	expand = new ImageIcon(JajeemComboBoxUI.class.getResource("/icons/noa_en/arrow.png"));
		collapse = new ImageIcon(JajeemComboBoxUI.class.getResource("/icons/noa_en/arrowup.png"));
		expandIcon =new ImageIcon(expand.getImage().getScaledInstance(15, 9, Image.SCALE_SMOOTH));
		collapseIcon = new ImageIcon(collapse.getImage().getScaledInstance(15, 9, Image.SCALE_SMOOTH));;
    }

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new WebComboBoxUI ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        final JComboBox comboBox = ( JComboBox ) c;

        // Default settings
        SwingUtils.setOrientation ( comboBox );
        comboBox.setFocusable ( true );
        comboBox.setOpaque ( false );

        // Updating border
        updateBorder ();

        // Drfault renderer
        if ( !( comboBox.getRenderer () instanceof WebComboBoxCellRenderer ) )
        {
            comboBox.setRenderer ( new WebComboBoxCellRenderer ( comboBox ) );
        }

        // Rollover scrolling listener
        mouseWheelListener = new MouseWheelListener ()
        {
            @Override
            public void mouseWheelMoved ( MouseWheelEvent e )
            {
                if ( mouseWheelScrollingEnabled && comboBox.isEnabled () )
                {
                    // Changing selection in case index actually changed
                    final int index = comboBox.getSelectedIndex ();
                    final int newIndex = Math.min ( Math.max ( 0, index + e.getWheelRotation () ), comboBox.getModel ().getSize () - 1 );
                    if ( newIndex != index )
                    {
                        comboBox.setSelectedIndex ( newIndex );
                    }
                }
            }
        };
        comboBox.addMouseWheelListener ( mouseWheelListener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstallUI ( JComponent c )
    {
        c.removeMouseWheelListener ( mouseWheelListener );
        mouseWheelListener = null;
        arrow = null;

        super.uninstallUI ( c );
    }

    public void setEditorColumns ( int columns )
    {
        if ( editor instanceof JTextField )
        {
            ( ( JTextField ) editor ).setColumns ( columns );
        }
    }

    private void updateBorder ()
    {
        if ( drawBorder )
        {
            comboBox.setBorder ( BorderFactory.createEmptyBorder ( shadeWidth + 1, shadeWidth + 1, shadeWidth + 1, shadeWidth + 1 ) );
        }
        else
        {
            comboBox.setBorder ( null );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void installComponents ()
    {
        comboBox.setLayout ( createLayoutManager () );

        arrowButton = createArrowButton ();
        comboBox.add ( arrowButton, "1,0" );
        if ( arrowButton != null )
        {
            configureArrowButton ();
        }

        if ( comboBox.isEditable () )
        {
            addEditor ();
        }

        comboBox.add ( currentValuePane, "0,0" );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ComboBoxEditor createEditor ()
    {
        final ComboBoxEditor editor = super.createEditor ();
        Component e = editor.getEditorComponent ();
        e.addFocusListener ( new FocusAdapter ()
        {
            @Override
            public void focusGained ( FocusEvent e )
            {
                comboBox.repaint ();
            }

            @Override
            public void focusLost ( FocusEvent e )
            {
                comboBox.repaint ();
            }
        } );
        if ( e instanceof JComponent )
        {
            ( ( JComponent ) e ).setOpaque ( false );
        }
        if ( e instanceof JTextField )
        {
            JTextField textField = ( JTextField ) e;
            textField.setUI ( new WebTextFieldUI ( textField, false ) );
            textField.setMargin ( new Insets ( 0, 1, 0, 1 ) );
        }
        return editor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JButton createArrowButton ()
    {
        arrow = new WebButton ();
        arrow.setUndecorated ( true );
        arrow.setDrawFocus ( false );
        arrow.setMoveIconOnPress ( false );
        arrow.setName ( "ComboBox.arrowButton" );
        arrow.setIcon ( expandIcon);
        arrow.setLeftRightSpacing ( iconSpacing );
        return arrow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureArrowButton ()
    {
        super.configureArrowButton ();
        if ( arrowButton != null )
        {
            arrowButton.setFocusable ( false );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ComboPopup createPopup ()
    {
        return new BasicComboPopup ( comboBox )
        {
            @Override
            protected JScrollPane createScroller ()
            {
                final JScrollPane scroll = super.createScroller ();
                if ( WebLookAndFeel.isInstalled () )
                {
                    scroll.setOpaque ( false );
                    scroll.getViewport ().setOpaque ( false );
                }

                final ScrollPaneUI scrollPaneUI = scroll.getUI ();
                if ( scrollPaneUI instanceof WebScrollPaneUI )
                {
                    final WebScrollPaneUI webScrollPaneUI = ( WebScrollPaneUI ) scrollPaneUI;
                    webScrollPaneUI.setDrawBorder ( false );

                    final ScrollBarUI scrollBarUI = scroll.getVerticalScrollBar ().getUI ();
                    if ( scrollBarUI instanceof WebScrollBarUI )
                    {
                        final WebScrollBarUI webScrollBarUI = ( WebScrollBarUI ) scrollBarUI;
                        webScrollBarUI.setDrawBorder ( false );
                    }
                }

                return scroll;
            }

            @Override
            protected JList createList ()
            {
                final JList list = super.createList ();
                //  list.setOpaque ( false );

                final ListUI listUI = list.getUI ();
                if ( listUI instanceof WebListUI )
                {
                    final WebListUI webListUI = ( WebListUI ) listUI;
//                    webListUI.setHighlightRolloverCell ( false );
//                    webListUI.setDecorateSelection ( false );
                }

                return list;
            }

            @Override
            protected void configurePopup ()
            {
                super.configurePopup ();

                // Button updater
                addPopupMenuListener ( new PopupMenuListener ()
                {
                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void popupMenuWillBecomeVisible ( final PopupMenuEvent e )
                    {
                        arrow.setIcon ( collapseIcon );

                        // Fix for combobox repaint on popup opening
                        comboBox.repaint ();
                    }

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void popupMenuWillBecomeInvisible ( final PopupMenuEvent e )
                    {
                        arrow.setIcon ( expandIcon );
                    }

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void popupMenuCanceled ( final PopupMenuEvent e )
                    {
                        arrow.setIcon ( expandIcon );
                    }
                } );
            }

            @Override
            public void show ()
            {
                comboBox.firePopupMenuWillBecomeVisible ();

                setListSelection ( comboBox.getSelectedIndex () );

                final Point location = getPopupLocation ();
                show ( comboBox, location.x, location.y );
            }

            private void setListSelection ( int selectedIndex )
            {
                if ( selectedIndex == -1 )
                {
                    list.clearSelection ();
                }
                else
                {
                    list.setSelectedIndex ( selectedIndex );
                    list.ensureIndexIsVisible ( selectedIndex );
                }
            }

            private Point getPopupLocation ()
            {
                final Dimension comboSize = comboBox.getSize ();
                comboSize.setSize ( comboSize.width - 2, getPopupHeightForRowCount ( comboBox.getMaximumRowCount () ) );
                final Rectangle popupBounds = computePopupBounds ( 0, comboBox.getBounds ().height, comboSize.width, comboSize.height );

                final Dimension scrollSize = popupBounds.getSize ();
                scroller.setMaximumSize ( scrollSize );
                scroller.setPreferredSize ( scrollSize );
                scroller.setMinimumSize ( scrollSize );
                list.revalidate ();

                return popupBounds.getLocation ();
            }
        };
    }

    public boolean isComboboxCellEditor ()
    {
        if ( comboBox != null )
        {
            final Object cellEditor = comboBox.getClientProperty ( WebDefaultCellEditor.COMBOBOX_CELL_EDITOR );
            return cellEditor != null && ( Boolean ) cellEditor;
        }
        else
        {
            return false;
        }
    }

    public ImageIcon getExpandIcon ()
    {
        return expandIcon;
    }

    public void setExpandIcon ( ImageIcon expandIcon )
    {
        this.expandIcon = expandIcon;
        if ( arrow != null && !isPopupVisible ( comboBox ) )
        {
            arrow.setIcon ( expandIcon );
        }
    }

    public ImageIcon getCollapseIcon ()
    {
        return collapseIcon;
    }

    public void setCollapseIcon ( ImageIcon collapseIcon )
    {
        this.collapseIcon = collapseIcon;
        if ( arrow != null && isPopupVisible ( comboBox ) )
        {
            arrow.setIcon ( collapseIcon );
        }
    }

    public int getIconSpacing ()
    {
        return iconSpacing;
    }

    public void setIconSpacing ( int iconSpacing )
    {
        this.iconSpacing = iconSpacing;
        if ( arrow != null )
        {
            arrow.setLeftRightSpacing ( iconSpacing );
        }
    }

    public boolean isDrawBorder ()
    {
        return drawBorder;
    }

    public void setDrawBorder ( boolean drawBorder )
    {
        this.drawBorder = drawBorder;
        updateBorder ();
    }

    public boolean isDrawFocus ()
    {
        return drawFocus;
    }

    public void setDrawFocus ( boolean drawFocus )
    {
        this.drawFocus = drawFocus;
    }

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    public void setShadeWidth ( int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
        updateBorder ();
    }

    public boolean isMouseWheelScrollingEnabled ()
    {
        return mouseWheelScrollingEnabled;
    }

    public void setMouseWheelScrollingEnabled ( boolean enabled )
    {
        this.mouseWheelScrollingEnabled = enabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint ( Graphics g, JComponent c )
    {
        hasFocus = comboBox.hasFocus ();
        Rectangle r = rectangleForCurrentValue ();

        // Background
        paintCurrentValueBackground ( g, r, hasFocus );

        // Selected uneditable value
        if ( !comboBox.isEditable () )
        {
            paintCurrentValue ( g, r, hasFocus );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintCurrentValueBackground ( Graphics g, Rectangle bounds, boolean hasFocus )
    {
        Graphics2D g2d = ( Graphics2D ) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if ( drawBorder )
        {
            // Border and background
            comboBox.setBackground ( StyleConstants.selectedBgColor );
//            LafUtils.drawWebStyle ( g2d, comboBox,
//                    drawFocus && SwingUtils.hasFocusOwner ( comboBox ) ? StyleConstants.fieldFocusColor : StyleConstants.shadeColor,
//                    shadeWidth, round, true, !isPopupVisible ( comboBox ) );
            
            g2d.setColor(new Color(200,200,200,255));
            g2d.fillRoundRect(0, 0, comboBox.getWidth()-1, comboBox.getHeight()-1, 10, 10);
            g2d.setColor(new Color(63,63,63,255));
            float width = comboBox.getWidth() / 5,height = comboBox.getHeight()-1;
            float radius = 20;
	        Path2D path = new Path2D.Double();
	        path.moveTo(0, 0);
	        path.lineTo(width - radius, 0);
	        path.curveTo(width, 0, width, 0, width, radius);
	        path.lineTo(width, height - radius);
	        path.curveTo(width, height, width, height, width - radius, height);
	        path.lineTo(0, height);
	        path.closePath();
	        int x = comboBox.getWidth() - comboBox.getWidth() / 5,y = 0;
	        g2d.translate(x, y);
            g2d.fill(path);
        }
        else
        {
            // Simple background
            boolean pressed = isPopupVisible ( comboBox );
            Rectangle cb = SwingUtils.size ( comboBox );
            g2d.setPaint ( new GradientPaint ( 0, shadeWidth, pressed ? StyleConstants.topSelectedBgColor : StyleConstants.topBgColor, 0,
                    comboBox.getHeight () - shadeWidth, pressed ? StyleConstants.bottomSelectedBgColor : StyleConstants.bottomBgColor ) );
            g2d.fillRect ( cb.x, cb.y, cb.width, cb.height );
        }

        // Separator line
        if ( comboBox.isEditable () )
        {
            boolean ltr = comboBox.getComponentOrientation ().isLeftToRight ();
            Insets insets = comboBox.getInsets ();
            int lx = ltr ? comboBox.getWidth () - insets.right - arrow.getWidth () - 1 : insets.left + arrow.getWidth ();

            g2d.setPaint ( comboBox.isEnabled () ? StyleConstants.borderColor : StyleConstants.disabledBorderColor );
            g2d.drawLine ( lx, insets.top + 1, lx, comboBox.getHeight () - insets.bottom - 2 );
        }
        g2d.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintCurrentValue ( Graphics g, Rectangle bounds, boolean hasFocus )
    {
        ListCellRenderer renderer = comboBox.getRenderer ();
        Component c;

        if ( hasFocus && !isPopupVisible ( comboBox ) )
        {
            c = renderer.getListCellRendererComponent ( listBox, comboBox.getSelectedItem (), -1, true, false );
        }
        else
        {
            c = renderer.getListCellRendererComponent ( listBox, comboBox.getSelectedItem (), -1, false, false );
            c.setBackground ( UIManager.getColor ( "ComboBox.background" ) );
        }
        c.setFont ( comboBox.getFont () );

        if ( comboBox.isEnabled () )
        {
            c.setForeground ( comboBox.getForeground () );
            c.setBackground ( comboBox.getBackground () );
        }
        else
        {
            c.setForeground ( UIManager.getColor ( "ComboBox.disabledForeground" ) );
            c.setBackground ( UIManager.getColor ( "ComboBox.disabledBackground" ) );
        }

        boolean shouldValidate = false;
        if ( c instanceof JPanel )
        {
            shouldValidate = true;
        }

        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.width;
        int h = bounds.height;

        currentValuePane.paintComponent ( g, c, comboBox, x, y, w, h, shouldValidate );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutManager createLayoutManager ()
    {
        return new WebComboBoxLayout ();
    }

    /**
     * Custom layout manager for WebComboBoxUI.
     */
    private class WebComboBoxLayout extends AbstractLayoutManager
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension preferredLayoutSize ( Container parent )
        {
            return parent.getPreferredSize ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension minimumLayoutSize ( Container parent )
        {
            return parent.getMinimumSize ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void layoutContainer ( Container parent )
        {
            JComboBox cb = ( JComboBox ) parent;
            int width = cb.getWidth ();
            int height = cb.getHeight ();

            if ( arrowButton != null )
            {
                Insets insets = getInsets ();
                int buttonHeight = height - ( insets.top + insets.bottom );
                int buttonWidth = arrowButton.getPreferredSize ().width;
                if ( cb.getComponentOrientation ().isLeftToRight () )
                {
                    arrowButton.setBounds ( width - ( insets.right + buttonWidth ), insets.top, buttonWidth, buttonHeight );
                    arrowButton.setBounds ( width - ( (comboBox.getWidth() / 10) + buttonWidth / 2 ), insets.top, buttonWidth, buttonHeight );
                }
                else
                {
                    arrowButton.setBounds ( insets.left, insets.top, buttonWidth, buttonHeight );
                }
            }

            if ( editor != null )
            {
                editor.setBounds ( rectangleForCurrentValue () );
            }
        }
    }
}
