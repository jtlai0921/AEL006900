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
public class JTextPaneDemo extends JFrame implements CaretListener, DocumentListener, UndoableEditListener {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[13];
  JTextPane jtextpane = new JTextPane();
  JPopupMenu jpopupmenu1 = new JPopupMenu();
  JPopupMenu jpopupmenu2 = new JPopupMenu();
  JPopupMenu jpopupmenu3 = new JPopupMenu();
  JLabel jlabel = new JLabel();

  JComboBox cboFont = new JComboBox();
  JComboBox cboSize = new JComboBox();

  // ���˦�
  javax.swing.text.StyledDocument document;

  javax.swing.undo.UndoManager undoManager = new UndoManager();

  String menulabel[]={"File|F", "Edit|E", "Style|S", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Undo|U|undo.gif", "Redo|R|redo.gif", "-", "Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Font|F", "Size|S"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "Bold", "Italic", "Underline", "Justify Left", "Justify Center", "Justify Right", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "bold", "italic", "underline", "justifyleft", "justifycenter", "justifyright", "about"};

  String font[] = {"Arial", "Courier New", "Dialog", "SansSerif", "Serif", "Tahoma", "Times New Roman", "Verdana"};
  int size[] = {8, 9, 10, 11, 12, 14, 16, 18, 20};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][8];
  JMenuItem submenu[] = new JMenuItem[2];
  JMenuItem submenuitem[][] = new JMenuItem[2][9];

  Action action = null;

  UndoAction undoAction;
  RedoAction redoAction;

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

