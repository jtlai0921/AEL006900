import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class CustomDialogDemo extends java.awt.Frame implements ActionListener {

  String menulabel[]={"File"};

  String menuitemlabel[][]={
    {"New", "Open", "-", "Save", "Save As...", "-", "Exit"}
  };

  Menu menu[] = new Menu[1];
  MenuItem menuitem[][] = new MenuItem[1][7];

  // Demo only
  Label label;

  public static void main(String args[]){
    new CustomDialogDemo();
  }
  
  // �غc�禡
  public CustomDialogDemo() {
    super("File Dialog Demo");

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
        if (menuitemlabel[i][j].equals("-")) {
          // �s�W���j�u
          menu[i].addSeparator();
        }
        else {
          // �إ߿�涵��
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

    if (menuitem.getLabel().equals("New")) { // New
      String title = menuitem.getLabel();
      
      CustomDialog dialog = new CustomDialog(this, title, title + " Dialog Demo.", true);
  
      boolean flag = dialog.getState();
      
      if(flag) 
        label.setText("Select OK Button.");
      else
        label.setText("Select Cancel Button.");
    }
    else if (menuitem.getLabel().equals("Open")) { // Open
      FileDialog filedialog = new FileDialog(this, "Open File", FileDialog.LOAD);
      
      // �w�q�z�����: ���ɦW��java
      Filter filter = new Filter("java");

      // �]�w�ɮ׹�ܲ����ɮצW�ٿz�����
      // �u�C�X���ɦW��java���ɮצW��
      filedialog.setFilenameFilter(filter);

      filedialog.setVisible(true);
      
      label.setText("File Selected: " + filedialog.getDirectory() + filedialog.getFile());
    }
    else if (menuitem.getLabel().equals("Save")) { // Save
      FileDialog filedialog = new FileDialog(this, "Save File", FileDialog.SAVE);
      
      // �w�q�z�����: ���ɦW��java
      Filter filter = new Filter("java");

      // �]�w�ɮ׹�ܲ����ɮצW�ٿz�����
      // �u�C�X���ɦW��java���ɮצW��
      filedialog.setFilenameFilter(filter);

      filedialog.setVisible(true);
      
      label.setText("File Selected: " + filedialog.getDirectory() + filedialog.getFile());
    }
    else if (menuitem.getLabel().equals("Save As...")) { // Save As...
      String title = menuitem.getLabel();
      
      CustomDialog dialog = new CustomDialog(this, title, title + " Dialog Demo.", true);
  
      boolean flag = dialog.getState();
      
      if(flag) 
        label.setText("Select OK Button.");
      else
        label.setText("Select Cancel Button.");
    }
    else if (menuitem.getLabel().equals("Exit")) { // Exit
      System.exit(0);
    }
  }
}
