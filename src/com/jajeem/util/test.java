package com.jajeem.util;

    import java.awt.*;  
    import java.awt.geom.*;  
    import java.awt.image.BufferedImage;  
    import java.io.*;  
    import java.net.*;  
    import javax.imageio.ImageIO;  
    import javax.swing.*;  
       
    public class test  
    {  
        public static void main(String[] args)  
        {  
            JLabel label = new JLabel();  
            label.setPreferredSize(new Dimension(300,500));  
            JScrollPane scrollPane = new JScrollPane();  
            scrollPane.setViewport(new CustomViewport());  
            scrollPane.getViewport().setView(label);  
            JFrame f = new JFrame();  
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            f.getContentPane().add(scrollPane);  
            f.setSize(400,400);  
            f.setLocation(200,200);  
            f.setVisible(true);  
        }  
    }  
       
    