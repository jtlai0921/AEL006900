import java.awt.*;
import java.awt.event.*;

public class MouseEventDemo extends java.awt.Frame {
  
  // Main method
  public static void main(String[] args) {
    new MouseEventDemo();
  }

  // 建構函式
  public MouseEventDemo() {
    super("Mouse Event Demo");

    // 以Inner Class的方式使用MouseAdapter
    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        System.out.println("按下並釋放滑鼠按鍵: " + e.getButton() + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseEntered(MouseEvent e) {
        System.out.println("滑鼠移至物件上方" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseExited(MouseEvent e) {
        System.out.println("滑鼠離開物件" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mousePressed(MouseEvent e) {
        System.out.println("按下滑鼠按鍵: " + e.getButton() + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseReleased(MouseEvent e) {
        System.out.println("釋放滑鼠按鍵" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    });        

    // 以Inner Class的方式使用MouseMotionAdapter
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        System.out.println("按下滑鼠按鍵並拖曳滑鼠" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseMoved(MouseEvent e) {
        System.out.println("在物件上方移動滑鼠" + "  X: " + e.getX() + ", Y: " + e.getY());
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
