import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.io.*;

public class JListEventDemo extends JFrame implements ListSelectionListener { // ��@ListSelectionListener����
    
  JTextArea jtextArea = new JTextArea();

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

    new JListEventDemo();
  }

  // �غc�禡
  public JListEventDemo() {
    super("JList Event Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �]�w Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new BorderLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "�ﶵ"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new BorderLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "���G"));

    final int length = 12;  // �ﶵ���حӼ�
    String[] items = new String[length];

    for (int i=0; i < length; i++)    
      items[i] = "Item " + i;

    JList jlist = new JList(items);
    // ���U addListSelectionListener
    jlist.addListSelectionListener(this);

    // ���b
    JScrollPane jscrollpane1 = new JScrollPane(jlist);
    jpanel1.add(jscrollpane1, BorderLayout.CENTER);

    jtextArea.setEditable(false);
    // ���b
    JScrollPane jscrollpane2 = new JScrollPane(jtextArea);
    jpanel2.add(jscrollpane2, BorderLayout.CENTER);

    this.add(jpanel1, BorderLayout.WEST);
    this.add(jpanel2, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(350, 250));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  // ��ﶵ���سQ����Ψ��������
  public void valueChanged(ListSelectionEvent e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    
    pw.println("�Ĥ@�ӯ��ޭ�: " + e.getFirstIndex() + ", �̫�@�ӯ��ޭ�: " + e.getLastIndex());
    
    // �P�_���ƥ�O�_���h�Ӥ��P���ƥ󤧤@
    boolean adjust = e.getValueIsAdjusting();
    
    if (!adjust) {
      JList source = (JList)e.getSource();
      
      // ���o�Q������ت����ޭ�
      int selections[] = source.getSelectedIndices();
      // ���o�w�Q���������
      Object selectionValues[] = source.getSelectedValues();
      
      for (int i=0, n=selections.length; i<n; i++) {
        if (i==0) {
          pw.print("  �������: ");
        }
        pw.print(selections[i] + "(" + selectionValues[i] + ") ");
      }
      pw.println();
    }
    jtextArea.append(sw.toString());
  }
}
