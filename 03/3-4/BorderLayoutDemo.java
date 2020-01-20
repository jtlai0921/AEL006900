import java.awt.*;
import java.awt.event.*;

public class BorderLayoutDemo extends java.awt.Frame {

  public static void main(String args[]){
    new BorderLayoutDemo();
  }
  
  // 建構函式
  public BorderLayoutDemo() {
    super("Border Layout Demo");
    
    Button button1 = new Button("EAST");
    Button button2 = new Button("SOUTH");
    Button button3 = new Button("WEST");
    Button button4 = new Button("NORTH");
    Button button5 = new Button("CENTER");

    // 將物件加至Frame中
    add(button1, BorderLayout.EAST);
    add(button2, BorderLayout.SOUTH);
    add(button3, BorderLayout.WEST);
    add(button4, BorderLayout.NORTH);
    add(button5, BorderLayout.CENTER);

    // 設定視窗的大小
    this.setSize(200, 200);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}