package com.jajeem.ui.textbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

import com.alee.extended.painter.Painter;
import com.alee.extended.painter.PainterSupport;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.text.TextComponentLayout;
import com.alee.laf.text.WebTextFieldStyle;
import com.alee.laf.text.WebTextFieldUI;
import com.alee.utils.LafUtils;
import com.alee.utils.SwingUtils;
import com.jajeem.ui.border.ImageBorder;

public class JajeemTexFieldUI extends BasicTextFieldUI {

	private JTextField textField = null;

	private boolean drawBorder = WebTextFieldStyle.drawBorder;
	private boolean drawFocus = WebTextFieldStyle.drawFocus;
	private int round = WebTextFieldStyle.round;
	private boolean drawShade = WebTextFieldStyle.drawShade;
	private int shadeWidth = WebTextFieldStyle.shadeWidth;
	private boolean drawBackground = WebTextFieldStyle.drawBackground;
	private boolean webColored = WebTextFieldStyle.webColored;
	private Painter painter = WebTextFieldStyle.painter;
	private Insets fieldMargin = WebTextFieldStyle.fieldMargin;
	private String inputPrompt = WebTextFieldStyle.inputPrompt;
	private Font inputPromptFont = WebTextFieldStyle.inputPromptFont;
	private Color inputPromptForeground = WebTextFieldStyle.inputPromptForeground;
	private int inputPromptPosition = WebTextFieldStyle.inputPromptPosition;
	private boolean hideInputPromptOnFocus = WebTextFieldStyle.hideInputPromptOnFocus;

	private JComponent leadingComponent = null;
	private JComponent trailingComponent = null;

	private boolean inputPromptSet = false;

	private FocusListener focusListener;
	private PropertyChangeListener accessibleChangeListener;
	private PropertyChangeListener orientationChangeListener;
	private PropertyChangeListener marginChangeListener;
	private ComponentAdapter componentResizeListener;
	
	private String Uri;

	public JajeemTexFieldUI(final JTextField textField) {
		this(textField, true);
	}

	public JajeemTexFieldUI(final JTextField textField, boolean drawBorder) {
		super();
		this.drawBorder = drawBorder;
		this.textField = textField;
	}

	public JajeemTexFieldUI(final JTextField textField, String uri2) {
		this(textField, true);
		Uri = uri2;
	}

	public static ComponentUI createUI(JComponent c) {
		return new WebTextFieldUI((JTextField) c);
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);

		// Default settings
		SwingUtils.setOrientation(textField);
		textField.putClientProperty(SwingUtils.HANDLES_ENABLE_STATE, true);
		textField.setFocusable(true);
		textField.setOpaque(false);
		textField.setMargin(WebTextFieldStyle.margin);
		textField.setBackground(Color.WHITE);
		textField.setSelectionColor(StyleConstants.textSelectionColor);
		textField.setForeground(Color.BLACK);
		textField.setSelectedTextColor(Color.BLACK);
		textField.setCaretColor(Color.GRAY);
		textField.setLayout(new TextComponentLayout(textField));
		PainterSupport.installPainter(textField, this.painter);

		// Updating border
		updateBorder();

