package com.jajeem.core.design.teacher;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import org.jscroll.JScrollDesktopPane;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.jajeem.core.design.ui.CustomTeacherFrame2;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.i18n;

public class CopyOfInstructorNoa {
	private CustomTeacherFrame2 main_frame;
	private JLabel lblLogo;
	private JLabel lblLogoText;
	private JScrollDesktopPane scrollDesktopPane;
	Font fontRightPanel = new Font("Arial", Font.BOLD, 18);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CopyOfInstructorNoa test = new CopyOfInstructorNoa();
					JInternalFrame f = new JInternalFrame("test");
					f.setSize(120, 120);
					f.setVisible(true);
					test.scrollDesktopPane.add(f);
				} catch (Exception e) {
					JajeemExceptionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public CopyOfInstructorNoa() throws Exception {
//		UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

		new Config();
		new i18n();
		initializeUI();
		initializeUI_Events();
		main_frame.setVisible(true);
	}

	private CustomTeacherFrame2 initializeUI() throws Exception {
		main_frame = new CustomTeacherFrame2();
		
		JPanel panel_Top = new JPanel();
		panel_Top.setOpaque(false);
		panel_Top.setBackground(Color.GREEN);
		GroupLayout groupLayout = new GroupLayout(main_frame.getTopContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_Top, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_Top, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
		);
		
		lblLogo = new JLabel("");
		
		ClockTimerPanel clockTimerPanel = new ClockTimerPanel();
		
		lblLogoText = new JLabel("");
		GroupLayout gl_panel_Top = new GroupLayout(panel_Top);
		gl_panel_Top.setHorizontalGroup(
			gl_panel_Top.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Top.createSequentialGroup()
					.addGap(22)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblLogoText, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 890, Short.MAX_VALUE)
					.addComponent(clockTimerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_Top.setVerticalGroup(
			gl_panel_Top.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Top.createSequentialGroup()
					.addGroup(gl_panel_Top.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_Top.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLogoText, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_Top.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel_Top.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblLogo, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
							.addComponent(clockTimerPanel, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panel_Top.setLayout(gl_panel_Top);
		main_frame.getTopContentPane().setLayout(groupLayout);
		
		JPanel panel_Main = new JPanel();
		panel_Main.setOpaque(false);
		panel_Main.setBackground(Color.ORANGE);
		GroupLayout groupLayout_1 = new GroupLayout(main_frame.getMainContentPane());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_Main, GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_Main, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
		);
		
		JPanel panel_right = new JPanel();
		
		JPanel panel_Bottom = new JPanel();
		
		JPanel panel_Desktop = new JPanel();
		GroupLayout gl_panel_Main = new GroupLayout(panel_Main);
		gl_panel_Main.setHorizontalGroup(
			gl_panel_Main.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Main.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Main.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_Desktop, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
						.addComponent(panel_Bottom, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_right, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_Main.setVerticalGroup(
			gl_panel_Main.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_Main.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Main.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Main.createSequentialGroup()
							.addComponent(panel_Desktop, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_Bottom, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_right, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_Desktop.setLayout(new CardLayout(0, 0));
		
		JPanel panel_ThumbsView = new JPanel();
		panel_Desktop.add(panel_ThumbsView, "thumbs");
		
		scrollDesktopPane = new JScrollDesktopPane();
		GroupLayout gl_panel_ThumbsView = new GroupLayout(panel_ThumbsView);
		gl_panel_ThumbsView.setHorizontalGroup(
			gl_panel_ThumbsView.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollDesktopPane, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
		);
		gl_panel_ThumbsView.setVerticalGroup(
			gl_panel_ThumbsView.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollDesktopPane, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
		);
		panel_ThumbsView.setLayout(gl_panel_ThumbsView);
		panel_Main.setLayout(gl_panel_Main);
		main_frame.getMainContentPane().setLayout(groupLayout_1);
		return main_frame;
	}

	private void initializeUI_Events() {
		main_frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				BufferedImage logo = null;
				BufferedImage logoText = null;
				try {
					logo = ImageIO.read(getClass().getResource("/icons/noa_en/logoteacher.png"));
					logoText = ImageIO.read(getClass().getResource("/icons/noa_en/classmatelogo.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				ImageIcon scaledLogo = new ImageIcon(logo.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH));
				lblLogo.setIcon(scaledLogo);
				ImageIcon scaledLogoText = new ImageIcon(logoText.getScaledInstance(lblLogoText.getWidth(), lblLogoText.getHeight(), Image.SCALE_SMOOTH));
				lblLogoText.setIcon(scaledLogoText);
				super.componentShown(e);
			}
		});
	}
}

