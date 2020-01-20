import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.awt.print.*;  

import java.net.*;
import java.io.*;
import java.util.*;

// JDIC
import org.jdesktop.jdic.browser.*;

// 實作org.jdesktop.jdic.browser.WebBrowserListener
public class WebBrowser extends JFrame implements WebBrowserListener {
  
  org.jdesktop.jdic.browser.WebBrowser webBrowser;

  JTextField txtURL = new JTextField();
  JFileChooser jfilechooser = new JFileChooser();

  JLabel lblStatus = new JLabel();

  String menulabel[]={"File|F", "View|E", "Go|G", "Help|H"};

  String menuitemlabel[][]={
    {"Open|O|open.gif", "Save|S|save.gif", "Print|P|print.gif", "-", "Exit|X"},
    {"Page Source|P|source.gif"},
    {"Back|B|back.gif", "Forward|W|forward.gif", "Stop|S|stop.gif", "Refresh|R|refresh.gif"},
    {"About|A|about.gif"}
  };

  String item[]  = {"Back", "Forward", "Stop", "Refresh", "Open", "Save", "Print", "Page Source", "About"};
  String image[] = {"back", "forward", "stop", "refresh", "open", "save", "print", "source", "about"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][5];
  JButton jbutton[] = new JButton[9];
  
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

