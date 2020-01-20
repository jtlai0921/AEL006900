import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

public class LookandFeelDemo extends javax.swing.JFrame implements ActionListener {

  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[7];

  String item[] = {"Metal", "CDE/Motif", "Windows XP", "Windows Classic", "GTK+", "Mac", "Nimbus"};

  String classname[] = {
    "javax.swing.plaf.metal.MetalLookAndFeel", 
    "com.sun.java.swing.plaf.motif.MotifLookAndFeel", 
    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel", 
    "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", 
    "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
    "com.sun.java.swing.plaf.mac.MacLookAndFeel",
    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"};

  // Main method
  public static void main(String[] args) {
    try {
      // �]�wMetal Look and Feel 
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
    new LookandFeelDemo();
  }

  // �غc�禡
  // ���ե�
  public LookandFeelDemo() {
    super("Look and Feel Demo");

    // �إ߿��C
    JMenuBar jmenubar = createJMenuBar();

    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // �]�w�������j�p
    this.setSize(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

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

  private JMenuBar createJMenuBar() {
    // �إ߿��C
    JMenuBar jmenubar = new JMenuBar();

    // �إ߿��
    JMenu jmenuFile = new JMenu("File");

    // �إ߿�涵��
    JMenuItem jmenuFileExit = new JMenuItem("Exit");
    // ���UActionListener
    jmenuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });
    jmenuFile.add(jmenuFileExit);

    // �إ߿��
    JMenu jmenuLF = new JMenu("L & F");

    // �غc�s��
    ButtonGroup group = new ButtonGroup();
    
    for (int i=0; i<item.length; i++) {
      // �]�w�ﶵ���s��涵��
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      
      // �]�w�O�_�ҥο�涵��
      jrbmenuitem[i].setEnabled(isLookAndFeelSupported(classname[i]));

      jmenuLF.add(jrbmenuitem[i]);
      group.add(jrbmenuitem[i]);
      
      // ���UActionListener
      jrbmenuitem[i].addActionListener(this);
     
      if (i==0)
        jrbmenuitem[i].setSelected(true);
    }

    // �s�W���ܿ��C
    jmenubar.add(jmenuFile);
    jmenubar.add(jmenuLF);

    return jmenubar;
  }

  private boolean isLookAndFeelSupported(String lnfname) {
    try { 
      Class lnfclass = Class.forName(lnfname);
      javax.swing.LookAndFeel lnf = (LookAndFeel)(lnfclass.newInstance());
      
      // �P�_�@�~�t�άO�_�䴩Look and Feel
      return lnf.isSupportedLookAndFeel();
    } 
    catch(Exception e) { 
      return false;
    }
  }

  public void actionPerformed(ActionEvent e) {
    try {
      for (int i=0; i<item.length; i++) {
        if(e.getActionCommand().equals(item[i])) { 
          // �]�wLook and Feel
          UIManager.setLookAndFeel(classname[i]);
        }
      }      
    }
    catch(Exception ex) {}
    
    // ���涥�q�ܧ�Look and Feel
    SwingUtilities.updateComponentTreeUI(this);
  }
}
