/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Natalia
 */

public class Indexer {
    private IndexWriter indexWriter;
    WebPageManager webPageManager;
    private float time;
    private int cuantityDocuments;
    private int numDocs;
    
    
    public Indexer(){
        this.webPageManager = new WebPageManager();
    }
    
    public void Index(String indexPath, String collectionPath, String Stopwords) throws IOException{
        
        //Empezar el tiempo en milisegundos
        long startTime = System.currentTimeMillis();
        
        //Crear un directorio con el string de la ruta dada
        Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
        
        //Crear el indice o agregarle
        IndexWriterConfig indexConfig = new IndexWriterConfig(new StandardAnalyzer());
        indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        
        //Crear el IndexWriter
        this.indexWriter = new IndexWriter(indexDirectory, indexConfig);
        
        //Llama a agregar documentos
        int documents = this.addDocuments(collectionPath,Stopwords);
        
        this.commit();
        this.close();
        
        //Cierra el tiempo que tardó en indexar
        long endTime = System.currentTimeMillis();
        
        //Add Time result
        this.time = endTime - startTime;
        System.out.println("Total Indexing _Time: " + this.time);
        //Cuantity of indexed documents
        this.cuantityDocuments = documents;
       
    }
    
    private int addDocuments(String collectionPath, String Stopwords) throws IOException{
        //Toma las paginas web segun la ruta dada
        ArrayList<WebPage> webPages;
        webPages = this.webPageManager.getWebPages(collectionPath,Stopwords); 
      
        long startTime = System.currentTimeMillis();
        
        //Iterar las paginas web para indizar su informacion
        for(WebPage webPage : webPages){
            //Document that is going to be indexed
            Document doc = new Document();

            //Campos guardados y tokenizados 
            doc.add(new TextField(EnumWebElements.TEXT.toString(), webPage.getText(), Field.Store.YES));
            doc.add(new TextField(EnumWebElements.REFERENCE.toString(), webPage.getRef(), Field.Store.YES));
            doc.add(new TextField(EnumWebElements.HEADER.toString(), webPage.getHeader(), Field.Store.YES));
            doc.add(new TextField(EnumWebElements.TITLE.toString(), webPage.getTitle(), Field.Store.YES));

            //Fields that are a single string, one token. No Store
            //doc.add(new StringField(WebPageConstants.ENLACE, webPage.getEnlace(), Field.Store.NO));
            
            //Collection of the documet, Path.
            doc.add(new StringField(EnumWebElements.COLLECTION.toString(), webPage.getCollection(), Field.Store.YES));
            
            //Initial and end position of the Document in the Collection .txt file
            doc.add(new StringField(EnumWebElements.INITIALPOSITION.toString(), String.valueOf(webPage.getInitialPosition()), Field.Store.YES));
            doc.add(new StringField(EnumWebElements.FINALPOSITION.toString(),  String.valueOf(webPage.getFinalPosition()), Field.Store.YES));
            
            //Agregar documento al indice
            this.indexWriter.addDocument(doc);
            
        }
       
        
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de indización: " + (endTime - startTime));
        
        return webPages.size();
    }

    public float getTime() {
        return time / 1000;
    }

    public int getCuantityDocuments() {
        return cuantityDocuments;
    }
    
    public int getNumDocs(){
        return numDocs;
    }
           
    private void close() throws CorruptIndexException, IOException {
        this.indexWriter.close();
    }
    
    private void commit() throws IOException{
        this.indexWriter.commit();
    }  
    
}