import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;
 
// 繼承javax.swing.filechooser.FileView
public class CustomFileView extends javax.swing.filechooser.FileView {
  ImageIcon wavIcon  = null;
  ImageIcon auIcon   = null;
  ImageIcon aifIcon  = null;
  ImageIcon aiffIcon = null;

  // 建構函式
  public CustomFileView() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 定義圖示
    wavIcon  = new ImageIcon(cl.getResource("images/wav.png"));
    auIcon   = new ImageIcon(cl.getResource("images/au.png"));
    aifIcon  = new ImageIcon(cl.getResource("images/aif.png"));
    aiffIcon = new ImageIcon(cl.getResource("images/aiff.png"));
  }

  public Icon getIcon(File f) {
    String extension = getExtension(f);
    Icon icon = null;

    if (extension != null) {
      if (extension.equals("wav")) {
        icon = wavIcon;
      } 
      else if (extension.equals("au")) {
        icon = auIcon;
      } 
      else if (extension.equals("aif")) {
        icon = aifIcon;
      } 
      else if (extension.equals("aiff")) {
        icon = aiffIcon;
      }
    }
    return icon;
  }

  private String getExtension(File f) {
    String extension = null;
    String filename = f.getName();
    int i = filename.lastIndexOf('.');

    if (i > 0 &&  i < filename.length() - 1) {
      extension = filename.substring(i+1).toLowerCase();
    }
    return extension;
  }
}
