import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;

public class AcceleratorDemo extends javax.swing.JFrame implements ActionListener, ItemListener {

  String menulabel[]={"File|F", "Edit|E", "Options|O", "L & F|L", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "Save As...|A", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Show Tooltips|T", "Show Images|I", "-", "Plain Text|P", "HTML|H", "RTF|R"},
    {"Metal|M", "CDE/Motif|C", "Windows XP|W", "Windows Classic|D", "GTK+|G", "Mac|A"},
    {"Index|I", "Use Help|U", "-", "About|A|about.gif"}
  };

  int accelerator[][]={
    {KeyEvent.VK_N, KeyEvent.VK_O, -1, KeyEvent.VK_S, KeyEvent.VK_A, -1, KeyEvent.VK_X},
    {KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V},
    {KeyEvent.VK_T, KeyEvent.VK_I, -1, KeyEvent.VK_P, KeyEvent.VK_H, KeyEvent.VK_R},
    {KeyEvent.VK_M, KeyEvent.VK_C, KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_G, KeyEvent.VK_A},
    {KeyEvent.VK_I, -1, -1, -1}
  };

  int modifier[][]={
    {InputEvent.CTRL_DOWN_MASK, InputEvent.CTRL_DOWN_MASK, -1, InputEvent.CTRL_DOWN_MASK, InputEvent.CTRL_DOWN_MASK, -1, InputEvent.ALT_DOWN_MASK},
    {InputEvent.CTRL_DOWN_MASK, InputEvent.CTRL_DOWN_MASK, InputEvent.CTRL_DOWN_MASK},
    {InputEvent.SHIFT_DOWN_MASK, InputEvent.SHIFT_DOWN_MASK, -1, InputEvent.SHIFT_DOWN_MASK, InputEvent.SHIFT_DOWN_MASK, InputEvent.SHIFT_DOWN_MASK},
    {InputEvent.ALT_DOWN_MASK, InputEvent.ALT_DOWN_MASK, InputEvent.ALT_DOWN_MASK, InputEvent.ALT_DOWN_MASK, InputEvent.ALT_DOWN_MASK, InputEvent.ALT_DOWN_MASK},
    {InputEvent.CTRL_DOWN_MASK, -1, -1, -1}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[5][7];
  JCheckBoxMenuItem jcbmenuitem[] = new JCheckBoxMenuItem[6];
  JRadioButtonMenuItem jrbmenuitem[] = new JRadioButtonMenuItem[6];

  JLabel jlabel;

  String classname[] = {
    "javax.swing.plaf.metal.MetalLookAndFeel", 
    "com.sun.java.swing.plaf.motif.MotifLookAndFeel", 
    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel", 
    "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", 
    "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
    "com.sun.java.swing.plaf.mac.MacLookAndFeel"};

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

    new AcceleratorDemo();
  }

