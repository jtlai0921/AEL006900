import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;
   
// 繼承javax.swing.filechooser.FileView
public class CustomFileView extends javax.swing.filechooser.FileView {
  ImageIcon jpgIcon = null;
  ImageIcon gifIcon = null;
  ImageIcon tifIcon = null;
  ImageIcon pngIcon = null;

  // 建構函式
  public CustomFileView() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 定義圖示
    jpgIcon = new ImageIcon(cl.getResource("images/jpg.png"));
    gifIcon = new ImageIcon(cl.getResource("images/gif.png"));
    tifIcon = new ImageIcon(cl.getResource("images/tif.png"));
    pngIcon = new ImageIcon(cl.getResource("images/png.png"));
  }

  public Icon getIcon(File f) {
    String extension = getExtension(f);
    Icon icon = null;

    if (extension != null) {
       if (extension.equals("jpg") || extension.equals("jpeg")) {
        icon = jpgIcon;
      } 
      else if (extension.equals("gif")) {
        icon = gifIcon;
      } 
      else if (extension.equals("tif") || extension.equals("tiff")) {
        icon = tifIcon;
      } 
      else if (extension.equals("png")) {
        icon = pngIcon;
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
