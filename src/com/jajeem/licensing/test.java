package com.jajeem.licensing;
 
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.*;
import com.sun.jna.NativeLibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
/** Simple example of native library declaration and usage. */
public class test {
    public interface HardwareDLL extends Library {
	
        HardwareDLL INSTANCE = (HardwareDLL) Native.loadLibrary("HardwareIDExtractorC", HardwareDLL.class);
        // it's possible to check the platform on which program runs, for example purposes we assume that there's a linux port of the library (it's not attached to the downloadable project)
        String GetCpuIdNow(); // char giveVoidPtrGetChar(void* param);
        String GetIDESerialNumber(Byte DriveNumber);   //int giveVoidPtrGetInt(void* param);
        String BiosProductID();               // int giveIntGetInt(int a);
        void simpleCall();                      // void simpleCall();
    }
 
    public static void main(String[] args) {
	System.out.println(DiskUtils.getSerialNumber("D:\\"));
    }
}

class DiskUtils {
    private DiskUtils() {  }

    public static String getSerialNumber(String drive) {
    String result = "";
      try {
        File file = File.createTempFile("realhowto",".vbs");
        file.deleteOnExit();
        FileWriter fw = new java.io.FileWriter(file);

        String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    +"Set colDrives = objFSO.Drives\n"
                    +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    +"Wscript.Echo objDrive.SerialNumber";  // see note
        fw.write(vbs);
        fw.close();
        Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
        BufferedReader input =
          new BufferedReader
            (new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
           result += line;
        }
        input.close();
      }
      catch(Exception e){
          e.printStackTrace();
      }
      return result.trim();
    }

    public static void main(String[] args){
      String sn = DiskUtils.getSerialNumber("C");
      javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
           null, sn, "Serial Number of C:",
           javax.swing.JOptionPane.DEFAULT_OPTION);
    }
  }