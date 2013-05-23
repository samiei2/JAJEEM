package com.jajeem.core.design;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.laf.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.WebPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;

public class InstructorTop {

	public static WebPanel createPanel(WebPanel panel) {

		WebToolBar aft = new WebToolBar(WebToolBar.HORIZONTAL);
		aft.setToolbarStyle(ToolbarStyle.attached);

		aft.setUndecorated(true);
		setupToolBar(aft);
		panel.add(aft);
		panel.setUndecorated(true);

		return panel;
	}

	private static void setupToolBar(WebToolBar toolbar) {

		final WebButton volumeButton;
		final WebPopup popup = new WebPopup();
		final WebSlider slider1 = new WebSlider(WebSlider.HORIZONTAL);

		ImageIcon imgToolTip = new ImageIcon(InstructorTop.class.getResource(
				"/icons/menubar/tooltip.png").getPath());
		TooltipManager.setDefaultDelay(1000);

		WebButton settingButton = new WebButton();

		try {
			settingButton = WebButton
					.createIconWebButton(
							new ImageIcon(
									ImageIO.read(InstructorTop.class
											.getResourceAsStream("/icons/menubar/setting.png"))),
							StyleConstants.smallRound, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TooltipManager.setTooltip(settingButton, imgToolTip,
				"Extra options will be added here!", TooltipWay.down);
		// toolbar.add(settingButton);

		// toolbar.addSeparator();

		try {
			volumeButton = WebButton
					.createIconWebButton(
							new ImageIcon(
									ImageIO.read(InstructorTop.class
											.getResourceAsStream("/icons/menubar/volume.png"))),
							StyleConstants.smallRound, true);
			TooltipManager.setTooltip(volumeButton, imgToolTip,
					"Change speaker volume of selected student",
					TooltipWay.down);
			toolbar.add(volumeButton);
			volumeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					slider1.setMinimum(0);
					slider1.setMaximum(100);
					slider1.setMinorTickSpacing(10);
					slider1.setMajorTickSpacing(50);
					slider1.setPaintTicks(false);
					slider1.setPaintLabels(false);

					popup.setPopupStyle(PopupStyle.lightSmall);
					popup.setMargin(0);
					popup.add(slider1);
					popup.setRound(0);

					if (popup.isShowing()) {
						popup.hidePopup();
						if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorCenter.desktopPane
									.getSelectedFrame().getClientProperty("ip");
							int vol = slider1.getValue();
							try {
								ServerService ss = new ServerService();
								VolumeCommand vc = new VolumeCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent, Integer
												.parseInt(Config
														.getParam("port")),
										"set", vol * 650);
								ss.send(vc);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					} else {
						popup.showPopup(volumeButton);
					}

				}

			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
