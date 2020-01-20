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
    // �Y�@�~�t�Τ䴩�t�ίȧX�\��
    if (SystemTray.isSupported()) {
      //  ���o�t�ίȧX�ϰ�
      java.awt.SystemTray tray = SystemTray.getSystemTray();

      // �إ߬��㦡���
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

      // �إߤl���
      Menu submenu = new Menu("Level");
      
      // �إ߮֨���涵��
      cbItem = new CheckboxMenuItem[3];

      // �w�q�֨���涵�ؤw�Q���
      cbItem[0] = new CheckboxMenuItem("Basic", true);
      cbItem[0].addItemListener(this);
      cbItem[1] = new CheckboxMenuItem("Intermediate", false);
      cbItem[1].addItemListener(this);
      cbItem[2] = new CheckboxMenuItem("Advance", false);
      cbItem[2].addItemListener(this);

      submenu.add(cbItem[0]);
      submenu.add(cbItem[1]);
      submenu.add(cbItem[2]);
      
      // �إ߬��㦡���
      pop.add(mnuCut);
      pop.add(mnuCopy);
      pop.add(mnuPaste);
      pop.add("-");
      // �s�W�l���ܿ�椤
      pop.add(submenu);
      pop.add("-");
      pop.add(mnuAbout);
      pop.add("-");
      pop.add(mnuExit);

      Image image = Toolkit.getDefaultToolkit().getImage("images/dukeswing.gif");
      
      // �إ߯ȧX�ϥ�
      trayIcon = new TrayIcon(image, "Tray Demo", pop);
      // �]�w�Ϲ��O�_���۰ʽվ�j�p
      trayIcon.setImageAutoSize(true);
      
      // �إ߯ȧX�ϥܪ��ʧ@Listener
      trayIcon.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });

      // �إ߯ȧX�ϥܪ��ƹ�Listener
      trayIcon.addMouseListener(new MouseListener() {
        public void mouseClicked(MouseEvent e) {
          System.out.println("���U������ƹ�����");                 
        }
        public void mouseEntered(MouseEvent e) {
          System.out.println("�ƹ����ܯȧX�ϥܤW��");                 
        }
        public void mouseExited(MouseEvent e) {
          System.out.println("�ƹ����}�ȧX�ϥ�");                 
        }
        public void mousePressed(MouseEvent e) {
          System.out.println("���U�ƹ�����");                 
        }
        public void mouseReleased(MouseEvent e) {
          System.out.println("����ƹ�����");                 
        }
      });
      
      try {
        // �NTrayIcon����[�JSystemTray���󤧤�
        tray.add(trayIcon);
      } 
      catch (AWTException ex) {
        ex.printStackTrace();
      }

      // ��ܯȧX�ϥܰT����      
      trayIcon.displayMessage("TrayIcon - Caption", "TrayIcon - Text", java.awt.TrayIcon.MessageType.INFO);
    } 
    else {
      System.out.println("�@�~�t�Τ��䴩�t�ίȧX�\��");
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
