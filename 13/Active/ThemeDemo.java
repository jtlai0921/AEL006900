import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Theme
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class ThemeDemo extends JFrame {
  JMenuBar jmenubar;
  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[9];

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[10];

  String item[] = {
    "Ocean", "Steel", "Green", "Aqua", "Sandstone", 
    "Ruby", "Presentation", "Contrast", "Low Vision"};

  String tbitem[]  = {"New", "Open", "Save", "Save As", "About"};
  String tbimage[] = {"new", "open", "save", "saveas", "about"};

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new ThemeDemo();
  }

  // 建構函式
  public ThemeDemo() {
    this.setUndecorated(true);
    this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    this.setTitle("Active Window");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
    // Create icons
//    this.setIconImage(new ImageIcon(cl.getResource("images/JavaCup.png")).getImage());

    this.setLayout(new BorderLayout());

    // 測試用
    JTextArea jtextarea = new JTextArea();
    JScrollPane jscrollpane = new JScrollPane(jtextarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    for (int i=0; i<tbimage.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + tbimage[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(tbitem[i]);
      
      jtoolbar.add(jbutton[i]);
      
      if (i==3 || i==5)
        jtoolbar.addSeparator();
    }

    jtoolbar.addSeparator();

    JLabel jLabel1 = new JLabel("Student Name:");
    JTextField jTextField1 = new JTextField();
    
    JPanel jpanel = new JPanel();
    

    // 定義Layout Manager為GroupLayout
    GroupLayout layout = new GroupLayout(jpanel);
    jpanel.setLayout(layout);

    // 自動產生物件間之間隔
    layout.setAutoCreateGaps(true);
    
    // 自動產生物件與容器間之間隔
    layout.setAutoCreateContainerGaps(true);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addComponent(jLabel1)
        .addComponent(jTextField1)
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組並沿著物件之基準線對齊 
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(jLabel1)
        .addComponent(jTextField1)
    );

//    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jpanel, BorderLayout.NORTH);
    this.add(jscrollpane, BorderLayout.CENTER);

    jmenubar = new JMenuBar();
    this.setJMenuBar(jmenubar);

    JMenu jmenuFile = new JMenu();
    jmenuFile.setText("File");

    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open");
//    JMenuItem jmenuOpen = new JMenuItem("Open ...", new ImageIcon(cl.getResource("images/open.gif")));
    jmenuOpen.setMnemonic('O');
//    jmenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
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

    JMenuItem jmenuFileExit = new JMenuItem("Exit");
    jmenuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    jmenuFile.add(jmenuOpen);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuFileExit);

    jmenubar.add(jmenuFile);

    JMenu jmenuTheme = new JMenu();
    jmenuTheme.setText("Theme");

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();

    for (int i=0; i<item.length; i++) {
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      jmenuTheme.add(jrbmenuitem[i]);
      group1.add(jrbmenuitem[i]);
      
      // 註冊 ActionListener
      jrbmenuitem[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
     
      if (i==0)
        jrbmenuitem[i].setSelected(true);
    }

    jmenubar.add(jmenuTheme);

    JMenu jmenuHelp = new JMenu();
    jmenuHelp.setText("Help");

    // About
    JMenuItem jmenuAbout = new JMenuItem("About", new ImageIcon(cl.getResource("images/about.gif")));
    jmenuAbout.setMnemonic('A');
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Java Theme Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    jmenuHelp.add(jmenuAbout);
    jmenubar.add(jmenuHelp);

    this.validate();
    this.setSize(new Dimension(250, 250));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);
  }

  public void menu_actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals(item[0])) { 
      MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    }
    else if(e.getActionCommand().equals(item[1])) {
      MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
    }
    else if(e.getActionCommand().equals(item[2])) {
      MetalLookAndFeel.setCurrentTheme(new GreenTheme());
    }
    else if(e.getActionCommand().equals(item[3])) {
      MetalLookAndFeel.setCurrentTheme(new AquaTheme());
    }
    else if(e.getActionCommand().equals(item[4])) {
      MetalLookAndFeel.setCurrentTheme(new SandstoneTheme());
    }
    else if(e.getActionCommand().equals(item[5])) {
      MetalLookAndFeel.setCurrentTheme(new RubyTheme());
    }
    else if(e.getActionCommand().equals(item[6])) {
      MetalLookAndFeel.setCurrentTheme(new PresentationTheme());
    }
    else if(e.getActionCommand().equals(item[7])) {
      MetalLookAndFeel.setCurrentTheme(new ContrastTheme());
    }
    else if(e.getActionCommand().equals(item[8])) {
      MetalLookAndFeel.setCurrentTheme(new LowVisionTheme());
    }

//    this.setTitle("Theme Demo: " + e.getActionCommand().toString());
    this.setTitle("Active Window");
      
    try {
      UIManager.setLookAndFeel(new MetalLookAndFeel());
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