  // �غc�禡
  public AcceleratorDemo() {
    super("Menu Accelerator Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    jlabel = new JLabel(" ");

    JPanel jpanel = new JPanel();
    // �]�w������ؼ˦�
    jpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jpanel.setLayout(new BorderLayout());
    jpanel.add(jlabel, BorderLayout.WEST);
    
    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());
    this.add(jpanel, BorderLayout.SOUTH);
    
    // �]�w�������j�p
    this.setSize(250, 250);

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

  private JMenuBar createMenuBar() {
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �إ߿��C
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menulabel.length];
    
    // �إ߿��
    for (int i=0; i<menulabel.length; i++){
      // �إ߿��
      if (menulabel[i].indexOf("|") != -1)
        jmenu[i] = new JMenu(menulabel[i].substring(0, menulabel[i].indexOf("|")));
      else
        jmenu[i] = new JMenu(menulabel[i]);

      // �]�w���U�O�X
      jmenu[i].setMnemonic(menulabel[i].split("\\|")[1].charAt(0));

      if (i != menulabel.length)
        // �[�J���ܿ��C
        jmenubar.add(jmenu[i]);
      else
        // �]�w���C�������U�������
        jmenubar.setHelpMenu(jmenu[i]);
    }

    // �غc�s��
    ButtonGroup group1 = new ButtonGroup();
    ButtonGroup group2 = new ButtonGroup();

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else {
          if (i == 2) { // Options ���
            if (menuitemlabel[i][j].equals("-")) 
              // �[�J���j�u
              jmenu[i].addSeparator();
            // �إ߮֨������涵��
            else if (menuitemlabel[i][j].indexOf("|") != -1)
              jcbmenuitem[j] = new JCheckBoxMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
            else
              jcbmenuitem[j] = new JCheckBoxMenuItem(menuitemlabel[i][j]);

            // �]�w��涵�اU�O�X
            jcbmenuitem[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

            // �]�w���ֳt��
            if (accelerator[i][j] != -1 || modifier[i][j] != -1)
              jcbmenuitem[j].setAccelerator(KeyStroke.getKeyStroke(accelerator[i][j], modifier[i][j]));
    
            // �إ߹Ϲ�
            if (menuitemlabel[i][j].endsWith(".gif")) 
              jcbmenuitem[j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

            // �]�w�֨������涵�ؤ�������A
            if (j==3) 
              jcbmenuitem[j].setState(true);
              
            if (j>=3) 
              group1.add(jcbmenuitem[j]);
  
            // ���U ItemListener
            jcbmenuitem[j].addItemListener(this);
  
            // �[�J��涵��
            jmenu[i].add(jcbmenuitem[j]);
          }
          else if (i == 3) { // L & F ���
            if (menuitemlabel[i][j].equals("-")) 
              // �[�J���j�u
              jmenu[i].addSeparator();
            // �إ߿ﶵ���s��涵��
            else if (menuitemlabel[i][j].indexOf("|") != -1)
              jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
            else
              jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j]);

            // �]�w��涵�اU�O�X
            jrbmenuitem[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
    
            // �]�w���ֳt��
            if (accelerator[i][j] != -1 || modifier[i][j] != -1)
              jrbmenuitem[j].setAccelerator(KeyStroke.getKeyStroke(accelerator[i][j], modifier[i][j]));

            // �إ߹Ϲ�
            if (menuitemlabel[i][j].endsWith(".gif")) 
              jrbmenuitem[j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

            // �]�w�ﶵ���s��涵�ؤ�������A
            if (j==0) 
              jrbmenuitem[j].setSelected(true);

            // �]�w�O�_�ҥο�涵��
            jrbmenuitem[j].setEnabled(isLookAndFeelSupported(classname[j]));
              
            group2.add(jrbmenuitem[j]);
  
            // ���U ActionListener
            jrbmenuitem[j].addActionListener(this);
  
            // �[�J��涵��
            jmenu[i].add(jrbmenuitem[j]);
          }
          else {
            // �إ߿�涵��
            if (menuitemlabel[i][j].indexOf("|") != -1)
              jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
            else
              jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

            // �]�w��涵�اU�O�X
            jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
  
            // �]�w���ֳt��
            if (accelerator[i][j] != -1 || modifier[i][j] != -1)
              jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(accelerator[i][j], modifier[i][j]));

            // �إ߹Ϲ�
            if (menuitemlabel[i][j].endsWith(".gif")) 
              jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
  
            // ���U ActionListener
            jmenuitem[i][j].addActionListener(this);
  
            // �[�J��涵��
            jmenu[i].add(jmenuitem[i][j]);
          }
        }
      }
    }
    
    return jmenubar;
  }

  private boolean isLookAndFeelSupported(String lnfname) {
    try { 
      Class lnfclass = Class.forName(lnfname);
      javax.swing.LookAndFeel lnf = (LookAndFeel)(lnfclass.newInstance());
      
      // �P�_�@�~�t�άO�_�䴩Look and Feel
      return lnf.isSupportedLookAndFeel();
    } 
    catch(Exception e) { 
      return false;
    }
  }

  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("Metal")) { 
      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[0]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("CDE/Motif")) { 
      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[1]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Windows XP")) { 
      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[2]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Windows Classic")) { 
      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[3]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("GTK+")) { 
      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[4]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Mac")) { 
      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[5]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      System.exit(0);
    }
    else {
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");
    }
  }

  public void itemStateChanged(ItemEvent e) {
    // ���o���Ͷ��بƥ󪺿�涵��
    JCheckBoxMenuItem jcbmenuitem = (JCheckBoxMenuItem)e.getSource();

    // �P�_�֨�����O�_�Q���
    if(jcbmenuitem.getState()) 
      jlabel.setText("Select "  + jcbmenuitem.getText() + " Menu Item.");
    else
      jlabel.setText("Deselect "  + jcbmenuitem.getText() + " Menu Item.");
  }
}
