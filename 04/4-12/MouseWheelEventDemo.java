import java.awt.*;
import java.awt.event.*;

public class MouseWheelEventDemo extends java.awt.Frame {

  // Main method
  public static void main(String[] args) {
    new MouseWheelEventDemo();
  }

  // 建構函式
  public MouseWheelEventDemo() {
    super("Mouse Wheel Event Demo");

    // 註冊 MouseWheelListener
    this.addMouseWheelListener(new MouseWheelListener() {
      // 當滾動滑鼠滾輪時
      public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Scroll Type: " + e.getScrollType() + 
          ", Units: " + e.getScrollAmount() + 
          ", Clicks: " + e.getWheelRotation());
    
        int dy = e.getScrollAmount();
        int direction = e.getWheelRotation();
    
        MouseWheelEventDemo.this.setLocation(MouseWheelEventDemo.this.getX(), MouseWheelEventDemo.this.getY() + dy*direction);
       }
    });

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
