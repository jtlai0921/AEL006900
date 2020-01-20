import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.util.*;

public class JProgressBarDemo extends JFrame implements ActionListener {

  JProgressBar jprogressbar;
  JButton jbutton;
  JLabel jlabel;
  JPanel jpanel;
  
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JProgressBarDemo();
  }

  // 建構函式
  public JProgressBarDemo() {
    super("JProgressBar Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    jpanel = new JPanel();
    
    jlabel = new JLabel("進度:");
    jlabel.setPreferredSize(new Dimension(250, 25));
    jpanel.add(jlabel);
    
    // 建立進度列
    jprogressbar = new JProgressBar();
    // 設定進度列的最小值
    jprogressbar.setMinimum(0);
    // 設定進度列的最大值
    jprogressbar.setMaximum(100);
    // 設定進度列的目前值
    jprogressbar.setValue(0);
    // 設定進度列是否允許顯示字串
    jprogressbar.setStringPainted(true);
    jprogressbar.setBounds(20, 35, 200, 20);
    jprogressbar.setPreferredSize(new Dimension(250, 20));
    jpanel.add(jprogressbar);

    jbutton = new JButton("開始");
    jbutton.setPreferredSize(new Dimension(80, 30));
    jbutton.addActionListener(this);
    jpanel.add(jbutton);

    this.add(jpanel);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(280, 140));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == jbutton) {
      // 設定是否啟用物件
      jbutton.setEnabled(false);

      // 設定為等候（忙碌中）滑鼠游標
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      
      for(int index = 0; index <= 100; index++)  {
        // 產生隨機亂數
        Random random = new Random(index);
    
        for(int value = 0; value < random.nextFloat() * 10000; value++) {
          System.out.println("Index = " + index + ", " + "Value = " + value + ", " + jprogressbar.getPercentComplete());
        }

        // 取得進度列的完成百分比
        jlabel.setText("進度: " + index + "/100 (" + jprogressbar.getPercentComplete() + ")");
        
        Rectangle rect = jlabel.getBounds();
        rect.x = 0;
        rect.y = 0;
        jlabel.paintImmediately(rect);

        // 設定進度列的目前值
        jprogressbar.setValue(index);
        rect = jprogressbar.getBounds();
        rect.x = 0;
        rect.y = 0;
        jprogressbar.paintImmediately(rect);
      }

      // 設定為預設滑鼠游標，通常為箭號游標
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

      jbutton.setEnabled(true);
     }
  }
}
