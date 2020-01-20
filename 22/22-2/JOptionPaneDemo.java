import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

public class JOptionPaneDemo extends JFrame implements ActionListener {
  
  JOptionPane joptionpane = new JOptionPane();

  JLabel jlabel = new JLabel();

  String menulabel[]={"Type|T"};

  String menuitemlabel[][]={
    {"Confirm Dialog", "Input Dialog", "Message Dialog", "Option Dialog", "-", "Exit"}
  };

  String submenuitemlabel[]={"Error", "Information", "Plain", "Question", "Warning"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[1][6];
  JRadioButtonMenuItem jrbmenuitem[][] = new JRadioButtonMenuItem[4][5];

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

    new JOptionPaneDemo();
  }

  // 建構函式
  public JOptionPaneDemo() {
    super("JOptionPane Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    this.add(jlabel, BorderLayout.SOUTH);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(300, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  private JMenuBar createMenuBar() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menulabel.length];
    
    // 建立選單
    for (int i=0; i<menulabel.length; i++){
      // 建立選單
      if (menulabel[i].indexOf("|") != -1)
        jmenu[i] = new JMenu(menulabel[i].substring(0, menulabel[i].indexOf("|")));
      else
        jmenu[i] = new JMenu(menulabel[i]);

      // 設定選單助記碼
      jmenu[i].setMnemonic(menulabel[i].split("\\|")[1].charAt(0));

      if (i != menulabel.length)
        // 加入選單至選單列
        jmenubar.add(jmenu[i]);
      else
        // 設定選單列中的輔助說明選單
        jmenubar.setHelpMenu(jmenu[i]);
    }

    ButtonGroup menugroup[] = new ButtonGroup[6];

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        
        // 建構群組  
        menugroup[j] = new ButtonGroup();
        
        if ((i==0 && j==0) || (i==0 && j==1) || (i==0 && j==2) || (i==0 && j==3)) { // 子選單
          // 建立子選單
          JMenu submenu = new JMenu(menuitemlabel[i][j]);

          // 建立選單項目
          for (int k=0; k<submenuitemlabel.length; k++){
            jrbmenuitem[j][k] = new JRadioButtonMenuItem(submenuitemlabel[k]);

            // 註冊 ActionListener
            jrbmenuitem[j][k].addActionListener(this);

            // 設定選項按鈕選單項目之選取狀態
            if (k==0) 
              jrbmenuitem[j][k].setSelected(true);
      
            // 加入選單項目至子選單
            submenu.add(jrbmenuitem[j][k]);
            menugroup[j].add(jrbmenuitem[j][k]);
          }
      
          jmenu[i].add(submenu);
        }
        else if (menuitemlabel[i][j].equals("-")) {
          // 加入分隔線
          jmenu[i].addSeparator();
          // 或
          // jmenu[i].add(new JSeparator());
        }
        else {
          // 建立選單項目
          jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

          // 註冊 ActionListener
          jmenuitem[i][j].addActionListener(this);

          // 加入選單項目
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    // 自訂按鈕 (測試用)
    String option[] = {"Yes", "Well...", "No Way", "Cancel"};

    // 
    // 確認對話框 
    // 
    if (e.getSource().equals(jrbmenuitem[0][0])) { // 錯誤訊息 
      int result = joptionpane.showConfirmDialog(
        null, "確認對話框顯示錯誤訊息", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        
      jlabel.setText("確認對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][1])) { // 一般訊息 
      int result = joptionpane.showConfirmDialog(
        null, "確認對話框顯示一般訊息", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
      jlabel.setText("確認對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][2])) { // 純文字訊息
      int result = joptionpane.showConfirmDialog(
        null, "確認對話框顯示純文字訊息", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
      jlabel.setText("確認對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][3])) { // 詢問訊息
      int result = joptionpane.showConfirmDialog(
        null, "確認對話框顯示詢問訊息", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
      jlabel.setText("確認對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[0][4])) { // 警告訊息
      int result = joptionpane.showConfirmDialog(
        null, "確認對話框顯示警告訊息", "Confirm Dialog", 
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
      jlabel.setText("確認對話框回傳值: " + result);
    }
    // 
    // 輸入對話框 
    // 
    else if (e.getSource().equals(jrbmenuitem[1][0])) { // 錯誤訊息 
      String result = joptionpane.showInputDialog(
        null, "輸入對話框顯示錯誤訊息", "Input Dialog", 
        JOptionPane.ERROR_MESSAGE);
        
      jlabel.setText("輸入對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][1])) { // 一般訊息 
      String result = joptionpane.showInputDialog(
        null, "輸入對話框顯示一般訊息", "Input Dialog", 
        JOptionPane.INFORMATION_MESSAGE);
        
      jlabel.setText("輸入對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][2])) { // 純文字訊息
      String result = joptionpane.showInputDialog(
        null, "輸入對話框顯示純文字訊息", "Input Dialog", 
        JOptionPane.PLAIN_MESSAGE);
        
      jlabel.setText("輸入對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][3])) { // 詢問訊息
      String result = joptionpane.showInputDialog(
        null, "輸入對話框顯示詢問訊息", "Input Dialog", 
        JOptionPane.QUESTION_MESSAGE);
        
      jlabel.setText("輸入對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[1][4])) { // 警告訊息
      String result = joptionpane.showInputDialog(
        null, "輸入對話框顯示警告訊息", "Input Dialog", 
        JOptionPane.WARNING_MESSAGE);
        
      jlabel.setText("輸入對話框回傳值: " + result);
    }
    // 
    // 訊息對話框 
    // 
    else if (e.getSource().equals(jrbmenuitem[2][0])) { // 錯誤訊息 
      joptionpane.showMessageDialog(
        null, "訊息對話框顯示錯誤訊息", "Message Dialog", 
        JOptionPane.ERROR_MESSAGE);
        
      jlabel.setText("訊息對話框無回傳值");
    }
    else if (e.getSource().equals(jrbmenuitem[2][1])) { // 一般訊息 
      joptionpane.showMessageDialog(
        null, "訊息對話框顯示一般訊息", "Message Dialog", 
        JOptionPane.INFORMATION_MESSAGE);
        
      jlabel.setText("訊息對話框無回傳值");
    }
    else if (e.getSource().equals(jrbmenuitem[2][2])) { // 純文字訊息
      joptionpane.showMessageDialog(
        null, "訊息對話框顯示純文字訊息", "Message Dialog", 
        JOptionPane.PLAIN_MESSAGE);
        
      jlabel.setText("訊息對話框無回傳值");
    }
    else if (e.getSource().equals(jrbmenuitem[2][3])) { // 詢問訊息
      joptionpane.showMessageDialog(
        null, "訊息對話框顯示詢問訊息", "Message Dialog", 
        JOptionPane.QUESTION_MESSAGE);
        
      jlabel.setText("訊息對話框無回傳值");
    }
    else if (e.getSource().equals(jrbmenuitem[2][4])) { // 警告訊息
      joptionpane.showMessageDialog(
        null, "訊息對話框顯示警告訊息", "Message Dialog", 
        JOptionPane.WARNING_MESSAGE);
        
      jlabel.setText("訊息對話框無回傳值");
    }
    // 
    // 選項對話框 
    // 
    else if (e.getSource().equals(jrbmenuitem[3][0])) { // 錯誤訊息 
      int result = joptionpane.showOptionDialog(
        null, "選項對話框顯示錯誤訊息", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, 
        null, option, option[0]);
        
      jlabel.setText("選項對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][1])) { // 一般訊息 
      int result = joptionpane.showOptionDialog(
        null, "選項對話框顯示一般訊息", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
        null, option, option[1]);
        
      jlabel.setText("選項對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][2])) { // 純文字訊息
      int result = joptionpane.showOptionDialog(
        null, "選項對話框顯示純文字訊息", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
        null, option, option[2]);
        
      jlabel.setText("選項對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][3])) { // 詢問訊息
      int result = joptionpane.showOptionDialog(
        null, "選項對話框顯示詢問訊息", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
        null, option, option[3]);
        
      jlabel.setText("選項對話框回傳值: " + result);
    }
    else if (e.getSource().equals(jrbmenuitem[3][4])) { // 警告訊息
      int result = joptionpane.showOptionDialog(
        null, "選項對話框顯示警告訊息", "Option Dialog", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
        null, option, option[0]);
        
      jlabel.setText("選項對話框回傳值: " + result);
    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      System.exit(0);
    }
  }
}
