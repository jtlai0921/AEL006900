import java.awt.*;
import java.awt.event.*;

public class TextFieldEventDemo extends java.awt.Frame implements 
  ActionListener,      // ��@ActionListener���� 
  KeyListener,         // ��@KeyListener����  
  MouseListener,       // ��@MouseListener���� 
  MouseMotionListener, // ��@MouseMotionListener����
  TextListener {       // ��@TextListener����

  java.awt.TextField textfield;

  public static void main(String args[]){
    new TextFieldEventDemo();
  }
  
  // �غc�禡
  public TextFieldEventDemo() {
    super("TextField Event Demo");

    // �w�q Layout Manager �� FlowLayout
    setLayout(new FlowLayout());

    // �غc�禡 1
    textfield = new TextField();
    // �]�w��r��쪺��ܤ�r
    textfield.setText("TextField 1");
    // �]�w��r��쪺��ܤ�r�r��
    textfield.setColumns(10);
    // �]�w�I���C��
    textfield.setBackground(Color.PINK);
    // �]�w�e���C��
    textfield.setForeground(Color.CYAN);
    // ����Ҧ�����r���e
    textfield.selectAll();
    
    // ���U ActionListener
    textfield.addActionListener(this);
    // ���U KeyListener
    textfield.addKeyListener(this);    
    // ���U MouseListener
    textfield.addMouseListener(this);    
    // ���U MouseMotionListener
    textfield.addMouseMotionListener(this);    
    // ���U TextListener
    textfield.addTextListener(this);    
    
    add(textfield);

    // �]�w�������j�p
    this.setSize(200, 100);

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

  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(textfield))
      System.out.println("���r���W���UEnter��");
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
      System.out.println("��r��줺�e����");
  }
}
