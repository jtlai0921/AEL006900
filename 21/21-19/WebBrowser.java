import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import java.awt.print.*;  

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

// 實作接收Hyper link事件的HyperlinkListener介面
public class WebBrowser extends JFrame implements HyperlinkListener {
  JTextField txtURL = new JTextField();
  JEditorPane htmlPane;
  JFileChooser jfilechooser = new JFileChooser();

  JButton btnBack = new JButton();
  JButton btnForward = new JButton();
  JMenuItem jmenuBack = new JMenuItem();
  JMenuItem jmenuForward = new JMenuItem();

  JPopupMenu jpopupmenu1 = new JPopupMenu();
  JPopupMenu jpopupmenu2 = new JPopupMenu();

  JTabbedPane jtabbedpane;
  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[6];

  ImageIcon tabImage;
  
  static String history[] = new String[10];

  static String home;
  static int historyIndex = -1;
  static int currentIndex;

  // JDK 5.0 泛型Generics
  static Vector<Object> vHtml = new Vector<Object>();

  private String item[] = {"Wrap", "Scroll", "Top", "Bottom", "Left", "Right"};

  private static String proxyHost, proxyPort;
  
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
    super("Java Web Browser");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    this.setIconImage(new ImageIcon(cl.getResource("images/web.gif")).getImage());

    tabImage = new ImageIcon(cl.getResource("images/newpage.gif"));
    
    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

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

