import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;

public class JMenuDemo extends javax.swing.JFrame { 

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|new.gif", "Open|open.gif", "-", "Save|save.gif", "Save As...", "-", "Sub Menu", "-", "Exit"},
    {"Cut|cut.gif", "Copy|copy.gif", "Paste|paste.gif"},
    {"Index", "Use Help", "-", "About|about.gif"}
  };

  String submenuitemlabel[]={"Basic", "Intermediate", "Advance"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][9];
  JMenuItem submenuitem[] = new JMenuItem[3];

  static JLabel jlabel;

  Action action = null;

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

    new JMenuDemo();
  }

  // �غc�禡
  public JMenuDemo() {
    super("JMenu Demo");

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

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (i==0 && j==6) { // �l���
          // �إߤl���
          JMenu submenu = new JMenu(menuitemlabel[i][j]);

          // �إ߿�涵��
          for (int k=0; k<submenuitemlabel.length; k++){
            submenuitem[k] = new JMenuItem(submenuitemlabel[k]);

            action = new MenuItemAction(submenuitemlabel[k]);

            // �]�w��涵�ت��ʧ@
            submenuitem[k].setAction(action);

            // �[�J��涵�ئܤl���
            submenu.add(submenuitem[k]);
          }
      
          jmenu[i].add(submenu);
        }
        else if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else {
          String title = "";
          
          // �إ߿�涵��
          if (menuitemlabel[i][j].indexOf("|") != -1)
            title = menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|"));
          else
            title = menuitemlabel[i][j];

          if (menuitemlabel[i][j].endsWith(".gif")) {
            action = new MenuItemAction(title, new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
          }
          else {
            action = new MenuItemAction(title);
          }

          jmenuitem[i][j] = new JMenuItem();

          // �]�w��涵�ت��ʧ@
          jmenuitem[i][j].setAction(action);

          // �[�J��涵��
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  class MenuItemAction extends AbstractAction {
    public MenuItemAction(String text) {
      super(text);
    }
  
    public MenuItemAction(String text, ImageIcon icon) {
      super(text, icon);
    }
  
    public void actionPerformed(ActionEvent e) {
      // ���o���Ͱʧ@�ƥ󪺿�涵��
      JMenuItem jmenuitem = (JMenuItem)e.getSource();
  
      if (jmenuitem.getText().equals("Exit")) { // Exit
        System.exit(0);
      }
      else {
        JMenuDemo.jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");
      }
    }
  }  
}
