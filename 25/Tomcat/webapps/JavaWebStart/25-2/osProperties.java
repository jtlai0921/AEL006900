import java.lang.*;
import java.util.*;

public class osProperties
{
  public static void main(String args[]) 
  {
    // Operating system name
    String osName = System.getProperty("os.name");
    // Operating system architecture
    String osArch = System.getProperty("os.arch");
    // Operating system version
    String osVersion = System.getProperty("os.version");
    
    System.out.println("OS: " + osName);
    System.out.println("OS architecture: " + osArch);
    System.out.println("OS version: " + osVersion);
  }
}

