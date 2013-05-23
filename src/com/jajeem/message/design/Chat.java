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
import java.net.UnknownHostException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.text.WebTextArea;
import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.service.ServerService;

public class Chat extends WebFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2055942118955727767L;
	private JPanel contentPane;
	private DefaultListModel listModel = new DefaultListModel();
	private WebList list = new WebList(getListModel());
	private WebScrollPane scrollPane = new WebScrollPane(getList());
	private static ServerService serverService;
	private File file;

	private String to = "";
	private int port;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat("", 0);
					frame.setVisible(true);
				} catch (Exception e) {
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
	public Chat(String to, int port) throws NumberFormatException, Exception {
		super("Chat");
		file = new File("/chat" + getTo() + ".txt");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Chat.class.getResource("/menubar/chat.png")));

		setTo(to);
		setPort(port);

		serverService = new ServerService();

		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (Exception e) {
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
		panel.add(btnSave);

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
		splitPane.setRightComponent(textArea);

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

					ChatCommand chatCommand;
					try {
						chatCommand = new ChatCommand(InetAddress
								.getLocalHost().getHostAddress(), getTo(),
								getPort(), textArea.getText());
						serverService.send(chatCommand);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
					DefaultListModel myList = getListModel();
					int lsize = myList.size();
					for (int i = 0; i < lsize; i++) {
						Object o = myList.get(i);

						bw.write(o.toString());

					}
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		WebLookAndFeel.setDecorateFrames(decorateFrames);
		setVisible(true);
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

	public DefaultListModel getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel listModel) {
		this.listModel = listModel;
	}

}
