import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.io.*;
import java.util.*;

public class OptionDialog extends JDialog {
  JButton btnBlank = new JButton();
  JButton btnSelect = new JButton();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  
  JTextField txtHome = new JTextField();
  JTextField txtHttp = new JTextField();
  JTextField txtPort = new JTextField();

  private static String history[] = new String[10];
    
  public OptionDialog() {
    this.setTitle("Option");

    // ©w¸qLayout Manager ¬° BorderLayout
    this.setLayout(new BorderLayout());
  
    loadProperties();

    btnSelect.setText("Select");
    btnSelect.setMnemonic('S');
    btnSelect.setPreferredSize(new Dimension(80, 30));
    btnSelect.setMinimumSize(new Dimension(80, 30));
    btnSelect.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });
  
    btnBlank.setText("Blank");
    btnBlank.setMnemonic('B');
    btnBlank.setPreferredSize(new Dimension(80, 30));
    btnBlank.setMinimumSize(new Dimension(80, 30));
    btnBlank.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });

    btnOK.setText("OK");
    btnOK.setMnemonic('O');
    btnOK.setMinimumSize(new Dimension(80, 30));
    btnOK.setPreferredSize(new Dimension(80, 30));
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });
  
    btnCancel.setText("Cancel");
    btnCancel.setMnemonic('C');
    btnCancel.setMinimumSize(new Dimension(80, 30));
    btnCancel.setPreferredSize(new Dimension(80, 30));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });

    JPanel Panel1 = new JPanel();
    Panel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Home Page"));
    Panel1.setMinimumSize(new Dimension(10, 100));
    Panel1.setPreferredSize(new Dimension(10, 100));
    Panel1.setLayout(new BorderLayout());
  
    JPanel Panel2 = new JPanel();
    Panel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Proxy Setting"));
    Panel2.setMinimumSize(new Dimension(10, 90));
    Panel2.setPreferredSize(new Dimension(10, 90));
    Panel2.setLayout(new BorderLayout());

    JPanel Panel3 = new JPanel();
    Panel3.setBorder(BorderFactory.createEmptyBorder());
    Panel3.setMinimumSize(new Dimension(10, 40));
    Panel3.setPreferredSize(new Dimension(10, 40));
  
    JPanel Panel4 = new JPanel();
    Panel4.setLayout(new BorderLayout());
    Panel4.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

    JPanel Panel5 = new JPanel();
    Panel5.setMinimumSize(new Dimension(10, 40));
    Panel5.setPreferredSize(new Dimension(10, 40));
  
    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.setRows(2);
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(2);
    gridLayout1.setVgap(2);
    JPanel Panel6 = new JPanel();
    Panel6.setBorder(BorderFactory.createEmptyBorder(4,10,4,2));
    Panel6.setMinimumSize(new Dimension(50, 10));
    Panel6.setPreferredSize(new Dimension(50, 10));
    Panel6.setLayout(gridLayout1);
  
    GridLayout gridLayout2 = new GridLayout();
    gridLayout2.setRows(2);
    gridLayout2.setColumns(1);
    gridLayout2.setHgap(2);
    gridLayout2.setVgap(4);
    JPanel Panel7 = new JPanel();
    Panel7.setBorder(BorderFactory.createEmptyBorder(4,2,4,5));
    Panel7.setLayout(gridLayout2);
  
    JLabel jlabel1 = new JLabel("HTTP:");
    JLabel jlabel2 = new JLabel("Port:");

    Panel4.add(txtHome, BorderLayout.CENTER);
    Panel1.add(Panel4, BorderLayout.CENTER);
    Panel5.add(btnSelect);
    Panel5.add(btnBlank);
    Panel1.add(Panel5, BorderLayout.SOUTH);

    Panel6.add(jlabel1);
    Panel6.add(jlabel2);
    Panel2.add(Panel6, BorderLayout.WEST);
    Panel7.add(txtHttp);
    Panel7.add(txtPort);
    Panel2.add(Panel7, BorderLayout.CENTER);
    Panel3.add(btnOK);
    Panel3.add(btnCancel);

    this.add(Panel1, BorderLayout.NORTH);
    this.add(Panel2, BorderLayout.CENTER);
    this.add(Panel3, BorderLayout.SOUTH);
  
    this.validate();
    this.setSize(new Dimension(260, 260));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dlgSize = this.getSize();
    if (dlgSize.height > screenSize.height)
      dlgSize.height = screenSize.height;
    if (dlgSize.width > screenSize.width)
      dlgSize.width = screenSize.width;
    this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setModal(true);
    this.setResizable(false);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });    
  }
  
  private void loadProperties() {
    try {
      Properties p = new Properties();
      
      FileInputStream in = new FileInputStream("browser.properties");
      
      p.load(in);
      
      txtHome.setText(p.getProperty("home.page")) ;
      txtHttp.setText(p.getProperty("http.proxyHost")) ;
      txtPort.setText(p.getProperty("http.proxyPort")) ;

      for (int i = 0; i < 10; i++) 
        history[i] = p.getProperty("history." + i) ;
      
      in.close() ;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  private void saveProperties() {
    try {
      Properties p = new Properties();
      
      FileOutputStream os = new FileOutputStream("browser.properties");
      
      p.setProperty("home.page", txtHome.getText()) ;
      p.setProperty("http.proxyHost", txtHttp.getText()) ;
      p.setProperty("http.proxyPort", txtPort.getText()) ;
      
      for (int i = 0; i < 10; i++) 
        p.setProperty("history." + i, history[i]) ;
      
      p.store(os, null);
      os.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void button_actionPerformed(ActionEvent e) {
    if (e.getSource() == btnOK) {
      saveProperties();
      dispose();
    }
    else if (e.getSource() == btnCancel) {
      dispose();
    }
    else if (e.getSource() == btnSelect) {
      selectFile();
    }
    else if (e.getSource() == btnBlank) {
      txtHome.setText("<Blank>");
    }
  }

  private void selectFile() {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setCurrentDirectory(new File("."));

    HtmlFileFilter[] filter = new HtmlFileFilter[2];
    
    filter[0] = new HtmlFileFilter("HTML Files", "html");
    filter[1] = new HtmlFileFilter("HTM Files" , "Htm");

    for (int i=0; i < filter.length; i++) 
      jfileChooser.addChoosableFileFilter(filter[i]);

    jfileChooser.setMultiSelectionEnabled(false);
    jfileChooser.setFileFilter(filter[0]);

    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setDialogTitle("Select Home Page");
  
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
      return;
  
    this.repaint();
  
    String fileName = jfileChooser.getSelectedFile().getAbsolutePath();
  
    txtHome.setText(fileName);
  }
}
