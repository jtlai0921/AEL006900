import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class TitledBorderDemo extends javax.swing.JFrame {
   
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

    new TitledBorderDemo();
  }

  // �غc�禡
  public TitledBorderDemo() {
    super("Titled Border Demo");

    final int row = 3;    // �C
    final int column = 3; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    // �إߥW�U�k����إH�@�����D��ؤ����
    Border eborder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    Border border[] = new Border[7];

    // �]�w��ؤ��W�U���k�����O�Z��
    Insets insets = new Insets(20, 20, 20, 20);
    
    // �إ߼��D���

    // �غc�禡1
    border[0] = new javax.swing.border.TitledBorder(eborder);

    // �غc�禡2
    border[1] = new javax.swing.border.TitledBorder(eborder, "Default Position");

    // �غc�禡3
    border[2] = new javax.swing.border.TitledBorder(eborder, "Left-Top", 
      TitledBorder.LEFT, TitledBorder.TOP);

    // �غc�禡4
    border[3] = new javax.swing.border.TitledBorder(eborder, "Right-Bottom", 
      TitledBorder.RIGHT, TitledBorder.BOTTOM, 
      new Font("sansserif", Font.PLAIN | Font.ITALIC, 12));

    // �غc�禡5
    border[4] = new javax.swing.border.TitledBorder(eborder, "Center-Above Top", 
      TitledBorder.CENTER, TitledBorder.ABOVE_TOP, 
      new Font("dialog", Font.BOLD | Font.ITALIC, 12),
      new Color(245,185,60));

    // �غc�禡6
    border[5] = new javax.swing.border.TitledBorder("Only Title");
    
    // �ƦX���D���
    border[6] = new javax.swing.border.TitledBorder(border[2], "Right-Bottom", 
      TitledBorder.RIGHT, TitledBorder.BOTTOM);
    
    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      jpanel.add(new JLabel("Titled Border " + (i+1), JLabel.CENTER), BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // �]�w�������j�p
    this.setSize(400, 250);

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