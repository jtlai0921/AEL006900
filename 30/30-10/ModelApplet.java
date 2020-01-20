import java.awt.*;
import javax.swing.*;

public class ModelApplet extends JApplet {
  public void init() {
    // ¦Û­qJOGLPanelÃþ§O
    Model displayPanel = new Model(this);

    this.setLayout(new BorderLayout());
    this.add(displayPanel, BorderLayout.CENTER);
  }

  public void start() {}
  public void stop() {}
  public void destroy() {}
}

