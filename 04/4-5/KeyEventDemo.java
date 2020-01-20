import java.awt.*;
import java.awt.event.*;

public class KeyEventDemo extends java.awt.Frame {
  
  // Main method
  public static void main(String[] args) {
    new KeyEventDemo();
  }

  // 建構函式
  public KeyEventDemo() {
    super("Key Event Demo");

    // 以Inner Class的方式使用KeyAdapter
    this.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int dx = 0;
        int dy = 0;
    
        // 判斷是否按下Alt鍵
        if (e.isAltDown()) 
          System.out.println("Press Alt key") ;
        
        // 判斷是否按下Ctrl鍵
        if (e.isControlDown()) 
          System.out.println("Press Control key") ;
        
        // 判斷是否按下Shift鍵
        if (e.isShiftDown()) 
          System.out.println("Press Shift key") ;
        
        // 回傳按鍵所代表的按鍵值
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
