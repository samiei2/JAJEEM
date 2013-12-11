package com.jajeem.core.design.teacher;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class DesktopPaneIconMoveAdapter extends MouseAdapter {
	public static final String DRAGGED_MARK = "was.dragged";

	private boolean dragging = false;
	private Point startPoint = null;
	private Rectangle startBounds = null;

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			dragging = true;
			startPoint = e.getLocationOnScreen();
			startBounds = e.getComponent().getBounds();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragging) {
			e.getComponent().setBounds(
					new Rectangle(startBounds.x + e.getLocationOnScreen().x
							- startPoint.x, startBounds.y
							+ e.getLocationOnScreen().y - startPoint.y,
							startBounds.width, startBounds.height));
			if (e.getComponent() instanceof JComponent) {
				((JComponent) e.getComponent()).putClientProperty(DRAGGED_MARK,
						true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && dragging) {
			Rectangle bounds = e.getComponent().getBounds();

			Container parent = e.getComponent().getParent();
			boolean setBounds = false;
			for (int i = 25; i < parent.getWidth(); i += 125) {
				for (int j = 25; j < parent.getHeight(); j += 100) {
					Rectangle cell = new Rectangle(i, j, 100, 75);
					if (cell.intersects(bounds)) {
						Rectangle intersection = cell.intersection(bounds);
						if (intersection.width * intersection.height >= bounds.width
								* bounds.height / 8) {
							e.getComponent().setBounds(cell);
							setBounds = true;
							break;
						}
					}
				}
				if (setBounds) {
					break;
				}
			}

			if (e.getComponent() instanceof JComponent) {
				((JComponent) e.getComponent()).putClientProperty(DRAGGED_MARK,
						null);
			}
			dragging = false;
		}
	}
}
