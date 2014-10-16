package uk.co.caprica.vlcj.test.basic;
/**
 * File Name    : MyFileFilter.java
 * Description  : The MyFileFilter provides an implementation of 
 *                operations and the edit operations, like undo and redo.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;

import com.alee.extended.filefilter.DefaultFileFilter;

import java.io.File;

/**
 * Class MyFileFilter provides the implementation of the filter
 * to display the target files, which are ended with the specific
 * names.
 */
public class MyFileFilter extends FileFilter {
    /** The end name of files */
    private String endNames;

    /** The description of this end name*/
    private String description;

    /** The constructor */
    public MyFileFilter(String endNames, String description) {
        this.endNames = endNames;
        this.description = description;
    }

    /**
     * Check the file whether it is a directory or a file
     * which ends the specified name
     */
    public boolean accept(File file) {
        String fileName = file.getName().toLowerCase();
        if(file.isDirectory()){
            // the file is directory
            return true;
        }

        // the file ends with names in the endNames
        return fileName.endsWith(endNames);           
    }

    /** Get the description */
    public String getDescription() {
            return description;
    }
}
