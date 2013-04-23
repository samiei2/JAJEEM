package com.jajeem.util;

public class H2ConnectionImpl extends ConnectionImpl{
	public H2ConnectionImpl(){
        conman.connectDriver = "org.h2.Driver";
        conman.connectURI="jdbc:h2:file:jajeem.db";
    }
}
