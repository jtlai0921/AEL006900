import java.awt.*;
import java.applet.*;
import javax.swing.*;

public class MoveLightApplet extends JApplet {

  public void init() {
    MoveLight displayPanel = new MoveLight(this);

    this.setLayout(new BorderLayout());
    this.add(displayPanel, BorderLayout.CENTER);
  }

  public void start() {}
  public void stop() {}
  public void destory() {}
}
