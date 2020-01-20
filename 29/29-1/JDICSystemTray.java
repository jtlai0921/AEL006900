import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

// JDIC
import org.jdesktop.jdic.tray.*;

public class JDICSystemTray {

  // TrayIcon
  org.jdesktop.jdic.tray.TrayIcon trayIcon;
  
  // 建立突顯式選單
  JPopupMenu jpopupmenu = new JPopupMenu();

  JCheckBoxMenuItem jcbmenuitem;
  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[4];

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } 
    catch (Exception e) {
      e.printStackTrace();
    }

    new JDICSystemTray();
  }

  // 
  public JDICSystemTray() {
    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader(); 

    // 建立選單項目並使用選單快速鍵
    JMenuItem jmnuNew = new JMenuItem("New", KeyEvent.VK_N);
    jmnuNew.setIcon(new ImageIcon(cl.getResource("images/new.gif")));
    KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
    jmnuNew.setAccelerator(keystroke);
    jmnuNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    JMenuItem jmnuOpen = new JMenuItem("Open", new ImageIcon(cl.getResource("images/open.gif")));
    jmnuOpen.setMnemonic(KeyEvent.VK_O);
    jmnuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    jmnuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    JMenuItem jmnuSave = new JMenuItem("Save", new ImageIcon(cl.getResource("images/save.gif")));
    jmnuSave.setMnemonic('S');
    jmnuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    jmnuSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    JMenuItem jmnuSaveAs = new JMenuItem("Save As...", new ImageIcon(cl.getResource("images/saveas.gif")));
    jmnuSaveAs.setMnemonic('A');
    jmnuSaveAs.setDisplayedMnemonicIndex(5);
    jmnuSaveAs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // 建立子選單
    JMenu submenu1 = new JMenu("Message Type");
    submenu1.setMnemonic(KeyEvent.VK_T);

    // 建構群組
    ButtonGroup group = new ButtonGroup();

    jrbmenuitem[0] = new JRadioButtonMenuItem("Information");
    jrbmenuitem[0].setSelected(true);
    jrbmenuitem[0].setMnemonic(KeyEvent.VK_I);
    jrbmenuitem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
    jrbmenuitem[0].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbmenu_actionPerformed(e);
      }
    });

    jrbmenuitem[1] = new JRadioButtonMenuItem("Error");
    jrbmenuitem[1].setMnemonic(KeyEvent.VK_E);
    jrbmenuitem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
    jrbmenuitem[1].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbmenu_actionPerformed(e);
      }
    });

    jrbmenuitem[2] = new JRadioButtonMenuItem("Warning");
    jrbmenuitem[2].setMnemonic(KeyEvent.VK_W);
    jrbmenuitem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
    jrbmenuitem[2].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbmenu_actionPerformed(e);
      }
    });

    jrbmenuitem[3] = new JRadioButtonMenuItem("None");
    jrbmenuitem[3].setMnemonic(KeyEvent.VK_N);
    jrbmenuitem[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
    jrbmenuitem[3].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbmenu_actionPerformed(e);
      }
    });

    group.add(jrbmenuitem[0]);
    group.add(jrbmenuitem[1]);
    group.add(jrbmenuitem[2]);
    group.add(jrbmenuitem[3]);
    submenu1.add(jrbmenuitem[0]);
    submenu1.add(jrbmenuitem[1]);
    submenu1.add(jrbmenuitem[2]);
    submenu1.add(jrbmenuitem[3]);

    jcbmenuitem = new JCheckBoxMenuItem("Show Popup Message");
    jcbmenuitem.setMnemonic(KeyEvent.VK_M);
    jcbmenuitem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (jcbmenuitem.isSelected()) {
          int type = 0;
          
          if (jrbmenuitem[0].isSelected())
            type = org.jdesktop.jdic.tray.TrayIcon.INFO_MESSAGE_TYPE;
          else if (jrbmenuitem[1].isSelected())
            type = org.jdesktop.jdic.tray.TrayIcon.ERROR_MESSAGE_TYPE;
          else if (jrbmenuitem[2].isSelected())
            type = org.jdesktop.jdic.tray.TrayIcon.WARNING_MESSAGE_TYPE;
          else if (jrbmenuitem[3].isSelected())
            type = org.jdesktop.jdic.tray.TrayIcon.NONE_MESSAGE_TYPE;
          
          // 顯示紙匣圖示訊息框
          trayIcon.displayMessage("TrayIcon - Caption", "TrayIcon - Text", type);
        }
      }
    });

    JMenuItem jmnuExit = new JMenuItem("Exit");
    jmnuExit.setMnemonic('X');
    jmnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmnuExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });

    // 新增突顯式選單項目
    jpopupmenu.add(jmnuNew);
    jpopupmenu.add(jmnuOpen);
    // 新增分隔線
    jpopupmenu.addSeparator();
    jpopupmenu.add(jmnuSave);
    jpopupmenu.add(jmnuSaveAs);
    jpopupmenu.addSeparator();
    jpopupmenu.add(submenu1);
    jpopupmenu.add(jcbmenuitem);
    jpopupmenu.addSeparator();
    jpopupmenu.add(jmnuExit);

    // 建立TrayIcon物件
    trayIcon = new org.jdesktop.jdic.tray.TrayIcon(new ImageIcon(cl.getResource(("images/dukeswing.gif"))));

    // 設定紙匣圖示之標題
    trayIcon.setCaption("JDIC System Tray Icon");
    // 設定紙匣圖示之突顯式選單
    trayIcon.setPopupMenu(jpopupmenu);
    // 設定圖像是否為自動調整大小
    trayIcon.setIconAutoSize(true);
    // 設定紙匣圖示的提示說明
    trayIcon.setToolTip("JDIC System Tray Icon");
    
    trayIcon.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "JDIC System Tray Icon - Mouse Click", "JDIC API", JOptionPane.INFORMATION_MESSAGE);
      }
    });

//    trayIcon.addBalloonActionListener(new ActionListener(){ 
//      public void actionPerformed(ActionEvent e) {
//      }
//    });
    
    // 取得作業系統之系統紙匣區域
    org.jdesktop.jdic.tray.SystemTray systemTray = org.jdesktop.jdic.tray.SystemTray.getDefaultSystemTray();

    // 將紙匣圖像加入系統紙匣區域之中
    systemTray.addTrayIcon(trayIcon);
  }

  private void menu_actionPerformed(ActionEvent e) {
    String command = e.getActionCommand().toString();
    
    JOptionPane.showMessageDialog(null, "JDIC System Tray Icon - " + command, "JDIC API", JOptionPane.INFORMATION_MESSAGE);
  }

  private void cbmenu_actionPerformed(ActionEvent e) {
    if (jcbmenuitem.isSelected()) {
      int type = 0;

      if (e.getActionCommand().equals("Information"))
        type = org.jdesktop.jdic.tray.TrayIcon.INFO_MESSAGE_TYPE;
      else if (e.getActionCommand().equals("Error"))
        type = org.jdesktop.jdic.tray.TrayIcon.ERROR_MESSAGE_TYPE;
      else if (e.getActionCommand().equals("Warning"))
        type = org.jdesktop.jdic.tray.TrayIcon.WARNING_MESSAGE_TYPE;
      else if (e.getActionCommand().equals("None"))
        type = org.jdesktop.jdic.tray.TrayIcon.NONE_MESSAGE_TYPE;
      
      // 顯示紙匣圖示訊息框
      trayIcon.displayMessage("TrayIcon - Caption", "TrayIcon - Text", type);
    }
  }
}
