import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JSpinnerDemo extends javax.swing.JFrame {

  JLabel[] jlabel = new JLabel[4];
  JSpinner[] jspinner = new JSpinner[4];

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

    new JSpinnerDemo();
  }

  // 建構函式
  public JSpinnerDemo() {
    super("JSpinner Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager為BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    // 定義JPanel之Layout Manager為SpringLayout
    jpanel.setLayout(new SpringLayout());

    // 取得JPanel之Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    /*   
     *  Red
     */
    // 以MVC Model建立Spinner物件
    jspinner[0] = new JSpinner(new RedModel(255));
    // 設定JSpinner之編輯物件
    jspinner[0].setEditor(new RedEditor(jspinner[0]));
    
    jlabel[0] = new JLabel("Red:");
    jlabel[0].setLabelFor(jspinner[0]);
    jpanel.add(jlabel[0]);
    jpanel.add(jspinner[0]);
    
    /*   
     *  Green
     */
    // 以MVC Model建立Spinner物件
    jspinner[1] = new JSpinner(new GreenModel(255));
    // 設定JSpinner之編輯物件
    jspinner[1].setEditor(new GreenEditor(jspinner[1]));
    
    jlabel[1] = new JLabel("Green:");
    jlabel[1].setLabelFor(jspinner[1]);
    jpanel.add(jlabel[1]);
    jpanel.add(jspinner[1]);
    
    /*   
     *  Blue
     */
    // 以MVC Model建立Spinner物件
    jspinner[2] = new JSpinner(new BlueModel(255));
    // 設定JSpinner之編輯物件
    jspinner[2].setEditor(new BlueEditor(jspinner[2]));
    
    jlabel[2] = new JLabel("Blue:");
    jlabel[2].setLabelFor(jspinner[2]);
    jpanel.add(jlabel[2]);
    jpanel.add(jspinner[2]);
    
    /*   
     *  Color
     */
    // 以MVC Model建立Spinner物件
    jspinner[3] = new JSpinner(new SpinnerColorModel(255));
    // 設定JSpinner之編輯物件
    jspinner[3].setEditor(new ColorEditor(jspinner[3]));
    
    jlabel[3] = new JLabel("Color:");
    jlabel[3].setLabelFor(jspinner[3]);
    jpanel.add(jlabel[3]);
    jpanel.add(jspinner[3]);
    
    /*   
     *  配置物件
     */
    Spring x = Spring.constant(10);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jspinner.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(10)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jspinner.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jspinner[i]);

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
