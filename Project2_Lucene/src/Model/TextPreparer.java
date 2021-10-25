/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.Normalizer;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.tartarus.snowball.ext.SpanishStemmer;

/**
 *
 * @author Natalia
 */
public class TextPreparer {

    public TextPreparer() {
    }
    
    public static String deleteStopWords(String text,List<String> sw){
        StringBuilder cleanText = new StringBuilder();
        List<String> stopwords = sw;
        for(String word : stopwords){
            if(!stopwords.contains(word))
                cleanText.append(word).append(" ");
        }
        return cleanText.toString();
    }
    
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
    
    public static String deteleAccents(String s) {
         /*Salvamos las ñ*/
        s = s.replace('ñ', '\001');
        s = s.replace('Ñ', '\002');
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        /*Volvemos las ñ a la cadena*/
        s = s.replace('\001', 'ñ');
        s = s.replace('\002', 'Ñ');

        return s;
    }
    
    public static String cleanText(String text){
        StringBuilder newText = new StringBuilder(text);
        
        ///startTime = System.currentTimeMillis();
        String cleanText = Pattern.compile("(\\w*[^a-zñ|\\s]+\\w*)").matcher(newText).replaceAll("") + " ";  
        //endTime = System.currentTimeMillis();
       // this.timeMakeItSpanish += (endTime-startTime);
        
        return cleanText;
    }
    
    
}
