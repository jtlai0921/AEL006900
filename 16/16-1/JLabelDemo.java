import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JLabelDemo extends javax.swing.JFrame {

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

    new JLabelDemo();
  }

  // �غc�禡
  public JLabelDemo() {
    super("JLabel Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إߥW�U�k�����
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    JLabel[] jlabel = new JLabel[3];
    // �¤�r����
    jlabel[0] = new JLabel("Plain Label");
    // �]�w��������覡
    jlabel[0].setHorizontalAlignment(JLabel.CENTER);

    // �Ϲ�����
    jlabel[1] = new JLabel("Image Label", new ImageIcon(cl.getResource("images/dukeswing.gif")), JLabel.LEADING);
    // �]�w�r��
    jlabel[1].setFont(new Font("dialog", Font.BOLD, 10));
    // �]�w�e���C��
    jlabel[1].setForeground(new Color(120,50,0));
    // �]�w���Ҧr��۹��Ϲ���������m
    jlabel[1].setHorizontalTextPosition(JLabel.LEADING);
    // �]�w���Ҧr��۹��Ϲ���������m
    jlabel[1].setVerticalTextPosition(JLabel.CENTER);
    // �H�r���]�w���Ҫ�����U�O�X
    jlabel[1].setDisplayedMnemonic('e');
    // �]�w���ҧU�O�X��index��
    jlabel[1].setDisplayedMnemonicIndex(4);
    // �]�w���Ҥ���ؼ˦�
    jlabel[1].setBorder(border);

    // HTML����
    jlabel[2] = new JLabel("<html><font face=Verdana size=2 color=#FF0000><i>HTML Label</i></font></html>");
    // �]�w��������覡
    jlabel[2].setHorizontalAlignment(JLabel.TRAILING);
    // �]�w��������覡
    jlabel[2].setVerticalAlignment(JLabel.TOP);

    this.add(jlabel[0], BorderLayout.NORTH);
    this.add(jlabel[1], BorderLayout.CENTER);
    this.add(jlabel[2], BorderLayout.SOUTH);

    // �]�w�������j�p
    this.setSize(200, 200);

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