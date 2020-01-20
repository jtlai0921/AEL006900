import java.awt.*;
import java.awt.event.*;

public class MouseEventDemo extends java.awt.Frame {
  
  // Main method
  public static void main(String[] args) {
    new MouseEventDemo();
  }

  // �غc�禡
  public MouseEventDemo() {
    super("Mouse Event Demo");

    // �HInner Class���覡�ϥ�MouseAdapter
    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        System.out.println("���U������ƹ�����: " + e.getButton() + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseEntered(MouseEvent e) {
        System.out.println("�ƹ����ܪ���W��" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseExited(MouseEvent e) {
        System.out.println("�ƹ����}����" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mousePressed(MouseEvent e) {
        System.out.println("���U�ƹ�����: " + e.getButton() + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseReleased(MouseEvent e) {
        System.out.println("����ƹ�����" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    });        

    // �HInner Class���覡�ϥ�MouseMotionAdapter
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        System.out.println("���U�ƹ�����é즲�ƹ�" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    
      public void mouseMoved(MouseEvent e) {
        System.out.println("�b����W�貾�ʷƹ�" + "  X: " + e.getX() + ", Y: " + e.getY());
      }
    });        
    
    // �]�w�������j�p
    this.setSize(200, 200);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
