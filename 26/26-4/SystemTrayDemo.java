import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SystemTrayDemo implements ItemListener {
  java.awt.TrayIcon trayIcon;

  CheckboxMenuItem cbItem[];

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);

    new SystemTrayDemo();
  }
    
  public SystemTrayDemo() {
    // 若作業系統支援系統紙匣功能
    if (SystemTray.isSupported()) {
      //  取得系統紙匣區域
      java.awt.SystemTray tray = SystemTray.getSystemTray();

      // 建立突顯式選單
      PopupMenu pop = new PopupMenu();
  
      MenuItem mnuCut = new MenuItem("Cut");
      mnuCut.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
  
      MenuItem mnuCopy = new MenuItem("Copy");
      mnuCopy.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
  
      MenuItem mnuPaste = new MenuItem("Paste");
      mnuPaste.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
  
      // About
      MenuItem mnuAbout = new MenuItem("About");
      mnuAbout.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
  
      // Exit
      MenuItem mnuExit = new MenuItem("Exit");
      mnuExit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane joptionpane = new JOptionPane();
          int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          
          if (iResult == 0) {
            //dispose();
            System.exit(0);
          }
        }
      });

      // 建立子選單
      Menu submenu = new Menu("Level");
      
      // 建立核取選單項目
      cbItem = new CheckboxMenuItem[3];

      // 定義核取選單項目已被選取
      cbItem[0] = new CheckboxMenuItem("Basic", true);
      cbItem[0].addItemListener(this);
      cbItem[1] = new CheckboxMenuItem("Intermediate", false);
      cbItem[1].addItemListener(this);
      cbItem[2] = new CheckboxMenuItem("Advance", false);
      cbItem[2].addItemListener(this);

      submenu.add(cbItem[0]);
      submenu.add(cbItem[1]);
      submenu.add(cbItem[2]);
      
      // 建立突顯式選單
      pop.add(mnuCut);
      pop.add(mnuCopy);
      pop.add(mnuPaste);
      pop.add("-");
      // 新增子選單至選單中
      pop.add(submenu);
      pop.add("-");
      pop.add(mnuAbout);
      pop.add("-");
      pop.add(mnuExit);

      Image image = Toolkit.getDefaultToolkit().getImage("images/dukeswing.gif");
      
      // 建立紙匣圖示
      trayIcon = new TrayIcon(image, "Tray Demo", pop);
      // 設定圖像是否為自動調整大小
      trayIcon.setImageAutoSize(true);
      
      // 建立紙匣圖示的動作Listener
      trayIcon.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });

      // 建立紙匣圖示的滑鼠Listener
      trayIcon.addMouseListener(new MouseListener() {
        public void mouseClicked(MouseEvent e) {
          System.out.println("按下並釋放滑鼠按鍵");                 
        }
        public void mouseEntered(MouseEvent e) {
          System.out.println("滑鼠移至紙匣圖示上方");                 
        }
        public void mouseExited(MouseEvent e) {
          System.out.println("滑鼠離開紙匣圖示");                 
        }
        public void mousePressed(MouseEvent e) {
          System.out.println("按下滑鼠按鍵");                 
        }
        public void mouseReleased(MouseEvent e) {
          System.out.println("釋放滑鼠按鍵");                 
        }
      });
      
      try {
        // 將TrayIcon物件加入SystemTray物件之中
        tray.add(trayIcon);
      } 
      catch (AWTException ex) {
        ex.printStackTrace();
      }

      // 顯示紙匣圖示訊息框      
      trayIcon.displayMessage("TrayIcon - Caption", "TrayIcon - Text", java.awt.TrayIcon.MessageType.INFO);
    } 
    else {
      System.out.println("作業系統不支援系統紙匣功能");
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cut")) { // cut
      JOptionPane.showMessageDialog(null, "Click Popup Menu - Cut", "System Tray Demo", JOptionPane.INFORMATION_MESSAGE);
    } 
    else if (e.getActionCommand().equals("Copy")) { // copy
      JOptionPane.showMessageDialog(null, "Click Popup Menu - Copy", "System Tray Demo", JOptionPane.INFORMATION_MESSAGE);
    } 
    else if (e.getActionCommand().equals("Paste")) { // paste
      JOptionPane.showMessageDialog(null, "Click Popup Menu - Paste", "System Tray Demo", JOptionPane.INFORMATION_MESSAGE);
    } 
    else if (e.getActionCommand().equals("About")) { // about
      JOptionPane.showMessageDialog(null, "System Tray Demo", "About - System Tray", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public void itemStateChanged(ItemEvent e) {
    CheckboxMenuItem menuitem = (CheckboxMenuItem)(e.getItemSelectable());
    
    for (int i=0; i < 3; i++) {
      cbItem[i].setState(false);
    }
      
    for (int i=0; i < 3; i++) {
      if(menuitem == cbItem[i]) 
        cbItem[i].setState(true);
    }
  }
}
