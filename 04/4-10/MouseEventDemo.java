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

    // 自訂繼承MouseAdapter之類別
    MouseHandler handler1 = new MouseHandler();
    // 註冊 MouseListener
    addMouseListener(handler1);    

    // 自訂繼承MouseMotionAdapter之類別
    MouseMotionHandler handler2 = new MouseMotionHandler();
    // 註冊 MouseMotionListener
    addMouseMotionListener(handler2);  

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

// 繼承 MouseAdapter 類別
class MouseHandler extends MouseAdapter {

  // 建構函式
  public MouseHandler() {
  }

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
}  

// 繼承 MouseMotionAdapter 類別
class MouseMotionHandler extends MouseMotionAdapter {

  // 建構函式
  public MouseMotionHandler() {
  }

  public void mouseDragged(MouseEvent e) {
    System.out.println("按下滑鼠按鍵並拖曳滑鼠" + "  X: " + e.getX() + ", Y: " + e.getY());
  }

  public void mouseMoved(MouseEvent e) {
    System.out.println("在物件上方移動滑鼠" + "  X: " + e.getX() + ", Y: " + e.getY());
  }
}