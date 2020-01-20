import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class DecorationDemo extends javax.swing.JFrame implements ActionListener {

  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[9];
  
  String item[] = {
    "Frame", "Plain Dialog", "File Chooser Dialog", 
    "Color Chooser Dialog", "Information Dialog", "Question Dialog", 
    "Error Dialog", "Warning Dialog", "None"};

  ClassLoader cl;
  
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      // �]�wMetal Look and Feel
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
    new DecorationDemo();
  }

  // �غc�禡
  public DecorationDemo() {
    // ���o�ثe��Class Loader
    cl = this.getClass().getClassLoader();

    this.setUndecorated(true);
    this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    this.setTitle("Decoration: FRAME");

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �إ߿��C
    JMenuBar jmenubar = createJMenuBar();

    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // �]�w�������j�p
    this.setSize(300, 120);
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
    JMenuItem jmenuOpen = new JMenuItem("Open ...", new ImageIcon(cl.getResource("images/open.gif")));
    jmenuOpen.setMnemonic('O');
    jmenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    // ���U ActionListener
    jmenuOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser jfileChooser = new JFileChooser();

        jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        jfileChooser.setDialogTitle("Open File ...");
        
        if (jfileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) 
          return;
      }
    });

    // �إ߿�涵��
    JMenuItem jmenuFileExit = new JMenuItem("Exit");
    jmenuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuOpen);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuFileExit);

    // �إ߿��
    JMenu jmenuDec = new JMenu("Decoration");

    // �غc�s��
    ButtonGroup group = new ButtonGroup();

    for (int i=0; i<item.length; i++) {
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      jmenuDec.add(jrbmenuitem[i]);
      group.add(jrbmenuitem[i]);
      
      // ���U ActionListener
      jrbmenuitem[i].addActionListener(this);
     
      if (i==0)
        jrbmenuitem[i].setSelected(true);
    }

    // �s�W���ܿ��C

    JMenu jmenuHelp = new JMenu();
    jmenuHelp.setText("Help");

    // About
    JMenuItem jmenuAbout = new JMenuItem("About", new ImageIcon(cl.getResource("images/about.gif")));
    jmenuAbout.setMnemonic('A');
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Window Decoration Style", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    jmenuHelp.add(jmenuAbout);

    // �s�W���ܿ��C
    jmenubar.add(jmenuFile);
    jmenubar.add(jmenuDec);
    jmenubar.add(jmenuHelp);

    return jmenubar;
  }

  public void actionPerformed(ActionEvent e) {
    try {
      if(e.getActionCommand().equals(item[0])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        this.setTitle("Decoration: FRAME");
      }
      else if(e.getActionCommand().equals(item[1])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        this.setTitle("Decoration: PLAIN_DIALOG");
      }
      else if(e.getActionCommand().equals(item[2])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
        this.setTitle("Decoration: FILE_CHOOSER_DIALOG");
      }
      else if(e.getActionCommand().equals(item[3])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
        this.setTitle("Decoration: COLOR_CHOOSER_DIALOG");
      }
      else if(e.getActionCommand().equals(item[4])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        this.setTitle("Decoration: INFORMATION_DIALOG");
      }
      else if(e.getActionCommand().equals(item[5])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
        this.setTitle("Decoration: QUESTION_DIALOG");
      }
      else if(e.getActionCommand().equals(item[6])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
        this.setTitle("Decoration: ERROR_DIALOG");
      }
      else if(e.getActionCommand().equals(item[7])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
        this.setTitle("Decoration: WARNING_DIALOG");
      }
      else if(e.getActionCommand().equals(item[8])) {
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setTitle("Decoration: NONE");
      }
    }
    catch(Exception ex) {}
  }
}
