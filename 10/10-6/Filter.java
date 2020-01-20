import java.io.*;

// ��@FilenameFilter����
public class Filter implements java.io.FilenameFilter {
  private String extension = null;

  // �غc�禡
  public Filter(String extension) {  
    this.extension = "." + extension; 
  }

  public String getExtension() {
    return this.extension;
  }
  
  // ��@FilenameFilter��������k
  public boolean accept(File dir, String name){ 
    return name.endsWith(extension); 
  }
}
