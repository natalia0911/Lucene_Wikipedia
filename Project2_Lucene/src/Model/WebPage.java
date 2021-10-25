/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Natalia
 */
public class WebPage {
    private String text;
    private String ref;
    private String header;
    private String title;
    private String collection;
    
    private int initialPosition;
    private int FinalPosition;

    public WebPage(String text, String ref, String header, String title, int initialPosition, int FinalPosition, String collection) {
        this.text = text;
        this.ref = ref;
        this.header = header;
        this.title = title;
        this.collection = collection;
        this.initialPosition = initialPosition;
        this.FinalPosition = FinalPosition;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    public int getFinalPosition() {
        return FinalPosition;
    }

    public void setFinalPosition(int FinalPosition) {
        this.FinalPosition = FinalPosition;
    }
    
    
    

}
