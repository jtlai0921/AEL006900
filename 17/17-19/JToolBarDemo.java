import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JToolBarDemo extends javax.swing.JFrame {

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

    new JToolBarDemo();
  }

  // �غc�禡
  public JToolBarDemo() {
    super("JToolBar Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    // �]�w���B�ʤu��C
    jtoolbar.setFloatable(true);
    // �]�wRollover�u��C�W���s���ĪG
    jtoolbar.setRollover(true);

    String title[] = {"New", "Open", "Save", "Cut", "Copy", "Paste"};
    JButton jbutton[] = new JButton[title.length];

    for (int i=0; i<title.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + title[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(36, 36));
      jbutton[i].setToolTipText(title[i]);

      // �[�J���s�ܤu��C��
      jtoolbar.add(jbutton[i]);
      
      if (i==2) 
        // �[�J���j�u
        jtoolbar.addSeparator();
    }

    jtoolbar.addSeparator();
    
    // ���ե�
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String font[] = ge.getAvailableFontFamilyNames();
    
    JComboBox jcombobox = new JComboBox();
    jcombobox.setPreferredSize(new Dimension(120, 36));
    jcombobox.setMaximumSize(new Dimension(120, 36));
    jcombobox.setToolTipText("Font");

    for(int i=0; i < font.length; i++) 
      jcombobox.addItem(font[i]);

    // �����anIndex�ӿﶵ����
    jcombobox.setSelectedIndex(0);
    // �]�w�U��JComboBox���O�ɡA�̦h�i��ܪ����ئC��
    jcombobox.setMaximumRowCount(5);

    jtoolbar.add(jcombobox);
    // �[�J���j�u
    jtoolbar.addSeparator();
    
    this.add(jtoolbar, BorderLayout.NORTH);

    // �]�w�������j�p
    this.setSize(400, 100);

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
}
