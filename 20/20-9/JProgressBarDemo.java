import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.beans.*;
import java.util.*;

public class JProgressBarDemo extends JFrame implements ActionListener, PropertyChangeListener {

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
			// 設定進度列是否以Indeterminate模式顯示
			jprogressbar.setIndeterminate(true);

      // 設定是否啟用物件
      jbutton.setEnabled(false);

      // 設定為等候（忙碌中）滑鼠游標
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      Task task = new Task();
      // 註冊PropertyChangeListener介面
      task.addPropertyChangeListener(this);
      task.execute();
     }
  }

	// 當物件的屬性狀態改變時
  public void propertyChange(PropertyChangeEvent e) {
    if ("progress" == e.getPropertyName()) {
    	// 取得改變的屬性值名稱
      int progress = (Integer) e.getNewValue();
      // 設定進度列是否以Indeterminate模式顯示
      jprogressbar.setIndeterminate(false);

      jlabel.setText("進度: " + progress + "/100 (" + jprogressbar.getPercentComplete() + ")");

      Rectangle rect = jlabel.getBounds();
      rect.x = 0;
      rect.y = 0;
      jlabel.paintImmediately(rect);

      // 設定進度列的目前值
      jprogressbar.setValue(progress);
      rect = jprogressbar.getBounds();
      rect.x = 0;
      rect.y = 0;
      jprogressbar.paintImmediately(rect);
    }
  }

	// 繼承SwingWorker抽象類別
  class Task extends SwingWorker<Void, Void> {

		// 覆寫doInBackground方法
		// 計算結果
    @Override
    public Void doInBackground() {
      Random random = new Random();

      int progress = 0;

      setProgress(0);

      try {
        Thread.sleep(500 + random.nextInt(1000));
      } 
      catch (InterruptedException ignore) {}
      
      while (progress < 100) {
        try {
          Thread.sleep(random.nextInt(500));
        } 
        catch (InterruptedException ignore) {}

        progress += 1;

        setProgress(Math.min(progress, 100));
      }
      return null;
    }

		// 覆寫done方法
		// doInBackground方法完成後執行此方法
    public void done() {
      // 設定為預設滑鼠游標，通常為箭號游標
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

      jbutton.setEnabled(true);
    }
  }
}
