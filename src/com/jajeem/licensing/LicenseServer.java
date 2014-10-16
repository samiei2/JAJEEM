package com.jajeem.licensing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.misc.BASE64Encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.InvalidLicenseTimeException;
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
			URL serverUrl = new URL(ServerList.getDefault());
			InetAddress addr = InetAddress.getByName(serverUrl.getHost());
			addr.isReachable(10000);
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
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
		      String userpassword = "admin" + ":" + "c6hJbmdeGa5P5axWXb";
		      String encodedAuthorization = enc.encode( userpassword.getBytes() );
//		      urlConn.setRequestProperty("Authorization", "Basic "+
//		            encodedAuthorization);
		      urlConn.setConnectTimeout(10000);
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
			ParseException, InvalidLicenseTimeException {
		if (respondedLicense == null) {
			throw new LicenseServerErrorException(0);
		}
		char a = 'A';
		for (String key : respondedLicense.keySet()) {
			if(key.equals(LicenseConstants.ACTIVATION_CODE)){
				String activationCode = String.valueOf(respondedLicense.get(key));
				String newExpireDate = activationCode.substring(14, 22);
				newExpireDate = newExpireDate.substring(0, 4)+newExpireDate.substring(4, 6)+newExpireDate.substring(6, 8);
				String decodedExpireDate = "";
				decodedExpireDate+=newExpireDate.charAt(0) - a;
				decodedExpireDate+=newExpireDate.charAt(1) - a;
				decodedExpireDate+=newExpireDate.charAt(2) - a;
				decodedExpireDate+=newExpireDate.charAt(3) - a;
				decodedExpireDate+="-";
				decodedExpireDate+=newExpireDate.charAt(4) - a;
				decodedExpireDate+=newExpireDate.charAt(5) - a;
				decodedExpireDate+="-";
				decodedExpireDate+=newExpireDate.charAt(6) - a;
				decodedExpireDate+=newExpireDate.charAt(7) - a;
				newExpireDate = decodedExpireDate;
				Date expDate = new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT).parse(newExpireDate);
				Date startDate = new Date(System.currentTimeMillis());
				String newTimeLeft = String.valueOf(getDateDiff(startDate, expDate, TimeUnit.DAYS));
				String newNoUsers = activationCode.substring(6,8);
				newNoUsers = String.valueOf(Integer.parseInt(newNoUsers,16) - 16);
				activationCode = activationCode.substring(0,6)+activationCode.substring(8,14)+activationCode.substring(22,activationCode.length());
				
				context.getLicense().getLicenseInfo().put(LicenseConstants.ACTIVATION_CODE, activationCode);
				context.getLicense().getLicenseInfo().put(LicenseConstants.USERS, newNoUsers);
				if(!context.getLicense().getLicenseInfo().containsKey(LicenseConstants.ACTIVATION_CODE))
					context.getLicense().getLicenseInfo().put(LicenseConstants.START_DATE, new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT)
					.format(startDate));
				context.getLicense().getLicenseInfo().put(LicenseConstants.EXPIRE_DATE, newExpireDate);
				context.getLicense().getLicenseInfo().put(LicenseConstants.TIME_LEFT, newTimeLeft);
			}
			else{
				context.getLicense().getLicenseInfo().put(key, String.valueOf(respondedLicense.get(key)));
			}
		}
		
		//context.getLicense().setLicenseInfo(respondedLicense);

//		if (respondedLicense.get(LicenseConstants.STATUS).equals("0")) {
//			//throw new InvalidLicenseException("Server:\nInvalid License.");
//		}
//		
//		else if (respondedLicense.get(LicenseConstants.STATUS).equals("1")) {
//			throw new InvalidLicenseException("Server:\nTrial license disable.Contact support for more info!");
//		}
//		
//		else if (respondedLicense.get(LicenseConstants.STATUS).equals("2")) {
//			//throw new InvalidLicenseException("Server:\nInvalid License.");
//		}
//		
//		else if (respondedLicense.get(LicenseConstants.STATUS).equals("3")) {
//			throw new InvalidLicenseException("Server:\nActivated license disabled.Contact support for more info!");
//		}
//		
//		else if (respondedLicense.get(LicenseConstants.STATUS).equals("4")) {
//			throw new InvalidLicenseException("Server:\nLicense deactivated.Contact support for more info!");
//		}
//		
//		else{
//			throw new InvalidLicenseException("Server:\nInvalid Server Response.Contact support for more info!");
//		}
		//returns to the license class for validation
//		License lic = new License();
//		lic.setFilePath(context.getLicense().getFilePath());
//		lic.setLicenseInfo(respondedLicense);
//		lic.setContext(context);

//		LicenseValidator validator = new LicenseValidator();
//		validator.validate(lic);
//		context.getLicense().setValid(true);
	}

	public void validateOnline(License decLic)
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey,
			UninitializedLicensingContextException, GeneralSecurityException,
			ParseException, InvalidLicenseTimeException {

		if (context == null) {
			throw new UninitializedLicensingContextException("Server:\nUninitialized license exception.");
		}

		JsonConvert converter = new JsonConvert();
		String jsonQuery = null;
		try {
			jsonQuery = converter.ConvertToJson(decLic.getLicenseInfo());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String jsonResponse = handleServerRequest(LicenseConstants.VALIDATIONSERVER,
				jsonQuery);
		System.out.println("Query : "+jsonQuery);
		jsonResponse = jsonResponse.replace("{\"response\":", "");
		System.out.println("Query : "+jsonResponse);
		jsonResponse = jsonResponse.substring(0, jsonResponse.lastIndexOf("}"));
		HashMap<String, String> respondedLicense = converter.ConvertFromJson(
				jsonResponse, HashMap.class);
		ValidateLicense(respondedLicense);
	}

	public void activateOnline(License license) throws JsonProcessingException, LicenseServerErrorException {
		JsonConvert converter = new JsonConvert();
		String json = converter.ConvertToJson(license.getLicenseInfo());
		handleServerRequest(LicenseConstants.ACTIVATIONSERVER, json);
	}
	
	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
