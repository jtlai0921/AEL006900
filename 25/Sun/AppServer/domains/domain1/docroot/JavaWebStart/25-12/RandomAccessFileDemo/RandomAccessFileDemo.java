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

public class RandomAccessFileDemo extends javax.swing.JFrame {

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[3];
  JTextArea txtContent = new JTextArea();
  
  // JNLP Service
  private FileOpenService fos = null;
  private FileContents fc = null;
  private JNLPRandomAccessFile raf = null; 
  
  private ClassLoader cl;    
  private String item[]  = {"Write", "Read", "About"};
  private String image[] = {"write", "read", "about"};
   
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

    new RandomAccessFileDemo();
  }

  // �غc�禡
  public RandomAccessFileDemo() {
    super("JNLPRandomAccessFile API");

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
    // Write
    JMenuItem jmnuWrite = new JMenuItem(item[0], new ImageIcon(cl.getResource("images/" + image[0] + ".gif")));
    jmnuWrite.setMnemonic(KeyEvent.VK_W);
    jmnuWrite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuWrite);
    jmnuWrite.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Read
    JMenuItem jmnuRead = new JMenuItem(item[1], new ImageIcon(cl.getResource("images/" + image[1] + ".gif")));
    jmnuRead.setMnemonic(KeyEvent.VK_R);
    jmnuRead.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuRead);
    jmnuRead.addActionListener(new ActionListener() {
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
  
  // write to a random access file
  private void writeRandomAccess() {
    // Line Separator
    String linesep = System.getProperty("line.separator");
    
    if (fos == null) {
      try {
        // �d��FileOpenService�A��
        fos = (FileOpenService)ServiceManager.lookup("javax.jnlp.FileOpenService");
      } 
      catch(UnavailableServiceException e) {
        // �L�k�d�ߨ���w�A�ȡA�^��UnavailableServiceException
        fos = null;
      }
    }

    if (fos != null) {
      try {
        fc = fos.openFileDialog(null, null);
  
        if (fc != null) {
          txtContent.append("����ɮ�: " + fc.getName() + linesep);
    
          // ���o�ɮת���
          long grantedLength = fc.getLength(); 
          
          // �P�_�ɮפ��̤j����
          if (grantedLength + 1024 > fc.getMaxLength()) { 
            // �]�w�ɮ׳̤j���� 
            grantedLength = fc.setMaxLength(grantedLength + 1024); 
          }
          
          // �ˬd�ɮ׬O�_�i�мg
          if (fc.canWrite()) { 
            // �H���s���ɮסA�]�w�ɮ��ݩʬ��i�мg�]rw�^ 
            raf = fc.getRandomAccessFile("rw"); 
            txtContent.append("�]�w�H���s���ɮ��ݩʬ��i�мg" + linesep);
            
            // �N�ɮ׫��в����ɮװ_�l��m
            raf.seek(0);
            txtContent.append("�N�ɮ׫��в����ɮװ_�l��m" + linesep);
            
            // ���ɮװ_�l��m�g�J���e
            raf.writeUTF("Java Web Start JNLPRandomAccessFile API");
            txtContent.append("���ɮװ_�l��m�g�J���e" + linesep);
             
            // �N�ɮ׫��в����ɮ׵�����m
            raf.seek(raf.length()-1);
            txtContent.append("�N�ɮ׫��в����ɮ׵�����m" + linesep);
            
            // ���ɮ��ɮ׵����g�J���e
            raf.writeUTF("The end of file");
            txtContent.append("���ɮ��ɮ׵����g�J���e" + linesep);
  
            // �����H���s���ɮצ�y 
            raf.close(); 
            txtContent.append("�����H���s���ɮצ�y" + linesep);
          }
        }
      } 
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  // read from a random access file
  private void readRandomAccess() {
    // Line Separator
    String linesep = System.getProperty("line.separator");
    
    if (fos == null) {
      try {
        // �d��FileOpenService�A��
        fos = (FileOpenService)ServiceManager.lookup("javax.jnlp.FileOpenService");
      } 
      catch(UnavailableServiceException e) {
        // �L�k�d�ߨ���w�A�ȡA�^��UnavailableServiceException
        fos = null;
      }
    }

    if (fos != null) {
      try {
        fc = fos.openFileDialog(null, null);
  
        if (fc != null) {
          txtContent.append("����ɮ�: " + fc.getName() + linesep);
    
          // �ˬd�ɮ׬O�_�iŪ��
          if (fc.canRead()) { 
            // �H���s���ɮסA�]�w�ɮ��ݩʬ���Ū�]r�^ 
            raf = fc.getRandomAccessFile("r"); 
            txtContent.append("�]�w�H���s���ɮ��ݩʬ���Ū" + linesep);

            // �N�ɮ׫��в����ɮװ_�l��m
            raf.seek(0);
            txtContent.append("�N�ɮ׫��в����ɮװ_�l��m" + linesep);
    
            // ���ɮװ_�l��mŪ�X���e
            txtContent.append("���ɮװ_�l��mŪ�X���e" + linesep);
    
            String str = raf.readUTF();
            
            while (str != null){ 
              txtContent.append(str + linesep);
              str = raf.readLine();
            }
  
            // �����H���s���ɮצ�y 
            raf.close(); 
            txtContent.append("�����H���s���ɮצ�y" + linesep);
          } 
        } 
      } 
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(item[0])) { // write
      writeRandomAccess();
    }
    else if (e.getActionCommand().equals(item[1])) { // read
      readRandomAccess();
    }
    else if (e.getActionCommand().equals(item[2])) { // about
      JOptionPane.showMessageDialog(null, "JNLPRandomAccessFile API", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // write
      writeRandomAccess();
    }
    else if (e.getSource().equals(jbutton[1])) { // read
      readRandomAccess();
    }
    else if (e.getSource().equals(jbutton[2])) { // about
      JOptionPane.showMessageDialog(null, "JNLPRandomAccessFile API", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
