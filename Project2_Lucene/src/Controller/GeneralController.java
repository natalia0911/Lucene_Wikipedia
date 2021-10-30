/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PathChooser;
import java.io.IOException;
import Model.Indexer;
import Model.QuerySearcher;
import Model.StopWordsFile;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import View.QuerySearcherView;
import View.IndexerView;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;



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
    
    public void searchQuery(String index,String busqueda){
        QuerySearcher searcher = new QuerySearcher();
        ArrayList<Document> documents = null;
        try {
            documents = searcher.Search(index,busqueda);
        } catch (IOException ex) {
            Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Document doc: documents){
            System.out.println(doc);
        }
        
        
    }
    
    public void createIndex(IndexerView vistaIndice,String collection, String index, String stopwords){
        Indexer ind = new Indexer();
        StopWordsFile sf = new StopWordsFile(stopwords);
        List<String> stopWords = sf.readTxt(); 
        
        try {
            ind.Index(index, collection, stopWords);
        } catch (IOException ex) {
            Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vistaIndice.setVisible(false);
        QuerySearcherView searchView = new QuerySearcherView();
        searchView.setVisible(true);
        
    }
    
    
}
