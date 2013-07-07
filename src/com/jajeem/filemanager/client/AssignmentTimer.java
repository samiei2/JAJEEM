package com.jajeem.filemanager.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.label.WebLabel;
import com.alee.laf.button.WebButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

public class AssignmentTimer extends JDialog {

	private JPanel contentPane;
	
	private Timer timer; // Updates the count every second
	private long remaining; // How many milliseconds remain in the countdown.
	private long lastUpdate; // When count was last updated
	private int total;
	private WebLabel webLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AssignmentTimer frame = new AssignmentTimer();
					frame.setVisible(true);
					frame.start("12");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AssignmentTimer() {
		setResizable(false);
		setType(Type.POPUP);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 321, 101);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		webLabel = new WebLabel();
		
		WebButton wbtnClose = new WebButton();
		wbtnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		wbtnClose.setText("Close");
		
		WebLabel wblblTimeLeft = new WebLabel();
		wblblTimeLeft.setText("Time Left : ");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(wblblTimeLeft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(122)
							.addComponent(wbtnClose, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(webLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(wblblTimeLeft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnClose, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public void start(String time) {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateDisplay();
			}

			private void updateDisplay() {
				NumberFormat format = NumberFormat.getInstance();

				long now = System.currentTimeMillis(); // current time in ms
				long elapsed = now - lastUpdate; // ms elapsed since last
													// update
				remaining -= elapsed; // adjust remaining time
				lastUpdate = now; // remember this update time
				// Convert remaining milliseconds to mm:ss format and
				// display
				if (remaining < 0)
					remaining = 0;
				int minutes = (int) (remaining / 60000);
				int seconds = (int) ((remaining % 60000) / 1000);
				webLabel.setText(format.format(minutes) + ":"
						+ format.format(seconds));

				// If we've completed the countdown beep and display new
				// page
				if (remaining == 0) {
					// Stop updating now.
					timer.stop();
				}
			}
		};
		
		if (!time.equals("0")) {
			webLabel.setText(time
					.concat(":00"));
			remaining = Integer.parseInt(time) * 60000;
			timer = new Timer(1000, taskPerformer);
			timer.setInitialDelay(0);
			lastUpdate = System.currentTimeMillis();
			timer.start();
		} 

	}
}
