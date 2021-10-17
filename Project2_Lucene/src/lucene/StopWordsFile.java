    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Natalia
 */
public class StopWordsFile {
    
    private String idFichero;

    public StopWordsFile(String file) {
        this.idFichero = file;
    }

    public String getFile() {
        return idFichero;
    }

    public void setFile(String file) {
        this.idFichero = file;
    }
    
    public List<String> readTxt(){
        String linea;
        List<String>lista=new ArrayList<>();
 
        System.out.println("Reading de file: " + idFichero);
        try (Scanner datosFichero = new Scanner(new File(idFichero))) {
            while (datosFichero.hasNextLine()) {
                linea = datosFichero.nextLine();
                lista.add(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    
}
    

