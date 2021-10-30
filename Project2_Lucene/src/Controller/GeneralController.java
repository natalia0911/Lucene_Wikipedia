/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.FileManager;
import Model.Indexer;
import Model.QuerySearcher;
import Model.WebPageManager;
import View.QuerySearcherView;
import Model.WebPage;
import Model.EnumWebElements;
//import com.sun.javafx.webkit.WebConsoleListener;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import java.nio.file.Paths;
import java.nio.file.Path;
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
    
    private QuerySearcher searcher; 
    private static GeneralController controller;
    private WebPageManager webPageManager;
    private String indexPath;
    
     ArrayList<Document> documents;
    int documentsForPage;
    int page;
    int maxPages;
    
    String htmlFilePath = "Data/Html/openDoc.html";
    

    public GeneralController() {
        searcher = new QuerySearcher();
        webPageManager = new WebPageManager();   
        this.documentsForPage = 20;
        
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
      
        try {
            
            this.documents = searcher.Search(index,busqueda);
            this.page = 1;
            this.maxPages = this.documents.size() / documentsForPage;
            
        } catch (IOException ex) {
            Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(documents.size()%documentsForPage != 0) maxPages++;
        
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
    
      public void openDocument(int docPosition) throws IOException{
        String html = getDocument(docPosition);
        FileManager.writeObject(html, htmlFilePath);
        Path path = Paths.get(htmlFilePath);
        String htmlDir = path.toAbsolutePath().toString();
        File htmlFile = new File(htmlDir);
        Desktop.getDesktop().browse(htmlFile.toURI());
        System.out.println(htmlDir);
            
    }
     public String getDocument(int docPosition) throws IOException{
        
        int docIndex = (documentsForPage*page) - (documentsForPage+1 - (docPosition+1));
        System.out.println("DocIndex: " + docIndex);
        System.out.println("Page: " + page + "  Position: " + docPosition);
        //int docIndex = (docPosition*page);
        Document doc = this.documents.get(docIndex);
        System.out.println("Title: " + doc.getField("TITLE").stringValue());
        return SearchHTMLDocument(doc);
    }
    
    public String[] getDocumentAtPage(){
       String documents_20 = "";
       Document doc;
       int initialIndex = (page-1)*documentsForPage;
       for(int docIndex = initialIndex; (docIndex < this.documents.size() && docIndex < initialIndex+documentsForPage) ; docIndex++){
           doc = this.documents.get(docIndex);  
          // File file = new File(doc.getField(WebPageConstants.COLLECTION).toString());
           documents_20 += doc.getField(EnumWebElements.TITLE.toString()).stringValue() + "\n"; //+ " from " + file.getName() + "\n";
       }
               
        return documents_20.split("\n");
    }
    
    private String SearchHTMLDocument(Document document) throws IOException{
     ArrayList<String> htmlLines = webPageManager.getHTMLDocument(
         document.getField(EnumWebElements.COLLECTION.toString()).stringValue(),
         Integer.valueOf(document.getField(EnumWebElements.INITIALPOSITION.toString()).stringValue()),
         Integer.valueOf(document.getField(EnumWebElements.FINALPOSITION.toString()).stringValue())
     );
     String html = "";
     for(String line : htmlLines)
         html += line;

     return html;
    }
    
    public boolean getNextPage(){
        if(page == maxPages) return false;
        page++;
        return true;
    }
    
    public boolean getPreviousPage(){
        if(page == 1) return false;
        page--;
        return true;
    }
    
    public int getPage(){
        return page;
    }
    
   
  
}
