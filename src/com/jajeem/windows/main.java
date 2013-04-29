package com.jajeem.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class main extends JFrame {

	private JPanel contentPane;

	private static void createAndShowGUI()
    {
        JFrame frame = new JFrame("Card Layout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // This JPanel is the base for CardLayout for other JPanels.
        final JPanel contentPane = new JPanel();
        contentPane.setLayout(new CardLayout(20, 20));

        /* Here we be making objects of the Window Series classes
         * so that, each one of them can be added to the JPanel 
         * having CardLayout. 
         */
        window1 win1 = new window1();
        contentPane.add(win1, "");
        window2 win2 = new window2();
        contentPane.add(win2, "");
        window3 win3 = new window3();
        contentPane.add(win3, "");

        /* We need two JButtons to go to the next Card
         * or come back to the previous Card, as and when
         * desired by the User.
         */
        JPanel buttonPanel = new JPanel(); 
        final JButton previousButton = new JButton("PREVIOUS");
        previousButton.setBackground(Color.BLACK);
        previousButton.setForeground(Color.WHITE);
        final JButton nextButton = new JButton("NEXT");
        nextButton.setBackground(Color.RED);
        nextButton.setForeground(Color.WHITE);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        /* Adding the ActionListeners to the JButton,
         * so that the user can see the next Card or
         * come back to the previous Card, as desired.
         */
        previousButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.previous(contentPane);
            }
        });
        nextButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);   
            }
        });

        // Adding the contentPane (JPanel) and buttonPanel to JFrame.
        frame.add(contentPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
