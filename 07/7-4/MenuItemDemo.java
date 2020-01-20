import java.awt.*;
import java.awt.event.*;

public class MenuItemDemo extends java.awt.Frame implements ActionListener {

  String menulabel[]={"File", "Help"};

  String menuitemlabel[][]={
    {"New", "Open", "-", "Save", "Save As...", "-", "Exit"},
    {"Index", "Use Help", "-", "About"}
  };

  Menu menu[] = new Menu[2];
  MenuItem menuitem[][] = new MenuItem[2][7];

  // Demo only
  Label label;

  public static void main(String args[]){
    new MenuItemDemo();
  }
  
  // �غc�禡
  public MenuItemDemo() {
    super("Menu Item Demo");

    // �w�q Layout Manager �� BorderLayout
    setLayout(new BorderLayout());
    
    // Demo only
    label = new Label();
    add(label, BorderLayout.SOUTH);

    // �إ߿��C
    MenuBar menuBar = createMenuBar();

    // �w�q�����ϥΪ̤��������C
    setMenuBar(menuBar);

    // �]�w�������j�p
    this.setSize(250, 250);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private MenuBar createMenuBar() {
    // �إ߿��C
    MenuBar menuBar = new MenuBar();

    // �إ߿��
    for (int i=0; i<menulabel.length; i++){
      // �إ߿��
      menu[i] = new Menu(menulabel[i]);
      menu[i].setFont(new Font("dialog", Font.PLAIN, 11));

      // �[�J���ܿ��C
      menuBar.add(menu[i]);
    }

    // �إ߿�涵��
    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          menu[i].addSeparator();
        }
        else {
          // �إ߿�涵��
          menuitem[i][j] = new MenuItem(menuitemlabel[i][j]);
          menuitem[i][j].setFont(new Font("dialog", Font.PLAIN, 11));

          // ���U ActionListener
          menuitem[i][j].addActionListener(this);

          // �[�J��涵��
          menu[i].add(menuitem[i][j]);
        }          
      }
    }

    return menuBar;
  }

  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    MenuItem menuitem = (MenuItem)e.getSource();

    if (menuitem.getLabel().equals("Exit")) { // Exit
      System.exit(0);
    }
    else {
      label.setText("Select "  + menuitem.getLabel() + " Menu Item.");
    }
  }
}