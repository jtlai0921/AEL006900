import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class LowVisionTheme extends ContrastTheme {
  // 設定字型
  private final FontUIResource controlFont = new FontUIResource("Dialog", Font.BOLD, 24);
  private final FontUIResource smallFont = new FontUIResource("Dialog", Font.PLAIN, 20);
  private final FontUIResource systemFont = new FontUIResource("Dialog", Font.PLAIN, 24);
  private final FontUIResource userFont = new FontUIResource("SansSerif", Font.PLAIN, 24);
  private final FontUIResource windowTitleFont = new FontUIResource("Dialog", Font.BOLD, 24);

  // 取得字型
  public FontUIResource getControlTextFont() { return controlFont;}
  public FontUIResource getMenuTextFont() { return controlFont;}
  public FontUIResource getSubTextFont() { return smallFont;}
  public FontUIResource getSystemTextFont() { return systemFont;}
  public FontUIResource getUserTextFont() { return userFont;}
  public FontUIResource getWindowTitleFont() { return windowTitleFont;}

  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    
    final int internalFrameIconSize = 30;
    table.put("InternalFrame.closeIcon", MetalIconFactory.getInternalFrameCloseIcon(internalFrameIconSize));
    table.put("InternalFrame.minimizeIcon", MetalIconFactory.getInternalFrameAltMaximizeIcon(internalFrameIconSize));
    table.put("InternalFrame.maximizeIcon", MetalIconFactory.getInternalFrameMaximizeIcon(internalFrameIconSize));
    table.put("InternalFrame.minimizeIcon", MetalIconFactory.getInternalFrameAltMaximizeIcon(internalFrameIconSize));
    
    Border blackLineBorder = new BorderUIResource(new MatteBorder( 2,2,2,2, Color.black));
    Border textBorder = blackLineBorder;

    table.put("TextArea.border", textBorder);
    table.put("TextField.border", textBorder);
    table.put("TextPane.font", textBorder);
    table.put("ToolTip.border", blackLineBorder);
    table.put("ScrollPane.border", blackLineBorder);
    table.put("ScrollBar.width", new Integer(25));
  }
}
