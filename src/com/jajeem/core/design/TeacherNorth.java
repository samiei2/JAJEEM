package com.jajeem.core.design;

import javax.swing.ImageIcon;

import com.alee.laf.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;

public class TeacherNorth {

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
		
		ImageIcon imgToolTip = new ImageIcon("icons/menubar/tooltip.png");
		TooltipManager.setDefaultDelay(1000);
		
		WebButton settingButton = new WebButton();
		settingButton = WebButton.createIconWebButton(new ImageIcon(
				"icons/menubar/setting.png"),
				StyleConstants.smallRound, true);
		TooltipManager.setTooltip(settingButton, imgToolTip,
				"Extra options will be added here!", TooltipWay.down);
		toolbar.add(settingButton);
		
		toolbar.addSeparator();
		
		WebButton AdminButton = new WebButton();
		AdminButton = WebButton.createIconWebButton(new ImageIcon(
				"icons/menubar/setting.png"),
				StyleConstants.smallRound, true);
		TooltipManager.setTooltip(AdminButton, imgToolTip,
				"Extra options will be added here!", TooltipWay.down);
		toolbar.add(AdminButton);
	}
}
