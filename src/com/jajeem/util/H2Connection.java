package com.jajeem.util;

import java.io.File;
import java.io.InputStream;

public class H2Connection extends Connection{
	public H2Connection(){
		File dbfolder = new File("db\\");
		if(!dbfolder.exists())
			dbfolder.mkdirs();
        conman.connectDriver = "org.h2.Driver";
        conman.connectURI="jdbc:h2:file:db\\jajeem.db";
    }
}
