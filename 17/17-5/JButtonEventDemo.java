import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JButtonEventDemo extends javax.swing.JFrame { 

  static JButton[] jbutton = new JButton[6];
  static JLabel jlabel;

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JButtonEventDemo();
  }

  // 建構函式
  public JButtonEventDemo() {
    super("JButton Event Demo");
    
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    String title[] = {"New", "Open", "Save", "Cut", "Copy", "Paste"};

    Action action = null;
    
    for (int i=0; i < jbutton.length; i++) {
      action = new ButtonAction(title[i], new ImageIcon(cl.getResource("images/" + title[i] + ".gif")));

      jbutton[i] = new JButton();
      jbutton[i].setPreferredSize(new Dimension(36, 36));
      // 設定按鈕的動作
      jbutton[i].setAction(action);

      jtoolbar.add(jbutton[i]);
    }

    // 建立凹下蝕刻邊框
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    JPanel jpanel = new JPanel();
    // 設定物件之邊框樣式
    jpanel.setBorder(border);
    jpanel.setLayout(new BorderLayout());

    jlabel = new JLabel("JButton Event");
    jpanel.add(jlabel, BorderLayout.WEST);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jpanel, BorderLayout.SOUTH);

    // 設定視窗的大小
    this.setSize(300, 150);

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

  class ButtonAction extends AbstractAction {
    public ButtonAction(String text, ImageIcon icon) {
      super(text, icon);
    }
  
    public void actionPerformed(ActionEvent e) {
      String source = "";
  
      for (int i=0; i < JButtonEventDemo.jbutton.length; i++) {
        if (e.getSource().equals(JButtonEventDemo.jbutton[i]))
          source = "Select " + JButtonEventDemo.jbutton[i].getText() + " Button";
      }
  
      JButtonEventDemo.jlabel.setText(source);
    }
  }  
}
