import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.event.*;

public class MenuKeyEventDemo extends javax.swing.JFrame implements MenuKeyListener {

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "Save As...|A", "-", "Sub Menu", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Index|I", "Use Help|U", "-", "About|A|about.gif"}
  };

  String submenuitemlabel[]={"Basic", "Intermediate", "Advance"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][9];
  JMenuItem submenuitem[] = new JMenuItem[3];

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

    new MenuKeyEventDemo();
  }

  // �غc�禡
  public MenuKeyEventDemo() {
    super("JMenuItem Event Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

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

            // ���U MenuKeyListener
            submenuitem[k].addMenuKeyListener(this);
      
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

          // ���U MenuKeyListener
          jmenuitem[i][j].addMenuKeyListener(this);

          // �[�J��涵��
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  // ��@MenuKeyListener��������k
  // �����U��L����ɩҩI�s����k
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
    
    // �^�ǫ���ҥN���������
    System.out.println("Key Pressed: " + e.getKeyCode());
 
    // ���o���ͦ��ƥ󤧿�涵�ت����h���|
    MenuElement[] element = e.getPath();
    
    for (int i=0; i<element.length; i++) 
      System.out.println("Menu Item: " + element[i].getComponent()) ;
  }  

  // ��������L����ɩҩI�s����k
  public void menuKeyReleased(MenuKeyEvent e) {}
  // �����U��������L����ɩҩI�s����k
  public void menuKeyTyped(MenuKeyEvent e) {} 
}