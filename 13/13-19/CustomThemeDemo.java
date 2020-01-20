import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

// Theme
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class CustomThemeDemo extends javax.swing.JFrame implements ActionListener {

  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[9];

  String item[] = {
    "Ocean", "Steel", "Aqua", "Green", "Ruby", "Sandstone",
    "Contrast", "Low Vision", "Presentation"};

  ClassLoader cl;

  // Main method
  public static void main(String[] args) {
    // �NMetal Look and Feel�����r�ΥѲ���אּ�зǦr��
    UIManager.put("swing.boldMetal", Boolean.FALSE);
//    JDialog.setDefaultLookAndFeelDecorated(true);
//    JFrame.setDefaultLookAndFeelDecorated(true);
//    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      // Metal Look & Feel
      UIManager.setLookAndFeel(new NimbusLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new CustomThemeDemo();
  }

  // �غc�禡
  public CustomThemeDemo() {
    // Get current classloader
    cl = this.getClass().getClassLoader();

    // �إ߿��C
    JMenuBar jmenubar = createJMenuBar();

    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

//    this.setUndecorated(true);
//    this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    this.setTitle("Theme: Ocean");

    this.setLayout(new BorderLayout());

    // ���ե�
    JTextArea jtextarea = new JTextArea();
    JScrollPane jscrollpane = new JScrollPane(jtextarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    JToolBar jtoolbar = new JToolBar();
    JButton jbutton[] = new JButton[5];
    String tbitem[]  = {"New", "Open", "Save", "Save As", "About"};
    String tbimage[] = {"new", "open", "save", "saveas", "about"};

    for (int i=0; i<tbimage.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + tbimage[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(tbitem[i]);
      
      jtoolbar.add(jbutton[i]);
      
      if (i==3)
        jtoolbar.addSeparator();
    }
    
    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jscrollpane, BorderLayout.CENTER);

    // �]�w�������j�p
    this.setSize(250, 250);
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

    // ��ܵ���
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private JMenuBar createJMenuBar() {
    // �إ߿��C
    JMenuBar jmenubar = new JMenuBar();

    // �إ߿��
    JMenu jmenuFile = new JMenu("File");

    // �إ߿�涵��
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

    // �إ߿�涵��
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

    // �إ߿��
    JMenu jmenuTheme = new JMenu("Theme");

    // �غc�s��
    ButtonGroup group = new ButtonGroup();

    for (int i=0; i<item.length; i++) {
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      jmenuTheme.add(jrbmenuitem[i]);
      group.add(jrbmenuitem[i]);
      
      // ���U ActionListener
      jrbmenuitem[i].addActionListener(this);
     
      if (i==0)
        jrbmenuitem[i].setSelected(true);
    }

    // �s�W���ܿ��C

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

    // �s�W���ܿ��C
    jmenubar.add(jmenuFile);
    jmenubar.add(jmenuTheme);
    jmenubar.add(jmenuHelp);

    return jmenubar;
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals(item[0])) { 
      MetalLookAndFeel.setCurrentTheme(new OceanTheme());
    }
    else if(e.getActionCommand().equals(item[1])) {
      MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
    }
    else if(e.getActionCommand().equals(item[2])) {
      MetalLookAndFeel.setCurrentTheme(new AquaTheme());
    }
    else if(e.getActionCommand().equals(item[3])) {
      MetalLookAndFeel.setCurrentTheme(new GreenTheme());
    }
    else if(e.getActionCommand().equals(item[4])) {
      MetalLookAndFeel.setCurrentTheme(new RubyTheme());
    }
    else if(e.getActionCommand().equals(item[5])) {
      MetalLookAndFeel.setCurrentTheme(new SandstoneTheme());
    }
    else if(e.getActionCommand().equals(item[6])) {
      MetalLookAndFeel.setCurrentTheme(new ContrastTheme());
    }
    else if(e.getActionCommand().equals(item[7])) {
      MetalLookAndFeel.setCurrentTheme(new LowVisionTheme());
    }
    else if(e.getActionCommand().equals(item[8])) {
      MetalLookAndFeel.setCurrentTheme(new PresentationTheme());
    }

    this.setTitle("Theme: " + e.getActionCommand().toString());
      
    try {
      UIManager.setLookAndFeel(new NimbusLookAndFeel());
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
