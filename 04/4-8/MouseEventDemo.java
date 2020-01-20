import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

// 實作 MouseInputListener 介面
public class MouseEventDemo extends java.awt.Frame implements MouseInputListener {
  
  // Main method
  public static void main(String[] args) {
    new MouseEventDemo();
  }

  // 建構函式
  public MouseEventDemo() {
    super("Mouse Event Demo");

    // 註冊 MouseListener
    this.addMouseListener(this);    
    // 註冊 MouseMotionListener
    this.addMouseMotionListener(this);    

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
  
  // 實作MouseListener介面之方法
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

  // 實作MouseMotionListener介面之方法
  public void mouseDragged(MouseEvent e) {
    System.out.println("按下滑鼠按鍵並拖曳滑鼠" + "  X: " + e.getX() + ", Y: " + e.getY());
  }

  public void mouseMoved(MouseEvent e) {
    System.out.println("在物件上方移動滑鼠" + "  X: " + e.getX() + ", Y: " + e.getY() + ", ID: " + e.getID());
  }
}