    btnBack.setEnabled(false);
    btnBack.setIcon(new ImageIcon(cl.getResource("images/back.gif")));
    btnBack.setDisabledIcon(new ImageIcon(cl.getResource("images/backD.gif")));
    btnBack.setMinimumSize(new Dimension(30, 30));
    btnBack.setMaximumSize(new Dimension(30, 30));
    btnBack.setPreferredSize(new Dimension(30, 30));
    btnBack.setToolTipText("Back");
    btnBack.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goBack();
      }
    });
    
    btnForward.setEnabled(false);
    btnForward.setIcon(new ImageIcon(cl.getResource("images/fwd.gif")));
    btnForward.setDisabledIcon(new ImageIcon(cl.getResource("images/fwdD.gif")));
    btnForward.setMaximumSize(new Dimension(30, 30));
    btnForward.setMinimumSize(new Dimension(30, 30));
    btnForward.setPreferredSize(new Dimension(30, 30));
    btnForward.setToolTipText("Forward");
    btnForward.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goForward();
      }
    });
    
    JButton btnHome = new JButton();
    btnHome.setIcon(new ImageIcon(cl.getResource("images/home.gif")));
    btnHome.setDisabledIcon(new ImageIcon(cl.getResource("images/homeD.gif")));
    btnHome.setMaximumSize(new Dimension(30, 30));
    btnHome.setMinimumSize(new Dimension(30, 30));
    btnHome.setPreferredSize(new Dimension(30, 30));
    btnHome.setToolTipText("Home");
    btnHome.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goHome();
      }
    });
    
    JButton btnReload = new JButton();
    btnReload.setIcon(new ImageIcon(cl.getResource("images/reload.gif")));
    btnReload.setDisabledIcon(new ImageIcon(cl.getResource("images/reloadD.gif")));
    btnReload.setMaximumSize(new Dimension(30, 30));
    btnReload.setMinimumSize(new Dimension(30, 30));
    btnReload.setPreferredSize(new Dimension(30, 30));
    btnReload.setToolTipText("Reload");
    btnReload.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goReload();
      }
    });
    
    JButton btnHistory = new JButton();
    btnHistory.setIcon(new ImageIcon(cl.getResource("images/history.gif")));
    btnHistory.setMaximumSize(new Dimension(30, 30));
    btnHistory.setMinimumSize(new Dimension(30, 30));
    btnHistory.setPreferredSize(new Dimension(30, 30));
    btnHistory.setToolTipText("History");
    btnHistory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goHistory();
      }
    });

    JButton btnOpen = new JButton();
    btnOpen.setIcon(new ImageIcon(cl.getResource("images/open.gif")));
    btnOpen.setToolTipText("Open File");
    btnOpen.setMaximumSize(new Dimension(30, 30));
    btnOpen.setMinimumSize(new Dimension(30, 30));
    btnOpen.setPreferredSize(new Dimension(30, 30));
    btnOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openFile();
      }
    });
    
    JButton btnSave = new JButton();
    btnSave.setIcon(new ImageIcon(cl.getResource("images/save.gif")));
    btnSave.setToolTipText("Save Source");
    btnSave.setMaximumSize(new Dimension(30, 30));
    btnSave.setMinimumSize(new Dimension(30, 30));
    btnSave.setPreferredSize(new Dimension(30, 30));
    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveAs();
      }
    });
    
    JButton btnView = new JButton();
    btnView.setIcon(new ImageIcon(cl.getResource("images/source.gif")));
    btnView.setToolTipText("View Source");
    btnView.setMaximumSize(new Dimension(30, 30));
    btnView.setMinimumSize(new Dimension(30, 30));
    btnView.setPreferredSize(new Dimension(30, 30));
    btnView.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewSource();
      }
    });
    
    JButton btnAbout = new JButton();
    btnAbout.setIcon(new ImageIcon(cl.getResource("images/about.gif")));
    btnAbout.setToolTipText("About");
    btnAbout.setMaximumSize(new Dimension(30, 30));
    btnAbout.setMinimumSize(new Dimension(30, 30));
    btnAbout.setPreferredSize(new Dimension(30, 30));
    btnAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Java Web Browser", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    JToolBar jtoolbar = new JToolBar();
    jtoolbar.setRollover(true);
    jtoolbar.add(btnBack);
    jtoolbar.add(btnForward);
    jtoolbar.add(btnHome);
    jtoolbar.add(btnReload);
    jtoolbar.add(btnHistory);
    jtoolbar.addSeparator();
    jtoolbar.add(btnOpen);
    jtoolbar.add(btnView);
    jtoolbar.add(btnSave);
    jtoolbar.addSeparator();
    jtoolbar.add(btnAbout);

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 建立選單
    JMenu jmenuFile = new JMenu("File");
    jmenuFile.setMnemonic('F');
    jmenubar.add(jmenuFile);

    // New Tab
    JMenuItem jmenuAdd = new JMenuItem("New Tab");
    jmenuAdd.setMnemonic('N');
    jmenuAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createTab();
      }
    });

    // 建立選單項目
    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open ...", new ImageIcon(cl.getResource("images/open.gif")));
    jmenuOpen.setMnemonic('O');
    jmenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openFile();
      }
    });

    // Save As
    JMenuItem jmenuSaveAs = new JMenuItem("Save As", new ImageIcon(cl.getResource("images/save.gif")));
    jmenuSaveAs.setMnemonic('S');
    jmenuSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuSaveAs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveAs();
      }
    });

    // Print
    JMenuItem jmenuPrint = new JMenuItem("Print", new ImageIcon(cl.getResource("images/print.gif")));
    jmenuPrint.setMnemonic('P');
    jmenuPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    jmenuPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        final JEditorPane currentPane = (JEditorPane) vHtml.elementAt(jtabbedpane.getSelectedIndex());
        
        new Thread() {
          public void run() {
            try {
              // 列印內容
              currentPane.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      }
    });

    // Option
    JMenuItem jmenuOption = new JMenuItem("Option ...", new ImageIcon(cl.getResource("images/option.gif")));
    jmenuOption.setMnemonic('P');
    jmenuOption.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        option();
      }
    });

    // Exit
    JMenuItem jmenuExit = new JMenuItem();
    jmenuExit.setText("Exit");
    jmenuExit.setMnemonic('X');
    jmenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          resetHistory();
          dispose();
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuAdd);
    jmenuFile.add(jmenuOpen);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuSaveAs);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuPrint);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuOption);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuExit);

    JMenuItem jmenuAdd1 = new JMenuItem("New Tab");
    jmenuAdd1.setMnemonic('N');
    jmenuAdd1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createTab();
      }
    });

    // Close Tab
    JMenuItem jmenuClose1 = new JMenuItem("Close Tab");
    jmenuClose1.setMnemonic('C');
    jmenuClose1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeTab();
      }
    });
    // 建立突顯式選單
    jpopupmenu1.add(jmenuAdd1);
    jpopupmenu1.add(jmenuClose1);

    // 建立選單
    JMenu jmenuView = new JMenu("View");
    jmenuView.setMnemonic('V');
    jmenubar.add(jmenuView);

    // 建立選單項目
    // View Source
    JMenuItem jmenuSource = new JMenuItem("Page Source");
    jmenuSource.setIcon(new ImageIcon(cl.getResource("images/source.gif")));
    jmenuSource.setMnemonic('P');
    jmenuSource.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewSource();
      }
    });

    jmenuView.add(jmenuSource);

    // View Source
    JMenuItem jmenuSource1 = new JMenuItem("Page Source");
    jmenuSource1.setIcon(new ImageIcon(cl.getResource("images/source.gif")));
    jmenuSource1.setMnemonic('P');
    jmenuSource1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewSource();
      }
    });

    // 建立突顯式選單
    jpopupmenu2.add(jmenuSource1);

    // 建立選單
    JMenu jmenuGo = new JMenu("Go");
    jmenuGo.setMnemonic('G');
    jmenubar.add(jmenuGo);

    // 建立選單項目
    // Back
    jmenuBack.setText("Back");
    jmenuBack.setMnemonic('B');
    jmenuBack.setEnabled(false);
    jmenuBack.setIcon(new ImageIcon(cl.getResource("images/back.gif")));
    jmenuBack.setDisabledIcon(new ImageIcon(cl.getResource("images/backD.gif")));
    jmenuBack.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goBack();
      }
    });

    jmenuForward.setText("Forward");
    jmenuForward.setMnemonic('W');
    jmenuForward.setEnabled(false);
    jmenuForward.setIcon(new ImageIcon(cl.getResource("images/fwd.gif")));
    jmenuForward.setDisabledIcon(new ImageIcon(cl.getResource("images/fwdD.gif")));
    jmenuForward.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goForward();
      }
    });

    JMenuItem jmenuHome = new JMenuItem("Home", new ImageIcon(cl.getResource("images/home.gif")));
    jmenuHome.setMnemonic('M');
    jmenuHome.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goHome();
      }
    });

    JMenuItem jmenuReload = new JMenuItem("Reload", new ImageIcon(cl.getResource("images/reload.gif")));
    jmenuReload.setMnemonic('R');
    jmenuReload.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goReload();
      }
    });

    JMenuItem jmenuHistory = new JMenuItem("History", new ImageIcon(cl.getResource("images/history.gif")));
    jmenuHistory.setMnemonic('H');
    jmenuHistory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        goHistory();
      }
    });

    jmenuGo.add(jmenuBack);
    jmenuGo.add(jmenuForward);
    jmenuGo.add(jmenuHome);
    jmenuGo.add(jmenuReload);
    jmenuGo.add(jmenuHistory);

    // 建立選單
    JMenu jmenuTab = new JMenu("Tab");
    jmenuTab.setMnemonic('T');
    jmenubar.add(jmenuTab);

    for (int i=0; i<item.length; i++) {
      jrbmenuitem[i] = new JRadioButtonMenuItem(item[i]);
      
      // 註冊 ActionListener
      jrbmenuitem[i].addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          menu_actionPerformed(e);
        }
      });
     
      if (i==1 || i==2)
        jrbmenuitem[i].setSelected(true);
    }

    // 建立子選單
    JMenu submenu1 = new JMenu("Layout Policy");
    submenu1.setMnemonic(KeyEvent.VK_L);
    submenu1.add(jrbmenuitem[0]);
    submenu1.add(jrbmenuitem[1]);
    jmenuTab.add(submenu1);

    JMenu submenu2 = new JMenu("Placement");
    submenu2.setMnemonic(KeyEvent.VK_P);
    submenu2.add(jrbmenuitem[2]);
    submenu2.add(jrbmenuitem[3]);
    submenu2.add(jrbmenuitem[4]);
    submenu2.add(jrbmenuitem[5]);
    jmenuTab.add(submenu2);

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jrbmenuitem[0]);
    group1.add(jrbmenuitem[1]);

    ButtonGroup group2 = new ButtonGroup();
    group2.add(jrbmenuitem[2]);
    group2.add(jrbmenuitem[3]);
    group2.add(jrbmenuitem[4]);
    group2.add(jrbmenuitem[5]);

    // 建立選單
    // Help
    JMenu jmenuHelp = new JMenu("Help");
    jmenuHelp.setMnemonic('H');
    jmenubar.add(jmenuHelp);

    // About
    JMenuItem jmenuAbout = new JMenuItem("About", new ImageIcon(cl.getResource("images/about.gif")));
    jmenuAbout.setMnemonic('A');
    jmenuHelp.add(jmenuAbout);
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Java Web Browser", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BorderLayout());
    Panel2.setPreferredSize(new Dimension(10, 40));

    JPanel Panel3 = new JPanel();
    Panel3.setLayout(new BorderLayout());
    Panel3.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
    Panel3.setPreferredSize(new Dimension(40, 10));

    JPanel Panel4 = new JPanel();
    Panel4.setLayout(new BorderLayout());
    Panel4.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

    Panel4.add(txtURL,  BorderLayout.CENTER);
    Panel3.add(jlabel1, BorderLayout.CENTER);
    Panel2.add(Panel4,  BorderLayout.CENTER);
    Panel2.add(Panel3,  BorderLayout.WEST);

    jtabbedpane = new JTabbedPane();
    jtabbedpane.setTabPlacement(SwingConstants.TOP);
    jtabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    jtabbedpane.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    // Create a new tab
    createTab();
    
    Panel1.add(Panel2,  BorderLayout.NORTH);
    Panel1.add(jtabbedpane, BorderLayout.CENTER);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(Panel1, BorderLayout.CENTER);

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
        resetHistory();
        System.exit(0);
      }
    });    

    loadProperties();
  }

  private void showPopmenu(MouseEvent e) {
    // 當按下滑鼠按鍵時
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
      int j = jtabbedpane.getSelectedIndex();    
      JEditorPane currentPane = (JEditorPane) vHtml.elementAt(j);

      if (e.getSource().equals(jtabbedpane)) { // JTtabbedPane
        // 顯示突顯式選單
        jpopupmenu1.show(e.getComponent(), e.getX(), e.getY());
      }
      if (e.getSource().equals(currentPane)) { // JEditorPane
        // 顯示突顯式選單
        jpopupmenu2.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }

  private void createTab() {
    htmlPane = new JEditorPane();
    htmlPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
    htmlPane.setEditable(false);
    
    // 註冊 HyperlinkListener
    htmlPane.addHyperlinkListener(this);

    // 註冊 MouseListener
    htmlPane.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    vHtml.add(htmlPane);
        
    JScrollPane jscrollpane = new JScrollPane();
    jtabbedpane.addTab("(Blank)", tabImage, jscrollpane);
    jtabbedpane.setSelectedIndex(jtabbedpane.getTabCount()-1);
    jscrollpane.getViewport().add((java.awt.Component) vHtml.elementAt(jtabbedpane.getSelectedIndex()));
  }

  private void closeTab() {
    if (jtabbedpane.getTabCount() > 1) {
      int j = jtabbedpane.getSelectedIndex();
      jtabbedpane.removeTabAt(j);
      vHtml.remove(j);
    }
  }

  private void txtURL_actionPerformed(ActionEvent e) {
    final String urlString = txtURL.getText().toString().trim();

    if ((!urlString.equals("")) && (!urlString.equalsIgnoreCase("<About:Blank>"))) {
      Thread thread = new Thread() {
        public void run() {
          showHTML(urlString);
          updateHistory(urlString) ;
        }
      };
      thread.start();
    }
    else {
      int j = jtabbedpane.getSelectedIndex();    
      txtURL.setText("<About:Blank>");
      JEditorPane currentPane = (JEditorPane) vHtml.elementAt(j);
      currentPane.setText("<font face=Verdana size=3>Blank Page</font>");
    }
  }

  public void hyperlinkUpdate(HyperlinkEvent e) {
    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      final String urlString = e.getURL().toString().trim();
    
      Thread thread = new Thread() {
        public void run() {
          showHTML(urlString);
          updateHistory(urlString) ;
        }
      };
      thread.start();
    }
  }

  private void showHTML(String urlString) {
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    
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

        // Set the Proxy
        Properties prop = System.getProperties();
        prop.put("http.proxyHost", this.proxyHost) ;
        prop.put("http.proxyPort", this.proxyPort) ;

        java.net.URL url = new URL(urlString);
        
        int j = jtabbedpane.getSelectedIndex();    
        JEditorPane currentPane = (JEditorPane) vHtml.elementAt(j);
        currentPane.setPage(url);
        txtURL.setText(urlString);
        txtURL.select(0, urlString.length());
        this.setTitle("Java Web Browser: " + urlString);
        
        // Set the title and tool tip of tab
        String pageTitle = getPageTitle();
        jtabbedpane.setTitleAt(j, pageTitle) ;
        jtabbedpane.setToolTipTextAt(j, pageTitle) ;
      }
    }
    catch (java.net.MalformedURLException muex) {
      JOptionPane.showMessageDialog(null, muex.toString() + ": " + urlString,
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception ex){
      JOptionPane.showMessageDialog(null, ex.toString() + ": " + urlString,
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void saveAs() {
    JEditorPane currentPane = (JEditorPane) vHtml.elementAt(jtabbedpane.getSelectedIndex());
    final String urlString = currentPane.getPage().toString();
    
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
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
  }
  
  private String getPageTitle() {
    String pageTitle = "(Blank)";
  
    int beginIndex = 0;
    int endIndex = 0;
    
    String startTag[] = {"<title>",  "<Title>",  "<TITLE>"};
    String endTag[]   = {"</title>", "</Title>", "</TITLE>"};

    JEditorPane currentPane = (JEditorPane) vHtml.elementAt(jtabbedpane.getSelectedIndex());

    if (!currentPane.equals("")) {
      String urlString = currentPane.getPage().toString();
      
      if (!urlString.equals("")) {
        String htmlSource = getHtmlSource(urlString);

        for (int i=0; i < startTag.length; i++) {
          beginIndex = htmlSource.indexOf(startTag[i]);
          if (beginIndex > 0)  break;
        }
    
        for (int j=0; j < endTag.length; j++) {
          endIndex = htmlSource.indexOf(endTag[j]);
          if (endIndex > 0)  break;
        }
    
        String temp = htmlSource.substring(beginIndex, endIndex) ;
        beginIndex = temp.indexOf(">");
        pageTitle = temp.substring(beginIndex+1).trim() ;
      }
    } 

    return pageTitle;
  } 
  
  private void viewSource() {
    JEditorPane currentPane = (JEditorPane) vHtml.elementAt(jtabbedpane.getSelectedIndex());

    if (!currentPane.equals("")) {
      String urlString = currentPane.getPage().toString();
      String htmlSource = currentPane.getText();

      if (!htmlSource.equals("")) 
        new ViewSourceFrame(htmlSource, urlString);
    } 
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
        "Java Web Browser", JOptionPane.INFORMATION_MESSAGE);
    }
    catch (java.net.MalformedURLException muex) {
      JOptionPane.showMessageDialog(null, muex.toString(),
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(null, ex.toString(),
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private String getHtmlSource(String urlString) {
    String linesep, htmlLine ;
    
    // line separator
    linesep = System.getProperty("line.separator") ;
    
    String htmlSource = "" ;
    
    try {
      java.net.URL url = new URL(urlString);
  
      // 建立連結並取得URL中所指定網頁的內容
      InputStream in = new BufferedInputStream(url.openStream());
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      
      while ((htmlLine = br.readLine()) != null) {
        htmlSource = htmlSource + htmlLine + linesep;
      }
      
      return htmlSource;
    }
    catch (java.net.MalformedURLException muex) {
      JOptionPane.showMessageDialog(null, muex.toString(),
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);

      return "";
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.toString(),
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);

      return "";
    }
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

  private void goBack() {
    currentIndex = currentIndex - 1;
    if (currentIndex < 0) 
      currentIndex = 0;

    navigator(currentIndex) ;
  }
    
  private void goForward() {
    currentIndex = currentIndex + 1;
    if (currentIndex > historyIndex) 
      currentIndex = historyIndex;
    
    navigator(currentIndex) ;
  }
    
  private void goHome() {
    int j = jtabbedpane.getSelectedIndex();

    loadProperties() ;
    
    if ((!home.equalsIgnoreCase("<Blank>")) && (!home.equals(""))) {
      txtURL.setText(home);

      final String urlString = txtURL.getText().toString().trim();
      
      Thread thread = new Thread() {
        public void run() {
          showHTML(urlString);
          updateHistory(urlString) ;
        }
      };
      thread.start();
    }
    else if (home.equalsIgnoreCase("<Blank>")) {
      txtURL.setText("<About:Blank>");
      JEditorPane currentPane = (JEditorPane) vHtml.elementAt(j);
      currentPane.setText("<font face=Verdana size=3>Blank Page</font>");
    }
  }
    
  private void goReload() {
    JEditorPane currentPane = (JEditorPane) vHtml.elementAt(jtabbedpane.getSelectedIndex());
    final String urlString = currentPane.getPage().toString();

    int j = jtabbedpane.getSelectedIndex();    
    
    if ((!urlString.equals("")) && (!urlString.equalsIgnoreCase("<About:Blank>"))) {
      Thread thread = new Thread() {
        public void run() {
          showHTML(urlString);
        }
      };
      thread.start();
    }
    else {
      txtURL.setText("<About:Blank>");
      currentPane = (JEditorPane) vHtml.elementAt(j);
      currentPane.setText("<font face=Verdana size=3>Blank Page</font>");
    }
  }
    
  private void goHistory() {
    String strHistory = "" ;

    loadProperties() ;

    int j = jtabbedpane.getSelectedIndex();    
    txtURL.setText("<About:History>");

    JEditorPane currentPane = (JEditorPane) vHtml.elementAt(j);
    
    if (historyIndex == -1) {
      currentPane.setText("<font face=Verdana size=3>No History</font>");
    }
    else {
      strHistory = "<ul>" ;
      
      for (int i = historyIndex; i >= 0; --i) {
        strHistory = strHistory + "<li><font face=Verdana size=3>" + history[i] + "</font></li>" ;
      }
      
      strHistory = strHistory + "</ul>" ;
      currentPane.setText(strHistory);
    }
  }

  private void navigator(int i) {
    if (i == 0) {
      btnBack.setEnabled(false);
      jmenuBack.setEnabled(false);
    }
    if (i > 0) {
      btnBack.setEnabled(true);
      jmenuBack.setEnabled(true);
    }
    if (i < historyIndex) {
      btnForward.setEnabled(true);
      jmenuForward.setEnabled(true);
    }
    if (i == historyIndex) {
      btnForward.setEnabled(false);
      jmenuForward.setEnabled(false);
    }
    
    loadProperties() ;

    int j = jtabbedpane.getSelectedIndex();
        
    if ((!history[i].equalsIgnoreCase("<Blank>")) && (!history[i].equals(""))) {
      txtURL.setText(history[i]);

      final String urlString = txtURL.getText().toString().trim();
      
      Thread thread = new Thread() {
        public void run() {
          showHTML(urlString);
        }
      };
      thread.start();
    }
    else if (history[i].equalsIgnoreCase("<Blank>")) {
      txtURL.setText("<About:Blank>");
      JEditorPane currentPane = (JEditorPane) vHtml.elementAt(j);
      currentPane.setText("<font face=Verdana size=3>Blank Page</font>");
    }
  }

  private void loadProperties() {
    try {
      Properties p = new Properties();
      FileInputStream in = new FileInputStream("browser.properties");
      p.load(in);
      home = p.getProperty("home.page") ;

      // Proxy Setting
      proxyHost = p.getProperty("http.proxyHost");
      proxyPort = p.getProperty("http.proxyPort");
      
      for (int i = 0; i < 10; i++) {
        history[i] = p.getProperty("history." + i) ;
      }
  
      in.close() ;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
    
  private void resetHistory() {
    try {
      Properties p = new Properties();
      FileOutputStream os = new FileOutputStream("browser.properties");
      
      p.setProperty("home.page", home) ;

      // Proxy Setting
      p.setProperty("http.proxyHost", proxyHost);
      p.setProperty("http.proxyPort", proxyPort);
      
      for (int i = 0; i < 10; i++) {
        p.setProperty("history." + i, "") ;
        history[i] = "" ;
      }
      
      p.store(os, null);
      os.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void updateHistory(String urlString) {
    historyIndex ++;
    
    if (historyIndex >= 9) historyIndex = 9;
    
    currentIndex = historyIndex ;

    if (currentIndex > 0) {
      btnBack.setEnabled(true);
      jmenuBack.setEnabled(true);
    }
    
    this.repaint();
    
    try {
      Properties p = new Properties();
      
      FileOutputStream os = new FileOutputStream("browser.properties");
      
      p.setProperty("home.page", home) ;

      // Proxy Setting
      p.setProperty("http.proxyHost", proxyHost);
      p.setProperty("http.proxyPort", proxyPort);
      
      history[historyIndex] = urlString ;
      p.setProperty("history." + historyIndex, history[historyIndex]) ;
      
      for (int i = 0; i < historyIndex; i++) {
        p.setProperty("history." + i, history[i]) ;
      }
      
      for (int i = historyIndex + 1; i < 10; i++) {
        p.setProperty("history." + i, history[i]) ;
      }
      
      p.store(os, null);
      os.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void menu_actionPerformed(ActionEvent e) {
    if (e.getSource() == jrbmenuitem[0]) 
      jtabbedpane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    else if(e.getSource() == jrbmenuitem[1]) 
      jtabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    else if(e.getSource() == jrbmenuitem[2]) 
      jtabbedpane.setTabPlacement(SwingConstants.TOP);
    else if(e.getSource() == jrbmenuitem[3]) 
      jtabbedpane.setTabPlacement(SwingConstants.BOTTOM);
    else if(e.getSource() == jrbmenuitem[4]) 
      jtabbedpane.setTabPlacement(SwingConstants.LEFT);
    else if(e.getSource() == jrbmenuitem[5]) 
      jtabbedpane.setTabPlacement(SwingConstants.RIGHT);

    jtabbedpane.revalidate();
    jtabbedpane.repaint();
  }

  private void option() {
    new OptionDialog();
  } 
}
