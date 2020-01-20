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

public class JColorChooserDemo extends JFrame {
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[8];
  JTextArea jtextarea = new JTextArea();
  JPopupMenu jpopupmenu = new JPopupMenu();

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "Color", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "color", "about"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][8];

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

    new JColorChooserDemo();
  }

  // �غc�禡
  public JColorChooserDemo() {
    super("JColorChooser Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

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
    jpopupmenu.add(jmnuCut1);
    jpopupmenu.add(jmnuCopy1);
    jpopupmenu.add(jmnuPaste1);

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

      if (i==2 || i==5 || i==6)
        jtoolbar.addSeparator();
    }
   
    // �]�w�۰ʴ��檺�W�h
    jtextarea.setWrapStyleWord(false);
    // �]�wJTextArea���r�W�L��e�ɡA�O�_�۰ʴ���C
    jtextarea.setLineWrap(false);
    // �]�w�O�_�i�s��
    jtextarea.setEditable(true);
    // �]�w��r�r��
    jtextarea.setFont(new Font("Courier New", Font.PLAIN, 11));
    jtextarea.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });
    
    // �]�w���b���O
    JScrollPane jscrollpane = new JScrollPane(jtextarea);
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jscrollpane, BorderLayout.CENTER);

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
    
    return jmenubar;
  }

  private void showPopmenu(MouseEvent e) {
    // ����U�ƹ������
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) 
      // ��ܬ��㦡���
      jpopupmenu.show(e.getComponent(), e.getX(), e.getY());
  }

  private void menu_actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("New")) { // New
      jtextarea.setText("");
    }
    else if (jmenuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (jmenuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (jmenuitem.getText().equals("Print")) { // Print
      if (! jtextarea.getText().equals("")) {
        new Thread() {
          public void run() {
            try {
              // �C�L���e
              jtextarea.print();
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
//    else if (jmenuitem.getText().equals("Page Setup")) { // Page Setup
//      pageSetup();
//    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (jmenuitem.getText().equals("Cut")) { // Cut
      // �ŤU
      jtextarea.cut();
    }
    else if (jmenuitem.getText().equals("Copy")) { // Copy
      // �ƻs
      jtextarea.copy();
    }
    else if (jmenuitem.getText().equals("Paste")) { // Paste
      // �K�W
      jtextarea.paste();
    } 
    else if (jmenuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JColorChooser Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // New
      jtextarea.setText("");
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // Save
      save();
    }
    else if (e.getSource().equals(jbutton[3])) { // Cut
      // �ŤU
      jtextarea.cut();
    }
    else if (e.getSource().equals(jbutton[4])) { // Copy
      // �ƻs
      jtextarea.copy();
    }
    else if (e.getSource().equals(jbutton[5])) { // Paste
      // �K�W
      jtextarea.paste();
    }
    else if (e.getSource().equals(jbutton[6])) { // Color
      Color newColor = JColorChooser.showDialog(this, "Choose Color", jtextarea.getForeground());
      
      jtextarea.setForeground(newColor);
    } 
    else if (e.getSource().equals(jbutton[7])) { // About
      JOptionPane.showMessageDialog(null, "JColorChooser Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    } 
  }

  private void open() {
    
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
      jtextarea.read(in, filename);
    }  
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void save() {
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
      jtextarea.write(out);

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
