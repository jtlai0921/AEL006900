import java.awt.*;
import javax.swing.*;

public class JOGLApplet extends JApplet {

  public void init() {
    // �ۭqJOGLPanel���O
    JOGLPanel displayPanel = new JOGLPanel(this);
    this.setLayout(new BorderLayout());
    this.add(displayPanel, BorderLayout.CENTER);
  }

  public void start() {}
  public void stop() {}
  public void destory() {}
}
