import java.awt.*;
import java.applet.*;

public class BorderLayoutDemo extends java.applet.Applet {

  // 建構函式
  public BorderLayoutDemo() {
  }

  public void init() {
    // 取得Java Applet之參數
    int hGap = Integer.parseInt(getParameter("hGap"));
    int vGap = Integer.parseInt(getParameter("vGap"));

    BorderLayout borderlayout = new BorderLayout();

    // 設定物件間的水平間距
    borderlayout.setHgap(hGap);
    // 設定物件間的垂直間距
    borderlayout.setVgap(vGap);

    // 設定Java Applet之Layout Manager
    setLayout(borderlayout); 
    
    Button button1 = new Button("EAST");
    Button button2 = new Button("SOUTH");
    Button button3 = new Button("WEST");
    Button button4 = new Button("NORTH");
    Button button5 = new Button("CENTER");

    // 將物件加至Java Applet中
    add(button1, BorderLayout.EAST);
    add(button2, BorderLayout.SOUTH);
    add(button3, BorderLayout.WEST);
    add(button4, BorderLayout.NORTH);
    add(button5, BorderLayout.CENTER);
  }
}