    new JTextPaneDemo();
  }

  // �غc�禡
  public JTextPaneDemo() {
    super("JTextPane Demo");
  
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w��r���O��ؤ��ݩʭ�
    UIManager.put("TextPane.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w��r�Ÿ����e���C�⤧�ݩʭ�
    UIManager.put("TextPane.caretForeground", Color.RED);
    
    // �]�w��r���Ҧr�����ݩʭ�
    UIManager.put("TextPane.font", new Font("dialog", Font.BOLD, 12));
    
    // �]�w��r���O�e�����ݩʭ�
    UIManager.put("TextPane.foreground", Color.PINK);
    
    // �]�w�Q����ɪ��I���C�⤧�ݩʭ�
    UIManager.put("TextPane.selectionBackground", Color.GREEN);
    
    // �]�w�Q����ɪ��e���C�⤧�ݩʭ�
    UIManager.put("TextPane.selectionForeground", Color.CYAN);
    
    // ���o��r���OPluggable Look and Feel�ݩʭ�
    System.out.println("JTextPane Look and Feel: " + UIManager.getString("TextPaneUI"));

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إ߿��\��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������\��C
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
    jpopupmenu1.add(jmnuCut1);
    jpopupmenu1.add(jmnuCopy1);
    jpopupmenu1.add(jmnuPaste1);

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton();

      if (i==0 || i==1 || i==2 || i==12) {      
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
        case 6: // Bold
          action = new StyledEditorKit.BoldAction();
          action.putValue(Action.NAME, "Bold");
          // �]�w���s���ʧ@
          jbutton[6].setAction(action);
          break;
        case 7: // Italic
          action = new StyledEditorKit.ItalicAction();
          action.putValue(Action.NAME, "Italic");
          // �]�w���s���ʧ@
          jbutton[7].setAction(action);
          break;
        case 8: // Underline
          action = new StyledEditorKit.UnderlineAction();
          action.putValue(Action.NAME, "Underline");
          // �]�w���s���ʧ@
          jbutton[8].setAction(action);
          break;
        case 9: // Justify Left
          // �V�����
          action = new StyledEditorKit.AlignmentAction("Justify Left", 0);
          action.putValue(Action.NAME, "Justify Left");
          // �]�w���s���ʧ@
          jbutton[9].setAction(action);
          break;
        case 10: // Justify Left
          // �V�����������
          action = new StyledEditorKit.AlignmentAction("Justify Center", 4);
          action.putValue(Action.NAME, "Justify Center");
          // �]�w���s���ʧ@
          jbutton[10].setAction(action);
          break;
        case 11: // Justify Right
          // �V�k���
          action = new StyledEditorKit.AlignmentAction("Justify Right", 2);
          action.putValue(Action.NAME, "Justify Right");
          // �]�w���s���ʧ@
          jbutton[11].setAction(action);
          break;
        default:
      }

      jbutton[i].setIcon(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setText("");
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==5 || i==8 || i==11)
        // �[�J���j�u
        jtoolbar.addSeparator();
    }

    // �]�w�O�_�i�s��
    jtextpane.setEditable(true);
    // �]�w��r�Ÿ��ثe����m
    jtextpane.setCaretPosition(0);

    jtextpane.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    // ���UCaretListener
    jtextpane.addCaretListener(this);

    // ���oMVC�[�c����Model����(���˦�)
    document = jtextpane.getStyledDocument();
    // ���UDocumentListener
    document.addDocumentListener(this);
    // ���UUndoableEditListener
    document.addUndoableEditListener(this);
    
    // �]�w���b���O
    JScrollPane jscrollpane = new JScrollPane(jtextpane);
    // �������b�G��W�Ljtextpane�C�Ʈɤ~�۰���ܫ������b
    jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�Ljtextpane��Ʈɤ~�۰���ܤ������b
    jscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
    this.setSize(new Dimension(500, 300));
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
          if (i==0 || i==3) {
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
            switch (j) { // �]�wJMenuItem��Action
              case 0: // Undo
                undoAction = new UndoAction();

                // �إ߿�涵��
                jmenuitem[i][j] = new JMenuItem();
                // �]�w��涵�ت��ʧ@
                jmenuitem[i][j].setAction(undoAction);
                // �]�w��涵�اU�O�X
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
                jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
                break;
              case 1: // Redo
                redoAction = new RedoAction();

                // �إ߿�涵��
                jmenuitem[i][j] = new JMenuItem();
                // �]�w��涵�ت��ʧ@
                jmenuitem[i][j].setAction(redoAction);
                // �]�w��涵�اU�O�X
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
                jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
                break;
              case 3: // Cut
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
              case 4: // Copy
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
              case 5: // Paste
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
          else if (i==2) { // Style Menu
            if (j==0) { // Font
              // �إߤl���
              submenu[j] = new JMenu(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
              // �]�w��涵�اU�O�X
              submenu[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

              for (int k=0; k<font.length; k++) {
                action = new StyledEditorKit.FontFamilyAction(font[k], font[k]);
                //action.putValue(Action.NAME, font[i]);
              
                submenuitem[j][k] = new JMenuItem();
                // �]�w��涵�ت��ʧ@
                submenuitem[j][k].setAction(action);
                submenuitem[j][k].setText(font[k]);
                submenu[j].add(submenuitem[j][k]);
              }
              jmenu[i].add(submenu[j]);
            }
            else if (j==1) { // Font Size
              // �إߤl���
              submenu[j] = new JMenu(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
              // �]�w��涵�اU�O�X
              submenu[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

              for (int k=0; k<size.length; k++) {
                action = new StyledEditorKit.FontSizeAction(size[k]+"", size[k]);
                //action.putValue(Action.NAME, font[i]);
              
                submenuitem[j][k] = new JMenuItem();
                // �]�w��涵�ت��ʧ@
                submenuitem[j][k].setAction(action);
                submenuitem[j][k].setText(size[k]+"");
                submenu[j].add(submenuitem[j][k]);
              }
              jmenu[i].add(submenu[j]);
            }
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
      jpopupmenu1.show(e.getComponent(), e.getX(), e.getY());
  }

  private void menu_actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("New")) { // New
      jtextpane.setText("");
    }
    else if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      if (! jtextpane.getText().equals("")) {
        new Thread() {
          public void run() {
            try {
              // �C�L���e
              jtextpane.print();
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
      JOptionPane.showMessageDialog(null, "JTextPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // New
      jtextpane.setText("");
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // Save
      save();
    }
    else if (e.getSource().equals(jbutton[12])) { // About
      JOptionPane.showMessageDialog(null, "JTextPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
      jtextpane.read(in, filename);
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
      jtextpane.write(out);

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
      undoAction.updateUndoStatus();
      redoAction.updateRedoStatus();
    }
    else
      System.out.println("Can not create UndoManager.");
  }
  
  class UndoAction extends AbstractAction {
    public UndoAction() {
      super("Undo");
      setEnabled(false);
    }
  
    public void actionPerformed(ActionEvent e) {
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
      redoAction.updateRedoStatus();
    }
  
    public void updateUndoStatus() {
      // �P�_�O�_�i�H�_��s��
      if (undoManager.canUndo()) {
        putValue(Action.NAME, undoManager.getUndoPresentationName());
        // �]�w�ҥ�Undo��涵��
        setEnabled(true);
      } 
      else {
        putValue(Action.NAME, "Undo");
        // �]�w���ҥ�Undo��涵��
        setEnabled(false);
      }  
    }
  }
  
  class RedoAction extends AbstractAction {
    public RedoAction() {
      super("Redo");
      setEnabled(false);
    }
  
    public void actionPerformed(ActionEvent e) {
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
      undoAction.updateUndoStatus();
    }
  
    public void updateRedoStatus() {
      // �P�_�O�_�i�H���ƽs��
      if (undoManager.canRedo()) {
        putValue(Action.NAME, undoManager.getRedoPresentationName());
        // �]�w�ҥ�Redo��涵��
        setEnabled(true);
      } 
      else {
        putValue(Action.NAME, "Redo");
        // �]�w���ҥ�Redo��涵��
        setEnabled(false);
      }
    }
  }  
}