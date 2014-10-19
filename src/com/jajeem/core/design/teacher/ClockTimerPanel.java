package com.jajeem.core.design.teacher;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.alee.utils.swing.AncestorAdapter;
import com.alee.utils.swing.Timer;
import com.jajeem.util.CustomPanel;
import com.jajeem.util.Threading.ThreadManager;

import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

public class ClockTimerPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblHour1;
	private JLabel lblHour2;
	private JLabel lblMin1;
	private JLabel lblMin2;

	final DateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	/**
	 * Create the panel.
	 */
	ImageIcon _0Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/0.png"));

	ImageIcon _1Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/1.png"));

	ImageIcon _2Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/2.png"));

	ImageIcon _3Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/3.png"));

	ImageIcon _4Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/4.png"));

	ImageIcon _5Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/5.png"));

	ImageIcon _6Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/6.png"));

	ImageIcon _7Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/7.png"));

	ImageIcon _8Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/8.png"));

	ImageIcon _9Icon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/9.png"));
	ImageIcon _ddIcon = new ImageIcon(
			InstructorNoa.class.getResource("/icons/noa_en/clockdd.png"));
	ImageIcon _ddIconScaled = new ImageIcon(_ddIcon.getImage()
			.getScaledInstance(10, 25, Image.SCALE_SMOOTH));
	private JLabel lblSystemTime;
	
	public ClockTimerPanel() {
		
		setOpaque(false);
		
		CustomPanel customPanel = new CustomPanel("/icons/noa_en/clockPanel.png");
		customPanel.setOpaque(false);
		add(customPanel);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		
		lblHour1 = new JLabel();
		lblHour1.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour1.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));
		
		lblHour2 = new JLabel();
		lblHour2.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour2.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));
		
		JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setIcon(_ddIconScaled);
		
		
		lblMin1 = new JLabel();
		lblMin1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin1.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));
		
		lblMin2 = new JLabel();
		lblMin2.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin2.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 166, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(6)
					.addComponent(lblHour1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHour2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMin1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMin2)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 67, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblMin2, Alignment.LEADING, 26, 45, 96)
						.addComponent(lblMin1, Alignment.LEADING, 26, 45, 96)
						.addComponent(label_2, Alignment.LEADING, 26, 45, 96)
						.addComponent(lblHour2, Alignment.LEADING, 26, 45, 96)
						.addComponent(lblHour1, Alignment.LEADING, 26, 45, 96))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblDate = new JLabel("00/00/0000");
		lblDate.setText(dateFormat.format(cal.getTime()).split(" ")[0]);
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		lblSystemTime = new JLabel("");
		lblSystemTime.setForeground(Color.WHITE);
		lblSystemTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, 80, 166, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDate)
							.addGap(18)
							.addComponent(lblSystemTime, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDate)
						.addComponent(lblSystemTime, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		GroupLayout gl_customPanel = new GroupLayout(customPanel);
		gl_customPanel.setHorizontalGroup(
			gl_customPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 196, Short.MAX_VALUE)
				.addGroup(gl_customPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 176, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_customPanel.setVerticalGroup(
			gl_customPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 142, Short.MAX_VALUE)
				.addGroup(gl_customPanel.createSequentialGroup()
					.addGap(4)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 138, Short.MAX_VALUE))
		);
		customPanel.setLayout(gl_customPanel);

		initEvents();
	}

	private void initEvents() {
		addAncestorListener(new AncestorAdapter() {
			public void ancestorAdded(AncestorEvent arg0) {
				lblHour1.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(lblHour1.getWidth(),
						lblHour1.getHeight(), Image.SCALE_SMOOTH)));
				lblHour2.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(lblHour2.getWidth(),
						lblHour2.getHeight(), Image.SCALE_SMOOTH)));
				lblMin1.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(lblMin1.getWidth(),
						lblMin1.getHeight(), Image.SCALE_SMOOTH)));
				lblMin2.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(lblMin2.getWidth(),
						lblMin2.getHeight(), Image.SCALE_SMOOTH)));
			}
		});
		
		

		ImageIcon _dIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/clockdd.png"));
		new ImageIcon(_dIcon.getImage().getScaledInstance(15, 15,
				Image.SCALE_SMOOTH));

		final HashMap<String, ImageIcon> clockIcons = new HashMap<String, ImageIcon>();
		clockIcons.put("0", _0Icon);
		clockIcons.put("1", _1Icon);
		clockIcons.put("2", _2Icon);
		clockIcons.put("3", _3Icon);
		clockIcons.put("4", _4Icon);
		clockIcons.put("5", _5Icon);
		clockIcons.put("6", _6Icon);
		clockIcons.put("7", _7Icon);
		clockIcons.put("8", _8Icon);
		clockIcons.put("9", _9Icon);

		Timer timer = new Timer(60000, new ActionListener() {
			long currentTime = 0;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTime += 60000;
				long minutes = currentTime / 60000;
				long minute = minutes % 60 ;
				long hour = (minutes - minute) / 60;
				
				
				final String Minute = String.format("%02d",minute);
				final String Hour = String.format("%02d",hour);
				
				try {
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							try {
								lblHour1.setIcon(new ImageIcon(
										clockIcons
												.get(String.valueOf(Hour
														.charAt(0)))
												.getImage()
												.getScaledInstance(
														lblHour1.getWidth(),
														lblHour1.getHeight(),
														Image.SCALE_SMOOTH)));
								lblHour2.setIcon(new ImageIcon(
										clockIcons
												.get(String.valueOf(Hour
														.charAt(1)))
												.getImage()
												.getScaledInstance(
														lblHour2.getWidth(),
														lblHour2.getHeight(),
														Image.SCALE_SMOOTH)));
								lblMin1.setIcon(new ImageIcon(clockIcons
										.get(String.valueOf(Minute
												.charAt(0)))
										.getImage()
										.getScaledInstance(
												lblMin1.getWidth(),
												lblMin1.getHeight(),
												Image.SCALE_SMOOTH)));
								lblMin2.setIcon(new ImageIcon(clockIcons
										.get(String.valueOf(Minute
												.charAt(1)))
										.getImage()
										.getScaledInstance(
												lblMin2.getWidth(),
												lblMin2.getHeight(),
												Image.SCALE_SMOOTH)));
								lblHour1.repaint();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		timer.setInitialDelay(60000);
		timer.start();
		
		ThreadManager.getInstance("ClockTimerPanel").runSingle(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Calendar cal = Calendar.getInstance();
					String CurrentTime = dateFormat.format(cal.getTime());
					final String time = CurrentTime.split(" ")[1];
					
					try {
						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								lblSystemTime.setText(time);
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
