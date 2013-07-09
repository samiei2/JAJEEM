/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Armin
 */
public class test {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
//        	try {
//    			final Process proc = Runtime
//    					.getRuntime()
//    					.exec("java -jar WhiteboardServer.jar "
//    					+"2019"+" "+"2020",null,new File("util/"));
//    			new Thread(new Runnable() {
//    				
//    				@Override
//    				public void run() {
//    					try {
//    						BufferedReader in = new BufferedReader(  
//    	                            new InputStreamReader(proc.getInputStream()));  
//    					        String line = null;  
//    					        while ((line = in.readLine()) != null) {  
//    					            System.out.println(line);  
//    					        }  
//    					} catch (Exception e) {
//    						// TODO: handle exception
//    					}
//    				}
//    			}).start();
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//    		
//    		try {
////    			builder = new ProcessBuilder("java -jar WhiteboardServer.jar "+"2019"+" "+"2020");
//
//    			final Process proc = Runtime.getRuntime().exec("java -jar WhiteboardTeacher.jar "+"2019"+" "+"2020");
//    			new Thread(new Runnable() {
//    				
//    				@Override
//    				public void run() {
//    					try {
//    						BufferedReader in = new BufferedReader(  
//    	                            new InputStreamReader(proc.getInputStream()));  
//    					        String line = null;  
//    					        while ((line = in.readLine()) != null) {  
//    					            System.out.println(line);  
//    					        }  
//    					} catch (Exception e) {
//    						// TODO: handle exception
//    					}
//    				}
//    			}).start();
////    			proc = builder.start();
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
    		
    		try {
    			final Process proc = Runtime.getRuntime().exec("java -jar WhiteboardStudent.jar "+ "192.168.227.129 " + "2003 " + "2004 " + "Armin " + "0",null,new File("util/"));
    			new Thread(new Runnable() {
    				
    				@Override
    				public void run() {
    					try {
    						BufferedReader in = new BufferedReader(  
    	                            new InputStreamReader(proc.getInputStream()));  
    					        String line = null;  
    					        while ((line = in.readLine()) != null) {  
    					            System.out.println(line);  
    					        }  
    					} catch (Exception e) {
    						// TODO: handle exception
    					}
    				}
    			}).start();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		
    		
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
}
