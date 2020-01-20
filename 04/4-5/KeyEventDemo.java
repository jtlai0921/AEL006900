import java.awt.*;
import java.awt.event.*;

public class KeyEventDemo extends java.awt.Frame {
  
  // Main method
  public static void main(String[] args) {
    new KeyEventDemo();
  }

  // �غc�禡
  public KeyEventDemo() {
    super("Key Event Demo");

    // �HInner Class���覡�ϥ�KeyAdapter
    this.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int dx = 0;
        int dy = 0;
    
        // �P�_�O�_���UAlt��
        if (e.isAltDown()) 
          System.out.println("Press Alt key") ;
        
        // �P�_�O�_���UCtrl��
        if (e.isControlDown()) 
          System.out.println("Press Control key") ;
        
        // �P�_�O�_���UShift��
        if (e.isShiftDown()) 
          System.out.println("Press Shift key") ;
        
        // �^�ǫ���ҥN�������
        int i = e.getKeyCode() ;
    
        System.out.println("Key Pressed: " + i);
    
        switch(i) {
          case KeyEvent.VK_UP: 
            dy = -5;
            break;
    
          case KeyEvent.VK_DOWN: 
            dy = 5;
            break;
    
          case KeyEvent.VK_LEFT: 
            dx = -5;
            break;
    
          case KeyEvent.VK_RIGHT: 
            dx = 5;
            break;
        }
        KeyEventDemo.this.setLocation(KeyEventDemo.this.getX() + dx, KeyEventDemo.this.getY() + dy);
      }  

      public void keyReleased(KeyEvent e) {
        System.out.println("Char: " + e.getKeyChar()) ;
      }  
    
      public void keyTyped(KeyEvent e) {
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
