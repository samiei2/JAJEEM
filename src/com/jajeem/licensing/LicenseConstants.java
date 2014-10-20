package com.jajeem.licensing;

import sun.net.www.content.text.plain;

public class LicenseConstants {
	public static final String LICENSE_SECRET_KEY = "LicenseSecretKey";
	public static final String ACTIVATION_CODE = "activationcode";
	public static final String LICENSE_TIME_FORMAT = "yyyy-MM-dd";
	public static final String TRIAL_TIME = "30";
	public static final String TIME_LEFT = "timeleft";
	public static final String EXPIRE_DATE = "expiredate";
	public static final String INSTALL_MILIS = "installmilis";
	public static final String START_MILIS = "startmilis";
	public static final String LAST_RUN_DATE = "lastrundate";
	public static final String INSTALL_DATE = "installdate";
	public static final String START_DATE = "startdate";
	public static final String HARDWARE_KEY = "hardwarekey";
	public static final String SERIAL_NUMBER = "serialnumber";
	public static final String JAJEEM = "Jajeem";
	public static final String UTF_8 = "UTF-8";
	public static final String LICENSE_REG_KEY = "ThreadingModel";
	public static final String IN_PROC_SERVER32 = "}\\InProcServer32";
	public static final String CLSID = "CLSID\\TEST\\{";
	public static final String VERSION = "version";
	public static final String APPVERSIONNO = "1.0.0";
	public static final String NAME = "name";
	public static final String COMPANY = "company";
	public static final String PHONE = "phone";
	public static final String INFO = "info";
	public static final String USERS = "users";
	public static final String NUMUSERS = "2";
	public static final String STATUS = "status";
	public static final String TRIAL = "0";//0 = trial ,1 = trial-expired,2 = active,3 = active-expire,4 = deactive
	public static final String VALIDATIONSERVER = ServerList.getDefault() + "validate/validation";
	public static final String ACTIVATIONSERVER = ServerList.getDefault() + "validate/activation";
}
