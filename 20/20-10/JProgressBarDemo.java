import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.util.*;

public class JProgressBarDemo extends JFrame implements ActionListener {

  JProgressBar jprogressbar;
  JButton jbutton;
  JLabel jlabel;
  JPanel jpanel;
  
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JProgressBarDemo();
  }

  // �غc�禡
  public JProgressBarDemo() {
    super("JProgressBar Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�i�צC��ؤ��ݩʭ�
    UIManager.put("ProgressBar.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w�i�צC�r�����ݩʭ�
    UIManager.put("ProgressBar.font", new Font("dialog", Font.BOLD, 12));
    
    // �]�w�i�צC�e���C�⤧�ݩʭ�
    UIManager.put("ProgressBar.foreground", Color.PINK);
    
    // �]�w�i�צC�I���C�⤧�ݩʭ�
    UIManager.put("ProgressBar.background", Color.RED);

    // �]�w�Q����ɪ��I���C�⤧�ݩʭ�
    UIManager.put("ProgressBar.selectionBackground", Color.GREEN);
    
    // �]�w�Q����ɪ��e���C�⤧�ݩʭ�
    UIManager.put("ProgressBar.selectionForeground", Color.CYAN);
        
    // ���o�i�צCPluggable Look and Feel�ݩʭ�
    System.out.println("JProgressBar Look and Feel: " + UIManager.getString("ProgressBarUI"));

    jpanel = new JPanel();
    
    jlabel = new JLabel("�i��:");
    jlabel.setPreferredSize(new Dimension(250, 25));
    jpanel.add(jlabel);
    
    // �إ߶i�צC
    jprogressbar = new JProgressBar();
    // �]�w�i�צC���̤p��
    jprogressbar.setMinimum(0);
    // �]�w�i�צC���̤j��
    jprogressbar.setMaximum(100);
    // �]�w�i�צC���ثe��
    jprogressbar.setValue(0);
    // �]�w�i�צC�O�_���\��ܦr��
    jprogressbar.setStringPainted(true);
    jprogressbar.setBounds(20, 35, 200, 20);
    jprogressbar.setPreferredSize(new Dimension(250, 20));
    jpanel.add(jprogressbar);

    jbutton = new JButton("�}�l");
    jbutton.setPreferredSize(new Dimension(80, 30));
    jbutton.addActionListener(this);
    jpanel.add(jbutton);

    this.add(jpanel);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(280, 140));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == jbutton) {
      // �]�w�O�_�ҥΪ���
      jbutton.setEnabled(false);

      // �]�w�����ԡ]���L���^�ƹ����
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      
      for(int index = 0; index <= 100; index++)  {
        // �����H���ü�
        Random random = new Random(index);
    
        for(int value = 0; value < random.nextFloat() * 10000; value++) {
          System.out.println("Index = " + index + ", " + "Value = " + value + ", " + jprogressbar.getPercentComplete());
        }

        // ���o�i�צC�������ʤ���
        jlabel.setText("�i��: " + index + "/100 (" + jprogressbar.getPercentComplete() + ")");
        
        Rectangle rect = jlabel.getBounds();
        rect.x = 0;
        rect.y = 0;
        jlabel.paintImmediately(rect);

        // �]�w�i�צC���ثe��
        jprogressbar.setValue(index);
        rect = jprogressbar.getBounds();
        rect.x = 0;
        rect.y = 0;
        jprogressbar.paintImmediately(rect);
      }

      // �]�w���w�]�ƹ���СA�q�`���b�����
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

      jbutton.setEnabled(true);
     }
  }
}
