import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

import java.io.*;
import java.text.*;
import java.util.*;

// Print Job
import java.awt.print.*;  

public class JTabbedPaneDemo extends JFrame {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[7];
  
  JTextArea jtextarea;
  
  JPopupMenu jpopupmenu1 = new JPopupMenu();
  JPopupMenu jpopupmenu2 = new JPopupMenu();

  String menulabel[]={"File|F", "Edit|E", "Tab|T", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"New Tab", "-", "Wrap", "Scroll", "Top", "Bottom", "Left", "Right"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "about"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][8];

  JTabbedPane jtabbedpane;
  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[8];

  ImageIcon tabImage;
  
  // JDK 5.0 �x��Generics
  static Vector<Object> vTextArea = new Vector<Object>();

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

    new JTabbedPaneDemo();
  }

  // �غc�禡
  public JTabbedPaneDemo() {
    super("JTabbedPane Demo");
  
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    tabImage = new ImageIcon(cl.getResource("images/newpage.gif"));

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);
    
    // �]�w���ҥ�Undo��涵��
    jmenuitem[1][0].setEnabled(false);
    // �]�w���ҥ�Redo��涵��
    jmenuitem[1][1].setEnabled(false);

    // Cut
    JMenuItem jmnuCut1 = new JMenuItem(item[3], new ImageIcon(cl.getResource("images/" + image[3] + ".gif")));
    jmnuCut1.setMnemonic('t');
    jmnuCut1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmnuCut1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    // Copy
    JMenuItem jmnuCopy1 = new JMenuItem(item[4], new ImageIcon(cl.getResource("images/" + image[4] + ".gif")));
    jmnuCopy1.setMnemonic('C');
    jmnuCopy1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    jmnuCopy1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Paste
    JMenuItem jmnuPaste1 = new JMenuItem(item[5], new ImageIcon(cl.getResource("images/" + image[5] + ".gif")));
    jmnuPaste1.setMnemonic('P');
    jmnuPaste1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    jmnuPaste1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    
    // �إ߬��㦡���
    jpopupmenu1.add(jmnuCut1);
    jpopupmenu1.add(jmnuCopy1);
    jpopupmenu1.add(jmnuPaste1);

    JMenuItem jmenuAdd1 = new JMenuItem("New Tab");
    jmenuAdd1.setMnemonic('N');
    jmenuAdd1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createTab();
      }
    });

    // Close Tab
    JMenuItem jmenuClose1 = new JMenuItem("Close Tab");
    jmenuClose1.setMnemonic('C');
    jmenuClose1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeTab();
      }
    });
    // �إ߬��㦡���
    jpopupmenu2.add(jmenuAdd1);
    jpopupmenu2.add(jmenuClose1);

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);
      
      // ���U ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==5)
        jtoolbar.addSeparator();
    }
   
    jtabbedpane = new JTabbedPane();
    // �]�w�������m���m
    jtabbedpane.setTabPlacement(SwingConstants.TOP);
    // �]�w�������ƦC��h
    jtabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    jtabbedpane.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    // Create a new tab
    createTab();
    
    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jtabbedpane, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(400, 300));
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
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else { 
          if (i == 2) { 
            if (j == 0 || j== 1) {
              if (menuitemlabel[i][j].equals("-")) 
                // �[�J���j�u
                jmenu[i].addSeparator();
              else {
                jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);
                    
                // ���U ActionListener
                jmenuitem[i][j].addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    menu_actionPerformed(e);
                  }
                });
      
                // �[�J��涵��
                jmenu[i].add(jmenuitem[i][j]);
              }
            }
            else {
              // �إ߿ﶵ���s��涵��
              if (menuitemlabel[i][j].indexOf("|") != -1)
                jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
              else
                jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j]);
  
              // �إ߿ﶵ���s��涵��
              jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j]);
      
              // �]�w�ﶵ���s��涵�ؤ�������A
              if (j==3 || j==4) 
                jrbmenuitem[j].setSelected(true);
  
              // ���U ActionListener
              jrbmenuitem[j].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  menu_actionPerformed(e);
                }
              });
            }
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
            jmenuitem[i][j].addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                menu_actionPerformed(e);
              }
            });
  
            // �[�J��涵��
            jmenu[i].add(jmenuitem[i][j]);
          }
        }
      }
    }

    // �إߤl���
    JMenu submenu1 = new JMenu("Layout Policy");
    submenu1.setMnemonic(KeyEvent.VK_L);
    JMenu submenu2 = new JMenu("Placement");
    submenu2.setMnemonic(KeyEvent.VK_P);

    // �غc�s��
    ButtonGroup group1 = new ButtonGroup();
    ButtonGroup group2 = new ButtonGroup();

    submenu1.add(jrbmenuitem[2]);
    submenu1.add(jrbmenuitem[3]);
    jmenu[2].add(submenu1);
    submenu2.add(jrbmenuitem[4]);
    submenu2.add(jrbmenuitem[5]);
    submenu2.add(jrbmenuitem[6]);
    submenu2.add(jrbmenuitem[7]);
    jmenu[2].add(submenu2);
    group1.add(jrbmenuitem[2]);
    group1.add(jrbmenuitem[3]);
    group2.add(jrbmenuitem[4]);
    group2.add(jrbmenuitem[5]);
    group2.add(jrbmenuitem[6]);
    group2.add(jrbmenuitem[7]);
    
    return jmenubar;
  }

  private void showPopmenu(MouseEvent e) {
    // ����U�ƹ������
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
      int j = jtabbedpane.getSelectedIndex();    
      JTextArea currentPane = (JTextArea) vTextArea.elementAt(j);

      if (e.getSource().equals(currentPane)) { // JTextArea
        // ��ܬ��㦡���
        jpopupmenu1.show(e.getComponent(), e.getX(), e.getY());
      }
      else if (e.getSource().equals(jtabbedpane)) { // JTabbedPane
        // ��ܬ��㦡���
        jpopupmenu2.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }

  private void createTab() {
    jtextarea = new JTextArea();

    // �]�w�۰ʴ��檺�W�h
    jtextarea.setWrapStyleWord(false);
    // �]�wJTextArea���r�W�L��e�ɡA�O�_�۰ʴ���C
    jtextarea.setLineWrap(false);
    // �]�w�O�_�i�s��
    jtextarea.setEditable(true);
    // �]�w��r�Ÿ��ثe����m
    jtextarea.setCaretPosition(0);
    
    // �]�w��r�r��
    jtextarea.setFont(new Font("Courier New", Font.PLAIN, 11));
    jtextarea.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    vTextArea.add(jtextarea);
        
    // �]�w���b���O
    JScrollPane jscrollpane = new JScrollPane();
    // �s�W����
    jtabbedpane.addTab("(Blank)", tabImage, jscrollpane);
    // �����index�Ӥ���
    jtabbedpane.setSelectedIndex(jtabbedpane.getTabCount()-1);
    // ���o���b���O������
    jscrollpane.getViewport().add((java.awt.Component) vTextArea.elementAt(jtabbedpane.getSelectedIndex()));
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  private void closeTab() {
    if (jtabbedpane.getTabCount() > 1) {
      // ���o�w�Q������������ޭ�
      int j = jtabbedpane.getSelectedIndex();
      // ������index�Ӥ���
      jtabbedpane.removeTabAt(j);
      vTextArea.remove(j);
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem menuitem = (JMenuItem)e.getSource();

    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    if (menuitem.getText().equals("New")) { // New
      currentPane.setText("");
    }
    else if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      if (! currentPane.getText().equals("")) {
        new Thread() {
          public void run() {
            try {
              // �C�L���e
              currentPane.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      } 
      else {
        JOptionPane.showMessageDialog(null, "�L��ƥi�C�L", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    else if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("Cut")) { // Cut
      // �ŤU
      currentPane.cut();
    }
    else if (menuitem.getText().equals("Copy")) { // Copy
      // �ƻs
      currentPane.copy();
    }
    else if (menuitem.getText().equals("Paste")) { // Paste
      // �K�W
      currentPane.paste();
    } 
    else if (menuitem.getText().equals("New Tab")) { // New Tab
      createTab();
    } 
    else if (menuitem.getText().equals("Wrap")) { // Wrap
      // �]�w�������ƦC��h
      jtabbedpane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Scroll")) { // Scroll
      // �]�w�������ƦC��h
      jtabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Top")) { // Top
      // �]�w�������m���m
      jtabbedpane.setTabPlacement(SwingConstants.TOP);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Bottom")) { // Bottom
      // �]�w�������m���m
      jtabbedpane.setTabPlacement(SwingConstants.BOTTOM);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Left")) { // Left
      // �]�w�������m���m
      jtabbedpane.setTabPlacement(SwingConstants.LEFT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Right")) { // Right
      // �]�w�������m���m
      jtabbedpane.setTabPlacement(SwingConstants.RIGHT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JTabbedPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    if (e.getSource().equals(jbutton[0])) { // New
      currentPane.setText("");
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // Save
      save();
    }
    else if (e.getSource().equals(jbutton[3])) { // Cut
      // �ŤU
      currentPane.cut();
    }
    else if (e.getSource().equals(jbutton[4])) { // Copy
      // �ƻs
      currentPane.copy();
    }
    else if (e.getSource().equals(jbutton[5])) { // Paste
      // �K�W
      currentPane.paste();
    }
    else if (e.getSource().equals(jbutton[6])) { // About
      JOptionPane.showMessageDialog(null, "JTabbedPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    } 
  }

  private void open() {
    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    JFileChooser jfilechooser = new JFileChooser();
    
    // �]�w�ɮ׹�ܲ������D
    jfilechooser.setDialogTitle("�}���ɮ�");
    // �]�w�ɮ׹�ܲ����ثe�ؿ�
    jfilechooser.setCurrentDirectory(new File("."));
    // �]�w�ɮ׹�ܲ�������
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    String filename = jfilechooser.getSelectedFile().toString();
    
    try {
      // �إ��ɮ׿�J��y
      FileReader in = new FileReader(filename);
      
      // ���ɮצ�y��Ū����Ʀܤ�r�ϰ줤
      currentPane.read(in, filename);
      
      // �]�w�������D
      jtabbedpane.setTitleAt(jtabbedpane.getSelectedIndex(), jfilechooser.getSelectedFile().getName());
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    }  
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void save() {
    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    JFileChooser jfilechooser = new JFileChooser();
    
    // �]�w�ɮ׹�ܲ������D
    jfilechooser.setDialogTitle("�x�s�ɮ�");
    // �]�w�ɮ׹�ܲ����ثe�ؿ�
    jfilechooser.setCurrentDirectory(new File("."));
    // �]�w�ɮ׹�ܲ�������
    jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
    
    if (jfilechooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    File filename = jfilechooser.getSelectedFile();
    
    try {
      // �إ��ɮ׿�X��y
      FileWriter out = new FileWriter(filename);
    
      // �N��r�ϰ쪺��Ƽg�J���ɮ׿�X��y��
      currentPane.write(out);

      // �]�w�������D
      jtabbedpane.setTitleAt(jtabbedpane.getSelectedIndex(), filename.getName());
      jtabbedpane.revalidate();
      jtabbedpane.repaint();

      JOptionPane.showMessageDialog(null, "��Ƥw�g�J���ɮ� " + filename, "About", JOptionPane.INFORMATION_MESSAGE);
    }  
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
