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

  JTabbedPane jtabbedpane;

  ImageIcon tabImage;
  
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

  // �غc�禡
  public ThemeDemo() {
    this.setUndecorated(true);
    this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    this.setTitle("Inactive Window");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
    tabImage = new ImageIcon(cl.getResource("images/newpage.gif"));
    // Create icons
//    this.setIconImage(new ImageIcon(cl.getResource("images/JavaCup.png")).getImage());

    this.setLayout(new BorderLayout());

    // ���ե�
//    JTextArea jtextarea = new JTextArea();
//    JScrollPane jscrollpane = new JScrollPane(jtextarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    for (int i=0; i<tbimage.length - 1; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + tbimage[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
//      jbutton[i].setToolTipText(tbitem[i]);

      if (i == 3)
        jbutton[i].setEnabled(false);
      
      jtoolbar.add(jbutton[i]);
      
      
//      if (i==3 || i==5)
//        jtoolbar.addSeparator();
    }

    jtoolbar.addSeparator();

    JToggleButton jtbutton = new JToggleButton(new ImageIcon(cl.getResource("images/" + tbimage[4] + ".gif")));
    jtbutton.setPreferredSize(new Dimension(32, 32));
//    jtbutton.setToolTipText(tbitem[i]);

    jtoolbar.add(jtbutton);

    jtabbedpane = new JTabbedPane();
    jtabbedpane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    jtabbedpane.setTabPlacement(JTabbedPane.TOP);

    // Create a new tab
    for (int i=0; i<3; i++) {
      createTab();
    }
    
    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jtabbedpane, BorderLayout.CENTER);

    jmenubar = new JMenuBar();
    this.setJMenuBar(jmenubar);

    JMenu jmenuFile = new JMenu();
    jmenuFile.setText("File");

    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open ...", new ImageIcon(cl.getResource("images/open.gif")));
    jmenuOpen.setMnemonic('O');
    jmenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    // ���U ActionListener
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

    // �غc�s��
    ButtonGroup group1 = new ButtonGroup();

    for (int i=0; i<item.length; i++) {
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      jmenuTheme.add(jrbmenuitem[i]);
      group1.add(jrbmenuitem[i]);
      
      // ���U ActionListener
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

  private void createTab() {
    int i = jtabbedpane.getTabCount() + 1;
/*
    JLabel jlabel = new JLabel("Tab " + i, JTabbedPane.CENTER);
    jlabel.setVerticalTextPosition(JTabbedPane.CENTER);
    jlabel.setHorizontalTextPosition(JTabbedPane.CENTER);
*/    
//    JPanel Panel1 = new JPanel();
//    Panel1.setLayout(new BorderLayout());
//    Panel1.add(jlabel, BorderLayout.CENTER);
    
    jtabbedpane.addTab("Tab " + i, null);
//    jtabbedpane.addTab("Tab " + i, tabImage, Panel1);
    jtabbedpane.setSelectedIndex(jtabbedpane.getTabCount()-1);
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

    this.setTitle("Inactive Window");
//    this.setTitle("Theme Demo: " + e.getActionCommand().toString());
      
    try {
      UIManager.setLookAndFeel(new MetalLookAndFeel());
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
