import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.table.*;

import java.text.MessageFormat;
import java.awt.print.PrinterException;

public class JTableDemo extends JFrame implements MouseListener { // ��@MouseListener���� 

  JTable jtable;

  String menulabel[]={"File|F", "Help|H"};

  String menuitemlabel[][]={
    {"Print|P|print.gif", "-", "Exit|X"},
    {"About|A|about.gif"}
  };
  
  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[2][3];
      
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

    new JTableDemo();
  }
    
  // �غc�禡
  public JTableDemo() {
    super("JTable Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);
  
    String[] columnheader = {"Customer ID", "Customer Name", "Product ID", "Category ID", "Quantity Per Unit", "Price"};
  
    Object[][] data = {
      {new Integer(1),  "Athena", new Integer(1), new Integer(1), "10 boxes x 20 bags", new Integer(18)},
      {new Integer(2),  "Leo",    new Integer(1), new Integer(1), "24 - 12 oz bottles", new Integer(20)},
      {new Integer(3),  "Teresa", new Integer(1), new Integer(2), "12 - 550 ml bottles", new Integer(19)},
      {new Integer(4),  "Ray",    new Integer(2), new Integer(2), "12 - 200 ml jars", new Integer(10)},
      {new Integer(5),  "Flora",  new Integer(2), new Integer(2), "32 - 500 g boxes", new Integer(5)},
      {new Integer(6),  "Shawn",  new Integer(3), new Integer(2), "750 cc per bottle", new Integer(12)},
      {new Integer(7),  "Howard", new Integer(3), new Integer(7), "24 - 50 g pkgs.", new Integer(21)},
      {new Integer(8),  "Gloria", new Integer(3), new Integer(2), "10 boxes x 8 pieces", new Integer(14)},
      {new Integer(9),  "Rachel", new Integer(4), new Integer(6), "16 - 2 kg boxes", new Integer(2)},
      {new Integer(10), "Tony",   new Integer(4), new Integer(8), "10 - 4 oz boxes", new Integer(6)}
    };
  
    // �إ�JTable����
    jtable = new JTable(data, columnheader);
    jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
    jtable.setFillsViewportHeight(true);

    // ���U MouseListener
    jtable.addMouseListener(this); 
    // ���b
    JScrollPane jscrollpane = new JScrollPane(jtable);
    jscrollpane.setWheelScrollingEnabled(true);
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
    this.setSize(new Dimension(500, 200));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  // ��@MouseListener��������k
  public void mouseClicked(MouseEvent e) {
    // ���o��檺�C��
    int numRows = jtable.getRowCount();
    // ���o��檺���
    int numColumns = jtable.getColumnCount();
    
    // ���oJTable���O��Table Model
    TableModel dataModel = jtable.getModel();

    for (int i=0; i < numRows; i++) {
      for (int j=0; j < numColumns; j++) {
        // ���o���w��]column�^�C�]row�^���椸��쪺��
        System.out.print(dataModel.getValueAt(i, j) + "  ");
      }
      System.out.println();
    }
  }

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {}

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

  private void menu_actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("Print")) { // Print
      printTable();
    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (jmenuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JTable Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  
  private void printTable() {
    // �����榡
    MessageFormat headerFormat = new MessageFormat("JTable Header");
    // �����榡
    MessageFormat footerFormat = new MessageFormat("Page {0}");

    // �t�X�ȱi�j�p�A�H���e�׽վ�C�L��檺��ҡA�H�K�C�L�ɯ�e�ǩҦ�������
    JTable.PrintMode printMode = JTable.PrintMode.FIT_WIDTH;
    // ���վ�C�L��檺��ҡA�H��楿�`�j�p�C�L   
    // JTable.PrintMode printMode = JTable.PrintMode.NORMAL;

    try {
      boolean status = jtable.print(printMode, headerFormat, footerFormat);

      if (status) 
        JOptionPane.showMessageDialog(null, "�C�L����", "JTable Print Demo", JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog(null, "�����C�L", "JTable Print Demo", JOptionPane.INFORMATION_MESSAGE);
    } 
    catch (PrinterException pex) {
      pex.printStackTrace();
    } 
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }  
}
