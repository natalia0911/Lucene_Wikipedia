/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.SpanishStemmer;

/**
 *
 * @author Natalia
 */
public class TextPreparer {

    public TextPreparer() {
    }
    
    /**
     * Quitar las stopWords 
     * //Recuperado de: https://stackoverflow.com/questions/64321901/using-default-and-custom-stop-words-with-apaches-lucene-weird-output/64323259#64323259
     * @param text
     * @param sw
     * @return string sin los stopwords
     */
    public static String deleteStopWords(String text,List<String> sw){
        final CharArraySet stopSet = new CharArraySet(sw, true);
        String s = "";
        try {
            Analyzer analyzer = new StandardAnalyzer(stopSet); //Filtra las palabras dadas del stopset
            TokenStream tokenStream = analyzer.tokenStream("stopWords", new StringReader(text));
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
           
            while(tokenStream.incrementToken()) {
                s +=  term.toString() + " "; 
            }
            tokenStream.close();
            analyzer.close();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
        
    }
    
    /**
     * Retorna el texto con las raices previamente extraidas
     * funciona si la palabra está en español, de lo contrario queda igual
     * @param text
     * @return 
     */
    public static String stemmer(String text){
        
        SpanishStemmer spanish = new SpanishStemmer();
        String[] words = text.split("\\s");
        StringBuilder stemmedText = new StringBuilder();
        for (String word : words) {
            spanish.setCurrent(word);
            spanish.stem();
            stemmedText.append(spanish.getCurrent()).append(" ");
        }
        
        return stemmedText.toString();
    }
    
    /**
     * Quita los acentos del texto, dejando la ñ tal cual
     * @param s
     * @return 
     */
    public static String deteleAccents(String s) {
        /*Salvamos las ñ, minuscula porque ya todo pasó por lower case*/
        s = s.replace('ñ', '\001');
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        /*Volvemos las ñ a la cadena*/
        s = s.replace('\001', 'ñ');

        return s;
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    public static String cleanText(String text){
        StringBuilder newText = new StringBuilder(text);
        
        ///startTime = System.currentTimeMillis();
        String cleanText = Pattern.compile("(\\w*[^a-zñ|\\s]+\\w*)").matcher(newText).replaceAll("") + " ";  
        //endTime = System.currentTimeMillis();
       // this.timeMakeItSpanish += (endTime-startTime);
        
        return cleanText;
    }
    
    
}