		focusListener = new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				textField.repaint();
			}

			@Override
			public void focusGained(FocusEvent e) {
				textField.repaint();
			}
		};
		textField.addFocusListener(focusListener);

		accessibleChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateInnerComponents();
			}
		};
		textField.addPropertyChangeListener(
				WebLookAndFeel.COMPONENT_ENABLED_PROPERTY,
				accessibleChangeListener);

		orientationChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateBorder();
			}
		};
		textField.addPropertyChangeListener(
				WebLookAndFeel.COMPONENT_ORIENTATION_PROPERTY,
				orientationChangeListener);

		marginChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateBorder();
			}
		};
		textField.addPropertyChangeListener(
				WebLookAndFeel.COMPONENT_MARGIN_PROPERTY, marginChangeListener);

		componentResizeListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateBorder();
			}
		};
	}

	@Override
	public void uninstallUI(JComponent c) {
		PainterSupport.uninstallPainter(textField, this.painter);

		textField.putClientProperty(SwingUtils.HANDLES_ENABLE_STATE, null);

		textField.removeFocusListener(focusListener);
		textField.removePropertyChangeListener(
				WebLookAndFeel.COMPONENT_ENABLED_PROPERTY,
				accessibleChangeListener);
		textField.removePropertyChangeListener(
				WebLookAndFeel.COMPONENT_ORIENTATION_PROPERTY,
				orientationChangeListener);
		textField.removePropertyChangeListener(
				WebLookAndFeel.COMPONENT_MARGIN_PROPERTY, marginChangeListener);

		cleanupLeadingComponent();
		cleanupTrailingComponent();
		textField.setLayout(null);

		super.uninstallUI(c);
	}

	private void cleanupLeadingComponent() {
		if (this.leadingComponent != null) {
			this.leadingComponent
					.removeComponentListener(componentResizeListener);
			textField.remove(this.leadingComponent);
			this.leadingComponent = null;
		}
	}

	private void cleanupTrailingComponent() {
		if (this.trailingComponent != null) {
			this.trailingComponent
					.removeComponentListener(componentResizeListener);
			textField.remove(this.trailingComponent);
			this.trailingComponent = null;
		}
	}

	private void updateInnerComponents() {
		if (leadingComponent != null) {
			leadingComponent.setEnabled(textField.isEnabled());
		}
		if (trailingComponent != null) {
			trailingComponent.setEnabled(textField.isEnabled());
		}
	}

	public void updateBorder() {
		if (textField != null) {
			// Style border
			Insets m;
			if (painter != null) {
				m = painter.getMargin(getComponent());
			} else if (drawBorder) {
				m = new Insets(shadeWidth + 1, shadeWidth + 1, shadeWidth + 1,
						shadeWidth + 1);
			} else {
				m = new Insets(0, 0, 0, 0);
			}

			// Taking margins into account
			boolean ltr = textField.getComponentOrientation().isLeftToRight();
			Insets margin = textField.getMargin();
			if (margin != null) {
				m.top += margin.top;
				m.left += ltr ? margin.left : margin.right;
				m.bottom += margin.bottom;
				m.right += ltr ? margin.right : margin.left;
			}
			if (fieldMargin != null) {
				m.top += fieldMargin.top;
				m.left += ltr ? fieldMargin.left : fieldMargin.right;
				m.bottom += fieldMargin.bottom;
				m.right += ltr ? fieldMargin.right : fieldMargin.left;
			}

			// Adding component sizes into border
			Component lc = ltr ? leadingComponent : trailingComponent;
			Component tc = ltr ? trailingComponent : leadingComponent;
			if (lc != null) {
				m.left += lc.getPreferredSize().width;
			}
			if (tc != null) {
				m.right += tc.getPreferredSize().width;
			}

			// Final border
			try {
				textField.setBorder(new ImageBorder(m,
						Uri, textField));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void paintSafely(Graphics g) {
		JTextComponent c = getComponent();
		Graphics2D g2d = (Graphics2D) g;

		if (c.isOpaque() && (painter == null || !painter.isOpaque(textField))) {
			// Paint default background
			g.setColor(c.getBackground());
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
		}

		if (painter != null || drawBorder) {
			Object aa = LafUtils.setupAntialias(g2d);
			if (painter != null) {
				// Use background painter instead of default UI graphics
				painter.paint(g2d, SwingUtils.size(c), c);
			} else if (drawBorder) {
				// Border, background and shade
				final Color shadeColor;
				if (drawShade) {
					shadeColor = drawFocus && c.isFocusOwner() ? StyleConstants.fieldFocusColor
							: StyleConstants.shadeColor;
				} else {
					shadeColor = null;
				}
				LafUtils.drawWebStyle(g2d, c, shadeColor, shadeWidth, round,
						drawBackground, webColored);
			}
			LafUtils.restoreAntialias(g2d, aa);
		}

		// Map hints = SwingUtils.setupTextAntialias ( g2d );
		super.paintSafely(g);
		if (isInputPromptVisible(c)) {
			boolean ltr = c.getComponentOrientation().isLeftToRight();
			Rectangle b = getVisibleEditorRect();
			Shape oc = LafUtils.intersectClip(g2d, b);
			g2d.setFont(inputPromptFont != null ? inputPromptFont : c.getFont());
			g2d.setPaint(inputPromptForeground != null ? inputPromptForeground
					: c.getForeground());

			FontMetrics fm = g2d.getFontMetrics();
			int x;
			if (inputPromptPosition == SwingConstants.CENTER) {
				x = b.x + b.width / 2 - fm.stringWidth(inputPrompt) / 2;
			} else if (ltr && inputPromptPosition == SwingConstants.LEADING
					|| !ltr && inputPromptPosition == SwingConstants.TRAILING) {
				x = b.x;
			} else {
				x = b.x + b.width - fm.stringWidth(inputPrompt);
			}
			g2d.drawString(inputPrompt, x,
					getBaseline(c, c.getWidth(), c.getHeight()));

			g2d.setClip(oc);
		}
		// SwingUtils.restoreTextAntialias ( g2d, hints );
	}

	private boolean isInputPromptVisible(JTextComponent c) {
		return inputPromptSet && c.isEditable() && c.isEnabled()
				&& (!hideInputPromptOnFocus || !c.isFocusOwner())
				&& c.getText().equals("");
	}
}
