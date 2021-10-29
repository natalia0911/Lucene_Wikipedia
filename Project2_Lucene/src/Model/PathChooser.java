/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Natalia
 */
public class PathChooser {
 
    private static JFileChooser chooser = new JFileChooser();
    
    /**
     * Choose a file and returns the string path 
     * @return String path
     * @throws IOException 
     */
    public static String Choose() throws IOException{


        String path = "";
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        Component parent = null; //to safe the selected directory
        chooser.setMultiSelectionEnabled(true);  //Allow multiple selection

        int returnVal = chooser.showSaveDialog(parent);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {

           File file = chooser.getSelectedFile();
           path = file.getAbsolutePath();
           

         }
        else{
            return null;
        }
        
        return path;
    }
    
    
    
}
