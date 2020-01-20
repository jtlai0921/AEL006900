import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Theme
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class ThemeDemo extends javax.swing.JFrame implements ActionListener {

  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[2];

  String item[] = {"Ocean", "Steel"};

  ClassLoader cl;

  // Main method
  public static void main(String[] args) {
    // 將Metal Look and Feel中的字形由粗體改為標準字型
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      // Metal Look & Feel
      UIManager.setLookAndFeel(new MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new ThemeDemo();
  }

  // 建構函式
  public ThemeDemo() {
    // 取得目前之Class Loader
    cl = this.getClass().getClassLoader();

    // 建立選單列
    JMenuBar jmenubar = createJMenuBar();

    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    this.setUndecorated(true);
    this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    this.setTitle("Theme: Ocean");

    // 設定視窗的大小
    this.setSize(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

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

  private JMenuBar createJMenuBar() {
    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();

    // 建立選單
    JMenu jmenuFile = new JMenu("File");

    // 建立選單項目
    JMenuItem jmenuOpen = new JMenuItem("Open ...", new ImageIcon(cl.getResource("images/open.gif")));
    jmenuOpen.setMnemonic('O');
    jmenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser jfileChooser = new JFileChooser();

        jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        jfileChooser.setDialogTitle("Open File ...");
        
        if (jfileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) 
          return;
      }
    });

    // 建立選單項目
    JMenuItem jmenuFileExit = new JMenuItem("Exit");
    jmenuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuOpen);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuFileExit);

    // 建立選單
    JMenu jmenuTheme = new JMenu("Theme");

    // 建構群組
    ButtonGroup group = new ButtonGroup();

    for (int i=0; i<item.length; i++) {
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      jmenuTheme.add(jrbmenuitem[i]);
      group.add(jrbmenuitem[i]);
      
      // 註冊 ActionListener
      jrbmenuitem[i].addActionListener(this);
     
      if (i==0)
        jrbmenuitem[i].setSelected(true);
    }

    JMenu jmenuHelp = new JMenu();
    jmenuHelp.setText("Help");

    // About
    JMenuItem jmenuAbout = new JMenuItem("About", new ImageIcon(cl.getResource("images/about.gif")));
    jmenuAbout.setMnemonic('A');
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Java Theme", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    jmenuHelp.add(jmenuAbout);

    // 新增選單至選單列
    jmenubar.add(jmenuFile);
    jmenubar.add(jmenuTheme);
    jmenubar.add(jmenuHelp);

    return jmenubar;
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals(item[0])) { 
      // 設定為Metal Look & Feel之Ocean主題
      MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    }
    else if(e.getActionCommand().equals(item[1])) {
      // 設定為Metal Look & Feel之Steel主題
      MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
    }

    this.setTitle("Theme: " + e.getActionCommand().toString());
      
    try {
      UIManager.setLookAndFeel(new MetalLookAndFeel());
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
