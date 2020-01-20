import java.io.File;
import java.util.Vector;

public class JDICUtility {
  private static String osName = System.getProperty("os.name").toLowerCase();
  private static File roots[] = null;

  public static String length2KB(long length) {
    long KB = 1024;
    long MB = 1024 * 1024;

    if (length == 0) {
      return String.valueOf("0 KB ");
    }

    String kbStr = "";
    long mbCount = length / MB;

    long kbCount = ((length - mbCount * MB) + 1023) / KB;

    if (mbCount != 0) {
      kbStr += String.valueOf(mbCount + ",");
    }

    if (kbCount == 0) {
      if (mbCount == 0) {
        kbStr += String.valueOf("0 KB ");
      } 
      else {
        kbStr += String.valueOf("000 KB ");
      }
    } 
    else {
      kbStr += String.valueOf(kbCount + " KB ");
    }

    return kbStr;
  }
  
  static class FileSystemRoot extends File {
    public FileSystemRoot(File f) {
      super(f, "");
    }
    
    public FileSystemRoot(String s) {
      super(s);
    }
    
    public boolean isDirectory() {
      return true;
    }
  }
  
  public static File[] getRoots() {
    if (roots == null) {
      constructRoots();
    }

    return roots;
  }
  
  private static void constructRoots() {
    if (osName.startsWith("windows")) {
      // ªx«¬Generics
      Vector<Object> rootsVector = new Vector<Object>();
  
      FileSystemRoot floppy = new FileSystemRoot("A" + ":" + "\\");
      rootsVector.addElement(floppy);

      for (char c = 'C'; c <= 'Z'; c++) {
        char device[] = {c, ':', '\\'};
        String deviceName = new String(device);
        File deviceFile = new FileSystemRoot(deviceName);
        if (deviceFile != null && deviceFile.exists()) {
          rootsVector.addElement(deviceFile);
        }
      }
      roots = new File[rootsVector.size()];
      rootsVector.copyInto(roots);
    } 
    else {
      roots = File.listRoots();
    }
  }
}
