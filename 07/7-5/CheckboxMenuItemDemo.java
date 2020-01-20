import java.awt.*;
import java.awt.event.*;

public class CheckboxMenuItemDemo extends java.awt.Frame implements ActionListener, ItemListener {

  String menulabel[]={"File", "Help"};

  String menuitemlabel[][]={
    {"New", "Open", "-", "Save", "Save As...", "-", "Sub Menu", "-", "Exit"},
    {"Index", "Use Help", "-", "About"}
  };
  
  String cbmenuitemlabel[]={"Basic", "Intermediate", "Advance"};
  
  Menu menu[] = new Menu[2];
  MenuItem menuitem[][] = new MenuItem[2][9];
  CheckboxMenuItem checkboxmenuitem[] = new CheckboxMenuItem[3];

  // Demo only
  Label label;

  public static void main(String args[]){
    new CheckboxMenuItemDemo();
  }
  
  // 建構函式
  public CheckboxMenuItemDemo() {
    super("Checkbox Menu Item Demo");

    // 定義 Layout Manager 為 BorderLayout
    setLayout(new BorderLayout());
    
    // Demo only
    label = new Label();
    add(label, BorderLayout.SOUTH);

    // 建立選單列
    MenuBar menuBar = createMenuBar();

    // 定義視窗使用者介面之選單列
    setMenuBar(menuBar);

    // 設定視窗的大小
    this.setSize(250, 250);

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

  private MenuBar createMenuBar() {
    // 建立選單列
    MenuBar menuBar = new MenuBar();

    // 建立選單
    for (int i=0; i<menulabel.length; i++){
      // 建立選單
      menu[i] = new Menu(menulabel[i]);
      menu[i].setFont(new Font("dialog", Font.PLAIN, 11));

      // 新增選單至選單列
      menuBar.add(menu[i]);
    }

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (i==0 && j==6) { // 子選單
          // 建立子選單
          Menu submenu = new Menu(menuitemlabel[i][j]);
          submenu.setFont(new Font("dialog", Font.PLAIN, 11));
          
          // 建立核取方塊選單項目
          for (int k=0; k<cbmenuitemlabel.length; k++){
            checkboxmenuitem[k] = new CheckboxMenuItem(cbmenuitemlabel[k]);
            
            // 設定核取方塊選單項目之選取狀態
            if (k==0) 
              checkboxmenuitem[k].setState(true);
            
            checkboxmenuitem[k].setFont(new Font("dialog", Font.PLAIN, 11));

            // 註冊 ItemListener
            checkboxmenuitem[k].addItemListener(this);
      
            // 新增核取方塊選單項目至子選單
            submenu.add(checkboxmenuitem[k]);
          }
      
          menu[i].add(submenu);
        }
        else if (menuitemlabel[i][j].equals("-")) {
          // 新增分隔線
          menu[i].addSeparator();
        }
        else {
          menuitem[i][j] = new MenuItem(menuitemlabel[i][j]);

          menuitem[i][j].setFont(new Font("dialog", Font.PLAIN, 11));

          // 註冊 ActionListener
          menuitem[i][j].addActionListener(this);

          // 新增選單項目
          menu[i].add(menuitem[i][j]);
        }          
      }
    }

    return menuBar;
  }

  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    MenuItem menuitem = (MenuItem)e.getSource();

    if (menuitem.getLabel().equals("Exit")) { // Exit
      System.exit(0);
    }
    else {
      label.setText("Select "  + menuitem.getLabel() + " Menu Item.");
    }
  }

  public void itemStateChanged(ItemEvent e) {
    // 取得產生項目事件的物件
    CheckboxMenuItem cbmi = (CheckboxMenuItem)(e.getItemSelectable());
    
    for (int i=0; i < checkboxmenuitem.length; i++) {
      if(cbmi == checkboxmenuitem[i]) {
        checkboxmenuitem[i].setState(true);

        label.setText("Select " + checkboxmenuitem[i].getLabel() + " Checkbox Menu Item.");
      }
      else
        checkboxmenuitem[i].setState(false);
    }
  }
}