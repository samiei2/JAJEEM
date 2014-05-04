package com.jajeem.licensing;

import java.util.UUID;

public class SerialNumber {

	public static String generateSerialNumber() {
		String uuid = UUID.randomUUID().toString().substring(0, 5);
		String uuid1 = UUID.randomUUID().toString().substring(0, 5);
		String uuid2 = UUID.randomUUID().toString().substring(0, 5);
		String uuid3 = UUID.randomUUID().toString().substring(0, 5);
		String uuid4 = UUID.randomUUID().toString().substring(0, 5);

		String serialnumber = uuid1 + "-" + uuid2 + "-" + uuid3 + "-" + uuid4
				+ "-" + uuid;
		return serialnumber;
	}

	private SerialNumber() {
	}
}
