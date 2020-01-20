import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class CompoundBorderDemo extends javax.swing.JFrame {
   
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

    new CompoundBorderDemo();
  }

  // �غc�禡
  public CompoundBorderDemo() {
    super("Compound Border Demo");

    final int row = 2;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    // �إߥW�U�k�����
    Border inborder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // �إߥW�U�������
    Border outborder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

    Border border[] = new Border[2];

    // �إ߽ƦX���

    // �غc�禡1
    border[0] = new javax.swing.border.CompoundBorder();

    // �غc�禡2
    border[1] = new javax.swing.border.CompoundBorder(outborder, inborder);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      jpanel.add(new JLabel("Compound Border " + (i+1), JLabel.CENTER), BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // �]�w�������j�p
    this.setSize(200, 150);

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