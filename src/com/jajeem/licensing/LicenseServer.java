package com.jajeem.licensing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jajeem.util.initDatabase;

public class LicenseServer {

	private boolean Available;

	/**
	 * @param args
	 * @throws LicenseServerErrorException
	 */
	public static void main(String[] args) throws LicenseServerErrorException {

		System.out.println(new LicenseServer().hanldeServerRequest(
				"http://www.qugram.com/api/validate",
				" {\"hardwareKey\" : \"jhgfh786vfee87\","
						+ "\"serialNumber\" : \"jhwwgj7vfvf76\","
						+ "\"name\" : \"arbi\","
						+ "\"startDate\" : \"2014-02-08\"} "));
	}

	public String hanldeServerRequest(String server, String content)
			throws LicenseServerErrorException {

		if (content == null || content == "")
			throw new LicenseServerErrorException(-1);

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
			if (responseCode != 200)
				throw new LicenseServerErrorException(responseCode);

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

	public String excutePost(String targetURL, String urlParameters) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public boolean isAvailable() {
		return Available;
	}

	public void setAvailable(boolean isAvailable) {
		Available = isAvailable;
	}

	public void ValidateOnline(License decLic)
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey {
		LicenseModel jsonModel = new LicenseModel();
		jsonModel.setName(decLic.getLicenseInfo().get("name"));
		jsonModel.setHardwareKey(decLic.getLicenseInfo().get("hardwarekey"));
		jsonModel.setSerialNumber(decLic.getLicenseInfo().get("serialnumber"));
		jsonModel.setStartDate(decLic.getLicenseInfo().get("startdate"));
		jsonModel.setActivationCode(decLic.getLicenseInfo().get(
				"activationcode"));

		JsonConvert converter = new JsonConvert();
		String jsonQuery = null;
		try {
			jsonQuery = converter.ConvertToJson(jsonModel);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String jsonResponse = hanldeServerRequest(ServerList.getDefault(),
				jsonQuery);
		LicenseModel respondedLicense = converter.ConvertFromJson(jsonResponse,
				LicenseModel.class);
		ValidateLicense(respondedLicense);
	}

	private void ValidateLicense(LicenseModel respondedLicense)
			throws InvalidLicenseException, InvalidActivationKey {
		if (respondedLicense == null)
			throw new InvalidLicenseException();

		if (!respondedLicense.isValid())
			throw new InvalidLicenseException();

		CheckSerialNumber(respondedLicense);
		CheckHardWareKey(respondedLicense);
		CheckStartDate(respondedLicense);
		CheckActivationCode(respondedLicense);
		CheckTrialValidity(respondedLicense);
	}

	private void CheckStartDate(LicenseModel respondedLicense) {
		
	}

	private void CheckHardWareKey(LicenseModel respondedLicense) {
		
	}

	private void CheckSerialNumber(LicenseModel respondedLicense) {
		
	}

	private void CheckTrialValidity(LicenseModel respondedLicense) {
		if (respondedLicense.get) {
			
		} else {

		}
	}

	private void CheckActivationCode(LicenseModel respondedLicense)
			throws InvalidActivationKey {
		if (respondedLicense.getActivationCode() == null
				|| respondedLicense.getActivationCode() == "")
			;
		else {
			ActivationCode code = new ActivationCode(respondedLicense.getActivationCode(),
					respondedLicense);
			code.validate();
		}
	}
}
