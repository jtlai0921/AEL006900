import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JToggleButtonEventDemo extends javax.swing.JFrame implements ActionListener {

  JToggleButton jbutton[] = new JToggleButton[7];
  JLabel jlabel;

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

    new JToggleButtonEventDemo();
  }

  // 建構函式
  public JToggleButtonEventDemo() {
    super("JToggleButton Event Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    String title[] = {"Left", "Right", "Home", "Reload", "Stop", "Receive", "Send"};

    for (int i=0; i<title.length; i++) {
      jbutton[i] = new JToggleButton(new ImageIcon(cl.getResource("images/" + title[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(36, 36));
      jbutton[i].setToolTipText(title[i]);

      // 註冊 ActionListener
      jbutton[i].addActionListener(this);

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==4)
        jtoolbar.addSeparator();
    }

    this.add(jtoolbar, BorderLayout.NORTH);

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jbutton[0]);
    group1.add(jbutton[1]);
    group1.add(jbutton[2]);

    // 建構群組
    ButtonGroup group2 = new ButtonGroup();
    group2.add(jbutton[3]);
    group2.add(jbutton[4]);

    // 建構群組
    ButtonGroup group3 = new ButtonGroup();
    group3.add(jbutton[5]);
    group3.add(jbutton[6]);

    // 建立凹下蝕刻邊框
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    JPanel jpanel = new JPanel();
    // 設定物件之邊框樣式
    jpanel.setBorder(border);
    jpanel.setLayout(new BorderLayout());

    jlabel = new JLabel("JToggleButton Event");
    jpanel.add(jlabel, BorderLayout.WEST);

    this.add(jpanel, BorderLayout.SOUTH);

    // 設定視窗的大小
    this.setSize(320, 150);

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

  // 當動作發生時
  public void actionPerformed(ActionEvent e) {
    String source = "";

    for (int i=0; i < jbutton.length; i++) {
      if (e.getSource().equals(jbutton[i]))
        source = "Select " + jbutton[i].getToolTipText() + " Toggle Button";
    }

    jlabel.setText(source);
  }
}
