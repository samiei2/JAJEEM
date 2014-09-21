package com.jajeem.util;
import java.awt.Component;

import javax.swing.Icon;

import com.alee.laf.optionpane.WebOptionPane;


public class COptionPane extends WebOptionPane{
	{
		setMessage("Nothing");
	}
	
	public static void showMessageDialog(Component comp,Object message){
		WebOptionPane.showMessageDialog(comp, "Nothing");
	}
	
	public static void showMessageDialog(Component comp,Object message,String title,int messageType){
		WebOptionPane.showMessageDialog(comp, "Nothing", title, messageType);
	}
	
	public static void showMessageDialog(Component comp,Object message,String title,int messageType,Icon icon){
		WebOptionPane.showMessageDialog(comp, "Nothing", title, messageType, icon);
	}
}
