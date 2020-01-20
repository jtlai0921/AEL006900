import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JButtonEventDemo extends javax.swing.JFrame implements ActionListener {

  JButton[] jbutton = new JButton[6];
  JLabel jlabel;

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

    new JButtonEventDemo();
  }

  // �غc�禡
  public JButtonEventDemo() {
    super("JButton Event Demo");
    
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    String title[] = {"New", "Open", "Save", "Cut", "Copy", "Paste"};

    for (int i=0; i < jbutton.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + title[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(36, 36));
      jbutton[i].setToolTipText(title[i]);

      // ���U ActionListener
      jbutton[i].addActionListener(this);

      jtoolbar.add(jbutton[i]);
    }

    // �إߥW�U�k�����
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    JPanel jpanel = new JPanel();
    // �]�w������ؼ˦�
    jpanel.setBorder(border);
    jpanel.setLayout(new BorderLayout());

    jlabel = new JLabel("JButton Event");
    jpanel.add(jlabel, BorderLayout.WEST);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jpanel, BorderLayout.SOUTH);

    // �]�w�������j�p
    this.setSize(300, 150);

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

  // ��ʧ@�o�ͮ�
  public void actionPerformed(ActionEvent e) {
    String source = "";

    for (int i=0; i < jbutton.length; i++) {
      if (e.getSource().equals(jbutton[i]))
        source = "Select " + jbutton[i].getToolTipText() + " Button";
    }

    jlabel.setText(source);
  }
}
