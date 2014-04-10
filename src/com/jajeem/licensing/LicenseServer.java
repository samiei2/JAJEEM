package com.jajeem.licensing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LicenseServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(hanldeServerRequest("http://yahoo.com/","?arnmin,as:us here?"));
	}

	public static String hanldeServerRequest(String server,String content){

        URL url = null;
        try {
            url = new URL(server);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpURLConnection urlConn = null;
        try {
            // URL connection channel.
            urlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Let the run-time system (RTS) know that we want input.
        urlConn.setDoInput (true);

        // Let the RTS know that we want to do output.
        urlConn.setDoOutput (true);

        // No caching, we want the real thing.
        urlConn.setUseCaches (false);

        try {
            urlConn.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            urlConn.connect();
        } catch (IOException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataOutputStream output = null;
        DataInputStream input = null;

        try {
            output = new DataOutputStream(urlConn.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Specify the content type if needed.
        //urlConn.setRequestProperty("Content-Type",
        //  "application/x-www-form-urlencoded");

        // Construct the POST data.

        // Send the request data.
        try {
            output.writeBytes(content);
            output.flush();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get response data.
        int str ;
        String response = "";
        try {
            input = new DataInputStream (urlConn.getInputStream());
            while ((str = input.read())!=-1) {
                response+=(char)str;
            }
            input.close ();
        } catch (IOException ex) {
            Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
	}
	
}
