package com.jajeem.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class LGPDemo extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GPanel gp;

	public LGPDemo() {
		super("LinearGradientPaint Demo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlContent = new JPanel();
		pnlContent.setLayout(new BorderLayout());

		JRadioButton rbCycleNone = new JRadioButton("No Cycle");
		rbCycleNone.setMnemonic(KeyEvent.VK_N);
		rbCycleNone.setActionCommand("A");
		rbCycleNone.setSelected(true);

		JRadioButton rbCycleReflect = new JRadioButton("Reflect");
		rbCycleReflect.setMnemonic(KeyEvent.VK_F);
		rbCycleReflect.setActionCommand("B");

		JRadioButton rbCycleRepeat = new JRadioButton("Repeat");
		rbCycleRepeat.setMnemonic(KeyEvent.VK_P);
		rbCycleRepeat.setActionCommand("C");

		ButtonGroup group = new ButtonGroup();
		group.add(rbCycleNone);
		group.add(rbCycleReflect);
		group.add(rbCycleRepeat);

		rbCycleNone.addActionListener(this);
		rbCycleReflect.addActionListener(this);
		rbCycleRepeat.addActionListener(this);

		JPanel pnlCycleMethod = new JPanel(new GridLayout(1, 0));
		pnlCycleMethod.setBorder(BorderFactory.createEtchedBorder());
		pnlCycleMethod.add(rbCycleNone);
		pnlCycleMethod.add(rbCycleReflect);
		pnlCycleMethod.add(rbCycleRepeat);

		pnlContent.add(pnlCycleMethod, BorderLayout.NORTH);

		pnlContent.add(gp = new GPanel());

		JRadioButton rbsRGB = new JRadioButton("sRGB");
		rbsRGB.setMnemonic(KeyEvent.VK_S);
		rbsRGB.setActionCommand("D");
		rbsRGB.setSelected(true);

		JRadioButton rbLinearRGB = new JRadioButton("Linear RGB");
		rbLinearRGB.setMnemonic(KeyEvent.VK_L);
		rbLinearRGB.setActionCommand("E");

		group = new ButtonGroup();
		group.add(rbsRGB);
		group.add(rbLinearRGB);

		rbsRGB.addActionListener(this);
		rbLinearRGB.addActionListener(this);

		JPanel pnlColorSpaceType = new JPanel(new GridLayout(1, 0));
		pnlCycleMethod.setBorder(BorderFactory.createEtchedBorder());
		pnlColorSpaceType.add(rbsRGB);
		pnlColorSpaceType.add(rbLinearRGB);

		pnlContent.add(pnlColorSpaceType, BorderLayout.SOUTH);

		setContentPane(pnlContent);

		setSize(400, 400);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String ac = ae.getActionCommand();

		if (ac.equals("A")) {
			gp.setCycleMethod(MultipleGradientPaint.CycleMethod.NO_CYCLE);
		} else if (ac.equals("B")) {
			gp.setCycleMethod(MultipleGradientPaint.CycleMethod.REFLECT);
		} else if (ac.equals("C")) {
			gp.setCycleMethod(MultipleGradientPaint.CycleMethod.REPEAT);
		} else if (ac.equals("D")) {
			gp.setColorSpaceType(MultipleGradientPaint.ColorSpaceType.SRGB);
		} else if (ac.equals("E")) {
			gp.setColorSpaceType(MultipleGradientPaint.ColorSpaceType.LINEAR_RGB);
		}
	}

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				new LGPDemo();
			}
		};
		EventQueue.invokeLater(r);
	}
}

class GPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MultipleGradientPaint mgp;

	private MultipleGradientPaint.ColorSpaceType cst = MultipleGradientPaint.ColorSpaceType.SRGB;

	private MultipleGradientPaint.CycleMethod cm = MultipleGradientPaint.CycleMethod.NO_CYCLE;

	private AffineTransform at;

	GPanel() {
		at = new AffineTransform();
		createGradientPaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (mgp == null) {
			createGradientPaint();
		}

		((Graphics2D) g).setPaint(mgp);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	void setColorSpaceType(MultipleGradientPaint.ColorSpaceType cst) {
		this.cst = cst;
		createGradientPaint();
		repaint();
	}

	void setCycleMethod(MultipleGradientPaint.CycleMethod cm) {
		this.cm = cm;
		createGradientPaint();
		repaint();
	}

	private void createGradientPaint() {
		Point2D start = new Point2D.Float(10, 10);
		Point2D end = new Point2D.Float(-150, 120);
		float[] dist = { 0.0f, 0.1f, 0.2f };
		Color[] colors = { new Color(77, 178, 184), new Color(174, 215, 222),
				new Color(42, 105, 87) };
		mgp = new LinearGradientPaint(start, end, dist, colors, cm, cst, at);
	}
}