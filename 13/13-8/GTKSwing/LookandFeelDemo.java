import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LookandFeelDemo extends javax.swing.JFrame {
  JButton jbutton[];
  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[3][9];
  JPopupMenu jpopupmenu = new JPopupMenu();

  String items[]={"New...", "Open", "Save", "Print", "Cut", "Copy", "Paste"};
  String images[]={
    "new.gif", "open.gif", "save.gif", "print.gif", 
    "cut.gif", "copy.gif", "paste.gif"};

  String menu[]={"File|F", "Edit|E", "Help|H"};
  String menuItem[][]={
    {"New...|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", 
        "Print|P|print.gif", "Page Setup|S|pagesetup.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"About|A|about.gif"}
  };

  // Main method
  public static void main(String[] args) {
    try {
      // 設定Open Source之GTKSwing Look and Feel
      UIManager.setLookAndFeel("org.gtk.java.swing.plaf.gtk.GtkLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new LookandFeelDemo();
  }

  // 建構函式
  public LookandFeelDemo() {
    super("GTKSwing Look and Feel");

    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();
    jtoolbar.setRollover(true);

    jbutton = new JButton[images.length];

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    for (int i=0; i<images.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + images[i])));
      jbutton[i].setMaximumSize(new Dimension(30, 30));
      jbutton[i].setMinimumSize(new Dimension(30, 30));
      jbutton[i].setPreferredSize(new Dimension(30, 30));
      jbutton[i].setToolTipText(items[i]);

      jtoolbar.add(jbutton[i]);
      
      if (i==3)
        jtoolbar.addSeparator();
    }

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    jmenu = new JMenu[menu.length];
    
    for (int i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      jmenubar.add(jmenu[i]);
    }

    for(int i=0; i<menu.length; i++){
      for(int j=0; j<menuItem[i].length; j++){
        if (menuItem[i][j].equals("-")) {
          jmenu[i].addSeparator();
        }
        else {
          jmenuItem[i][j] = new JMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
          jmenuItem[i][j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));

          if (menuItem[i][j].endsWith(".gif")) 
            jmenuItem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));

          jmenuItem[i][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              menu_actionPerformed(e);
            }
          });

          jmenu[i].add(jmenuItem[i][j]);
        }
      }
    }

    // 突顯式選單
    JMenuItem jmnuCut1 = new JMenuItem(items[4], new ImageIcon(cl.getResource("images/" + images[4])));
    JMenuItem jmnuCopy1 = new JMenuItem(items[5], new ImageIcon(cl.getResource("images/" + images[5])));
    JMenuItem jmnuPaste1 = new JMenuItem(items[6], new ImageIcon(cl.getResource("images/" + images[6])));
    
    // 建立突顯式選單
    jpopupmenu.add(jmnuCut1);
    jpopupmenu.add(jmnuCopy1);
    jpopupmenu.add(jmnuPaste1);

    JEditorPane txtMessage = new JEditorPane();
    txtMessage.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showJPopupMenu(e);
      }
    });

    JScrollPane jsp1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    jsp1.getViewport().add(txtMessage);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jsp1, BorderLayout.CENTER);

    // 設定視窗的大小
    this.setSize(250, 250);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private void showJPopupMenu(MouseEvent e) {
    // 當按下滑鼠按鍵時
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) 
      // 顯示突顯式選單
      jpopupmenu.show(e.getComponent(), e.getX(), e.getY());
  }

  public void menu_actionPerformed(ActionEvent e){
    if(e.getSource() == jmenuItem[0][8]){  // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        System.exit(0);
      }
    }
  }
}

