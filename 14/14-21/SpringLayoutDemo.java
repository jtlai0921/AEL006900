import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class SpringLayoutDemo extends javax.swing.JFrame {

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

    new SpringLayoutDemo();
  }

  // 建構函式
  public SpringLayoutDemo() {
    super("Spring Layout Demo");

    // 直接定義JFrame之Layout Manager為BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    // 定義JPanel之Layout Manager為SpringLayout
    jpanel.setLayout(new SpringLayout());

    // 取得JPanel之Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    JLabel[] jlabel = new JLabel[4];
    jlabel[0] = new JLabel("ID #:");
    jlabel[1] = new JLabel("Name:");
    jlabel[2] = new JLabel("Address:");
    jlabel[3] = new JLabel("E-Mail:");

    JTextField[] jtextfield = new JTextField[4];

    for (int i = 0; i < jtextfield.length; i++) {
      jtextfield[i] = new JTextField(10);
      jlabel[i].setLabelFor(jtextfield[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jtextfield[i]);
    }

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jtextfield.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jtextfield.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jtextfield[i]);

      consL.setX(x);
      consR.setX(rX);

      rWidth = consR.getWidth(); 
      
      consR.setWidth(Spring.sum(Spring.sum(parentWidth, negRX), Spring.constant(-5)));

      if (i == 0) {
        consL.setY(y);
        consR.setY(y);
        maxHeightSpring = Spring.sum(y, Spring.max(consL.getHeight(), consR.getHeight()));
      } 
      else {  
        Spring y1 = Spring.sum(Spring.max(lastConsL.getConstraint(SpringLayout.SOUTH), lastConsR.getConstraint(SpringLayout.SOUTH)), Spring.constant(5));

        consL.setY(y1);
        consR.setY(y1);
        maxHeightSpring = Spring.sum(Spring.constant(5), Spring.sum(maxHeightSpring, Spring.max(consL.getHeight(), consR.getHeight())));
      }
      lastConsL = consL;
      lastConsR = consR;
    }  
    
    SpringLayout.Constraints consParent = layout.getConstraints(jpanel);
    consParent.setConstraint(SpringLayout.EAST, Spring.sum(rX, Spring.sum(rWidth, Spring.constant(5))));
    consParent.setConstraint(SpringLayout.SOUTH, Spring.sum(maxHeightSpring, Spring.constant(5)));

    this.add(jpanel, BorderLayout.CENTER);

    // 設定視窗的大小
    this.setSize(250, 150);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
