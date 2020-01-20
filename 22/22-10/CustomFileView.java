import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;

// 繼承javax.swing.filechooser.FileView
public class CustomFileView extends javax.swing.filechooser.FileView {
  ImageIcon aviIcon  = null;
  ImageIcon midiIcon = null;
  ImageIcon movIcon  = null;
  ImageIcon mp3Icon  = null;
  ImageIcon mpegIcon = null;
  ImageIcon wavIcon  = null;
  ImageIcon wmaIcon  = null;

  // 建構函式
  public CustomFileView() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 定義圖示
    aviIcon  = new ImageIcon(cl.getResource("images/avi.png"));
    midiIcon = new ImageIcon(cl.getResource("images/midi.png"));
    movIcon  = new ImageIcon(cl.getResource("images/mov.png"));
    mp3Icon  = new ImageIcon(cl.getResource("images/mp3.png"));
    mpegIcon = new ImageIcon(cl.getResource("images/mpeg.png"));
    wavIcon  = new ImageIcon(cl.getResource("images/wav.png"));
    wmaIcon  = new ImageIcon(cl.getResource("images/wma.png"));
  }

  public Icon getIcon(File f) {
    String extension = getExtension(f);
    Icon icon = null;

    if (extension != null) {
      if (extension.equals("avi")) {
        icon = aviIcon;
      } 
      else if (extension.equals("midi")) {
        icon = midiIcon;
      } 
      else if (extension.equals("mov")) {
        icon = movIcon;
      } 
      else if (extension.equals("mp3")) {
        icon = mp3Icon;
      }
      else if (extension.equals("mpeg") || extension.equals("mpg")) {
        icon = mpegIcon;
      }
      else if (extension.equals("wav")) {
        icon = wavIcon;
      }
      else if (extension.equals("wma")) {
        icon = wmaIcon;
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
