package com.jajeem.licensing;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import net.sf.jni4net.Bridge;
import system.Console;

import com.jajeem.core.design.InstructorLogin;

public class LicenseValidator {

	static{
		try {
			Bridge.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!new File("lib/jni/").exists()){
			System.out.println("Interop tampering detected!");
			JOptionPane.showMessageDialog(null, "Problems with your license detected.System will close!");
			System.exit(1);
		}
		if(!new File("lib/jni/").exists()){
			System.out.println("Interop tampering detected!");
			JOptionPane.showMessageDialog(null, "Problems with your license detected.System will close!");
			System.exit(1);
		}
		Console.WriteLine("Interop Started!");
		Bridge.LoadAndRegisterAssemblyFrom(new File("lib/jni/iCalaboInteropServices.j4n.dll"));
	}
	
	public LicenseValidator(){
		
	}
	
	public static void main(String[] args) {
		int i=0;
		while(true){
			SilentValidateLicense();
			System.out.println(i++);
		}
	}
	
	public static void SilentValidateLicense(){
		icalabointeropservices.Interop op = new icalabointeropservices.Interop();
		op.SilentValidateLicense();
		String result = op.getValidationResult();
		if(result.equals("False"))
			System.exit(0);
		Console.WriteLine("Interop Done!");
	}
	
	public static void ActiveValidateLicense(){
		icalabointeropservices.Interop op = new icalabointeropservices.Interop();
		
		InstructorLogin.progressBarFrame.setVisible(false);
		
		op.ActiveValidateLicense();
		String result = op.getValidationResult();
		if(result.equals("False"))
			System.exit(0);
		Console.WriteLine("Interop Done!");
	}

}
