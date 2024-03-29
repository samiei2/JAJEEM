package com.jajeem.util;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * @see http://stackoverflow.com/a/12456113/909085
 */

public class WindowResizeAdapter extends MouseAdapter implements SwingConstants {
	private boolean resizing = false;
	private int prevX = -1;
	private int prevY = -1;

	private int resizeSide = 0;
	private Component componentTemp;

	public static void install(Component component, int resizeSide) {
		WindowResizeAdapter wra = new WindowResizeAdapter(resizeSide, component);
		component.addMouseListener(wra);
		component.addMouseMotionListener(wra);
	}

	public WindowResizeAdapter(int resizeSide, Component component) {
		super();
		this.resizeSide = resizeSide;
		this.componentTemp = component;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)
				&& componentTemp.getCursor().getType() == Cursor.SE_RESIZE_CURSOR) {
			resizing = true;
		}
		prevX = e.getXOnScreen();
		prevY = e.getYOnScreen();
		// super.mousePressed(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (prevX != -1 && prevY != -1 && resizing) {
			Window w = SwingUtilities.getWindowAncestor(e.getComponent());
			Rectangle rect;
			if (w == null) {
				w = (Window) e.getComponent();
			}
			rect = w.getBounds();

			Dimension dim;
			boolean undecorated;
			if (w instanceof JDialog) {
				dim = ((JDialog) w).getContentPane().getPreferredSize();
				undecorated = ((JDialog) w).isUndecorated();
			} else if (w instanceof JFrame) {
				dim = ((JFrame) w).getContentPane().getPreferredSize();
				undecorated = ((JFrame) w).isUndecorated();
			} else {
				dim = w.getPreferredSize();
				undecorated = true;
			}

			// Checking for minimal width and height
			int xInc = e.getXOnScreen() - prevX;
			int yInc = e.getYOnScreen() - prevY;
			if (undecorated) {
				if (resizeSide == SwingConstants.NORTH_WEST
						|| resizeSide == SwingConstants.WEST
						|| resizeSide == SwingConstants.SOUTH_WEST) {
					if (rect.width - xInc < dim.width) {
						xInc = 0;
					}
				} else if (resizeSide == SwingConstants.NORTH_EAST
						|| resizeSide == SwingConstants.EAST
						|| resizeSide == SwingConstants.SOUTH_EAST) {
					if (rect.width + xInc < dim.width) {
						xInc = 0;
					}
				}
				if (resizeSide == SwingConstants.NORTH_WEST
						|| resizeSide == SwingConstants.NORTH
						|| resizeSide == SwingConstants.NORTH_EAST) {
					if (rect.height - yInc < dim.height) {
						yInc = 0;
					}
				} else if (resizeSide == SwingConstants.SOUTH_WEST
						|| resizeSide == SwingConstants.SOUTH
						|| resizeSide == SwingConstants.SOUTH_EAST) {
					if (rect.height + yInc < dim.height) {
						yInc = 0;
					}
				}
			}

			// Resizing window if any changes are done
			if (xInc != 0 || yInc != 0) {
				if (resizeSide == SwingConstants.NORTH_WEST) {
					w.setBounds(rect.x + xInc, rect.y + yInc,
							rect.width - xInc, rect.height - yInc);
				} else if (resizeSide == SwingConstants.NORTH) {
					w.setBounds(rect.x, rect.y + yInc, rect.width, rect.height
							- yInc);
				} else if (resizeSide == SwingConstants.NORTH_EAST) {
					w.setBounds(rect.x, rect.y + yInc, rect.width + xInc,
							rect.height - yInc);
				} else if (resizeSide == SwingConstants.WEST) {
					w.setBounds(rect.x + xInc, rect.y, rect.width - xInc,
							rect.height);
				} else if (resizeSide == SwingConstants.EAST) {
					w.setBounds(rect.x, rect.y, rect.width + xInc, rect.height);
				} else if (resizeSide == SwingConstants.SOUTH_WEST) {
					w.setBounds(rect.x + xInc, rect.y, rect.width - xInc,
							rect.height + yInc);
				} else if (resizeSide == SwingConstants.SOUTH) {
					w.setBounds(rect.x, rect.y, rect.width, rect.height + yInc);
				} else if (resizeSide == SwingConstants.SOUTH_EAST) {
					w.setBounds(rect.x, rect.y, rect.width + xInc, rect.height
							+ yInc);
				}
				prevX = e.getXOnScreen();
				prevY = e.getYOnScreen();
			}
		}
		// super.mouseDragged(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		resizing = false;
		componentTemp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = this.componentTemp.getLocationOnScreen().x;
		int y = this.componentTemp.getLocationOnScreen().y;
		int w = this.componentTemp.getWidth();
		int h = this.componentTemp.getHeight();
		if ((x + w - 10 < e.getXOnScreen() && e.getXOnScreen() <= x + w)
				&& (y + h - 10 < e.getYOnScreen() && e.getYOnScreen() <= y + h)) {
			componentTemp.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
//			System.out.println("Resize");
		} else {
			componentTemp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			System.out.println("Default");
		}
	}
}