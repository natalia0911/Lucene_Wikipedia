
package Model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class FileManager implements Serializable{
    
    public static String readFile(String path) 
    throws FileNotFoundException, IOException
    {  String str;

        try(BufferedReader bufferReader = new BufferedReader(new FileReader(path))) 
        {
            StringBuilder stringBuilder = new StringBuilder();
            String element = bufferReader.readLine();

            while (element != null) {
                stringBuilder.append(element);
                stringBuilder.append(System.lineSeparator());
                element = bufferReader.readLine();
            }
        str = stringBuilder.toString();
        }   

        return str;
    }
       
    public static void writeObject
        (Object obj, String fileNamePath)
    {
        try{
            OutputStream fileName = new FileOutputStream(fileNamePath);
            OutputStream buffer = new BufferedOutputStream(fileName);
            ObjectOutput objectOutput = new ObjectOutputStream(buffer);
            try{
              objectOutput.writeObject(obj);
            }
            finally{
              objectOutput.close();
            }
          }  
          catch(IOException ex){
          System.out.println(ex.getMessage());}
        
    }
    
    public static Object 
        readObject(String fileNamePath)
    {
        try{
            InputStream fileName = new FileInputStream(fileNamePath);
            InputStream buffer = new BufferedInputStream(fileName);
            ObjectInput objectInput = new ObjectInputStream (buffer);
            try{
                
              return objectInput.readObject();
            }
            finally{
              objectInput.close();
            }
          }
          catch(ClassNotFoundException ex){
           
          }
          catch(IOException ex){
            
          }
        return null;
    }
}
