import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JToolBarDemo extends javax.swing.JFrame {

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

    new JToolBarDemo();
  }

  // 建構函式
  public JToolBarDemo() {
    super("JToolBar Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    // 設定為浮動工具列
    jtoolbar.setFloatable(true);
    // 設定Rollover工具列上按鈕的效果
    jtoolbar.setRollover(true);

    String title[] = {"New", "Open", "Save", "Cut", "Copy", "Paste"};
    JButton jbutton[] = new JButton[title.length];

    for (int i=0; i<title.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + title[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(36, 36));
      jbutton[i].setToolTipText(title[i]);

      // 加入按鈕至工具列中
      jtoolbar.add(jbutton[i]);
      
      if (i==2) 
        // 加入分隔線
        jtoolbar.addSeparator();
    }

    jtoolbar.addSeparator();
    
    // 測試用
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String font[] = ge.getAvailableFontFamilyNames();
    
    JComboBox jcombobox = new JComboBox();
    jcombobox.setPreferredSize(new Dimension(120, 36));
    jcombobox.setMaximumSize(new Dimension(120, 36));
    jcombobox.setToolTipText("Font");

    for(int i=0; i < font.length; i++) 
      jcombobox.addItem(font[i]);

    // 選取第anIndex個選項項目
    jcombobox.setSelectedIndex(0);
    // 設定下拉JComboBox類別時，最多可顯示的項目列數
    jcombobox.setMaximumRowCount(5);

    jtoolbar.add(jcombobox);
    // 加入分隔線
    jtoolbar.addSeparator();
    
    this.add(jtoolbar, BorderLayout.NORTH);

    // 設定視窗的大小
    this.setSize(400, 100);

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
