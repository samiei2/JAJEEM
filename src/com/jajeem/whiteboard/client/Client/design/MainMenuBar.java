package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : MainMenuBar.java
 * Description  : The MainMenuBar presents the menu of the related file
 *                operations and the edit operations, like undo and redo.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */

import com.alee.extended.filechooser.WebFileChooser;
import com.alee.extended.filefilter.DefaultFileFilter;
import com.jajeem.whiteboard.server.Module.Shape;
import com.jajeem.whiteboard.server.Module.Whiteboard;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Class MainMenuBar is the main menu which is under the
 * title of the main frame. It provides the choice of operations
 * like opening new file, saving file and saving image, and etc. 
 */
public class MainMenuBar extends JMenuBar {

    /** The check box in the menu */
    private JCheckBoxMenuItem transparentCheckBox;

    /** The main frame */
    private MainFrame mainFrame;

    /** The whiteboard */
    private Whiteboard whiteboard;

    /** The chooser of file */
    private JFileChooser fileChooser;

    /** The vector to store the shapes readed from the file */
    private Vector<Shape> shapeVector = null;

    /** The path of file saved */
    private String saveFilePath = null;

    /** The constructor to initialize the instance variables. */
    public MainMenuBar(MainFrame mainFrame, Whiteboard whiteboard) {
        // creates the file menu
        JMenu fileMenu;
        JMenuItem fileMenuNew;
        JMenuItem fileMenuOpen;
        JMenuItem fileMenuSave;
        JMenuItem fileMenuSaveAs;
        JMenuItem fileMenuSaveAsImage;
        JMenuItem fileMenuExit;

        // creates the edit menu
        JMenu editMenu;
        JMenuItem editMenuUndo;
        JMenuItem editMenuRedo;

        // creates the shape menu
        JMenu shapeMenu;

        this.mainFrame = mainFrame;
        this.whiteboard = whiteboard;
        this.fileChooser = new JFileChooser();
        
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        // create a new whiteboard
        fileMenuNew = new JMenuItem("New");
        fileMenuNew.setMnemonic('N');
        fileMenuNew.setActionCommand("NewFile");
        fileMenuNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileMenuNew_actionPerformed(e);
            }
        });

        // open a project file
        fileMenuOpen = new JMenuItem("Open...");
        fileMenuOpen.setMnemonic('O');
        fileMenuOpen.setActionCommand("OpenFile");
        fileMenuOpen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileMenuOpen_actionPerformed(e);
            }
        });

        // save to a project file
        fileMenuSave = new JMenuItem("Save");
        fileMenuSave.setMnemonic('S');
        fileMenuSave.setActionCommand("SaveFile");
        fileMenuSave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileMenuSave_actionPerformed(e);
            }
        });

        // save to a specified path 
        fileMenuSaveAs = new JMenuItem("Save As...");
        fileMenuSaveAs.setMnemonic('A');
        fileMenuSaveAs.setActionCommand("SaveAsFile");
        fileMenuSaveAs.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileMenuSaveAs_actionPerformed(e);
            }
        });     

        // Save to an image file
        fileMenuSaveAsImage = new JMenuItem("Save As A Image...");
        fileMenuSaveAsImage.setMnemonic('I');
        fileMenuSaveAsImage.setActionCommand("SaveAsImage");
        fileMenuSaveAsImage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileMenuSaveAsImage_actionPerformed(e);
            }
        });

        // exit operation
        fileMenuExit = new JMenuItem("Exit");
        fileMenuExit.setMnemonic('x');
        fileMenuExit.setActionCommand("Exit");
        fileMenuExit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileMenuExit_actionPerformed(e);
            }
        });

        // add the menu items of file operation
        fileMenu.add(fileMenuNew);
        fileMenu.add(fileMenuOpen);
        fileMenu.add(fileMenuSave);
        fileMenu.add(fileMenuSaveAs);
        fileMenu.add(fileMenuSaveAsImage);
        fileMenu.add(fileMenuExit);

        this.add(fileMenu);

        // the Edit menu
        editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        // the undo menu item
        editMenuUndo = new JMenuItem("Undo");
        editMenuUndo.setMnemonic('U');
        editMenuUndo.setActionCommand("Undo");
        editMenuUndo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                editMenuUndo_actionPerformed(e);
            }
        });
        // the redo menu item
        editMenuRedo = new JMenuItem("Redo");
        editMenuRedo.setMnemonic('R');
        editMenuRedo.setActionCommand("Redo");
        editMenuRedo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                editMenuRedo_actionPerformed(e);
            }
        });

        editMenu.add(editMenuUndo);
        editMenu.add(editMenuRedo);

        this.add(editMenu);

        // the Graphics menu
        shapeMenu = new JMenu("Graphics");
        shapeMenu.setMnemonic('G');

        // the menu item to decide whether drawing the empty shape
        // is transparent
        transparentCheckBox = new JCheckBoxMenuItem("Transparent");
        transparentCheckBox.setSelected(true);
        shapeMenu.add(transparentCheckBox);

        this.add(shapeMenu);

        if(mainFrame.getUserid() != 0) {
            // if the user is not an administrator
            // he/she does not have the right to
            // create a new whiteboard or open a
            // new whiteboard 
            fileMenuNew.setEnabled(false);
            fileMenuOpen.setEnabled(false);
        }
    }

    private void fileMenuNew_actionPerformed(ActionEvent e){
        try {

            // Check the whiteboard whether has been drawn.
            if(isWhiteboardDraw())
            {
                // the whiteboard has been drawn, so ask
                // the user whether the current whiteboard
                // should be discarded
                int choice = JOptionPane.showConfirmDialog(this,
                                "The current whiteboard is not saved, "
                                + "do you want to create a new whiteboard?"
                               ,"Confirm Create"
                               ,JOptionPane.OK_CANCEL_OPTION
                               ,JOptionPane.PLAIN_MESSAGE );
                if (choice == JOptionPane.OK_OPTION)
                {
                    // create a new whiteboard
                    whiteboard.createNewWhiteboard();
                }               
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                       "Error happens while opening a new whiteboard.",
                       "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Open a project file
     */
    @SuppressWarnings(value = {"unchecked"})
    private void fileMenuOpen_actionPerformed(ActionEvent e){
    	WebFileChooser fileChooser = new WebFileChooser(mainFrame);
        int flag;
        File file;
        fileChooser.setTitle("Open File");
        // create a custom file filter
        MyFileFilter projFileFilter = new MyFileFilter(".ttb","Project Files(*.ttb)");
        
        // set the file filter to the file chooser 
        fileChooser.setChooseFilter(projFileFilter);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            // display a dialog to choose file
            flag = fileChooser.showDialog();

            if( flag == JFileChooser.APPROVE_OPTION) {               
                // read the file object into the local shapeVector
                file = new File(fileChooser.getSelectedFile().getPath());
                // creates the input stream of object 
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);

                // read the object from the file
                Object obj = objectInputStream.readObject();
                shapeVector = (Vector<Shape>)obj;

                // create a new whiteboard
                // read the shapes into local vector.
                whiteboard.createNewWhiteboard();
                Shape shape;              
                int n = shapeVector.size();

                for( int i=0; i < n; i++) {
                    shape = shapeVector.elementAt(i);
                    // add the shape to the remote whiteboard.
                    whiteboard.addShape(shape);
                }
            }
        } catch(Exception cnfex){
            JOptionPane.showMessageDialog(null,
                   "Error happens while opening a project file.",
                   "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // closing the object input stream
            if(objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch(IOException ioex) {
                    JOptionPane.showMessageDialog(null,
                       "The closing stream of Open File Error.",
                       "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            // closing the file input stream
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch(IOException ioex) {
                    JOptionPane.showMessageDialog(null,
                       "the closing stream of Open File Error.",
                       "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Save a project file
     */
    private void fileMenuSave_actionPerformed(ActionEvent e){
        if( saveFilePath == null) {
            // if the current file path is empty,
            // invoke the SaveAs function to choose
            // a file path
            fileMenuSaveAs_actionPerformed(e);
        }
        else {
            // save to the current file path directly
            saveProjFile(saveFilePath);
        }
    }

    /**
     * Save a project file to a specified path 
     */
    private void fileMenuSaveAs_actionPerformed(ActionEvent e){
        int flag;
        fileChooser.setDialogTitle("Save File");
        // set the file filter 
        MyFileFilter projFileFilter = new MyFileFilter(".ttb","Project Files(*.ttb)");
        fileChooser.setFileFilter(projFileFilter);

        // display the dialog to choose file name saved
        flag = fileChooser.showSaveDialog(mainFrame);
        if( flag == JFileChooser.APPROVE_OPTION) {
            saveFilePath = fileChooser.getSelectedFile().getPath() + ".ttb";
            saveProjFile(saveFilePath);
        }
    }

    /**
     * Save an image file to a specified path
     */
    private void fileMenuSaveAsImage_actionPerformed(ActionEvent e) {
        int flag = 0;

        fileChooser.setDialogTitle("Save Image");
        // set the file filter
        MyFileFilter imageFileFilter = new MyFileFilter(".jpeg","Image Files(*.jpeg)");
        fileChooser.setFileFilter(imageFileFilter);

        // display the dialog to choose file name saved
        flag = fileChooser.showSaveDialog(mainFrame);
        if( flag == JFileChooser.APPROVE_OPTION) {
            saveImageFile(fileChooser.getSelectedFile().getPath() + ".jpeg");
        }
    }

    /**
     * Leave this session
     */
    private void fileMenuExit_actionPerformed(ActionEvent e) {
        try{
            mainFrame.window_exit();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                       "Error happens while closing the window.",
                       "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The undo operation
     */
    private void editMenuUndo_actionPerformed(ActionEvent e) {
        try {
            int currentRightAt = whiteboard.getCurrentRightAt();
            if(currentRightAt == mainFrame.getUserid()) {
                // the user who has right to draw can conduct
                // the undo operation
                whiteboard.undo();
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                       "A error happens while undo the operation",
                       "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The redo operation
     */
    private void editMenuRedo_actionPerformed(ActionEvent e) {
        try {
            int currentRightAt = whiteboard.getCurrentRightAt();
            if(currentRightAt == mainFrame.getUserid()) {
                // the user who has right to draw can conduct
                // the redo operation
                whiteboard.redo();
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                       "A error happens while redo the operation.",
                       "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The flag whether the whiteboard has already been drawn
     */
    private boolean isWhiteboardDraw() throws RemoteException {
        return this.whiteboard.getShapeVectorSize() != 0;
    }

    /**
     * Save an image file to the specified path
     */
    private void saveImageFile(String filePath){
        File file = new File(filePath);
        try {
            PaintPanel paintPanel = mainFrame.getPaintPanel();
            Dimension size = paintPanel.getSize();
            // creates a buffered image from the paint panel
            BufferedImage image = new BufferedImage(size.width,
                    size.height, BufferedImage.TYPE_INT_BGR);
            paintPanel.printAll(image.getGraphics());
            // write the buffered image into the file
            ImageIO.write(image, "jpeg", file);
        } catch(IOException ioe) {
            JOptionPane.showMessageDialog(null,
                       "A error happens while saving to a image file.",
                       "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * The method to save the project file
     */
    private void saveProjFile(String filePath){
        this.saveFilePath = filePath;
        File file;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            // read the shapes into local vector.
            Shape shape;
            shapeVector = new Vector<Shape>();
            int n = whiteboard.getShapeVectorSize();

            for( int i=0; i < n; i++) {
                shape = whiteboard.getShapeVectorElementAt(i);
                shapeVector.addElement(shape);
            }

            // write the shapevector to the file
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(shapeVector);
        } catch(IOException ioe) {
            JOptionPane.showMessageDialog(null,
                   "An error happens, while writing to the file.",
                   "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // closing the object stream
            try {
                if (objectOutputStream != null )
                    objectOutputStream.close();
            } catch(IOException ioe){
                JOptionPane.showMessageDialog(null,
                   "An error happens, while closing the object stream.",
                   "Error", JOptionPane.ERROR_MESSAGE);
            }

            // closing the file stream
            try {
                if (fileOutputStream != null )
                    fileOutputStream.close();
            } catch(IOException ioe){
                JOptionPane.showMessageDialog(null,
                   "An error happens, while closing the file stream.",
                   "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Get the flag state of transparent */
    public boolean isTransparent() {
        return transparentCheckBox.getState();
    }
}
