/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Natalia
 */
public class HTMLFile {
        private ArrayList<String> html;
    private String collection;
    private int initialPosition;
    private int endPosition;
    
    public static String intialTag = "<!DOCTYPE";
    public static String endTag = "</html>";

    public HTMLFile() {
        this.html = new ArrayList<>();
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
    
       
    public void addLine(String newLine){
        html.add(newLine);
    }

    public ArrayList<String> getHtml() {
        return html;
    }
    
    public String getHtmlText(){
        String text = "";
        for(String line : html){
            text += line;
        }
        return text;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
