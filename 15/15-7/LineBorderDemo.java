import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class LineBorderDemo extends javax.swing.JFrame {
   
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

    new LineBorderDemo();
  }

  // �غc�禡
  public LineBorderDemo() {
    super("Line Border Demo");

    final int row = 3;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[3];
    
    // �إ߽u�����

    // �غc�禡1
    border[0] = new javax.swing.border.LineBorder(Color.RED);

    // �غc�禡2
    border[1] = new javax.swing.border.LineBorder(new Color(120,50,0), 10);

    // �غc�禡3
    border[2] = new javax.swing.border.LineBorder(Color.GRAY, 15, true);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      JButton jbutton = new JButton("Line Border " + (i+1));
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