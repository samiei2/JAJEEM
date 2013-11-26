package com.jajeem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class testtt {
	public static void main(String[] args) {

		final DateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Thread _clockAndTime = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Calendar cal = Calendar.getInstance();
					String CurrentTime = dateFormat.format(cal.getTime());
					System.out.println(CurrentTime);
					String time = CurrentTime.split(" ")[1];
					String Hour = time.split(":")[0];
					System.out.println(Hour.charAt(0) + ":" + Hour.charAt(1));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		_clockAndTime.start();

	}
}