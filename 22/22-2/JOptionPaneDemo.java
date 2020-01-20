import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

public class JOptionPaneDemo extends JFrame implements ActionListener {
  
  JOptionPane joptionpane = new JOptionPane();

  JLabel jlabel = new JLabel();

  String menulabel[]={"Type|T"};

  String menuitemlabel[][]={
    {"Confirm Dialog", "Input Dialog", "Message Dialog", "Option Dialog", "-", "Exit"}
  };

  String submenuitemlabel[]={"Error", "Information", "Plain", "Question", "Warning"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[1][6];
  JRadioButtonMenuItem jrbmenuitem[][] = new JRadioButtonMenuItem[4][5];

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JOptionPaneDemo();
  }

  // �غc�禡
  public JOptionPaneDemo() {
    super("JOptionPane Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    this.add(jlabel, BorderLayout.SOUTH);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(300, 300));
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

    ButtonGroup menugroup[] = new ButtonGroup[6];

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        
        // �غc�s��  
        menugroup[j] = new ButtonGroup();
        
        if ((i==0 && j==0) || (i==0 && j==1) || (i==0 && j==2) || (i==0 && j==3)) { // �l���
          // �إߤl���
          JMenu submenu = new JMenu(menuitemlabel[i][j]);

          // �إ߿�涵��
          for (int k=0; k<submenuitemlabel.length; k++){
            jrbmenuitem[j][k] = new JRadioButtonMenuItem(submenuitemlabel[k]);

            // ���U ActionListener
            jrbmenuitem[j][k].addActionListener(this);

            // �]�w�ﶵ���s��涵�ؤ�������A
            if (k==0) 
              jrbmenuitem[j][k].setSelected(true);
      
            // �[�J��涵�ئܤl���
            submenu.add(jrbmenuitem[j][k]);
            menugroup[j].add(jrbmenuitem[j][k]);
          }
      
          jmenu[i].add(submenu);
        }
        else if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else {
          // �إ߿�涵��
          jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

          // ���U ActionListener
          jmenuitem[i][j].addActionListener(this);

          // �[�J��涵��
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    // �ۭq���s (���ե�)
    String option[] = {"Yes", "Well...", "No Way", "Cancel"};

    // 
    // �T�{��ܮ� 
    // 
    if (e.getSource().equals(jrbmenuitem[0][0])) { // ���~�T�� 
      int result = joptionpane.showConfirmDialog(
        null, "�T�{��ܮ���ܿ��~�T��", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        
      jlabel.setText("�T�{��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][1])) { // �@��T�� 
      int result = joptionpane.showConfirmDialog(
        null, "�T�{��ܮ���ܤ@��T��", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
      jlabel.setText("�T�{��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][2])) { // �¤�r�T��
      int result = joptionpane.showConfirmDialog(
        null, "�T�{��ܮ���ܯ¤�r�T��", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
      jlabel.setText("�T�{��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][3])) { // �߰ݰT��
      int result = joptionpane.showConfirmDialog(
        null, "�T�{��ܮ���ܸ߰ݰT��", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
      jlabel.setText("�T�{��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][4])) { // ĵ�i�T��
      int result = joptionpane.showConfirmDialog(
        null, "�T�{��ܮ����ĵ�i�T��", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
      jlabel.setText("�T�{��ܮئ^�ǭ�: " + result);
    }
    // 
    // ��J��ܮ� 
    // 
    else if (e.getSource().equals(jrbmenuitem[1][0])) { // ���~�T�� 
      String result = joptionpane.showInputDialog(
        null, "��J��ܮ���ܿ��~�T��", "Input Dialog", 
        JOptionPane.ERROR_MESSAGE);
        
      jlabel.setText("��J��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][1])) { // �@��T�� 
      String result = joptionpane.showInputDialog(
        null, "��J��ܮ���ܤ@��T��", "Input Dialog", 
        JOptionPane.INFORMATION_MESSAGE);
        
      jlabel.setText("��J��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][2])) { // �¤�r�T��
      String result = joptionpane.showInputDialog(
        null, "��J��ܮ���ܯ¤�r�T��", "Input Dialog", 
        JOptionPane.PLAIN_MESSAGE);
        
      jlabel.setText("��J��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][3])) { // �߰ݰT��
      String result = joptionpane.showInputDialog(
        null, "��J��ܮ���ܸ߰ݰT��", "Input Dialog", 
        JOptionPane.QUESTION_MESSAGE);
        
      jlabel.setText("��J��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][4])) { // ĵ�i�T��
      String result = joptionpane.showInputDialog(
        null, "��J��ܮ����ĵ�i�T��", "Input Dialog", 
        JOptionPane.WARNING_MESSAGE);
        
      jlabel.setText("��J��ܮئ^�ǭ�: " + result);
    }
    // 
    // �T����ܮ� 
    // 
    else if (e.getSource().equals(jrbmenuitem[2][0])) { // ���~�T�� 
      joptionpane.showMessageDialog(
        null, "�T����ܮ���ܿ��~�T��", "Message Dialog", 
        JOptionPane.ERROR_MESSAGE);
        
      jlabel.setText("�T����ܮصL�^�ǭ�");
    }
    else if (e.getSource().equals(jrbmenuitem[2][1])) { // �@��T�� 
      joptionpane.showMessageDialog(
        null, "�T����ܮ���ܤ@��T��", "Message Dialog", 
        JOptionPane.INFORMATION_MESSAGE);
        
      jlabel.setText("�T����ܮصL�^�ǭ�");
    }
    else if (e.getSource().equals(jrbmenuitem[2][2])) { // �¤�r�T��
      joptionpane.showMessageDialog(
        null, "�T����ܮ���ܯ¤�r�T��", "Message Dialog", 
        JOptionPane.PLAIN_MESSAGE);
        
      jlabel.setText("�T����ܮصL�^�ǭ�");
    }
    else if (e.getSource().equals(jrbmenuitem[2][3])) { // �߰ݰT��
      joptionpane.showMessageDialog(
        null, "�T����ܮ���ܸ߰ݰT��", "Message Dialog", 
        JOptionPane.QUESTION_MESSAGE);
        
      jlabel.setText("�T����ܮصL�^�ǭ�");
    }
    else if (e.getSource().equals(jrbmenuitem[2][4])) { // ĵ�i�T��
      joptionpane.showMessageDialog(
        null, "�T����ܮ����ĵ�i�T��", "Message Dialog", 
        JOptionPane.WARNING_MESSAGE);
        
      jlabel.setText("�T����ܮصL�^�ǭ�");
    }
    // 
    // �ﶵ��ܮ� 
    // 
    else if (e.getSource().equals(jrbmenuitem[3][0])) { // ���~�T�� 
      int result = joptionpane.showOptionDialog(
        null, "�ﶵ��ܮ���ܿ��~�T��", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, 
        null, option, option[0]);
        
      jlabel.setText("�ﶵ��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][1])) { // �@��T�� 
      int result = joptionpane.showOptionDialog(
        null, "�ﶵ��ܮ���ܤ@��T��", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
        null, option, option[1]);
        
      jlabel.setText("�ﶵ��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][2])) { // �¤�r�T��
      int result = joptionpane.showOptionDialog(
        null, "�ﶵ��ܮ���ܯ¤�r�T��", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
        null, option, option[2]);
        
      jlabel.setText("�ﶵ��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][3])) { // �߰ݰT��
      int result = joptionpane.showOptionDialog(
        null, "�ﶵ��ܮ���ܸ߰ݰT��", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
        null, option, option[3]);
        
      jlabel.setText("�ﶵ��ܮئ^�ǭ�: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][4])) { // ĵ�i�T��
      int result = joptionpane.showOptionDialog(
        null, "�ﶵ��ܮ����ĵ�i�T��", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
        null, option, option[0]);
        
      jlabel.setText("�ﶵ��ܮئ^�ǭ�: " + result);
    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      System.exit(0);
    }
  }
}
