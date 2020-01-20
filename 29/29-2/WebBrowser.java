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

// ��@org.jdesktop.jdic.browser.WebBrowserListener
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

  // �غc�禡
  public WebBrowser() {
    super("JDIC Web Browser");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    this.setIconImage(new ImageIcon(cl.getResource("images/web.gif")).getImage());

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // AWT �P Swing �ۮe
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

      // ���U ActionListener
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
        // �[�J���j�u
        jtoolbar.addSeparator();
    }

    // �إ߿��\��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������\��C
    this.setJMenuBar(jmenubar);

    // JDIC WebBrowser
    webBrowser = new org.jdesktop.jdic.browser.WebBrowser();
    
    // �O�_����
    webBrowser.setDebug(true);

    // ���UWebBrowserListener
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
    // 0.9.5���ϥΤ覡
    Panel1.add(webBrowser, BorderLayout.CENTER);        
    // jdic-20061102���ϥΤ覡
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
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �إ߿��\��C
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menulabel.length];
    
    // �إ߿��
    for (int i=0; i<menulabel.length; i++){
      // �إ߿��
      if (menulabel[i].indexOf("|") != -1)
        jmenu[i] = new JMenu(menulabel[i].substring(0, menulabel[i].indexOf("|")));
      else
        jmenu[i] = new JMenu(menulabel[i]);

      // �]�w���U�O�X
      jmenu[i].setMnemonic(menulabel[i].split("\\|")[1].charAt(0));

      if (i != menulabel.length)
        // �[�J���ܿ��\��C
        jmenubar.add(jmenu[i]);
      else
        // �]�w���\��C�������U�������
        jmenubar.setHelpMenu(jmenu[i]);
    }

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
          // ��
          // jmenu[i].add(new JSeparator());
        }
        else {
          // �إ߿�涵��
          if (menuitemlabel[i][j].indexOf("|") != -1)
            jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
          else
            jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

          // �]�w��涵�اU�O�X
          jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

          // �إ߹Ϲ�
          if (menuitemlabel[i][j].endsWith(".gif")) 
            jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

          // ���U ActionListener
          jmenuitem[i][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              menu_actionPerformed(e);
            }
          });

          // �[�J��涵��
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
        
        // �s��URL
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

      // �إ߳s���è��oURL���ҫ��w���������e
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
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("Open")) { // Open
      // �}���ɮ��s��
      openFile();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      // �t�s�s��
      saveAs();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      // �C�L���e
      // JDIC 20061102
      //webBrowser.print();
      // JDIC 0.9.5
      //webBrowser.print(webBrowser.getGraphics());
    }
    else if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        // ����window.close()��JavaScript
        webBrowser.executeScript("window.close();");

        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("Page Source")) { // Page Source
      // �˵�������l�X
      viewSource();
    }
    else if (menuitem.getText().equals("Back")) { // Back
      // �s���W�@��
      webBrowser.back();
    }
    else if (menuitem.getText().equals("Forward")) { // Forward
      // �s���U�@��
      webBrowser.forward();
    }
    else if (menuitem.getText().equals("Stop")) { // Stop
      // �����s��
      webBrowser.stop();
    }
    else if (menuitem.getText().equals("Refresh")) { // Refresh
      // ���s��z
      webBrowser.refresh();
    }
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JDIC Web Browser", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  
  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // Back
      // �s���W�@��
      webBrowser.back();
    }
    else if (e.getSource().equals(jbutton[1])) { // Forward
      // �s���U�@��
      webBrowser.forward();
    }
    else if (e.getSource().equals(jbutton[2])) { // Stop
      // �����s��
      webBrowser.stop();
    }
    else if (e.getSource().equals(jbutton[3])) { // Refresh
      // ���s��z
      webBrowser.refresh();
    }
    else if (e.getSource().equals(jbutton[4])) { // Open
      // �}���ɮ��s��
      openFile();
    }
    else if (e.getSource().equals(jbutton[5])) { // Save
      // �t�s�s��
      saveAs();
    }
    else if (e.getSource().equals(jbutton[6])) { // Print
      // �C�L���e
      // JDIC 20061102
      //webBrowser.print();
      // JDIC 0.9.5
      //webBrowser.print(webBrowser.getGraphics());
    }
    else if (e.getSource().equals(jbutton[7])) { // Page Source
      // �˵�������l�X
      viewSource();
    }
    else if (e.getSource().equals(jbutton[8])) { // About
      JOptionPane.showMessageDialog(null, "JDIC Web Browser", "About", JOptionPane.INFORMATION_MESSAGE);
    } 
  }
  
  // 
  // ��@WebBrowserListener��������k
  // 
  // ���������Τ����J�ɩҩI�s����k
  public void documentCompleted(WebBrowserEvent e) {
    lblStatus.setText("Document Completed");
  }
  
  // ���s�������Τ��o�Ϳ��~�ɩҩI�s����k
  public void downloadError(WebBrowserEvent e) {
    lblStatus.setText("Download Error");
  }
  
  // ���b�U�������Τ��ɩҩI�s����k
  public void downloadProgress(WebBrowserEvent e) {
//    lblStatus.setText("Download Progress");
  }
  
  // ��}�l�U�������Τ��ɩҩI�s����k
  public void downloadStarted(WebBrowserEvent e) {
    lblStatus.setText("Download Started");
  }
  
  // ����ҩl�Ƨ����ɩҩI�s����k
  public void initializationCompleted(WebBrowserEvent e) {
    lblStatus.setText("Initialization Completed");
  }
  
  // ���A�C���e���ܮɩҩI�s����k
  public void statusTextChange(WebBrowserEvent e) {
//    lblStatus.setText("Status Text Change");
  }
  
  // ������Τ����D���ܮɩҩI�s����k
  public void titleChange(WebBrowserEvent e) {
//    lblStatus.setText("Title Change");
  }
  
  // ����������ɩҩI�s����k
  public void windowClose(WebBrowserEvent e) {
    lblStatus.setText("Window Close");
  }

  // ���s�������Τ�󧹲��ɩҩI�s����k
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
