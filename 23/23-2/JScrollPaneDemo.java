import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
// Undo and Redo
import javax.swing.undo.*;
// Java Swing Text Component
import javax.swing.text.*;

import java.io.*;
import java.text.*;
import java.util.*;

// Print Job
import java.awt.print.*;  

// ��@CaretListener, DocumentListener�PUndoableEditListener����
public class JScrollPaneDemo extends JFrame implements CaretListener, DocumentListener, UndoableEditListener {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[7];
  JTextArea jtextarea = new JTextArea();
  JPopupMenu jpopupmenu = new JPopupMenu();
  JLabel jlabel = new JLabel();

  javax.swing.text.Document document;

  javax.swing.undo.UndoManager undoManager = new UndoManager();

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Undo|U|undo.gif", "Redo|R|redo.gif", "-", "Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "about"};

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

    new JScrollPaneDemo();
  }

  // �غc�禡
  public JScrollPaneDemo() {
    super("JScrollPane Demo");
  
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

      if (i==2 || i==5)
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

    // ���UCaretListener
    jtextarea.addCaretListener(this);

    // ���oMVC�[�c����Model����
    document = jtextarea.getDocument();
    // ���UDocumentListener
    document.addDocumentListener(this);
    // ���UUndoableEditListener
    document.addUndoableEditListener(this);
    
    // �]�w���b���O
    JScrollPane jscrollpane = new JScrollPane(jtextarea);
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JPanel jpanel = new JPanel(new GridLayout(1, 1));
    jlabel = new JLabel("Status: ");
    jpanel.add(jlabel);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jscrollpane, BorderLayout.CENTER);
    this.add(jpanel, BorderLayout.SOUTH);

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
    else if (e.getSource().equals(jmenuitem[1][0])) { // Undo
      // �_��s��
      undo();
    }
    else if (e.getSource().equals(jmenuitem[1][1])) { // Redo
      // ���ƽs��
      redo();
    }
    else if (menuitem.getText().equals("Cut")) { // Cut
      // �ŤU
      jtextarea.cut();
    }
    else if (menuitem.getText().equals("Copy")) { // Copy
      // �ƻs
      jtextarea.copy();
    }
    else if (menuitem.getText().equals("Paste")) { // Paste
      // �K�W
      jtextarea.paste();
    } 
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JScrollPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
    else if (e.getSource().equals(jbutton[6])) { // About
      JOptionPane.showMessageDialog(null, "JScrollPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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

  // 
  // ��@CaretListener��������k
  // 

  // ���r�Ÿ�����m���ܮ�
  public void caretUpdate(CaretEvent e) {
    // ���o��r�Ÿ��ثe����m
    final int dot = e.getDot();
    // ���o�����r�ɲ�r�Ÿ����̫��m
    // �Y�L�����r�A�h����m����getDot()��k�Ҧ^�Ǫ��ثe��m
    final int mark = e.getMark();
    
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (dot == mark) {  
          // �L�����r
          jlabel.setText("��Хثe��m: " + dot);
        } 
        else if (dot < mark) {
          // �����r
          jlabel.setText("����� " + dot + " �� " + mark + " �����e");
        } 
        else {
          // �����r
          jlabel.setText("����� " + mark + " �� " + dot + " �����e");
        }
      }
    });
  }

  // 
  // ��@DocumentListener��������k
  // 

  // ���r�s�誫�󪺤��e���ݩ��ܧ��
  public void changedUpdate(DocumentEvent e) {
    updateDocumentStatus(e);
  }

  // ���J���e�ܤ�r�s�誫���
  public void insertUpdate(DocumentEvent e) {
    updateDocumentStatus(e);
  }

  // ��ۤ�r�s�誫�󲾰����e��
  public void removeUpdate(DocumentEvent e) {
    updateDocumentStatus(e);
  }

  private void updateDocumentStatus(DocumentEvent e) {
    // ���o���ͤ��ƥ�MVC Model
    Document docModel = e.getDocument();
    // ���o���e�ܧ󪺪���
    int length = e.getLength();

    // ���o�ƥ����� (e.getType())
    System.out.println(e.getType().toString() + ": " + length + " �r��, ��r����: " + docModel.getLength());
  }

  // 
  // ��@UndoableEditListener��������k
  // 

  // ��o�ʹ_��έ��ƽs��ʧ@��
  public void undoableEditHappened(UndoableEditEvent e) {
    // �s�W�@UndoableEdit����ܥثe����r�s�誫��
    if (undoManager.addEdit(e.getEdit())) {
      updateUndoStatus();
      updateRedoStatus();
    }
    else
      System.out.println("Can not create UndoManager.");
  }

  private void undo() {
    // �_��
    try {
      // ����_��A�Y��r�s�誫�󤣤䴩�_��\���
      // �h�N����CannotUndoException���ҥ~
      undoManager.undo();
    } 
    catch (CannotUndoException ex) {
      ex.printStackTrace();
    }
    
    updateUndoStatus();
    updateRedoStatus();
  }
  
  private void redo() {
    // �_��
    try {
      // ����_��A�Y��r�s�誫�󤣤䴩���ƥ\���
      // �h�N����CannotRedoException���ҥ~
      undoManager.redo();
    } 
    catch (CannotRedoException ex) {
      ex.printStackTrace();
    }
    
    updateRedoStatus();
    updateUndoStatus();
  }
  
  private void updateUndoStatus() {
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    jmenuitem[1][0].setIcon(new ImageIcon(cl.getResource("images/undo.gif")));
    // �]�w��涵�ت��U�O�X
    jmenuitem[1][0].setMnemonic('U');
    // �]�w�]�w��涵�ت����ֳt�� (Ctrl + Z)
    jmenuitem[1][0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

    // �P�_�O�_�i�H�_��s��
    if (undoManager.canUndo()) {
      // ���o�_��s�誺�y�z
      jmenuitem[1][0].setText(undoManager.getUndoPresentationName());
      // �]�w�ҥ�Undo��涵��
      jmenuitem[1][0].setEnabled(true);
    } 
    else {
      jmenuitem[1][0].setText("Undo");
      // �]�w���ҥ�Undo��涵��
      jmenuitem[1][0].setEnabled(false);
    }
  }

  private void updateRedoStatus() {
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    jmenuitem[1][1].setIcon(new ImageIcon(cl.getResource("images/redo.gif")));
    // �]�w��涵�ت��U�O�X
    jmenuitem[1][1].setMnemonic('R');
    // �]�w�]�w��涵�ت����ֳt�� (Ctrl + Y)
    jmenuitem[1][1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

    // �P�_�O�_�i�H���ƽs��
    if (undoManager.canRedo()) {
      // ���o���ƽs�誺�y�z
      jmenuitem[1][1].setText(undoManager.getRedoPresentationName());
      // �]�w�ҥ�Redo��涵��
      jmenuitem[1][1].setEnabled(true);
    } 
    else {
      jmenuitem[1][1].setText("Redo");
      // �]�w���ҥ�Redo��涵��
      jmenuitem[1][1].setEnabled(false);
    }
  }
}
