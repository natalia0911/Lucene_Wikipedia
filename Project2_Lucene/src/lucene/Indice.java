/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Dell
 */
public class Indice {
    private String[] argumentos;
    private String indexPath = "index";
    private String docsPath = null;
    private boolean create = true;
    private Path docDir = null;

    public Indice(String[] argumentos) {
        this.argumentos = argumentos;
    }

    public String[] getArgumentos() {
        return argumentos;
    }

    public void setArgumentos(String[] argumentos) {
        this.argumentos = argumentos;
    }
    
    
    public void valideArguments(){
        for(int i=0;i<this.argumentos.length;i++) {
          if ("-index".equals(this.argumentos[i])) {
            System.out.println("YES");
            this.indexPath = this.argumentos[i+1];
            i++;
          } else if ("-docs".equals(argumentos[i])) {
            this.docsPath = this.argumentos[i+1];
            i++;
          } else if ("-update".equals(this.argumentos[i])) {
            this.create = false;
          }
        }
        
        if (docsPath == null) {
            System.err.println("Error with DocsPath");
            System.exit(1);
        }


        this.docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" +docDir.toAbsolutePath()+ "' does not exist or is not readable, please check the path");
            System.exit(1);
        }
        
    }
    
    public void createIndex(){
    
    
        Date start = new Date();
        try {
          System.out.println("Indexing to directory '" + indexPath + "'...");
          Directory dir = FSDirectory.open(Paths.get(indexPath));
          Analyzer analyzer = new StandardAnalyzer();
          IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
          
          //Crear o agregar al indice
          if (create) {
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
          } else {
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
          }

          IndexWriter writer = new IndexWriter(dir, iwc);
          indexDocs(writer, docDir);

          writer.close();

          Date end = new Date();
          System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
          System.out.println(" caught a " + e.getClass() +
           "\n with message: " + e.getMessage());
        }
    }
    
   /**
   * Indexes the given file using the given writer, or if a directory is given,
   * recurses over files and directories found under the given directory.
   * 
   * NOTE: This method indexes one document per input file.  This is slow.  For good
   * throughput, put multiple documents into your input file(s).  An example of this is
   * in the benchmark module, which can create "line doc" files, one document per line,
   * using the
   * <a href="../../../../../contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
   * >WriteLineDocTask</a>.
   *  
   * @param writer Writer to the index where the given file/dir info will be stored
   * @param path The file to index, or the directory to recurse into to find files to index
   * @throws IOException If there is a low-level I/O error
   */
    static void indexDocs(final IndexWriter writer, Path path) throws IOException {
        if (Files.isDirectory(path)) {
          Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
              try {
                indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
              } catch (IOException ignore) {
                // don't index files that can't be read.
              }
              return FileVisitResult.CONTINUE;
            }
          });
        } else {
          indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
    }
    
    
    
      /** Indexes a single document */
    static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            // make a new, empty document
            Document doc = new Document();
            
           
          
          // Add the path of the file as a field named "path".  Use a
          // field that is indexed (i.e. searchable), but don't tokenize 
          // the field into separate words and don't index term frequency
          // or positional information:
          System.out.println("file toString------------------------------"); System.out.println(file.toString());
          Field pathField = new StringField("path", file.toString(), Field.Store.YES);
          //doc.add(pathField);
          
          
          
          
            ///AYUDA DIOSSSSS  ----- ESTA ES LA PARTE DE QUITAR STOPWORD, ENTONCES HAY QUE QUITAR EL TEXTO DUMMY Y PASARLE LO DEL DOC
            StopWordsFile sf = new StopWordsFile("D:\\2 SEMESTRE 2021\\RIT\\PROYECTOS\\Proyecto 2\\Lucene_Wikipedia\\StopWords.txt");
        
            final String text = "Esas mañas que tenemos tantas";
            final List<String> stopWords = sf.readTxt(); //Filters the stopWords
            final CharArraySet stopSet = new CharArraySet(stopWords, true);

            try {
                ArrayList<String> remaining = new ArrayList<String>();
                Analyzer analyzer = new StandardAnalyzer(stopSet); // Filters stop words in the given "stopSet"
                TokenStream tokenStream = analyzer.tokenStream("stopWords", new StringReader(file.toString()));  //new StringReader(text)
                CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
                tokenStream.reset();
                while(tokenStream.incrementToken()) {
                    System.out.print("[" + term.toString() + "] ");
                    remaining.add(term.toString());
                }
                tokenStream.close();
                analyzer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
          //----------------------------------------------------------------------------------- FIN DE LO DE STOPWORDS
          
          

          // Add the last modified date of the file a field named "modified".
          // Use a LongPoint that is indexed (i.e. efficiently filterable with
          // PointRangeQuery).  This indexes to milli-second resolution, which
          // is often too fine.  You could instead create a number based on
          // year/month/day/hour/minutes/seconds, down the resolution you require.
          // For example the long value 2011021714 would mean
          // February 17, 2011, 2-3 PM.
          doc.add(new LongPoint("modified", lastModified));

          // Add the contents of the file to a field named "contents".  Specify a Reader,
          // so that the text of the file is tokenized and indexed, but not stored.
          // Note that FileReader expects the file to be in UTF-8 encoding.
          // If that's not the case searching for special characters will fail.
          doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

          if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
            // New index, so we just add the document (no old document can be there):
            System.out.println("adding " + file);
            writer.addDocument(doc);

          } else {
            // Existing index (an old copy of this document may have been indexed) so 
            // we use updateDocument instead to replace the old one matching the exact 
            // path, if present:
            System.out.println("updating " + file);
            writer.updateDocument(new Term("path", file.toString()), doc);
          }
        }
  }
    
    
}
