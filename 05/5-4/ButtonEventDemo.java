import java.awt.*;
import java.awt.event.*;

public class ButtonEventDemo extends java.awt.Frame {

  Button[] button = new Button[6];

  public static void main(String[] args) {
    new ButtonEventDemo();
  }

  // 建構函式
  public ButtonEventDemo() {
    super("Button Event Demo");

    // 定義 Layout Manager 為 FlowLayout
    setLayout(new FlowLayout());

    String[] label = {"New", "Open", "Save", "Cut", "Copy", "Paste"};

    for (int i=0; i<6; i++) {
      button[i] = new Button(label[i]);

      // 以Inner Class的方式使用ActionListener介面
      button[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          // 自訂方法
          button_actionPerformed(e);
        }
      });

      add(button[i]);
    }

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
  
  void button_actionPerformed(ActionEvent e) {
//    String source = null;
//    for (int i=0; i<6; i++) {
//      if (e.getSource().equals(button[i]))
//        source = "Press " + button[i].getActionCommand() + " button";
//    }

    String source = "Press " + e.getActionCommand() + " button";
    
    System.out.println(source);
  }
}
