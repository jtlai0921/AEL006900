import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class MatteBorderDemo extends javax.swing.JFrame {
   
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

    new MatteBorderDemo();
  }

  // �غc�禡
  public MatteBorderDemo() {
    super("Matte Border Demo");

    final int row = 3;    // �C
    final int column = 2; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[5];

    // �]�w��ؤ��W�U���k�����O�Z��
    Insets insets = new Insets(20, 20, 20, 20);
    
    // �إ�Matte���

    // �غc�禡1
    border[0] = new javax.swing.border.MatteBorder(new ImageIcon("images/border.gif"));

    // �غc�禡2
    border[1] = new javax.swing.border.MatteBorder(insets, new Color(120,50,0));

    // �غc�禡4
    border[2] = new javax.swing.border.MatteBorder(10, 10, 10, 10, Color.GRAY);

    // �غc�禡3
    border[3] = new javax.swing.border.MatteBorder(insets, new ImageIcon("images/border.gif"));

    // �غc�禡5
    border[4] = new javax.swing.border.MatteBorder(5, 5, 5, 5, new ImageIcon("images/border.gif"));

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      JButton jbutton = new JButton("Matte Border " + (i+1));
      jpanel.add(jbutton, BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // �]�w�������j�p
    this.setSize(320, 250);

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