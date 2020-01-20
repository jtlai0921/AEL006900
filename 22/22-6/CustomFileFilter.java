import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.*;

// �~��javax.swing.filechooser.FileFilter���O
public class CustomFileFilter extends javax.swing.filechooser.FileFilter {

  private String description = null;
  private String extension = null;

  // �غc�禡
  public CustomFileFilter(String description, String extension) {
    this.description = description;
    this.extension = "." + extension.toLowerCase();
  }

  // ���o�z����󪺻���
  public String getDescription() {
    return this.description;
  }

  // ���o�ɮת����ɦW
  public String getExtension() {
    return this.extension;
  }

  // �P�_�z�����O�_�������w�ɮ�
  public boolean accept(File f) {
    if (f == null) 
      return false;
    if (f.isDirectory())
      return true;
    
    return f.getName().toLowerCase().endsWith(extension);
  }
}
