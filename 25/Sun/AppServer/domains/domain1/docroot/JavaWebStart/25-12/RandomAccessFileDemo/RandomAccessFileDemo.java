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

  // 建構函式
  public RandomAccessFileDemo() {
    super("JNLPRandomAccessFile API");

    // Get current classloader
    cl = this.getClass().getClassLoader(); 

    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 建立選單
    JMenu jmenu1 = new JMenu("File");
    jmenu1.setMnemonic('F');
    jmenubar.add(jmenu1);

    // 建立選單項目並使用選單快速鍵
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

    // 新增分隔線
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

    // 建立選單
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
      
      // 註冊 ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);
    }

    // JTextArea    
    // 設定自動換行的規則
    txtContent.setWrapStyleWord(false);
    // 設定JTextArea當文字超過行寬時，是否自動換行。
    txtContent.setLineWrap(false);
    txtContent.setEditable(true);
    txtContent.setTabSize(2);
    txtContent.setFont(new Font("新細明體", Font.PLAIN, 12));
    
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
        // 查詢FileOpenService服務
        fos = (FileOpenService)ServiceManager.lookup("javax.jnlp.FileOpenService");
      } 
      catch(UnavailableServiceException e) {
        // 無法查詢到指定服務，回傳UnavailableServiceException
        fos = null;
      }
    }

    if (fos != null) {
      try {
        fc = fos.openFileDialog(null, null);
  
        if (fc != null) {
          txtContent.append("選取檔案: " + fc.getName() + linesep);
    
          // 取得檔案長度
          long grantedLength = fc.getLength(); 
          
          // 判斷檔案之最大長度
          if (grantedLength + 1024 > fc.getMaxLength()) { 
            // 設定檔案最大長度 
            grantedLength = fc.setMaxLength(grantedLength + 1024); 
          }
          
          // 檢查檔案是否可覆寫
          if (fc.canWrite()) { 
            // 隨機存取檔案，設定檔案屬性為可覆寫（rw） 
            raf = fc.getRandomAccessFile("rw"); 
            txtContent.append("設定隨機存取檔案屬性為可覆寫" + linesep);
            
            // 將檔案指標移至檔案起始位置
            raf.seek(0);
            txtContent.append("將檔案指標移至檔案起始位置" + linesep);
            
            // 自檔案起始位置寫入內容
            raf.writeUTF("Java Web Start JNLPRandomAccessFile API");
            txtContent.append("自檔案起始位置寫入內容" + linesep);
             
            // 將檔案指標移至檔案結尾位置
            raf.seek(raf.length()-1);
            txtContent.append("將檔案指標移至檔案結尾位置" + linesep);
            
            // 自檔案檔案結尾寫入內容
            raf.writeUTF("The end of file");
            txtContent.append("自檔案檔案結尾寫入內容" + linesep);
  
            // 關閉隨機存取檔案串流 
            raf.close(); 
            txtContent.append("關閉隨機存取檔案串流" + linesep);
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
        // 查詢FileOpenService服務
        fos = (FileOpenService)ServiceManager.lookup("javax.jnlp.FileOpenService");
      } 
      catch(UnavailableServiceException e) {
        // 無法查詢到指定服務，回傳UnavailableServiceException
        fos = null;
      }
    }

    if (fos != null) {
      try {
        fc = fos.openFileDialog(null, null);
  
        if (fc != null) {
          txtContent.append("選取檔案: " + fc.getName() + linesep);
    
          // 檢查檔案是否可讀取
          if (fc.canRead()) { 
            // 隨機存取檔案，設定檔案屬性為唯讀（r） 
            raf = fc.getRandomAccessFile("r"); 
            txtContent.append("設定隨機存取檔案屬性為唯讀" + linesep);

            // 將檔案指標移至檔案起始位置
            raf.seek(0);
            txtContent.append("將檔案指標移至檔案起始位置" + linesep);
    
            // 自檔案起始位置讀出內容
            txtContent.append("自檔案起始位置讀出內容" + linesep);
    
            String str = raf.readUTF();
            
            while (str != null){ 
              txtContent.append(str + linesep);
              str = raf.readLine();
            }
  
            // 關閉隨機存取檔案串流 
            raf.close(); 
            txtContent.append("關閉隨機存取檔案串流" + linesep);
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
