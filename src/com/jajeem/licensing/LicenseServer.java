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
import java.sql.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;
import com.jajeem.licensing.util.JsonConvert;

public class LicenseServer {

	private boolean Available;
	private LicenseValidationContext context;

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
		checkAvailability();
		return Available;
	}

	private void checkAvailability() {
		try {
			InetAddress addr = InetAddress.getByName("8.8.8.8");
			addr.isReachable(3000);
			//TODO Instead of above must actual licensing server address and available value should be set two
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAvailable(boolean isAvailable) {
		Available = isAvailable;
	}

	public void ValidateOnline(License decLic)
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey, UninitializedLicensingContextException {
		
		if(context == null)
			throw new UninitializedLicensingContextException();
		
		JsonConvert converter = new JsonConvert();
		String jsonQuery = null;
		try {
			jsonQuery = converter.ConvertToJson(decLic.getLicenseInfo());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String jsonResponse = hanldeServerRequest(ServerList.getDefault(),
				jsonQuery);
		HashMap<String, String> respondedLicense = converter.ConvertFromJson(jsonResponse,
				HashMap.class);
		ValidateLicense(respondedLicense);
	}

	private void ValidateLicense(HashMap<String, String> respondedLicense)
			throws InvalidLicenseException, InvalidActivationKey, LicenseServerErrorException {
		if (respondedLicense == null)
			throw new LicenseServerErrorException(0);

		if (!respondedLicense.get("valid").equals("0"))
			throw new InvalidLicenseException();

		CheckSerialNumber(respondedLicense);
		CheckHardWareKey(respondedLicense);
		CheckStartDate(respondedLicense);
		
		if(isActivationCodeValid(respondedLicense)){
			
		}
		else{
			CheckTrialValidity(respondedLicense);
		}
	}

	private void CheckStartDate(HashMap<String, String> respondedLicense) throws InvalidLicenseException {
		if(!context.getLicense().getLicenseInfo().get("startdate").equals(Date.valueOf(respondedLicense.get("startdate"))))
			throw new InvalidLicenseException();
	}

	private void CheckHardWareKey(HashMap<String, String> respondedLicense) throws InvalidLicenseException {
		if(!context.getLicense().getLicenseInfo().get("hardwarekey").equals(respondedLicense.get("hardwarekey")))
			throw new InvalidLicenseException();
	}

	private void CheckSerialNumber(HashMap<String, String> respondedLicense) throws InvalidLicenseException {
		if(!context.getLicense().getLicenseInfo().get("serialnumber").equals(respondedLicense.get("serialnumber")))
			throw new InvalidLicenseException();
	}

	private void CheckTrialValidity(HashMap<String, String> respondedLicense) throws InvalidLicenseException {
		if(!respondedLicense.get("valid").equals("0"))
			throw new InvalidLicenseException();
		if(!context.getLicense().isIsValid())
			throw new InvalidLicenseException();
		
		Date startTime = Date.valueOf(respondedLicense.get("startdate"));
		Date endTime = Date.valueOf(respondedLicense.get("expiredate"));
		Date systemTime = new Date(System.currentTimeMillis());
		
		if (startTime != null) {
			
		} else {
			
		}
	}

	private boolean isActivationCodeValid(HashMap<String, String> respondedLicense) {
		if (respondedLicense.get("activationcode") == null
				|| respondedLicense.get("activationcode") == "")
			;
		else {
			ActivationCode code = new ActivationCode(respondedLicense.get("activationcode"),
					respondedLicense);
			try {
				code.validate();
				return true;
			} catch (InvalidActivationKey e) {
				return false;
			}
		}
		return false;
	}

	public void setLicensingContext(LicenseValidationContext context) {
		this.context = context;
	}
}
