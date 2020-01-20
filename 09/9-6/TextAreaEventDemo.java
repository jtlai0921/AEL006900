import java.awt.*;
import java.awt.event.*;

public class TextAreaEventDemo extends java.awt.Frame implements 
  KeyListener,         // ��@KeyListener����  
  MouseListener,       // ��@MouseListener���� 
  MouseMotionListener, // ��@MouseMotionListener����
  TextListener {       // ��@TextListener����

  java.awt.TextArea textarea;

  public static void main(String args[]){
    new TextAreaEventDemo();
  }
  
  // �غc�禡
  public TextAreaEventDemo() {
    super("TextArea Event Demo");

    textarea = new TextArea();
    // �]�w��ܤ�r
    textarea.setText("TextArea Event Demo");
    // �]�wTextArea����ܦC��
    textarea.setRows(10);
    // �]�wTextArea����ܦ��
    textarea.setColumns(30);

    // ���U KeyListener
    textarea.addKeyListener(this);    
    // ���U MouseListener
    textarea.addMouseListener(this);    
    // ���U MouseMotionListener
    textarea.addMouseMotionListener(this);    
    // ���U TextListener
    textarea.addTextListener(this);    

    java.awt.Panel panel1 = new Panel();
    panel1.add(textarea);
    add(panel1);

    // �]�w�������j�p
    this.setSize(250, 220);

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

  // ��@KeyListener��������k
  public void keyPressed(KeyEvent e) {
    // �^�ǫ���ҥN�������
    System.out.println("Key Pressed: " + e.getKeyCode());
  }  

  public void keyReleased(KeyEvent e) {
    System.out.println("Char: " + e.getKeyChar()) ;
  }  

  public void keyTyped(KeyEvent e) {}  

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

  // ��@TextListener��������k
  public void textValueChanged(TextEvent e) {
    if (e.getID() == TextEvent.TEXT_VALUE_CHANGED)
      System.out.println("��r�ϰ줺�e����");
  }
}
