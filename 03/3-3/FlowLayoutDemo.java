import java.awt.*;
import java.awt.event.*;

public class FlowLayoutDemo extends java.awt.Frame {

  public static void main(String args[]){
    new FlowLayoutDemo();
  }
  
  // �غc�禡
  public FlowLayoutDemo() {
    super("Flow Layout Demo");
    
    // �V�����������
    FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER); 

    // �]�w���󶡪��������Z
    flowlayout.setHgap(10);
    // �]�w���󶡪��������Z
    flowlayout.setVgap(10);

    // �]�wLayout Manager
    setLayout(flowlayout); 
    
    Button button1 = new Button("OK");
    Button button2 = new Button("Cancel");
    Button button3 = new Button("Yes");
    Button button4 = new Button("No");
    
    // �N����[��Frame��
    add(button1);
    add(button2);
    add(button3);
    add(button4);

    // �]�w�������j�p
    this.setSize(300, 100);

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
}