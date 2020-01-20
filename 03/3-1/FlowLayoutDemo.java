import java.awt.*;
import java.applet.*;

public class FlowLayoutDemo extends java.applet.Applet {

  // 建構函式
  public FlowLayoutDemo() {
  }

  public void init() {
    Button button1 = new Button("OK");
    Button button2 = new Button("Cancel");
    Button button3 = new Button("Yes");
    Button button4 = new Button("No");
    
    // 將物件加至Java Applet中
    // 預設Layout Manager為FlowLayout
    add(button1);
    add(button2);
    add(button3);
    add(button4);
  }
}
