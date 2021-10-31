/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import Model.EnumWebElements;
import Model.Indexer;
import Model.QuerySearcher;
import Model.StopWordsFile;
import static Model.TextPreparer.*;
import View.IndexerView;
import static com.sun.xml.internal.bind.WhiteSpaceProcessor.replace;
import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
/**
 *
 * @author Natalia
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException{
        

        
        Indexer ind = new Indexer();
        StopWordsFile sf = new StopWordsFile("D:\\2 SEMESTRE 2021\\RIT\\PROYECTOS\\Proyecto 2\\Lucene_Wikipedia\\StopWords.txt");
        List<String> stopWords = sf.readTxt(); 
        
        String indexPath = "D:\\2 SEMESTRE 2021\\COLECCIONES\\INDICES\\Indice_h7";
        String collectionPath = "D:\\2 SEMESTRE 2021\\COLECCIONES\\COLECCIONES\\h3\\h3.txt";
        
        //ind.Index(indexPath, collectionPath, stopWords);
       
        
        
        QuerySearcher searcher = new QuerySearcher();
        //ArrayList<Document> documents = searcher.Search(indexPath,"tenistas de macedonia");
        ArrayList<Document> documents = searcher.Search(indexPath,"alejandro AND magno");
        for (Document doc: documents){
            System.out.println(doc);
        }
        
       
       
        //System.out.println(EnumWebElements.TITLE.toString());    
            
        
        
        /*
        //PRUEBAS DE STOPWORDS, STEAMING Y LIMPIEZA DE TEXTO
            Indexer ind = new Indexer();
            StopWordsFile sf = new StopWordsFile("D:\\2 SEMESTRE 2021\\RIT\\PROYECTOS\\Proyecto 2\\Lucene_Wikipedia\\StopWords.txt");
            List<String> stopWords = sf.readTxt();
            
            String text = "Désde que nacimos... [Tódos] nosotros... ¡Somos líbres! No importa que tan fuertes "
            + "sean quienes nos lo impidan...¡Lucha! ¡Si es por eso, no me importa morir! ¡No importa "
            + "lo terrible que sea este mundo!... ¡No importa que tan cruel sea! ¡¡Lucha!! (Eren Jagger) "
            + "ñame xd  NATALIA_2019 2021 20uvas ENCUENTROS-lejanos, pedigüeñería   ";
            

          
            text = text.toLowerCase();
            //System.out.println(text + "\n");
            text = deteleAccents(text);
           // System.out.println(text + "\n");
            text = cleanText(text);
            //System.out.println(text + "\n");
            text = deleteStopWords(text, stopWords);
            //System.out.println(text + "\n");
            text = stemmer(text);
            System.out.println(text + "\n");

      */
     
    }

   
   
    }      

