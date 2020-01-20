import java.awt.*;
import java.awt.event.*;

public class WindowEventDemo extends java.awt.Frame {

  // Main method
  public static void main(String[] args) {
    new WindowEventDemo();
  }

  // �غc�禡
  public WindowEventDemo() {
    super("Window Event Demo");

    // �HInner Class���覡�ϥ�WindowAdapter
    this.addWindowListener(new WindowAdapter() {
      public void windowActivated(WindowEvent e) {
        System.out.println("�������A�ѫD�@�Τ��ܬ��@�Τ�");
      }
      
      public void windowClosed(WindowEvent e) {
        System.out.println("�����w����");
      }
      
      public void windowClosing(WindowEvent e) {
        System.out.println("�������b����");
        dispose();
        System.exit(0);
      }
      
      public void windowDeactivated(WindowEvent e) {
        System.out.println("�������A�ѧ@�Τ��ܬ��D�@�Τ�");
      }
      
      public void windowDeiconified(WindowEvent e) {
        System.out.println("�������A�ѳ̤p���ܬ����`���A");
      }
      
      public void windowIconified(WindowEvent e) {
        System.out.println("�������A�ѥ��`���A�ܬ��̤p��");
      }
      
      public void windowOpened(WindowEvent e) {
        System.out.println("�����}��");
      }    
    });    

    this.addWindowFocusListener(new WindowAdapter() {
      public void windowGainedFocus(WindowEvent e) {
        System.out.println("�������o��J�J�I");
      }
    
      public void windowLostFocus(WindowEvent e) {
        System.out.println("�������h��J�J�I");
      }
    });    
        
    this.addWindowStateListener(new WindowAdapter() {
      public void windowStateChanged(WindowEvent e) {
        System.out.println("�������A����");
      }
    });    

    // �]�w�������j�p
    this.setSize(200, 200);

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
  }
}
