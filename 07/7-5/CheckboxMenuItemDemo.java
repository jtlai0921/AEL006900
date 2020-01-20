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
  
  // �غc�禡
  public CheckboxMenuItemDemo() {
    super("Checkbox Menu Item Demo");

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

      // �s�W���ܿ��C
      menuBar.add(menu[i]);
    }

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (i==0 && j==6) { // �l���
          // �إߤl���
          Menu submenu = new Menu(menuitemlabel[i][j]);
          submenu.setFont(new Font("dialog", Font.PLAIN, 11));
          
          // �إ߮֨������涵��
          for (int k=0; k<cbmenuitemlabel.length; k++){
            checkboxmenuitem[k] = new CheckboxMenuItem(cbmenuitemlabel[k]);
            
            // �]�w�֨������涵�ؤ�������A
            if (k==0) 
              checkboxmenuitem[k].setState(true);
            
            checkboxmenuitem[k].setFont(new Font("dialog", Font.PLAIN, 11));

            // ���U ItemListener
            checkboxmenuitem[k].addItemListener(this);
      
            // �s�W�֨������涵�ئܤl���
            submenu.add(checkboxmenuitem[k]);
          }
      
          menu[i].add(submenu);
        }
        else if (menuitemlabel[i][j].equals("-")) {
          // �s�W���j�u
          menu[i].addSeparator();
        }
        else {
          menuitem[i][j] = new MenuItem(menuitemlabel[i][j]);

          menuitem[i][j].setFont(new Font("dialog", Font.PLAIN, 11));

          // ���U ActionListener
          menuitem[i][j].addActionListener(this);

          // �s�W��涵��
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

  public void itemStateChanged(ItemEvent e) {
    // ���o���Ͷ��بƥ󪺪���
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