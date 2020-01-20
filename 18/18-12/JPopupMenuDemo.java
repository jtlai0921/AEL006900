import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;

import javax.swing.event.*;

public class JPopupMenuDemo extends javax.swing.JFrame implements ActionListener, MenuKeyListener, PopupMenuListener {

  JPopupMenu jpopupmenu;

  String menulabel[]={"File|F", "Edit|E", "L & F|L", "Help|H"};

  String menuitemlabel[][]={
    {"Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Metal|M", "CDE/Motif|C", "Windows XP|W", "Windows Classic|D", "GTK+|G", "Mac|A"},
    {"Index|I", "Use Help|U", "-", "About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][6];
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

    new JPopupMenuDemo();
  }

  // �غc�禡
  public JPopupMenuDemo() {
    super("JPopupMenu Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // �إ߬��㦡���
    jpopupmenu = createJPopupMenu("Popup Menu"); 
    
    // ���U MenuKeyListener
    jpopupmenu.addMenuKeyListener(this);
    // ���U PopupMenuListener
    jpopupmenu.addPopupMenuListener(this);

    jlabel = new JLabel(" ");

    JPanel jpanel = new JPanel();
    // �]�w������ؼ˦�
    jpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jpanel.setLayout(new BorderLayout());
    jpanel.add(jlabel, BorderLayout.WEST);

    JEditorPane txtMessage = new JEditorPane();
    // ���U MouseListener
    // �H�B�z����U�ƹ��������ܬ��㦡���
    txtMessage.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        showJPopupMenu(e);
      }
    
      public void mousePressed(MouseEvent e) {
        showJPopupMenu(e);
      }
    
      public void mouseReleased(MouseEvent e) {
        showJPopupMenu(e);
      }
    });

    JScrollPane jsp = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    jsp.getViewport().add(txtMessage);

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());
    this.add(jpanel, BorderLayout.SOUTH);
    this.add(jsp, BorderLayout.CENTER);
   
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
    ButtonGroup group = new ButtonGroup();

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else if (i == 2) { // L & F ���
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
  
          // �إ߹Ϲ�
          if (menuitemlabel[i][j].endsWith(".gif")) 
            jrbmenuitem[j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

          // �]�w�ﶵ���s��涵�ؤ�������A
          if (j==0) 
            jrbmenuitem[j].setSelected(true);

          // �]�w�O�_�ҥο�涵��
          jrbmenuitem[j].setEnabled(isLookAndFeelSupported(classname[j]));
            
          group.add(jrbmenuitem[j]);

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
    
    return jmenubar;
  }

  private JPopupMenu createJPopupMenu(String label) {
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �إ߬��㦡���
    JPopupMenu popupmenu = new JPopupMenu(label);

    // Cut, Copy, Paste
    for(int i=1, j=0; j<menuitemlabel[i].length; j++){
      // �إ߿�涵��
      if (menuitemlabel[i][j].indexOf("|") != -1)
        jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
      else
        jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

      // �]�w��涵�اU�O�X
      jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

      // �إ߹Ϲ�
      if (menuitemlabel[i][j].endsWith(".gif")) 
        jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

      // ���U ActionListener
      jmenuitem[i][j].addActionListener(this);

      // �[�J��涵��
      popupmenu.add(jmenuitem[i][j]);
    }

    return popupmenu;
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

  private void showJPopupMenu(MouseEvent e) {
    // �P�_�ƹ��ƥ�O�_�P���㦡��榳��
    if (jpopupmenu.isPopupTrigger(e)) {
      // ��
//    if (e.isPopupTrigger()) {
      // ��ܬ��㦡���
      jpopupmenu.show(e.getComponent(), e.getX(), e.getY());

      // �Ψ̧ǰ���H�U��k
//      jpopupmenu.setLocation(e.getX(), e.getY());
//      jpopupmenu.setInvoker(e.getComponent());
//      jpopupmenu.setVisible(true);
    }
  }

  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("Metal")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[0]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("CDE/Motif")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[1]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Windows XP")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[2]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Windows Classic")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[3]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("GTK+")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // �]�wLook and Feel
        UIManager.setLookAndFeel(classname[4]);

        // ���涥�q�ܧ�Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Mac")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

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

  // ��@MenuKeyListener��������k
  // ����U��L����ɩҩI�s����k
  public void menuKeyPressed(MenuKeyEvent e) {
    // �P�_�O�_���UAlt��
    if (e.isAltDown()) 
      System.out.println("Press Alt key") ;
    
    // �P�_�O�_���UCtrl��
    if (e.isControlDown()) 
      System.out.println("Press Control key") ;
    
    // �P�_�O�_���UShift��
    if (e.isShiftDown()) 
      System.out.println("Press Shift key") ;
    
    // �^�ǫ���ҥN�������
    System.out.println("Key Pressed: " + e.getKeyCode());
 
    // ���o���ͦ��ƥ󤧿�涵�ت����h���|
    MenuElement[] element = e.getPath();
    
    for (int i=0; i<element.length; i++) 
      System.out.println("Menu Item: " + element[i].getComponent()) ;
  }  

  // ��������L����ɩҩI�s����k
  public void menuKeyReleased(MenuKeyEvent e) {}
  // ����U��������L����ɩҩI�s����k
  public void menuKeyTyped(MenuKeyEvent e) {} 
  
  // ��@PopupMenuListener��������k
  // ��������㦡���ɩҩI�s����k
  public void popupMenuCanceled(PopupMenuEvent e) {
    jlabel.setText("JPopupMenu is canceled.");
  }

  // �����ì��㦡��椧�e�ҩI�s����k
  public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    jlabel.setText("JPopupMenu will be invisible.");
  }

  // ����ܬ��㦡��椧�e�ҩI�s����k
  public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    jlabel.setText("JPopupMenu will be visible.");
  }
}
