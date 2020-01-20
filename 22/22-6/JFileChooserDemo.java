import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;

public class JFileChooserDemo extends JFrame {
  
  String menulabel[]={"File|F", "Help|H"};

  String menuitemlabel[][]={
    {"Open|O|open.gif", "Save|S|save.gif", "-", "Exit|X"},
    {"About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[2][4];

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

    new JFileChooserDemo();
  }

  // �غc�禡
  public JFileChooserDemo() {
    super("JFileChooser Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �إ߿��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(200, 200));
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

  private void menu_actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
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
      JOptionPane.showMessageDialog(null, "JFileChooser Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
    // �O�_���\�h������ɮ�
    jfilechooser.setMultiSelectionEnabled(false);

    // �B�z�z�����]Filter�^
    CustomFileFilter[] filter = new CustomFileFilter[4];
    
    filter[0] = new CustomFileFilter("JPEG Files", "jpg");
    filter[1] = new CustomFileFilter("GIF Files" , "gif");
    filter[2] = new CustomFileFilter("TIFF Files", "tif");
    filter[3] = new CustomFileFilter("PNG Files" , "png");

    for (int i=0; i < filter.length; i++) 
      // �N�z�����[�J��Linked-list����
      jfilechooser.addChoosableFileFilter(filter[i]);

    // �]�w�ثe���z�����
    jfilechooser.setFileFilter(filter[0]);
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    System.out.println("�}���ɮ׹�ܲ��^�ǭ�: " + jfilechooser.getSelectedFile());    
  }

  private void save() {
    JFileChooser jfilechooser = new JFileChooser();
    
    // �]�w�ɮ׹�ܲ������D
    jfilechooser.setDialogTitle("�x�s�ɮ�");
    // �]�w�ɮ׹�ܲ����ثe�ؿ�
    jfilechooser.setCurrentDirectory(new File("."));
    // �]�w�ɮ׹�ܲ�������
    jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
    // �O�_���\�h������ɮ�
    jfilechooser.setMultiSelectionEnabled(false);
    
    // �B�z�z�����]Filter�^
    CustomFileFilter[] filter = new CustomFileFilter[4];
    
    filter[0] = new CustomFileFilter("JPEG Files", "jpg");
    filter[1] = new CustomFileFilter("GIF Files" , "gif");
    filter[2] = new CustomFileFilter("TIFF Files", "tif");
    filter[3] = new CustomFileFilter("PNG Files" , "png");

    for (int i=0; i < filter.length; i++) 
      // �N�z�����[�J��Linked-list����
      jfilechooser.addChoosableFileFilter(filter[i]);

    // �]�w�ثe���z�����
    jfilechooser.setFileFilter(filter[0]);

    if (jfilechooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    System.out.println("�x�s�ɮ׹�ܲ��^�ǭ�: " + jfilechooser.getSelectedFile());
  }
}

