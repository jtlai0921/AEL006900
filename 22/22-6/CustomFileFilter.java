import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.*;

// 繼承javax.swing.filechooser.FileFilter類別
public class CustomFileFilter extends javax.swing.filechooser.FileFilter {

  private String description = null;
  private String extension = null;

  // 建構函式
  public CustomFileFilter(String description, String extension) {
    this.description = description;
    this.extension = "." + extension.toLowerCase();
  }

  // 取得篩選條件的說明
  public String getDescription() {
    return this.description;
  }

  // 取得檔案的副檔名
  public String getExtension() {
    return this.extension;
  }

  // 判斷篩選條件是否接受指定檔案
  public boolean accept(File f) {
    if (f == null) 
      return false;
    if (f.isDirectory())
      return true;
    
    return f.getName().toLowerCase().endsWith(extension);
  }
}
