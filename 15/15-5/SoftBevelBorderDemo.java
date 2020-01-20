import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class SoftBevelBorderDemo extends javax.swing.JFrame {
   
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

    new SoftBevelBorderDemo();
  }

  // �غc�禡
  public SoftBevelBorderDemo() {
    super("Soft Bevel Border Demo");

    final int row = 3;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[3];
    
    // �إ߱������

    // �غc�禡1
    // �W�U���������
    border[0] = new javax.swing.border.SoftBevelBorder(BevelBorder.LOWERED);

    // �غc�禡2
    // �Y�_���������
    border[1] = new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED, 
      Color.PINK, Color.CYAN);

    // �غc�禡3
    // �W�U���������
    border[2] = new javax.swing.border.SoftBevelBorder(BevelBorder.LOWERED, 
      new Color(120,50,0), new Color(245,185,60), Color.RED, Color.BLUE);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      JButton jbutton = new JButton("Soft Bevel Border " + (i+1));
      jpanel.add(jbutton, BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // �]�w�������j�p
    this.setSize(250, 200);

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