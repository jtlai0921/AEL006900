import java.awt.*;
import java.awt.event.*;

// 實作MouseWheelListener介面
public class MouseWheelEventDemo extends java.awt.Frame implements MouseWheelListener {

  // Main method
  public static void main(String[] args) {
    new MouseWheelEventDemo();
  }

  // 建構函式
  public MouseWheelEventDemo() {
    super("Mouse Wheel Event Demo");

    this.addMouseWheelListener(this);    

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

  // 當滾動滑鼠滾輪時
  public void mouseWheelMoved(MouseWheelEvent e) {
    System.out.println("Scroll Type: " + e.getScrollType() + 
      ", Units: " + e.getScrollAmount() + 
      ", Clicks: " + e.getWheelRotation());

    int dy = e.getScrollAmount();
    int direction = e.getWheelRotation();

    this.setLocation(this.getX(), this.getY() + dy*direction);
   }
}
