import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.*;

public class HtmlFileFilter extends javax.swing.filechooser.FileFilter {

  private String description = null;
  private String extension = null;

  public HtmlFileFilter(String description, String extension) {
    this.description = description;
    this.extension = "." + extension.toLowerCase();
  }

  public String getDescription() {
    return this.description;
  }

  public String getExtension() {
    return this.extension;
  }

  public boolean accept(File f) {
    if (f == null) 
      return false;
    if (f.isDirectory())
      return true;
    
    return f.getName().toLowerCase().endsWith(extension);
  }
}
