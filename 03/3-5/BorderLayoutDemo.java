import java.awt.*;
import java.applet.*;

public class BorderLayoutDemo extends java.applet.Applet {

  // �غc�禡
  public BorderLayoutDemo() {
  }

  public void init() {
    // ���oJava Applet���Ѽ�
    int hGap = Integer.parseInt(getParameter("hGap"));
    int vGap = Integer.parseInt(getParameter("vGap"));

    BorderLayout borderlayout = new BorderLayout();

    // �]�w���󶡪��������Z
    borderlayout.setHgap(hGap);
    // �]�w���󶡪��������Z
    borderlayout.setVgap(vGap);

    // �]�wJava Applet��Layout Manager
    setLayout(borderlayout); 
    
    Button button1 = new Button("EAST");
    Button button2 = new Button("SOUTH");
    Button button3 = new Button("WEST");
    Button button4 = new Button("NORTH");
    Button button5 = new Button("CENTER");

    // �N����[��Java Applet��
    add(button1, BorderLayout.EAST);
    add(button2, BorderLayout.SOUTH);
    add(button3, BorderLayout.WEST);
    add(button4, BorderLayout.NORTH);
    add(button5, BorderLayout.CENTER);
  }
}
