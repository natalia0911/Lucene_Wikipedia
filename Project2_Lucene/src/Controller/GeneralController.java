/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PathChooser;
import java.io.IOException;


/**
 *
 * @author Natalia
 */
public class GeneralController {
    
    private static GeneralController controller;
    private String indexPath;

    public GeneralController() {
    }
    
    public synchronized static GeneralController getInstance(){
        if(controller == null){
            controller = new GeneralController();
        }
        return controller;
    }    
    
    
    public String getPath() throws IOException{
    
       String path =  PathChooser.Choose();
       return path;
    }
    

}
