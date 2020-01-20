import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
// Java Swing Text Component
import javax.swing.text.*;

import java.io.*;
import java.text.*;
import java.util.*;

// Print Job
import java.awt.print.*;  

public class JTextAreaDemo extends JFrame {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[7];
  JTextArea jtextarea = new JTextArea();
  JPopupMenu jpopupmenu = new JPopupMenu();

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "about"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][8];

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

    new JTextAreaDemo();
  }

  // �غc�禡
  public JTextAreaDemo() {
    super("JTextArea Demo");
  
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
    JMenuItem jmnuCut1 = new JMenuItem();
    action = new DefaultEditorKit.CutAction();
    action.putValue(Action.NAME, "Cut");
    // �]�w��涵�ت��ʧ@
    jmnuCut1.setAction(action);
    jmnuCut1.setIcon(new ImageIcon(cl.getResource("images/" + image[3] + ".gif")));
    jmnuCut1.setText(item[3]);
    jmnuCut1.setMnemonic('t');
    jmnuCut1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

    // Copy
    JMenuItem jmnuCopy1 = new JMenuItem();
    action = new DefaultEditorKit.CopyAction();
    action.putValue(Action.NAME, "Copy");
    // �]�w��涵�ت��ʧ@
    jmnuCopy1.setAction(action);
    jmnuCopy1.setIcon(new ImageIcon(cl.getResource("images/" + image[4] + ".gif")));
    jmnuCopy1.setText(item[4]);
    jmnuCopy1.setMnemonic('C');
    jmnuCopy1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

    // Paste
    JMenuItem jmnuPaste1 = new JMenuItem();
    action = new DefaultEditorKit.PasteAction();
    action.putValue(Action.NAME, "Paste");
    // �]�w��涵�ت��ʧ@
    jmnuPaste1.setAction(action);
    jmnuPaste1.setIcon(new ImageIcon(cl.getResource("images/" + image[5] + ".gif")));
    jmnuPaste1.setText(item[5]);
    jmnuPaste1.setMnemonic('P');
    jmnuPaste1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    
    // �إ߬��㦡���
    jpopupmenu.add(jmnuCut1);
    jpopupmenu.add(jmnuCopy1);
    jpopupmenu.add(jmnuPaste1);

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton();

      if (i==0 || i==1 || i==2 || i==6) {      
        // ���U ActionListener
        jbutton[i].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            button_actionPerformed(e);
          }
        });
      }

      switch (i) {
        case 3: // Cut
          action = new DefaultEditorKit.CutAction();
          action.putValue(Action.NAME, "Cut");
          // �]�w���s���ʧ@
          jbutton[3].setAction(action);
          break;
        case 4: // Copy
          action = new DefaultEditorKit.CopyAction();
          action.putValue(Action.NAME, "Copy");
          // �]�w���s���ʧ@
          jbutton[4].setAction(action);
          break;
        case 5: // Paste
          action = new DefaultEditorKit.PasteAction();
          action.putValue(Action.NAME, "Paste");
          // �]�w���s���ʧ@
          jbutton[5].setAction(action);
          break;
        default:
      }

      jbutton[i].setIcon(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setText("");
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==5)
        // �[�J���j�u
        jtoolbar.addSeparator();
    }  
     
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

    // �]�w���b���O
    JScrollPane jscrollpane = new JScrollPane(jtextarea);
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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

    // �إ߿��\��C
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
        // �[�J���ܿ��\��C
        jmenubar.add(jmenu[i]);
      else
        // �]�w���\��C�������U�������
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
          if (i==0 || i==2) {
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
          else if (i==1) { // Edit Menu
            switch (j) { // �]�wJButton��Action
              case 0: // Cut
                jmenuitem[i][j] = new JMenuItem();
                action = new DefaultEditorKit.CutAction();
                action.putValue(Action.NAME, "Cut");
                // �]�w��涵�ت��ʧ@
                jmenuitem[i][j].setAction(action);
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + image[j] + ".gif")));
                jmenuitem[i][j].setText(item[j]);
                jmenuitem[i][j].setMnemonic('t');
                jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
                break;
              case 1: // Copy
                jmenuitem[i][j] = new JMenuItem();
                action = new DefaultEditorKit.CopyAction();
                action.putValue(Action.NAME, "Copy");
                // �]�w��涵�ت��ʧ@
                jmenuitem[i][j].setAction(action);
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + image[j] + ".gif")));
                jmenuitem[i][j].setText(item[j]);
                jmenuitem[i][j].setMnemonic('C');
                jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
                break;
              case 2: // Paste
                jmenuitem[i][j] = new JMenuItem();
                action = new DefaultEditorKit.PasteAction();
                action.putValue(Action.NAME, "Paste");
                // �]�w��涵�ت��ʧ@
                jmenuitem[i][j].setAction(action);
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + image[j] + ".gif")));
                jmenuitem[i][j].setText(item[j]);
                jmenuitem[i][j].setMnemonic('P');
                jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
                break;
              default:
            }

            // �[�J��涵��
            jmenu[i].add(jmenuitem[i][j]);
          }
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
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("New")) { // New
      jtextarea.setText("");
    }
    else if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Print")) { // Print
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
    else if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JTextArea Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
    else if (e.getSource().equals(jbutton[6])) { // About
      JOptionPane.showMessageDialog(null, "JTextArea Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
