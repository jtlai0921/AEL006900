import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

import java.io.*;
import java.net.*;
import java.util.*;

// JNLP API
import javax.jnlp.*;

public class ExtendedServiceDemo extends javax.swing.JFrame {

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[3];
  JTextArea txtContent = new JTextArea();
  
  // JNLP Service
  private ExtendedService es = null;
  private FileContents fc = null;
  
  private ClassLoader cl;    
  private String item[]  = {"New", "Open", "About"};
  private String image[] = {"new", "open", "about"};
   
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

    new ExtendedServiceDemo();
  }

  // �غc�禡
  public ExtendedServiceDemo() {
    super("JNLP ExtendedService");

    // Get current classloader
    cl = this.getClass().getClassLoader(); 

    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // �إ߿��C
    JMenuBar jmenubar = new JMenuBar();
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // �إ߿��
    JMenu jmenu1 = new JMenu("File");
    jmenu1.setMnemonic('F');
    jmenubar.add(jmenu1);

    // �إ߿�涵�بèϥο��ֳt��
    // New
    JMenuItem jmnuNew = new JMenuItem(item[0], KeyEvent.VK_N);
    jmnuNew.setIcon(new ImageIcon(cl.getResource("images/" + image[0] + ".gif")));
    KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
    jmnuNew.setAccelerator(keystroke);
    jmenu1.add(jmnuNew);
    jmnuNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Open
    JMenuItem jmnuOpen = new JMenuItem(item[1], new ImageIcon(cl.getResource("images/" + image[1] + ".gif")));
    jmnuOpen.setMnemonic(KeyEvent.VK_O);
    jmnuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuOpen);
    jmnuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // �s�W���j�u
    jmenu1.addSeparator();

    // Exit
    JMenuItem jmnuExit = new JMenuItem("Exit");
    jmnuExit.setMnemonic('X');
    jmnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuExit);
    jmnuExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          dispose();
          System.exit(0);
        }
      }
    });

    // �إ߿��
    // Help
    JMenu jmenu2 = new JMenu("Help");
    jmenu2.setMnemonic('H');
    jmenubar.add(jmenu2);

    // About
    JMenuItem jmnuAbout = new JMenuItem(item[2], new ImageIcon(cl.getResource("images/" + image[2] + ".gif")));
    jmnuAbout.setMnemonic('A');
    jmenu2.add(jmnuAbout);
    jmnuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);
      
      // ���U ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);
      
      if (i==1)
        jtoolbar.addSeparator();
    }

    // JTextArea    
    // �]�w�۰ʴ��檺�W�h
    txtContent.setWrapStyleWord(false);
    // �]�wJTextArea���r�W�L��e�ɡA�O�_�۰ʴ���C
    txtContent.setLineWrap(false);
    txtContent.setEditable(true);
    txtContent.setTabSize(2);
    txtContent.setFont(new Font("�s�ө���", Font.PLAIN, 12));
    
    JScrollPane jscrollpane = new JScrollPane(txtContent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    contentPane.add(jtoolbar,    BorderLayout.NORTH);
    contentPane.add(jscrollpane, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(300, 300));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, 
      (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private String open() {
    // Line Separator
    String linesep = System.getProperty("line.separator");
    String str = null;
    
    if (es == null) {
      try {
        // �d��ExtendedService�A��
        es = (ExtendedService)ServiceManager.lookup("javax.jnlp.ExtendedService");
      } 
      catch(UnavailableServiceException e) {
        // �L�k�d�ߨ���w�A�ȡA�^��UnavailableServiceException
        es = null;
      }
    }

    if (es != null) {
      try {
        String filename = JOptionPane.showInputDialog(null, "Please input the file name:", "Open Dialog", JOptionPane.QUESTION_MESSAGE);

        if (filename != null) {        
          txtContent.append("����ɮ�: " + filename + linesep);
          
          fc = es.openFile(new File(filename));
    
          if (fc != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fc.getInputStream()));
            StringBuffer sb = new StringBuffer((int)fc.getLength());
            String line = br.readLine();
        
            while(line != null) {
              sb.append(line + linesep);
              line = br.readLine();
            }
            br.close();
    
            str = sb.toString();
          }
        }
      } 
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }

    return str;
  }

  private void menu_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(item[0])) { // new
      txtContent.setText("");
      fc = null;
    }
    else if (e.getActionCommand().equals(item[1])) { // open
      txtContent.setText("");

      String str = open();

      if (str != null) 
        txtContent.append(str);
    }
    else if (e.getActionCommand().equals(item[2])) { // about
      JOptionPane.showMessageDialog(null, "JNLP ExtendedService", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // new
      txtContent.setText("");
      fc = null;
    }
    else if (e.getSource().equals(jbutton[1])) { // open
      txtContent.setText("");

      String str = open();

      if (str != null) 
        txtContent.append(str);
    }
    else if (e.getSource().equals(jbutton[2])) { // about
      JOptionPane.showMessageDialog(null, "JNLP ExtendedService", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
