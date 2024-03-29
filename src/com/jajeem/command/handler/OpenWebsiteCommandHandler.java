package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.exception.JajeemExceptionHandler;

public class OpenWebsiteCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		String url = ((WebsiteCommand) cmd).getURL();
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		try {

			if (os.indexOf("win") >= 0) {

				// this doesn't support showing urls in the form of
				// "page.html#nameLink"

				Process p = rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
				System.out.println(url + " is oppened");

			} else if (os.indexOf("mac") >= 0) {

				rt.exec("open " + url);

			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				// Do a best guess on unix until we get a platform independent
				// way
				// Build a list of browsers to try, in this order.
				String[] browsers = { "epiphany", "firefox", "mozilla",
						"konqueror", "netscape", "opera", "links", "lynx" };

				// Build a command string which looks like
				// "browser1 "url" || browser2 "url" ||..."
				StringBuffer cmd1 = new StringBuffer();
				for (int i = 0; i < browsers.length; i++) {
					cmd1.append((i == 0 ? "" : " || ") + browsers[i] + " \""
							+ url + "\" ");
				}

				rt.exec(new String[] { "sh", "-c", cmd1.toString() });

			} else {
				return;
			}
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
			return;
		}
	}

}
