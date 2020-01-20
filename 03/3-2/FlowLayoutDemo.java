import java.awt.*;
import java.applet.*;

public class FlowLayoutDemo extends java.applet.Applet {

  // 建構函式
  public FlowLayoutDemo() {
  }

  public void init() {
    // 取得Java Applet之參數
    String align = getParameter("alignment");
    int hGap = Integer.parseInt(getParameter("hGap"));
    int vGap = Integer.parseInt(getParameter("vGap"));

    FlowLayout flowlayout = null;

    if (align.equals("CENTER"))
      flowlayout = new FlowLayout(FlowLayout.CENTER);
    else if (align.equals("LEADING"))
      flowlayout = new FlowLayout(FlowLayout.LEADING);
    else if (align.equals("LEFT"))
      flowlayout = new FlowLayout(FlowLayout.LEFT);
    else if (align.equals("RIGHT"))
      flowlayout = new FlowLayout(FlowLayout.RIGHT);
    else if (align.equals("TRAILING"))
      flowlayout = new FlowLayout(FlowLayout.TRAILING);
    
    // 設定物件間的水平間距
    flowlayout.setHgap(hGap);
    // 設定物件間的垂直間距
    flowlayout.setVgap(vGap);

    // 設定Java Applet之Layout Manager
    setLayout(flowlayout); 

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
