import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class GridLayoutDemo extends java.applet.Applet {
  private String orientation ;

  public static void main(String args[]){
    GridLayoutFrame frame = new GridLayoutFrame();
    
    // 設定視窗的大小
    frame.setSize(200, 200);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    frame.setVisible(true);
  }

  // 建構函式
  public GridLayoutDemo() {
    System.out.println("Applet建構函式");
  }
  
  public void init() {
    System.out.println("Applet init()");

    // 取得 Java Applet 之參數
    orientation = getParameter("orientation");

    final int row = 4;    // 列
    final int column = 3; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));
    
    // 設定物件的配置方向
    if (orientation.equalsIgnoreCase("RIGHT_TO_LEFT"))
      setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    else
      setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        add(new java.awt.Button("(" + i + ", " + j + ")"));
      }
    }
  }
}

class GridLayoutFrame extends java.awt.Frame {
  // 建構函式
  public GridLayoutFrame() {
    super("Grid Layout Demo");

    final int row = 4;    // 列
    final int column = 3; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));

    setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        add(new java.awt.Button("(" + i + ", " + j + ")"));
      }
    }

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}