package com.jajeem.core.design;

import javax.swing.ImageIcon;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.hotkey.Hotkey;

public class TeacherNorth {
	
	public static WebPanel createPanel (WebPanel panel) {
		WebMenuBar menuBar = new WebMenuBar();
		menuBar.setUndecorated(true);
		setupMenuBar(menuBar);
		panel.add(menuBar);
		
		return panel;
	}
	
	@SuppressWarnings("serial")
	public static void setupMenuBar(WebMenuBar menuBar) {
		menuBar.add(new WebMenu("Setting", new ImageIcon(
				"icons/menubar/setting.png")) {
			{

				add(new WebMenuItem("Edit class", new ImageIcon(
						"icons/menubar/edit.png")));
				addSeparator();

				add(new WebMenuItem("New text document", new ImageIcon(
						"icons/menubar/file_doc.png")));
				add(new WebMenuItem("New excel table", new ImageIcon(
						"icons/menubar/file_xls.png")));
				add(new WebMenuItem("New presentation", new ImageIcon(
						"icons/menubar/file_ppt.png")));
				addSeparator();
				add(new WebMenuItem("Exit", new ImageIcon(
						"icons/menubar/file_exit.png")) {
					{
						setHotkey(Hotkey.ALT_F4);
					}
				});
			}
		});
	}
}
