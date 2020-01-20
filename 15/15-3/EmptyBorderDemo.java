import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class EmptyBorderDemo extends javax.swing.JFrame {
   
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

    new EmptyBorderDemo();
  }

  // �غc�禡
  public EmptyBorderDemo() {
    super("Empty Border Demo");

    final int row = 2;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[2];
    
    // �إߵL����˦������

    // �]�w��ؤ��W�U���k�����O�Z��
    Insets insets = new Insets(10, 10, 10, 10);

    // �غc�禡1
    border[0] = new javax.swing.border.EmptyBorder(insets);

    // �غc�禡2
    border[1] = new javax.swing.border.EmptyBorder(20, 20, 20, 20);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // �]�w������ؼ˦�
      jpanel.setBorder(border[i]);

      // ���ե�
      // ���o��ؤ��W�U���k�����O�Z��
      Insets inset = border[i].getBorderInsets(jpanel);
      
      System.out.println("Inset " + (i+1) + " Top, Left, Bottom, Right: " + 
        inset.top + ", " + inset.left + ", " + inset.bottom + ", " + inset.right);

      JButton jbutton = new JButton("Empty Border " + (i+1));
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
