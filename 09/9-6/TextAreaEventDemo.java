import java.awt.*;
import java.awt.event.*;

public class TextAreaEventDemo extends java.awt.Frame implements 
  KeyListener,         // 實作KeyListener介面  
  MouseListener,       // 實作MouseListener介面 
  MouseMotionListener, // 實作MouseMotionListener介面
  TextListener {       // 實作TextListener介面

  java.awt.TextArea textarea;

  public static void main(String args[]){
    new TextAreaEventDemo();
  }
  
  // 建構函式
  public TextAreaEventDemo() {
    super("TextArea Event Demo");

    textarea = new TextArea();
    // 設定顯示文字
    textarea.setText("TextArea Event Demo");
    // 設定TextArea的顯示列數
    textarea.setRows(10);
    // 設定TextArea的顯示行數
    textarea.setColumns(30);

    // 註冊 KeyListener
    textarea.addKeyListener(this);    
    // 註冊 MouseListener
    textarea.addMouseListener(this);    
    // 註冊 MouseMotionListener
    textarea.addMouseMotionListener(this);    
    // 註冊 TextListener
    textarea.addTextListener(this);    

    java.awt.Panel panel1 = new Panel();
    panel1.add(textarea);
    add(panel1);

    // 設定視窗的大小
    this.setSize(250, 220);

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

  // 實作KeyListener介面之方法
  public void keyPressed(KeyEvent e) {
    // 回傳按鍵所代表的按鍵值
    System.out.println("Key Pressed: " + e.getKeyCode());
  }  

  public void keyReleased(KeyEvent e) {
    System.out.println("Char: " + e.getKeyChar()) ;
  }  

  public void keyTyped(KeyEvent e) {}  

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

  // 實作TextListener介面之方法
  public void textValueChanged(TextEvent e) {
    if (e.getID() == TextEvent.TEXT_VALUE_CHANGED)
      System.out.println("文字區域內容改變");
  }
}
