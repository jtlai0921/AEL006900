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

import java.util.List;

public class TableCellRendererDemo extends JFrame { 

  JTable jtable;

  String menulabel[]={"File|F", "Options|O", "Help|H"};

  String menuitemlabel[][]={
    {"Print|P|print.gif", "-", "Exit|X"},
    {"Show Grid|S", "Horizontal Line|H", "Vertical Line|V", "Row Selection|V", "Column Selection|C", "Reorder Column|R", "-", "Single Selection|L", "Single Interval Selection|I", "Multiple Interval Selection|M"},
    {"About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][3];
  JCheckBoxMenuItem jcbmenuitem[] = new JCheckBoxMenuItem[10];
  
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

    new TableCellRendererDemo();
  }
    
  // �غc�禡
  public TableCellRendererDemo() {
    super("TableCellRenderer Demo");

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

    ColorClass black = new ColorClass(Color.BLACK, "Black");
    ColorClass blue = new ColorClass(Color.BLUE, "Blue");
    ColorClass cyan = new ColorClass(Color.CYAN, "Cyan");
    ColorClass darkgray = new ColorClass(Color.DARK_GRAY, "Dark Gray");
    ColorClass gray = new ColorClass(Color.GRAY, "Gray");
    ColorClass green = new ColorClass(Color.GREEN, "Green");
    ColorClass lightgray = new ColorClass(Color.LIGHT_GRAY, "Light Gray");
    ColorClass magenta = new ColorClass(Color.MAGENTA, "Magenta");
    ColorClass orange = new ColorClass(Color.ORANGE, "Orange");
    ColorClass pink = new ColorClass(Color.PINK, "Pink");
    ColorClass red = new ColorClass(Color.RED, "Red");
    ColorClass white = new ColorClass(Color.WHITE, "White");
    ColorClass yellow = new ColorClass(Color.YELLOW, "Yellow");

    final String[] columnheader = {"Customer ID", "Customer Name", "Product ID", "Category ID", "Quantity Per Unit", "Price", "Image", "Color"};
  
    final Object[][] data = {
      {new Integer(1),  "Athena", new Integer(1), new Integer(1), "10 boxes x 20 bags", new Integer(18),  new ImageIcon(cl.getResource("images/Icon01.png")), black},
      {new Integer(2),  "Leo",    new Integer(1), new Integer(1), "24 - 12 oz bottles", new Integer(20),  new ImageIcon(cl.getResource("images/Icon02.png")), blue},
      {new Integer(3),  "Teresa", new Integer(1), new Integer(2), "12 - 550 ml bottles", new Integer(19), new ImageIcon(cl.getResource("images/Icon03.png")), cyan},
      {new Integer(4),  "Ray",    new Integer(2), new Integer(2), "12 - 200 ml jars", new Integer(10),    new ImageIcon(cl.getResource("images/Icon04.png")), darkgray},
      {new Integer(5),  "Flora",  new Integer(2), new Integer(2), "32 - 500 g boxes", new Integer(5),     new ImageIcon(cl.getResource("images/Icon05.png")), gray},
      {new Integer(6),  "Shawn",  new Integer(3), new Integer(2), "750 cc per bottle", new Integer(12),   new ImageIcon(cl.getResource("images/Icon06.png")), green},
      {new Integer(7),  "Howard", new Integer(3), new Integer(7), "24 - 50 g pkgs.", new Integer(21),     new ImageIcon(cl.getResource("images/Icon07.png")), lightgray},
      {new Integer(8),  "Gloria", new Integer(3), new Integer(2), "10 boxes x 8 pieces", new Integer(14), new ImageIcon(cl.getResource("images/Icon08.png")), magenta},
      {new Integer(9),  "Rachel", new Integer(4), new Integer(6), "16 - 2 kg boxes", new Integer(2),      new ImageIcon(cl.getResource("images/Icon09.png")), orange},
      {new Integer(10), "Tony",   new Integer(4), new Integer(8), "10 - 4 oz boxes", new Integer(6),      new ImageIcon(cl.getResource("images/Icon10.png")), pink}
    };

    JComboBox cboColor = new JComboBox();
    cboColor.addItem(black);
    cboColor.addItem(blue);
    cboColor.addItem(cyan);
    cboColor.addItem(darkgray);
    cboColor.addItem(gray);
    cboColor.addItem(green);
    cboColor.addItem(lightgray);
    cboColor.addItem(magenta);
    cboColor.addItem(orange);
    cboColor.addItem(pink);
    cboColor.addItem(red);
    cboColor.addItem(white);
    cboColor.addItem(yellow);

    // �إ�Table Model
    TableModel dataModel = new AbstractTableModel() {
      public int getColumnCount() { return columnheader.length; }
      public Class getColumnClass(int columnIndex) {return getValueAt(0, columnIndex).getClass();}
      public String getColumnName(int columnIndex) {return columnheader[columnIndex];}
      public int getRowCount() { return data.length;}
      public Object getValueAt(int rowIndex, int columnIndex) {return data[rowIndex][columnIndex];}
      public boolean isCellEditable(int rowIndex, int columnIndex) {return columnIndex != 6;}
      public void setValueAt(Object aValue, int rowIndex, int columnIndex) { data[rowIndex][columnIndex] = aValue; }
    };
  
    // �HTable Model�إ�JTable����
    jtable = new JTable(dataModel);
    jtable.setFillsViewportHeight(true);
    jtable.setRowHeight(50);

    DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer() {
      public void setValue(Object value) {
        if (value instanceof ColorClass) {
          ColorClass c = (ColorClass) value;
          setBackground(c);
          setForeground(c.getTextColor());
          setText(c.toString());
        } 
        else {
          super.setValue(value);
        }
      }
    };

    colorRenderer.setHorizontalAlignment(JLabel.CENTER);

    TableColumn colorColumn = jtable.getColumn("Color");
    colorColumn.setCellEditor(new DefaultCellEditor(cboColor));
    colorColumn.setCellRenderer(colorRenderer);
    
    // �إߪ�椧�ƧǾ��]Row Sorter�^
    TableRowSorter sorter = new TableRowSorter(dataModel);
    // �]�w��椧�ƧǾ��]Row Sorter�^
    jtable.setRowSorter(sorter);

    // �O�_��ܪ�檺��u
    jtable.setShowGrid(true); 
    // �O�_��ܪ�檺������u
    jtable.setShowHorizontalLines(true); 
    // �O�_��ܪ�檺������u
    jtable.setShowVerticalLines(true); 
    // �O�_���\��ܾ�C
    jtable.setRowSelectionAllowed(true);
    // �O�_���\��ܪ���
    jtable.setColumnSelectionAllowed(true);
    // �O�_���\���ʪ���
    jtable.getTableHeader().setReorderingAllowed(true);
    // �]�w�h����ܼҦ������
    jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
    this.setSize(new Dimension(640, 280));
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

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else {
          if (i != 1) {
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
          else {
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

            // �إ߹Ϲ�
            if (menuitemlabel[i][j].endsWith(".gif")) 
              jcbmenuitem[j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

            // �]�w�֨������涵�ؤ�������A
            if (j!=8 || j!=9) 
              jcbmenuitem[j].setState(true);
              
            if (j>=7) 
              group1.add(jcbmenuitem[j]);
              
            // ���U ItemListener
            jcbmenuitem[j].addItemListener(new ItemListener() {
              public void itemStateChanged(ItemEvent e) {
                menu_itemStateChanged(e);
              }
            });
  
            // �[�J��涵��
            jmenu[i].add(jcbmenuitem[j]);
          }
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
      JOptionPane.showMessageDialog(null, "TableCellRenderer Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
  
  private void menu_itemStateChanged(ItemEvent e) {
    // ���o���Ͷ��بƥ󪺿�涵��
    JCheckBoxMenuItem jcbmenuitem = (JCheckBoxMenuItem)e.getSource();
    
    if (jcbmenuitem.getText().equals("Show Grid")) { // Show Grid
      // �O�_��ܪ�檺��u
      jtable.setShowGrid(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Horizontal Line")) { // Horizontal Line
      // �O�_��ܪ�檺������u
      jtable.setShowHorizontalLines(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Vertical Line")) { // Vertical Line
      // �O�_��ܪ�檺������u
      jtable.setShowVerticalLines(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Row Selection")) { // Row Selection
      // �O�_���\��ܾ�C
      jtable.setRowSelectionAllowed(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Column Selection")) { // Column Selection
      // �O�_���\��ܪ���
      jtable.setColumnSelectionAllowed(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Reorder Column")) { // Reorder Column
      // �O�_���\���ʪ���
      jtable.getTableHeader().setReorderingAllowed(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Single Selection")) { // Single Selection
      // �]�w�h����ܼҦ������
      jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }
    else if (jcbmenuitem.getText().equals("Single Interval Selection")) { // Single Interval Selection
      // �]�w�h����ܼҦ����ȥi��ܳ�@�϶��A�����϶����i��ܦh�Ӷ���
      jtable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); 
    }
    else if (jcbmenuitem.getText().equals("Multiple Interval Selection")) { // Multiple Interval Selection
      // �]�w�h����ܼҦ����h���϶����
      jtable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
    }
  }
}

class ColorClass extends Color {
  String text;

  public ColorClass(Color c, String text) {
    super(c.getRGB());
    this.text = text;
  }
  
  public Color getTextColor() {
    int r = getRed();
    int g = getGreen();
    int b = getBlue();

    if(r > 240 || g > 240) {
      return Color.black;
    } 
    else {
      return Color.white;
    }
  }

  public String toString() {
    return text;
  }
}  
