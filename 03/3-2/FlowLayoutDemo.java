import java.awt.*;
import java.applet.*;

public class FlowLayoutDemo extends java.applet.Applet {

  // �غc�禡
  public FlowLayoutDemo() {
  }

  public void init() {
    // ���oJava Applet���Ѽ�
    String align = getParameter("alignment");
    int hGap = Integer.parseInt(getParameter("hGap"));
    int vGap = Integer.parseInt(getParameter("vGap"));

    FlowLayout flowlayout = null;

    if (align.equals("CENTER"))
      flowlayout = new FlowLayout(FlowLayout.CENTER);
    else if (align.equals("LEADING"))
      flowlayout = new FlowLayout(FlowLayout.LEADING);
    else if (align.equals("LEFT"))
      flowlayout = new FlowLayout(FlowLayout.LEFT);
    else if (align.equals("RIGHT"))
      flowlayout = new FlowLayout(FlowLayout.RIGHT);
    else if (align.equals("TRAILING"))
      flowlayout = new FlowLayout(FlowLayout.TRAILING);
    
    // �]�w���󶡪��������Z
    flowlayout.setHgap(hGap);
    // �]�w���󶡪��������Z
    flowlayout.setVgap(vGap);

    // �]�wJava Applet��Layout Manager
    setLayout(flowlayout); 

    Button button1 = new Button("OK");
    Button button2 = new Button("Cancel");
    Button button3 = new Button("Yes");
    Button button4 = new Button("No");
    
    // �N����[��Java Applet��
    // �w�]Layout Manager��FlowLayout
    add(button1);
    add(button2);
    add(button3);
    add(button4);
  }
}
