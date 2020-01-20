import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.io.*;
import java.net.*;

// JNLP API
import javax.jnlp.*;

public class BasicServiceDemo extends javax.swing.JFrame {

  private JButton btnOpen = new JButton();

  private JTextField txtURL = new JTextField();

  // JNLP BasicService
  private BasicService bs;

  private URL codebase = null;
   
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

    new BasicServiceDemo();
  }

  // �غc�禡
  public BasicServiceDemo() {
    super("JNLP BasicService");

    initJNLPService();

    Container contentPane = getContentPane();
    // �w�q contentPane �� Layout Manager �� BorderLayout
    contentPane.setLayout(new BorderLayout());

    JLabel lblURL = new JLabel("URL:");
    lblURL.setDisplayedMnemonic('U');

    // �]�w���ҩ��ݪ�����A�N��������ҩ��O�X�]Mnemonic Key�^��
    // �Ҩ��o��J�J�I�]Focus�^������C
    lblURL.setLabelFor(txtURL);

    btnOpen.setMnemonic('O');
    btnOpen.setText("Open URL");
    btnOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOpen_actionPerformed(e);
      }
    });

    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    panel1.setBorder(BorderFactory.createEmptyBorder(10,5,5,5));

    JPanel panel2 = new JPanel();
    panel2.setLayout(new FlowLayout());

    panel1.add(lblURL, BorderLayout.WEST);
    panel1.add(txtURL, BorderLayout.CENTER);
    panel2.add(btnOpen);
    contentPane.add(panel1, BorderLayout.CENTER);
    contentPane.add(panel2, BorderLayout.SOUTH);

    this.validate();
    this.setSize(new Dimension(330, 130));

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

  private void initJNLPService() {
    try { 
      // �d�ߪA��
      bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 
    } 
    catch (UnavailableServiceException e) { 
      // �L�k�d�ߨ���w�A�ȡA�^��UnavailableServiceException
      bs = null; 
    }
    
    if (bs != null) { 
      // �^��Java���ε{������m�A�æ^��java.net.URL���O
      codebase = bs.getCodeBase(); 
      
      txtURL.setText(codebase.toString());
    }
  }

  private void btnOpen_actionPerformed(ActionEvent e) {
    if (bs != null) { 
      // �ˬd�s�����O�_�䴩JNLP
      if (bs.isWebBrowserSupported()) {
        if (codebase != null) {
          // �ҰʥΤ���s�����}��URL
          bs.showDocument(codebase); 
        }
      }
    }
  }
}
