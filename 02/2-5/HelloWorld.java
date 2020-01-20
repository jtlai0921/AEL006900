import java.awt.*;
import java.awt.event.*;

public class HelloWorld extends java.awt.Frame { // �~��java.awt.Frame���O
  public static void main(String args[]){
    new HelloWorld();
  }

  // �غc�禡
  public HelloWorld() {
    // �]�w�������D
    super("Hello World");

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

    // �����ƥ�
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}