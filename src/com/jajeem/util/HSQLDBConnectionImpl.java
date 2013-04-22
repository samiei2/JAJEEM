/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.util;

import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 *
 * @author Armin
 */
public class HSQLDBConnectionImpl extends ConnectionImpl{
    public HSQLDBConnectionImpl(){
        conman.connectDriver = "org.hsqldb.jdbc.JDBCDriver";
        conman.connectURI="jdbc:hsqldb:file:enrolments";
    }
}
