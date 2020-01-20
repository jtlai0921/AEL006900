import java.io.*;
import java.util.*;
import java.lang.*;

public class deployProperties
{
  public static void main(String args[]) 
    throws FileNotFoundException, IOException
  {
    try {
      
      Properties p = new Properties();
  
      FileInputStream in = new FileInputStream("deployment.properties");

      p.load(in);
      
      String version = p.getProperty("deployment.version");
      
      System.out.println("deployment.version=" + version);
    }
    catch (FileNotFoundException fnfex) {
      System.out.println("File not found.");
      System.exit(0);
    }
    catch (IOException ioex) {
      System.out.println("File loading error.");
      System.exit(0);
    }
  }
}



