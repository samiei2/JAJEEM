package com.jajeem.licensing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;
import com.jajeem.licensing.util.JsonConvert;

public class LicenseServer {

	/**
	 * @param args
	 * @throws LicenseServerErrorException
	 */
	public static void main(String[] args) throws LicenseServerErrorException {

		System.out.println(new LicenseServer().handleServerRequest(
				"http://www.qugram.com/api/validate",
				" {\"hardwareKey\" : \"jhgfh786vfee87\","
						+ "\"serialNumber\" : \"jhwwgj7vfvf76\","
						+ "\"name\" : \"arbi\","
						+ "\"startDate\" : \"2014-02-08\"} "));
	}

	private boolean Available;

	private LicenseValidationContext context;

	private void checkAvailability() {
		try {
			InetAddress addr = InetAddress.getByName(new URL("http://www.qugram.com/").getHost());
			addr.isReachable(3000);
			// TODO Instead of above must actual licensing server address and
			// available value should be set two
			Available = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String handleServerRequest(String server, String content)
			throws LicenseServerErrorException {

		if (content == null || content == "") {
			throw new LicenseServerErrorException(-1);
		}

		URL url = null;
		try {
			url = new URL(server);
		} catch (MalformedURLException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		HttpURLConnection urlConn = null;
		try {
			// URL connection channel.
			urlConn = (HttpURLConnection) url.openConnection();
		} catch (IOException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		// Let the run-time system (RTS) know that we want input.
		urlConn.setDoInput(true);

		// Let the RTS know that we want to do output.
		urlConn.setDoOutput(true);

		// No caching, we want the real thing.
		urlConn.setUseCaches(false);

		try {
			urlConn.setRequestMethod("POST");
		} catch (ProtocolException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		try {
			urlConn.connect();
		} catch (IOException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		DataOutputStream output = null;
		DataInputStream input = null;

		try {
			output = new DataOutputStream(urlConn.getOutputStream());
		} catch (IOException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		// Specify the content type if needed.
		// urlConn.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded");

		// Construct the POST data.

		// Send the request data.
		try {
			output.writeBytes(content);
			output.flush();
			output.close();
		} catch (IOException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		// Get response data.
		int str;
		String response = "";
		try {
			int responseCode = urlConn.getResponseCode();
			if (responseCode != 200) {
				throw new LicenseServerErrorException(responseCode);
			}

			input = new DataInputStream(urlConn.getInputStream());
			while ((str = input.read()) != -1) {
				response += (char) str;
			}
			input.close();
		} catch (IOException ex) {
			Logger.getLogger(LicenseServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return response;
	}

	public boolean isAvailable() {
		checkAvailability();
		return Available;
	}

	public void setAvailable(boolean isAvailable) {
		Available = isAvailable;
	}

	public void setLicensingContext(LicenseValidationContext context) {
		this.context = context;
	}

	private void ValidateLicense(HashMap<String, String> respondedLicense)
			throws InvalidLicenseException, InvalidActivationKey,
			LicenseServerErrorException, GeneralSecurityException, IOException,
			ParseException {
		if (respondedLicense == null) {
			throw new LicenseServerErrorException(0);
		}

		if (!respondedLicense.get("valid").equals("0")) {
			throw new InvalidLicenseException();
		}

		License lic = new License();
		lic.setFilePath(context.getLicense().getFilePath());
		lic.setLicenseInfo(respondedLicense);
		lic.setContext(context);

		LicenseValidator validator = new LicenseValidator();
		validator.validate(lic);
		context.getLicense().setValid(true);
	}

	public void validateOnline(License decLic)
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey,
			UninitializedLicensingContextException, GeneralSecurityException,
			ParseException {

		if (context == null) {
			throw new UninitializedLicensingContextException();
		}

		JsonConvert converter = new JsonConvert();
		String jsonQuery = null;
		try {
			jsonQuery = converter.ConvertToJson(decLic.getLicenseInfo());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String jsonResponse = handleServerRequest(ServerList.getDefault() + "validate",
				jsonQuery);
		HashMap<String, String> respondedLicense = converter.ConvertFromJson(
				jsonResponse, HashMap.class);
		ValidateLicense(respondedLicense);
	}

	public void activateOnline(License license) throws JsonProcessingException, LicenseServerErrorException {
		JsonConvert converter = new JsonConvert();
		String json = converter.ConvertToJson(license.getLicenseInfo());
		handleServerRequest(ServerList.getDefault() + "activate", json);
	}
}
