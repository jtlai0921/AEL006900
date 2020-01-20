import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class PresentationTheme extends DefaultMetalTheme {
  // 設定字型
  private final FontUIResource controlFont = new FontUIResource("Dialog", Font.BOLD, 18);
  private final FontUIResource smallFont = new FontUIResource("Dialog", Font.PLAIN, 14);
  private final FontUIResource systemFont = new FontUIResource("Dialog", Font.PLAIN, 18);
  private final FontUIResource userFont = new FontUIResource("SansSerif", Font.PLAIN, 18);
  
  // 取得字型
  public FontUIResource getControlTextFont() { return controlFont;}
  public FontUIResource getMenuTextFont() { return controlFont;}
  public FontUIResource getSubTextFont() { return smallFont;}
  public FontUIResource getSystemTextFont() { return systemFont;}
  public FontUIResource getUserTextFont() { return userFont;}
  public FontUIResource getWindowTitleFont() { return controlFont;}
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    
    final int internalFrameIconSize = 22;
    table.put("InternalFrame.closeIcon", MetalIconFactory.getInternalFrameCloseIcon(internalFrameIconSize));
    table.put("InternalFrame.iconifyIcon", MetalIconFactory.getInternalFrameMinimizeIcon(internalFrameIconSize));
    table.put("InternalFrame.maximizeIcon", MetalIconFactory.getInternalFrameMaximizeIcon(internalFrameIconSize));
    table.put("InternalFrame.minimizeIcon", MetalIconFactory.getInternalFrameAltMaximizeIcon(internalFrameIconSize));
    
    table.put("ScrollBar.width", new Integer(21));
  }
}
