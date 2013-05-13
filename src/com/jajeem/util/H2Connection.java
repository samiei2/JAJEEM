package com.jajeem.util;

public class H2Connection extends Connection{
	public H2Connection(){
        conman.connectDriver = "org.h2.Driver";
        conman.connectURI="jdbc:h2:file:jajeem.db";
    }
}
