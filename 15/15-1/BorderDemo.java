import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BorderDemo extends javax.swing.JFrame {
   
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

    new BorderDemo();
  }

  // �غc�禡
  public BorderDemo() {
    super("Border Demo");

    final int row = 4;    // �C
    final int column = 2; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();
    
    Border border[] = new Border[8];                   

    // �إߥW�U�������
    border[0] = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    // �إߥY�_�������
    border[1] = BorderFactory.createRaisedBevelBorder();
    // �إߥW�U�k�����
    border[2] = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    // �إߥY�_�k�����
    border[3] = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(150, 150, 150));
    // �إߵL����˦������
    border[4] = BorderFactory.createEmptyBorder();
    // �إ߽u�����
    border[5] = BorderFactory.createLineBorder(Color.red);
    // �إߦۭq����˦������
    border[6] = BorderFactory.createMatteBorder(9, 9, 9, 9, new ImageIcon(cl.getResource("images/border.gif")));
    // �إ߽ƦX���
    // ��ؤ��t���W�U�������
    // ��إ~�t���Y�_�������
    border[7] = BorderFactory.createCompoundBorder(border[0], border[1]);

    String label[] = {
      "Lowered Bevel Border", "Raised Bevel Border", 
      "Lowered Etched Border", "Raised Etched Border",
      "Empty Border", "Line Border", 
      "Matte Border", "Compound Border" };

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);
      jpanel.add(new JLabel(label[i], JLabel.CENTER), BorderLayout.CENTER);

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
