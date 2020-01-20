import java.lang.*;

public class HelloWorld {
  public static void main(String args[]){
    if (args.length > 0) {
      for (int i=0; i < args.length; i++)
        System.out.println("Parameter " + i + ": " + args[i]); 
    }
    else {
      System.out.println("No parameter.");
    }
  }
}