import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class JDICTable extends JTable implements Scrollable {
  public boolean getScrollableTracksViewportHeight() {
    Component parent = getParent();

    if (parent instanceof JViewport) {
      return parent.getHeight() > getPreferredSize().height;
    }

    return false;
  }
}