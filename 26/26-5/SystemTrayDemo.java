import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

public class SystemTrayDemo {
  java.awt.TrayIcon trayIcon;

  // �إ߬��㦡���
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
    catch(Exception e) {
      e.printStackTrace();
    }

    new SystemTrayDemo();
  }
    
  public SystemTrayDemo() {
    // �Y�@�~�t�Τ䴩�t�ίȧX�\�� 
    if (SystemTray.isSupported()) {
      //  ���o�t�ίȧX�ϰ�
      java.awt.SystemTray tray = SystemTray.getSystemTray();

      // Get current classloader
      ClassLoader cl = this.getClass().getClassLoader(); 
      
      // �إ߿�涵�بèϥο��ֳt��
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
  
      JMenuItem jmnuSaveAs = new JMenuItem("Save As...");
      jmnuSaveAs.setMnemonic('A');
      jmnuSaveAs.setDisplayedMnemonicIndex(5);
      jmnuSaveAs.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
  
      // �إߤl���
      JMenu submenu1 = new JMenu("Message Type");
      submenu1.setMnemonic(KeyEvent.VK_T);
  
      // �غc�s��
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
            TrayIcon.MessageType type = null;
            
            if (jrbmenuitem[0].isSelected())
              type = java.awt.TrayIcon.MessageType.INFO;
            else if (jrbmenuitem[1].isSelected())
              type = java.awt.TrayIcon.MessageType.ERROR;
            else if (jrbmenuitem[2].isSelected())
              type = java.awt.TrayIcon.MessageType.WARNING;
            else if (jrbmenuitem[3].isSelected())
              type = java.awt.TrayIcon.MessageType.NONE;
            
            // ��ܯȧX�ϥܰT����      
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
  
      // �s�W���㦡��涵��
      jpopupmenu.add(jmnuNew);
      jpopupmenu.add(jmnuOpen);
      // �s�W���j�u
      jpopupmenu.addSeparator();
      jpopupmenu.add(jmnuSave);
      jpopupmenu.add(jmnuSaveAs);
      jpopupmenu.addSeparator();
      jpopupmenu.add(submenu1);
      jpopupmenu.add(jcbmenuitem);
      jpopupmenu.addSeparator();
      jpopupmenu.add(jmnuExit);

      Image image = Toolkit.getDefaultToolkit().getImage("images/dukeswing.gif");
      
      // �إ߯ȧX�ϥܡA�����n�]�w���㦡���
      trayIcon = new TrayIcon(image, "Tray Demo");
      // �]�w�Ϲ��O�_���۰ʽվ�j�p
      trayIcon.setImageAutoSize(true);
      
      // �إ߯ȧX�ϥܪ��ʧ@Listener
      trayIcon.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });

      // �إ߯ȧX�ϥܪ��ƹ�Listener
      trayIcon.addMouseListener(new MouseAdapter() {
        // �ݫ��U�ƹ�����A�h���JPopupMenu���㦡���
        public void mousePressed(MouseEvent e) {
          if (e.isPopupTrigger()) {
            jpopupmenu.setLocation(e.getX(), e.getY());
            jpopupmenu.setInvoker(jpopupmenu);
            jpopupmenu.setVisible(true);
          }
        }
        // ������ƹ�����A�h���JPopupMenu���㦡���
        public void mouseReleased(MouseEvent e) {
          if (e.isPopupTrigger()) {
            jpopupmenu.setLocation(e.getX(), e.getY());
            jpopupmenu.setInvoker(jpopupmenu);
            jpopupmenu.setVisible(true);
          }
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
      trayIcon.displayMessage("TrayIcon - Caption", "TrayIcon - Text", TrayIcon.MessageType.INFO);
    } 
    else {
      System.out.println("�@�~�t�Τ��䴩�t�ίȧX�\��");
    }
  }
  private void menu_actionPerformed(ActionEvent e) {
    String command = e.getActionCommand().toString();
    
    JOptionPane.showMessageDialog(null, "J2SE 6.0 System Tray Icon - " + command, "J2SE 6.0 API", JOptionPane.INFORMATION_MESSAGE);
  }

  private void cbmenu_actionPerformed(ActionEvent e) {
    if (jcbmenuitem.isSelected()) {
      TrayIcon.MessageType type = null;

      if (e.getActionCommand().equals("Information"))
        type = java.awt.TrayIcon.MessageType.INFO;
      else if (e.getActionCommand().equals("Error"))
        type = java.awt.TrayIcon.MessageType.ERROR;
      else if (e.getActionCommand().equals("Warning"))
        type = java.awt.TrayIcon.MessageType.WARNING;
      else if (e.getActionCommand().equals("None"))
        type = java.awt.TrayIcon.MessageType.NONE;
      
      // ��ܯȧX�ϥܰT����      
      trayIcon.displayMessage("TrayIcon - Caption", "TrayIcon - Text", type);
    }
  }
}
