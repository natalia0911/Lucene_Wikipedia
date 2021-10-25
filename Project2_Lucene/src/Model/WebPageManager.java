/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.FileManager.readFile;
import static Model.TextPreparer.cleanText;
import static Model.TextPreparer.deleteStopWords;
import static Model.TextPreparer.deteleAccents;
import static Model.TextPreparer.stemmer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

/**
 *
 * @author Natalia
 */
public class WebPageManager {

    Document doc;

    public WebPageManager() {}
    
    
    /**
     * Devuelve un arreglo de paginas web 
     * @param dataFile
     * @param stopWords
     * @return
     * @throws IOException 
     */
    public ArrayList<WebPage> getWebPages(String dataFile,List<String> stopWords) throws IOException{
        long startTime = System.currentTimeMillis();
        ArrayList<HTMLFile> htmlTexts = getHTMLDocuments(dataFile);
        long endTime = System.currentTimeMillis();
        ArrayList<WebPage> webPages = getWebPagesList(htmlTexts, dataFile, stopWords);
        System.out.println("Tiempo para tomar las paginas web: " + (endTime-startTime));
        return webPages;
    }
        
    /*
     Retorna un arreglo de hileras, donde cada una es una linea del documento de HTML

    public ArrayList<String> getHTMLDocument(String collection, int initialPosition, int endPosition) throws IOException{
        String data = readFile(collection);
        String[] dataLines = data.split("\n");
        ArrayList<String> html = new ArrayList<>();
        boolean isLine = false;
        int linePosition = 0;
        for(String line : dataLines){
            if(linePosition == initialPosition) isLine = true;
            if(isLine){
                html.add(line);
            }
            if(linePosition == endPosition) break;
            linePosition++;
        }
        return html;
    }
    */
 
    /**
     * Retorna los documentos HTML del txt tomando como referencia el inicio y final 
     * Empieza con <!DOCTYPE... y termina con </html>
     * @param filename
     * @return
     * @throws IOException 
     */
    public ArrayList<HTMLFile> getHTMLDocuments(String filename) throws IOException{
        

        ArrayList<HTMLFile> htmlDocuments = new ArrayList<>();
        String data = readFile(filename);
        String[] dataLines = data.split("\n");
        
        HTMLFile actualHtmlDocument = null;
        int linePosition = 0;
        for(String line : dataLines){
            if(line.contains(HTMLFile.intialTag)){
                actualHtmlDocument = new HTMLFile();
                actualHtmlDocument.setInitialPosition(linePosition);
            }
            else if(actualHtmlDocument != null && (line.contains(HTMLFile.endTag))){
                actualHtmlDocument.setEndPosition(linePosition);
                htmlDocuments.add(actualHtmlDocument);
            }
            
            actualHtmlDocument.addLine(line);
            
            linePosition++;
        }

        return htmlDocuments;
    }
    
    
    
    public ArrayList<WebPage> getWebPagesList(ArrayList<HTMLFile> htmlTexts, String dataPath, List<String> stopWords){
        
        String title;
        String body;
        String aText = "";
        String hText = "";
        
        ArrayList<WebPage> webPageList = new ArrayList();
        
        for (HTMLFile html : htmlTexts){
     
            //Tomar el html
            doc = Jsoup.parse(html.getHtmlText());
            title = doc.title();
            body = doc.body().text();  
            
            //Tomar las referencias
            StringBuilder aTagsBuilder = new StringBuilder();
            Elements aTags = doc.body().select("a");
            for (Element element : aTags){
                aTagsBuilder.append(element.ownText()).append(" ");
            }
            aText = aTagsBuilder.toString();
            
            //Tomar los subtitulos
            StringBuilder hTagsBuilder = new StringBuilder();
            Elements hTags = doc.select("h1, h2, h3, h4, h5, h6");      
            for (Element element : hTags){
              hTagsBuilder.append(element.ownText()).append(" ");
            }
            hText = hTagsBuilder.toString();
        
             
            System.out.println("ANTES DEL STEAMMING Y STOPWORDS Etiquetas h: " + hText + "\n" );
            System.out.println("ANTES DEL STEAMMING Y STOPWORDS Body: " + body + "\n" );
            
        

            //Minusculas
            title = title.toLowerCase();
            aText = aText.toLowerCase();
            hText = hText.toLowerCase();
            body = body.toLowerCase();
            //Quitar acentos y dejar ñ
            title = deteleAccents(title);
            aText = deteleAccents(aText);
            hText = deteleAccents(hText);
            body = deteleAccents(body);
            //Dejar solo palabras
            title = cleanText(title);
            aText = cleanText(aText);
            hText = cleanText(hText);
            body = cleanText(body);
            //Quitar stopWords, de title y a no se quitan porque se necesitan tal cual
            body = deleteStopWords(body, stopWords);
            hText = deleteStopWords(hText, stopWords);
            //Steaming
            hText = stemmer(hText);
            body = stemmer(body);
            //Tomar el inicio y final del html
            int startHTML = html.getInitialPosition();
            int endHTML = html.getEndPosition();
            //PRUEBAS
            System.out.println("Title: " + title + "\n" );
            System.out.println("Etiquetas a: " + aText + "\n" );
            System.out.println("Etiquetas h: " + hText + "\n" );
            System.out.println("Body: " + body + "\n" );
            System.out.println("collections: " + dataPath + "\n" + "\n" + "\n");
            
            webPageList.add(new WebPage(body, aText, hText, title, startHTML, endHTML, dataPath));     
        }     
        return webPageList;
        
    }
    

}
