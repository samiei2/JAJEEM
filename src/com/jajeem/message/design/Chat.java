package com.jajeem.message.design;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.text.WebTextArea;
import com.jajeem.command.model.ChatCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.StudentLogin;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.groupwork.model.Group;
import com.jajeem.util.Config;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Chat extends WebFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2055942118955727767L;
	private JPanel contentPane;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private WebList list = new WebList(getListModel());
	private WebScrollPane scrollPane = new WebScrollPane(getList());
	private File file;

	private String to = "";
	private int port;
	private boolean multi = false; // redundant!
	private int groupId = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat("", 0, false, -1, "");
					frame.setVisible(true);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public Chat(String to, int port, boolean multi, int groupId, String title)
			throws NumberFormatException, Exception {

		if (Integer.parseInt(Config.getParam("server")) == 1) {
			if (InstructorNoa.studentList.containsKey(to)) {
				setTitle(InstructorNoa.studentList.get(to).getFullName());
			} else {
				setTitle(title);
			}
		} else {
			setTitle(title);
		}

		new Config();
		File dir = new File("Messages/");
		if (!dir.exists())
			dir.mkdir();
		file = new File(dir, "/chat_" + to + ".txt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Chat.class.getResource("/icons/menubar/chat.png")));

		setTo(to);
		setPort(port);
		setMulti(multi);
		setGroupId(groupId);

		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
		boolean decorateFrames = WebLookAndFeel.isDecorateFrames();
		WebLookAndFeel.setDecorateFrames(true);

		setDefaultCloseOperation(WebFrame.HIDE_ON_CLOSE);

		ComponentMoveAdapter.install(getRootPane(), Chat.this);
		setBounds(200, 200, 450, 300);
		contentPane = new WebPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		WebPanel panel = new WebPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		WebButton btnSave = new WebButton("Save");
		btnSave.setRound(0);
		// panel.add(btnSave);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		WebSplitPane splitPane = new WebSplitPane();
		splitPane.setResizeWeight(0.9);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_1.add(splitPane);

		getList().setEditable(false);
		scrollPane.setViewportView(getList());
		splitPane.setLeftComponent(scrollPane);

		final WebTextArea textArea = new WebTextArea();
		textArea.requestFocus();
		WebScrollPane webScrollPane = new WebScrollPane(textArea);
		splitPane.setRightComponent(webScrollPane);

		JButton sendButton = new JButton("Send");
		sendButton.setHorizontalAlignment(SwingConstants.TRAILING);
		webScrollPane.setRowHeaderView(sendButton);

		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if ((!textArea.getText().equals(""))
						&& (textArea.getText() != null)) {
					getListModel().addElement("me: " + textArea.getText());
					scrollPane.getVerticalScrollBar()
							.setValue(
									scrollPane.getVerticalScrollBar()
											.getMaximum() + 10000);

					ChatCommand chatCommand = null;

					try {
						if (Integer.parseInt(Config.getParam("server")) == 1) {
							if (isMulti()) {
								Group group = InstructorNoa.getGroups().get(
										Integer.parseInt(getTo()));
								chatCommand = new ChatCommand(InetAddress
										.getLocalHost().getHostAddress(), "",
										getPort(), textArea.getText(),
										isMulti(), group.getId());
								for (String studentIp : group.getStudentIps()) {
									chatCommand.setTo(studentIp);
									InstructorNoa.getServerService().send(
											chatCommand);
								}
							} else {

								chatCommand = new ChatCommand(InetAddress
										.getLocalHost().getHostAddress(),
										getTo(), getPort(), textArea.getText(),
										isMulti(), -1);
								InstructorNoa.getServerService().send(
										chatCommand);
							}
						} else {
							chatCommand = new ChatCommand(InetAddress
									.getLocalHost().getHostAddress(),
									StudentLogin.getServerIp(), getPort(),
									textArea.getText(), isMulti(), getGroupId());
							StudentLogin.getServerService().send(chatCommand);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					textArea.setText("");
				}
			}
		});

		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)
						&& (!textArea.getText().equals(""))
						&& (textArea.getText() != null)) {
					getListModel().addElement("me: " + textArea.getText());
					scrollPane.getVerticalScrollBar()
							.setValue(
									scrollPane.getVerticalScrollBar()
											.getMaximum() + 10000);

					ChatCommand chatCommand = null;

					try {
						if (Integer.parseInt(Config.getParam("server")) == 1) {
							if (isMulti()) {
								Group group = InstructorNoa.getGroups().get(
										Integer.parseInt(getTo()));
								chatCommand = new ChatCommand(InetAddress
										.getLocalHost().getHostAddress(), "",
										getPort(), textArea.getText(),
										isMulti(), group.getId());
								for (String studentIp : group.getStudentIps()) {
									chatCommand.setTo(studentIp);
									InstructorNoa.getServerService().send(
											chatCommand);
								}
							} else {

								chatCommand = new ChatCommand(InetAddress
										.getLocalHost().getHostAddress(),
										getTo(), getPort(), textArea.getText(),
										isMulti(), -1);
								InstructorNoa.getServerService().send(
										chatCommand);
							}
						} else {
							chatCommand = new ChatCommand(InetAddress
									.getLocalHost().getHostAddress(),
									StudentLogin.getServerIp(), getPort(),
									textArea.getText(), isMulti(), getGroupId());
							StudentLogin.getServerService().send(chatCommand);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					textArea.setText("");
				}
			}
		});

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw;
					fw = new FileWriter(file.getAbsoluteFile(), true);
					BufferedWriter bw = new BufferedWriter(fw);
					DefaultListModel<String> myList = getListModel();
					int lsize = myList.size();
					for (int i = 0; i < lsize; i++) {
						Object o = myList.get(i);
						bw.write(o.toString());
					}
					bw.close();
				} catch (IOException e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}

			}
		});

		WebLookAndFeel.setDecorateFrames(decorateFrames);
		setVisible(true);
		textArea.requestFocus();
	}

	public void addLine(String text) {
		getListModel().addElement(text);
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public void scrollDown() {
		scrollPane.getVerticalScrollBar().setValue(
				scrollPane.getVerticalScrollBar().getMaximum());
	}

	public WebList getList() {
		return list;
	}

	public void setList(WebList list) {
		this.list = list;
	}

	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel<String> listModel) {
		this.listModel = listModel;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
