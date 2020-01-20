import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class EtchedBorderDemo extends javax.swing.JFrame {
   
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

    new EtchedBorderDemo();
  }

  // �غc�禡
  public EtchedBorderDemo() {
    super("Etched Border Demo");

    final int row = 4;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[4];
    
    // �إ߻k�����

    // �غc�禡1
    border[0] = new javax.swing.border.EtchedBorder();

    // �غc�禡2
    // �Y�_���k�����
    border[1] = new javax.swing.border.EtchedBorder(EtchedBorder.RAISED);

    // �غc�禡3
    // �W�U���k�����
    border[2] = new javax.swing.border.EtchedBorder(Color.PINK, Color.CYAN);

    // �غc�禡4
    // �W�U���k�����
    border[3] = new javax.swing.border.EtchedBorder(EtchedBorder.RAISED, 
      new Color(120,50,0), new Color(245,185,60));

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      JButton jbutton = new JButton("Etched Border " + (i+1));
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