    new WebBrowser();
  }

  // 建構函式
  public WebBrowser() {
    super("JDIC Web Browser");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    this.setIconImage(new ImageIcon(cl.getResource("images/web.gif")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // AWT 與 Swing 相容
    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    
    JLabel jlabel1 = new JLabel("URL:");

    txtURL.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        txtURL_actionPerformed(e);
      }
    });

    // JFileChooser
    jfilechooser.setCurrentDirectory(new File("."));

    HtmlFileFilter[] filter = new HtmlFileFilter[3];
    
    filter[0] = new HtmlFileFilter("HTML Files", "html");
    filter[1] = new HtmlFileFilter("HTM Files" , "Htm");
    filter[2] = new HtmlFileFilter("Text Files", "txt");

    for (int i=0; i < filter.length; i++) 
      jfilechooser.addChoosableFileFilter(filter[i]);

    jfilechooser.setMultiSelectionEnabled(false);
    jfilechooser.setFileFilter(filter[0]);

    JToolBar jtoolbar = new JToolBar();

    // Toolbar
    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton();
      jbutton[i].setIcon(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setText("");
      jbutton[i].setMinimumSize(new Dimension(32, 32));
      jbutton[i].setMaximumSize(new Dimension(32, 32));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);

      // 註冊 ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      if (i==0 || i==1) {
        jbutton[i].setEnabled(false);
        jbutton[i].setDisabledIcon(new ImageIcon(cl.getResource("images/" + image[i] + "D.gif")));
      }

      jtoolbar.add(jbutton[i]);

      if (i==3 || i==6 || i==7)
        // 加入分隔線
        jtoolbar.addSeparator();
    }

    // 建立選單功能列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單功能列
    this.setJMenuBar(jmenubar);

    // JDIC WebBrowser
    webBrowser = new org.jdesktop.jdic.browser.WebBrowser();
    
    // 是否除錯
    webBrowser.setDebug(true);

    // 註冊WebBrowserListener
    webBrowser.addWebBrowserListener(this);

    showHTML("http://java.sun.com");
    
    JPanel Panel5 = new JPanel();
    Panel5.setLayout(new BorderLayout());
    Panel5.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
    Panel5.setPreferredSize(new Dimension(40, 10));
    Panel5.add(jlabel1, BorderLayout.CENTER);

    JPanel Panel4 = new JPanel();
    Panel4.setLayout(new BorderLayout());
    Panel4.setBorder(BorderFactory.createEmptyBorder(2,0,2,5));
    Panel4.add(txtURL, BorderLayout.CENTER);

    JPanel Panel3 = new JPanel();
    Panel3.setPreferredSize(new Dimension(10, 40));
    Panel3.setLayout(new BorderLayout());
    Panel3.add(Panel5, BorderLayout.WEST);
    Panel3.add(Panel4, BorderLayout.CENTER);
    Panel3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(2, 0, 2, 0)));

    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BorderLayout());
    Panel2.add(Panel3, BorderLayout.CENTER);
    Panel2.add(jtoolbar, BorderLayout.NORTH);
    Panel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    // 0.9.5的使用方式
    Panel1.add(webBrowser, BorderLayout.CENTER);        
    // jdic-20061102的使用方式
    //Panel1.add(webBrowser.asComponent(), BorderLayout.CENTER);        

    JPanel jpanel = new JPanel(new GridLayout(1, 1));
    lblStatus = new JLabel("Status: ");
    jpanel.add(lblStatus);

    this.add(Panel2, BorderLayout.NORTH);
    this.add(Panel1, BorderLayout.CENTER);
    this.add(jpanel, BorderLayout.SOUTH);

    this.validate();
    this.setSize(new Dimension(350, 350));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
      }
    });    
  }

  private JMenuBar createMenuBar() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 建立選單功能列
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
        // 加入選單至選單功能列
        jmenubar.add(jmenu[i]);
      else
        // 設定選單功能列中的輔助說明選單
        jmenubar.setHelpMenu(jmenu[i]);
    }

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // 加入分隔線
          jmenu[i].addSeparator();
          // 或
          // jmenu[i].add(new JSeparator());
        }
        else {
          // 建立選單項目
          if (menuitemlabel[i][j].indexOf("|") != -1)
            jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
          else
            jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

          // 設定選單項目助記碼
          jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

          // 建立圖像
          if (menuitemlabel[i][j].endsWith(".gif")) 
            jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

          // 註冊 ActionListener
          jmenuitem[i][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              menu_actionPerformed(e);
            }
          });

          // 加入選單項目
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }

    jmenuitem[2][0].setEnabled(false);
    jmenuitem[2][0].setDisabledIcon(new ImageIcon(cl.getResource("images/backD.gif")));
    jmenuitem[2][1].setEnabled(false);
    jmenuitem[2][1].setDisabledIcon(new ImageIcon(cl.getResource("images/forwardD.gif")));
    
    return jmenubar;
  }

  private void txtURL_actionPerformed(ActionEvent e) {
    final String urlString = txtURL.getText().toString().trim();

    if (!urlString.equals("")) {
      Thread thread = new Thread() {
        public void run() {
          showHTML(urlString);
        }
      };
      thread.start();
    }
  }

  private void showHTML(String urlString) {
    webBrowser.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    
    try {
      if (urlString.length() > 0) {
        if (!urlString.startsWith("http://") && !urlString.startsWith("file:/")) {
          if (urlString.indexOf(':') == 1) {
            urlString = "file:/" + urlString;
          }
          else {
            urlString = "http://" + urlString;
          }
        }

        java.net.URL url = new URL(urlString);
        
        // 瀏覽URL
        webBrowser.setURL(url);

        txtURL.setText(urlString);
        txtURL.select(0, urlString.length());
        this.setTitle("JDIC Web Browser: " + urlString);
      }
    }
    catch (java.net.MalformedURLException muex) {
      JOptionPane.showMessageDialog(null, muex.toString() + ": " + urlString,
        "JDIC Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception ex){
      JOptionPane.showMessageDialog(null, ex.toString() + ": " + urlString,
        "JDIC Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    
    webBrowser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void saveAs() {
    final String urlString = txtURL.getText().toString().trim();
    
    if (!urlString.equals("")) {
      Thread thread = new Thread() {
        public void run() {
          saveFile(urlString);
        }
      };
      thread.start();
    }
    else 
      JOptionPane.showMessageDialog(null, "Please enter an URL",
        "JDIC Web Browser", JOptionPane.ERROR_MESSAGE);
  }
  
  private void viewSource() {
    String urlString = txtURL.getText().toString().trim();
    String htmlSource = webBrowser.getContent();

    if (!htmlSource.equals("")) 
      new ViewSourceFrame(htmlSource, urlString);
  } 

  private void openFile() {
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfilechooser.setDialogTitle("Open File ...");
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;

    this.repaint();

    final String urlString = "file:/" + jfilechooser.getSelectedFile();
    
    Thread thread = new Thread() {
      public void run() {
        showHTML(urlString);
      }
    };
    thread.start();
  }
  
  private void saveFile(String urlString) {
    jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
    jfilechooser.setDialogTitle("Save As");
    
    if (jfilechooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
    
    this.repaint();

    File fileName = jfilechooser.getSelectedFile();

    try  {
      java.net.URL url = new URL(urlString);

      // 建立連結並取得URL中所指定網頁的內容
      InputStream in = new BufferedInputStream(url.openStream());
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      
      FileWriter out = new FileWriter(fileName);
      BufferedWriter bw = new BufferedWriter(out);
      
      String line;
      
      while ((line = br.readLine()) !=null) {
        bw.write(line) ;
        bw.newLine();
      }
      bw.flush();
      bw.close();
      out.close();

      String linesep = System.getProperty("line.separator");

      JOptionPane.showMessageDialog(null, urlString + " has been saved to " + 
        linesep + fileName.getAbsolutePath(), 
        "JDIC Web Browser", JOptionPane.INFORMATION_MESSAGE);
    }
    catch (java.net.MalformedURLException muex) {
      JOptionPane.showMessageDialog(null, muex.toString(),
        "JDIC Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(null, ex.toString(),
        "JDIC Web Browser", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("Open")) { // Open
      // 開啟檔案瀏覽
      openFile();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      // 另存新檔
      saveAs();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      // 列印內容
      // JDIC 20061102
      //webBrowser.print();
      // JDIC 0.9.5
      //webBrowser.print(webBrowser.getGraphics());
    }
    else if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        // 執行window.close()之JavaScript
        webBrowser.executeScript("window.close();");

        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("Page Source")) { // Page Source
      // 檢視網頁原始碼
      viewSource();
    }
    else if (menuitem.getText().equals("Back")) { // Back
      // 瀏覽上一頁
      webBrowser.back();
    }
    else if (menuitem.getText().equals("Forward")) { // Forward
      // 瀏覽下一頁
      webBrowser.forward();
    }
    else if (menuitem.getText().equals("Stop")) { // Stop
      // 停止瀏覽
      webBrowser.stop();
    }
    else if (menuitem.getText().equals("Refresh")) { // Refresh
      // 重新整理
      webBrowser.refresh();
    }
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JDIC Web Browser", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  
  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // Back
      // 瀏覽上一頁
      webBrowser.back();
    }
    else if (e.getSource().equals(jbutton[1])) { // Forward
      // 瀏覽下一頁
      webBrowser.forward();
    }
    else if (e.getSource().equals(jbutton[2])) { // Stop
      // 停止瀏覽
      webBrowser.stop();
    }
    else if (e.getSource().equals(jbutton[3])) { // Refresh
      // 重新整理
      webBrowser.refresh();
    }
    else if (e.getSource().equals(jbutton[4])) { // Open
      // 開啟檔案瀏覽
      openFile();
    }
    else if (e.getSource().equals(jbutton[5])) { // Save
      // 另存新檔
      saveAs();
    }
    else if (e.getSource().equals(jbutton[6])) { // Print
      // 列印內容
      // JDIC 20061102
      //webBrowser.print();
      // JDIC 0.9.5
      //webBrowser.print(webBrowser.getGraphics());
    }
    else if (e.getSource().equals(jbutton[7])) { // Page Source
      // 檢視網頁原始碼
      viewSource();
    }
    else if (e.getSource().equals(jbutton[8])) { // About
      JOptionPane.showMessageDialog(null, "JDIC Web Browser", "About", JOptionPane.INFORMATION_MESSAGE);
    } 
  }
  
  // 
  // 實作WebBrowserListener介面的方法
  // 
  // 當完成網頁或文件載入時所呼叫的方法
  public void documentCompleted(WebBrowserEvent e) {
    lblStatus.setText("Document Completed");
  }
  
  // 當瀏覽網頁或文件發生錯誤時所呼叫的方法
  public void downloadError(WebBrowserEvent e) {
    lblStatus.setText("Download Error");
  }
  
  // 當正在下載網頁或文件時所呼叫的方法
  public void downloadProgress(WebBrowserEvent e) {
//    lblStatus.setText("Download Progress");
  }
  
  // 當開始下載網頁或文件時所呼叫的方法
  public void downloadStarted(WebBrowserEvent e) {
    lblStatus.setText("Download Started");
  }
  
  // 當物件啟始化完成時所呼叫的方法
  public void initializationCompleted(WebBrowserEvent e) {
    lblStatus.setText("Initialization Completed");
  }
  
  // 當狀態列內容改變時所呼叫的方法
  public void statusTextChange(WebBrowserEvent e) {
//    lblStatus.setText("Status Text Change");
  }
  
  // 當網頁或文件標題改變時所呼叫的方法
  public void titleChange(WebBrowserEvent e) {
//    lblStatus.setText("Title Change");
  }
  
  // 當視窗關閉時所呼叫的方法
  public void windowClose(WebBrowserEvent e) {
    lblStatus.setText("Window Close");
  }

  // 當瀏覽網頁或文件完畢時所呼叫的方法
  public void downloadCompleted(WebBrowserEvent e) {
    jbutton[0].setEnabled(webBrowser.isBackEnabled());
    jbutton[1].setEnabled(webBrowser.isForwardEnabled());
    jmenuitem[2][0].setEnabled(webBrowser.isBackEnabled());
    jmenuitem[2][1].setEnabled(webBrowser.isForwardEnabled());

    URL currentUrl = webBrowser.getURL();

    if (currentUrl != null) {
      txtURL.setText(currentUrl.toString());
      txtURL.select(0, currentUrl.toString().length());
      this.setTitle("JDIC Web Browser: " + currentUrl.toString());
    }

    lblStatus.setText("Download Completed");
  }
}
