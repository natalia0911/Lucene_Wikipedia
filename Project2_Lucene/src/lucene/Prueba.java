/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import Model.StopWordsFile;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;



/**
 *
 * @author Dell
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Recuperado de: https://stackoverflow.com/questions/64321901/using-default-and-custom-stop-words-with-apaches-lucene-weird-output/64323259#64323259
     
        StopWordsFile sf = new StopWordsFile("D:\\2 SEMESTRE 2021\\RIT\\PROYECTOS\\Proyecto 2\\Lucene_Wikipedia\\StopWords.txt");
        
        final String text = "Esas mañas que tenemos tantas nos hacen iferentes las unas e las otras";
        final List<String> stopWords = sf.readTxt(); //Filters the stopWords
        final CharArraySet stopSet = new CharArraySet(stopWords, true);

        try {
            ArrayList<String> remaining = new ArrayList<String>();

            Analyzer analyzer = new StandardAnalyzer(stopSet); // Filters stop words in the given "stopSet"
            

            TokenStream tokenStream = analyzer.tokenStream("stopWords", new StringReader(text));
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
           
            String s = "";
            while(tokenStream.incrementToken()) {
                s += " "+ term.toString();
                System.out.print(" " + term.toString() + " ");
                remaining.add(term.toString());
            }
            System.out.println(s);

            tokenStream.close();
            analyzer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
   
   
      /*
        String[] argumentos = new String[4];
        argumentos[0]= "-index";
        argumentos[1]= "D:\\2 SEMESTRE 2021\\RIT\\PROYECTOS\\Proyecto 2\\Lucene_Wikipedia\\INDEX";
        argumentos[2]= "-docs";
        argumentos[3]= "D:\\2 SEMESTRE 2021\\RIT\\PROYECTOS\\Proyecto 2\\Lucene_Wikipedia\\h8";
        Indice index = new Indice(argumentos);
        index.valideArguments();
        index.createIndex();
        
        
        String text = "áéíóú";
        String texto = "";
        texto = Normalizer.normalize(text, Normalizer.Form.NFD);
        texto = text.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        System.out.println(texto);
        */
        
        
        
    }
   
    }      

