package com.jajeem.command.handler;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.ArrayList;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.RestartStudentProgramCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.core.design.teacher.InstructorNoaUtil;
import com.jajeem.core.design.teacher.ScreenImageContainer;
import com.jajeem.util.Config;

public class StartUpCommandHandler implements ICommandHandler {
	static ArrayList<String> listOfIPs = new ArrayList<>();
	static Object licenseLock = new Object();
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
//		synchronized(lock){
//			if(listOfProcessings.contains(cmd.getFrom()))
//				return;
//			listOfProcessings.add(cmd.getFrom());
			StartUp(cmd);
//			listOfProcessings.remove(cmd.getFrom());
//		}
	}
	
	public void StartUp(Command cmd) throws NumberFormatException, Exception{
		new Config();

		try{
			int port = Integer.parseInt(Config.getParam("serverPort"));
			
	
			if (Integer.parseInt(Config.getParam("server")) != 1) {
				StartCaptureCommandHandler startCaptureCommandHandler = new StartCaptureCommandHandler();
				startCaptureCommandHandler.run(cmd);
				
//////////////////Simple image capture and return//////////////////////////////////
				Robot robot = new Robot();
				BufferedImage screenShot = toBufferedImage(robot
						.createScreenCapture(new Rectangle(Toolkit
								.getDefaultToolkit().getScreenSize()))
								.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
///////////////////////////////////////////////////////////////////////////////////
				
				StartUpCommand cmdToSend = new StartUpCommand(InetAddress
						.getLocalHost().getHostAddress(),
						((StartUpCommand) cmd).getSender(), port, InetAddress
								.getLocalHost().getHostAddress(),
						System.getProperty("user.name"));
				cmdToSend.setSender(InetAddress.getLocalHost().getHostAddress());
				cmdToSend.setScreenImage(new ScreenImageContainer(screenShot));
				cmdToSend.setPort(port);
				StudentLogin.getServerService().send(cmdToSend);
				StudentLogin.setServerIp(((StartUpCommand) cmd).getSender());
	
				if (!Student.getFrmJajeemProject().isVisible()) {
					StudentLogin.getLoginDialog().setVisible(true);
				} else {
					StudentLogin.getLoginDialog().setVisible(false);
				}
	
				Student.setCountdown(30000);
			} else if (Integer.parseInt(Config.getParam("server")) == 1
					&& cmd.getPort() == Integer.parseInt(Config
							.getParam("serverPort"))) {
				
//				synchronized (licenseLock) {
//					if (!listOfIPs.contains(cmd.getFrom())) {
//						if (listOfIPs.size() > Integer.parseInt(LicenseManager
//								.getInstance().getLicContext().getLicense()
//								.getLicenseInfo().get("users")))
//							return;
//						else
//							listOfIPs.add(cmd.getFrom());
//					}
//				}
				
				try{
					if (InstructorNoa.getDesktopPaneScroll().getDesktopMediator() != null) {
						if (InstructorNoa.getDesktopPane() != null) {
							InstructorNoaUtil.createFrame(
									InstructorNoa.getDesktopPane(),
									((StartUpCommand) cmd).getSender(),
									((StartUpCommand) cmd).getSenderName(),
									((StartUpCommand) cmd).getScreenImage().getImage());
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e){
					
				}
				catch(NullPointerException e){
					
				}
				catch(Exception e){
					try{
						ServerService service = new ServerService();
						RestartStudentProgramCommand restartCmd = new RestartStudentProgramCommand(
								InetAddress.getLocalHost().getHostAddress(), cmd.getFrom(),
											Integer.parseInt(com.jajeem.util.Config.getParam("port")));
						service.send(restartCmd);
						
					}
					catch(Exception ex){
						
					}
				}
			}
		}
		catch(Exception e){
			
		}
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
