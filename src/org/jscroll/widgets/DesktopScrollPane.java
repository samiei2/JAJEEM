/*
 * JScroll - the scrollable desktop pane for Java.
 * Copyright (C) 2003 Tom Tessier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.jscroll.widgets;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 * This class provides the scrollpane that contains the virtual desktop.
 * 
 * @author <a href="mailto:tessier@gabinternet.com">Tom Tessier</a>
 * @version 1.0 11-Aug-2001
 */
public class DesktopScrollPane extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DesktopMediator desktopMediator;
	private RootDesktopPane desktopPane;
	private FramePositioning positioning;

	/**
	 * creates the DesktopScrollPane object
	 * 
	 * @param desktopMediator
	 *            a reference to the DesktopMediator object
	 */
	public DesktopScrollPane(DesktopMediator desktopMediator) {
		this.desktopMediator = desktopMediator;

		setDesktopPane(new RootDesktopPane(this));
		setViewportView(getDesktopPane());

		positioning = new FramePositioning(this);

		// set some defaults
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// set scrollbars to scroll by 5 pixels each...
		getHorizontalScrollBar().setUnitIncrement(5);
		getVerticalScrollBar().setUnitIncrement(5);
	}

	/**
	 * creates a JScrollInternalFrame and adds it to the virtual desktop
	 * 
	 * @param dListener
	 *            a reference to the DesktopListener
	 * @param title
	 *            the title displayed in the title bar of the internal frame
	 * @param icon
	 *            the icon displayed in the title bar of the internal frame
	 * @param frameContents
	 *            the contents of the internal frame
	 * @param isClosable
	 *            <code>boolean</code> indicating whether internal frame is
	 *            closable
	 * @param x
	 *            x coordinates of internal frame within the scrollable desktop.
	 *            <code>-1</code> indicates the virtual desktop is to determine
	 *            the position
	 * @param y
	 *            y coordinates of internal frame within the scrollable desktop
	 *            <code>-1</code> indicates the virtual desktop is to determine
	 *            the position
	 * 
	 * @return the JScrollInternalFrame that was created
	 */
	public JScrollInternalFrame add(DesktopListener dListener, String title,
			ImageIcon icon, JPanel frameContents, boolean isClosable, int x,
			int y) {
		JScrollInternalFrame f = new JScrollInternalFrame(title, icon,
				frameContents, isClosable);
		// f.addComponentListener(dListener);

		initAndAddFrame(f, x, y);

		return f;
	}

	/**
	 * adds an internal frame to the virtual desktop
	 * 
	 * @param dListener
	 *            a reference to the DesktopListener
	 * @param f
	 *            the internal frame to add
	 * @param x
	 *            x coordinates of internal frame within the scrollable desktop.
	 *            <code>-1</code> indicates the virtual desktop is to determine
	 *            the position
	 * @param y
	 *            y coordinates of internal frame within the scrollable desktop
	 *            <code>-1</code> indicates the virtual desktop is to determine
	 *            the position
	 */
	public void add(DesktopListener dListener, JInternalFrame f, int x, int y) {
		// f.addComponentListener(dListener);
		initAndAddFrame(f, x, y);
	}

	/**
	 * initializes the frame for display and adds it to the desktop
	 * 
	 * @param f
	 *            the internal frame to add
	 * @param x
	 *            x coordinates of internal frame within the scrollable desktop.
	 *            <code>-1</code> indicates the virtual desktop is to determine
	 *            the position
	 * @param y
	 *            y coordinates of internal frame within the scrollable desktop
	 *            <code>-1</code> indicates the virtual desktop is to determine
	 *            the position
	 */
	private void initAndAddFrame(JInternalFrame f, int x, int y) {
		// override the position of this window?
		if ((x != -1) && (y != -1)) {
			f.setLocation(x, y);
		} else {
			if (!getAutoTile()) {
				f.setLocation(cascadeInternalFrame(f));
			}
		}

		getDesktopPane().add(f);

		// select the newly added frame (note that must select it AFTER it
		// has been added to the desktopPane)
		// try {
		// // f.setSelected(true);
		// } catch (java.beans.PropertyVetoException e) {
		// // swallow property veto exceptions here..!!
		// }

		// resize desktop when add a frame in case any frames off screen
		resizeDesktop();
	}

	/**
	 * returns all internal frames placed upon the virtual desktop
	 * 
	 * @return a JInternalFrame array containing references to the internal
	 *         frames
	 */
	public JInternalFrame[] getAllFrames() {
		return getDesktopPane().getAllFrames();
	}

	/**
	 * returns the internal frame currently selected upon the virtual desktop
	 * 
	 * @return a reference to the active JInternalFrame
	 */
	public JInternalFrame getSelectedFrame() {
		return getDesktopPane().getSelectedFrame();
	}

	/**
	 * closes the internal frame currently selected upon the virtual desktop
	 */
	public void closeSelectedFrame() {
		JInternalFrame f = getSelectedFrame();

		if (f != null) {
			f.doDefaultCloseAction();
		}
	}

	/**
	 * selects the specified internal frame upon the virtual desktop
	 * 
	 * @param f
	 *            the internal frame to select
	 */
	public void setSelectedFrame(JInternalFrame f) {
		try {
			JInternalFrame currentFrame = getDesktopPane().getSelectedFrame();

			if (currentFrame != null) {
				currentFrame.setSelected(false);
			}

			f.setSelected(true);
			f.setIcon(false); // de-iconify it (if iconified)
		} catch (java.beans.PropertyVetoException pve) {
			System.out.println(pve.getMessage());
		}
	}

	/**
	 * flags the specified internal frame as "contents changed." Used to notify
	 * the user when the contents of an inactive internal frame have changed.
	 * 
	 * @param f
	 *            the internal frame to flag as "contents changed"
	 */
	public void flagContentsChanged(JInternalFrame f) {
		if (getDesktopPane().getSelectedFrame() != f) {
			RootToggleButton button = (RootToggleButton) ((JScrollInternalFrame) f)
					.getAssociatedButton();
			button.flagContentsChanged(true);
		}
	}

	/**
	 * selects the next available frame upon the virtual desktop
	 */
	public void selectNextFrame() {
		JInternalFrame[] frames = getAllFrames();

		if (frames.length > 0) {
			try {
				frames[0].setSelected(true);
			} catch (java.beans.PropertyVetoException pve) {
				System.out.println("Bean veto: " + pve.getMessage());
			}
		}
	}

	/**
	 * returns the number of internal frames upon the virtual desktop
	 * 
	 * @return an <code>int</code> representing the number of internal frames
	 */
	public int getNumberOfFrames() {
		return getDesktopPane().getComponentCount();
	}

	/**
	 * sets the preferred size of the virtual desktop
	 * 
	 * @param dim
	 *            a Dimension object representing the desired preferred size
	 */
	public void setDesktopSize(Dimension dim) {
		getDesktopPane().setPreferredSize(dim);
		getDesktopPane().revalidate();
	}

	/**
	 * returns the preferred size of the virtual desktop
	 * 
	 * @return a Dimension object representing the current preferred size
	 */
	public Dimension getDesktopSize() {
		return getDesktopPane().getPreferredSize();
	}

	// ///
	// positioning methods
	// ///

	/**
	 * propogates setAutoTile to FramePositioning
	 * 
	 * @param autoTile
	 *            <code>boolean</code> representing autoTile mode. If
	 *            <code>true</code>, then all new frames are tiled
	 *            automatically. If <code>false</code>, then all new frames are
	 *            cascaded automatically.
	 */
	public void setAutoTile(boolean autoTile) {
		positioning.setAutoTile(autoTile);
	}

	/**
	 * propogates getAutoTile to FramePositioning
	 * 
	 * @return <code>boolean</code> representing current autoTile mode
	 */
	public boolean getAutoTile() {
		return positioning.getAutoTile();
	}

	/**
	 * propogates cascadeInternalFrame to FramePositioning
	 * 
	 * @param f
	 *            the internal frame to cascade
	 * 
	 * @return a Point object representing the position of the internal frame
	 */
	public Point cascadeInternalFrame(JInternalFrame f) {
		return positioning.cascadeInternalFrame(f);
	}

	/**
	 * propogates cascadeInternalFrames to FramePositioning
	 */
	public void cascadeInternalFrames() {
		positioning.cascadeInternalFrames();
	}

	/**
	 * propogates tileInternalFrames to FramePositioning
	 */
	public void tileInternalFrames() {
		positioning.tileInternalFrames();
	}

	/**
	 * centers the viewport of the virtual desktop around the provided internal
	 * frame
	 * 
	 * @param f
	 *            the internal frame to center the viewport about
	 */
	public void centerView(JScrollInternalFrame f) {
		// set the view centered around this item
		Rectangle viewP = getViewport().getViewRect();
		int xCoords = (f.getX() + (f.getWidth() / 2)) - (viewP.width / 2);
		int yCoords = (f.getY() + (f.getHeight() / 2)) - (viewP.height / 2);

		Dimension desktopSize = getDesktopSize();

		if ((xCoords + viewP.width) > desktopSize.width) {
			xCoords = desktopSize.width - viewP.width;
		} else if (xCoords < 0) {
			xCoords = 0;
		}

		if ((yCoords + viewP.height) > desktopSize.height) {
			yCoords = desktopSize.height - viewP.height;
		} else if (yCoords < 0) {
			yCoords = 0;
		}

		getViewport().setViewPosition(new Point(xCoords, yCoords));
	}

	/**
	 * resizes the virtual desktop based upon the locations of its internal
	 * frames. This updates the desktop scrollbars in real-time. Executes as an
	 * "invoked later" thread for a slight perceived performance boost.
	 */
	public void resizeDesktop() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// has to go through all the internal frames now and make sure
				// none
				// off screen, and if so, add those scroll bars!
				Rectangle viewP = getViewport().getViewRect();

				int maxX = viewP.width + viewP.x;
				int maxY = viewP.height + viewP.y;
				int minX = viewP.x;
				int minY = viewP.y;

				// determine the min/max extents of all internal frames
				JInternalFrame f = null;
				JInternalFrame[] frames = getAllFrames();

				for (int i = 0; i < frames.length; i++) {
					f = frames[i];

					if (f.getX() < minX) { // get minimum X
						minX = f.getX();
					}

					if ((f.getX() + f.getWidth()) > maxX) {
						maxX = f.getX() + f.getWidth();
					}

					if (f.getY() < minY) { // get minimum Y
						minY = f.getY();
					}

					if ((f.getY() + f.getHeight()) > maxY) {
						maxY = f.getY() + f.getHeight();
					}
				}

				setVisible(false); // don't update the viewport while we move

				// everything (otherwise desktop looks 'bouncy')
				if ((minX != 0) || (minY != 0)) {
					// have to scroll it to the right or up the amount that it's
					// off screen...
					// before scroll, move every component to the right / down
					// by that amount
					for (int i = 0; i < frames.length; i++) {
						f = frames[i];
						f.setLocation(f.getX() - minX, f.getY() - minY);
					}

					// have to scroll (set the viewport) to the right or up the
					// amount
					// that it's off screen...
					JViewport view = getViewport();
					view.setViewSize(new Dimension((maxX - minX), (maxY - minY)));
					view.setViewPosition(new Point((viewP.x - minX),
							(viewP.y - minY)));
					setViewport(view);
				}

				// resize the desktop
				setDesktopSize(new Dimension(maxX - minX, maxY - minY));

				setVisible(true); // update the viewport again
			}
		});
	}

	/**
	 * propogates removeAssociatedComponents to DesktopMediator
	 * 
	 * @param f
	 *            the internal frame whose associated components are to be
	 *            removed
	 */
	public void removeAssociatedComponents(JScrollInternalFrame f) {
		desktopMediator.removeAssociatedComponents(f);
	}

	public RootDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(RootDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}
}
