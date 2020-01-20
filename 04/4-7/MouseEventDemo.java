import java.awt.*;
import java.awt.event.*;

public class MouseEventDemo extends java.awt.Frame implements 
  MouseListener,         // ��@MouseListener���� 
  MouseMotionListener {  // ��@MouseMotionListener����

  // Main method
  public static void main(String[] args) {
    new MouseEventDemo();
  }

  // �غc�禡
  public MouseEventDemo() {
    super("Mouse Event Demo");

    // ���U MouseListener
    this.addMouseListener(this);    
    // ���U MouseMotionListener
    this.addMouseMotionListener(this);    

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
  
  // ��@MouseListener��������k
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

  // ��@MouseMotionListener��������k
  public void mouseDragged(MouseEvent e) {
    System.out.println("���U�ƹ�����é즲�ƹ�" + "  X: " + e.getX() + ", Y: " + e.getY());
  }

  public void mouseMoved(MouseEvent e) {
    System.out.println("�b����W�貾�ʷƹ�" + "  X: " + e.getX() + ", Y: " + e.getY() + ", ID: " + e.getID());
  }
}