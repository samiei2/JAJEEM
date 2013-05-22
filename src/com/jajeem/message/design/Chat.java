package com.jajeem.message.design;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
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
	private static ServerService serverService;
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
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public Chat(String to, int port) throws NumberFormatException, Exception {		
		super("Chat");
		
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

		setIconImages(WebLookAndFeel.getImages());
		setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);

		ComponentMoveAdapter.install(getRootPane(), Chat.this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		WebList list = new WebList(listModel);
		list.setEditable(false);
		final WebScrollPane scrollPane = new WebScrollPane(list);
		scrollPane.setViewportView(list);
		splitPane.setLeftComponent(scrollPane);

		final WebTextArea textArea = new WebTextArea();
		splitPane.setRightComponent(textArea);
		
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)
						&& (!textArea.getText().equals(""))
						&& (textArea.getText() != null)) {
					listModel.addElement(textArea.getText());
					scrollPane.getVerticalScrollBar().setValue(
							scrollPane.getVerticalScrollBar().getMaximum());
					
					ChatCommand chatCommand;
					try {
						chatCommand = new ChatCommand(InetAddress
								.getLocalHost().getHostAddress(), getTo(), getPort(), textArea.getText());
						serverService.send(chatCommand);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					textArea.setText("");
				}
			}
		});
		
		WebLookAndFeel.setDecorateFrames(decorateFrames);
	}
	
	public void addLine(String text) {
		listModel.addElement(text);
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

}
