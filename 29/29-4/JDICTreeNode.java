import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JOptionPane;

import java.io.File;

// 繼承javax.swing.tree.DefaultMutableTreeNode
public class JDICTreeNode extends DefaultMutableTreeNode {
  private static String osName = System.getProperty("os.name").toLowerCase();
  private boolean explored = false;

  public JDICTreeNode(File file) {
    setUserObject(file);
  }

  public boolean getAllowsChildren() {
    return isDirectory();
  }

  public boolean isLeaf() {
    return !isDirectory();
  }

  public File getFile() {
    return (File) getUserObject();
  }

  public boolean isExplored() {
    return explored;
  }

  public boolean isDirectory() {
    File file = getFile();

    return file.isDirectory();
  }

  public void explore() {
    File file = getFile();
  
    if (!isDirectory()) {
        return;
    }

    if (!file.exists()) {
      File currentFile = getFile();
      String fileName = currentFile.getName();
  
      if (osName.startsWith("windows")) {
        File parentFile = currentFile.getParentFile();
  
        if (currentFile.getParentFile() == null) {
          JOptionPane.showMessageDialog(null, "請將磁碟片插入磁碟機中.", "JDIC File Explorer", JOptionPane.ERROR_MESSAGE);
        }
      }
  
      JOptionPane.showMessageDialog(null, "檔案 " + fileName + " 不存在.", "JDIC File Explorer", JOptionPane.ERROR_MESSAGE);
  
      return;
    }
    
    if (!file.canRead()) {
      JOptionPane.showMessageDialog(null, "無使用權限.", "JDIC File Explorer", JOptionPane.ERROR_MESSAGE);
      return;
    }
    
    if (!isExplored()) {
      File[] children = file.listFiles();

      if (children != null) {
        for (int i = 0; i < children.length; ++i) {
          if (children[i].isDirectory()) {
            add(new JDICTreeNode(children[i]));
          }
        }
      }

      explored = true;
    }
  }

  public String toString() {
    File file = (File) getUserObject();
    String filename = file.toString();

    int index = filename.lastIndexOf(File.separator);

    return (index != -1 && index != filename.length() - 1) ? filename.substring(index + 1) : filename;
  }

  public int getChildrenCount() {
    File file = getFile();

    if (!file.exists()) {
      File currentFile = getFile();
      String fileName = currentFile.getName();
  
      if (osName.startsWith("windows")) {
        File parentFile = currentFile.getParentFile();
  
        if (currentFile.getParentFile() == null) {
          JOptionPane.showMessageDialog(null, "請將磁碟片插入磁碟機中.", "JDIC File Explorer", JOptionPane.ERROR_MESSAGE);
        }
      }
  
      JOptionPane.showMessageDialog(null, "檔案 " + fileName + " 不存在.", "JDIC File Explorer", JOptionPane.ERROR_MESSAGE);

      return 0;
    }

    if (!file.canRead()) {
      JOptionPane.showMessageDialog(null, "無使用權限.", "JDIC File Explorer", JOptionPane.ERROR_MESSAGE);

      return 0;
    }
    if (!isDirectory()) {
      return 0;
    } 
    else {
      File[] children = file.listFiles();

      return (children != null) ? children.length : 0;
    }
  }

  public long getSize() {
    File file = getFile();

    if (!file.canRead()) {
      return 0;    
    }
  
    if (!isDirectory()) {
      return (file.length());
    }

    File[] children = file.listFiles();

    long size = 0;

    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        size += children[i].length();
      }
    }

    return size;
  }
}
