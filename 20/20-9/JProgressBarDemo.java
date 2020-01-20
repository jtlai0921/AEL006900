import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.beans.*;
import java.util.*;

public class JProgressBarDemo extends JFrame implements ActionListener, PropertyChangeListener {

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
			// �]�w�i�צC�O�_�HIndeterminate�Ҧ����
			jprogressbar.setIndeterminate(true);

      // �]�w�O�_�ҥΪ���
      jbutton.setEnabled(false);

      // �]�w�����ԡ]���L���^�ƹ����
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      Task task = new Task();
      // ���UPropertyChangeListener����
      task.addPropertyChangeListener(this);
      task.execute();
     }
  }

	// �����ݩʪ��A���ܮ�
  public void propertyChange(PropertyChangeEvent e) {
    if ("progress" == e.getPropertyName()) {
    	// ���o���ܪ��ݩʭȦW��
      int progress = (Integer) e.getNewValue();
      // �]�w�i�צC�O�_�HIndeterminate�Ҧ����
      jprogressbar.setIndeterminate(false);

      jlabel.setText("�i��: " + progress + "/100 (" + jprogressbar.getPercentComplete() + ")");

      Rectangle rect = jlabel.getBounds();
      rect.x = 0;
      rect.y = 0;
      jlabel.paintImmediately(rect);

      // �]�w�i�צC���ثe��
      jprogressbar.setValue(progress);
      rect = jprogressbar.getBounds();
      rect.x = 0;
      rect.y = 0;
      jprogressbar.paintImmediately(rect);
    }
  }

	// �~��SwingWorker��H���O
  class Task extends SwingWorker<Void, Void> {

		// �мgdoInBackground��k
		// �p�⵲�G
    @Override
    public Void doInBackground() {
      Random random = new Random();

      int progress = 0;

      setProgress(0);

      try {
        Thread.sleep(500 + random.nextInt(1000));
      } 
      catch (InterruptedException ignore) {}
      
      while (progress < 100) {
        try {
          Thread.sleep(random.nextInt(500));
        } 
        catch (InterruptedException ignore) {}

        progress += 1;

        setProgress(Math.min(progress, 100));
      }
      return null;
    }

		// �мgdone��k
		// doInBackground��k��������榹��k
    public void done() {
      // �]�w���w�]�ƹ���СA�q�`���b�����
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

      jbutton.setEnabled(true);
    }
  }
}
