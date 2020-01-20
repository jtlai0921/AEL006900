import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
 
public class GreenTheme extends DefaultMetalTheme {
  // �]�wPrimary�D�D�C��
  private final ColorUIResource primary1 = new ColorUIResource(50, 100, 50);
  private final ColorUIResource primary2 = new ColorUIResource(100, 150, 100);
  private final ColorUIResource primary3 = new ColorUIResource(155, 205, 155); 

  // �]�wSecondary�D�D�C��
  private final ColorUIResource secondary1 = new ColorUIResource(110, 110, 110);
  private final ColorUIResource secondary2 = new ColorUIResource(160, 160, 160);
  private final ColorUIResource secondary3 = new ColorUIResource(205, 230, 205);
  
  // ���oPrimary�D�D�C��
  protected ColorUIResource getPrimary1() { return primary1; }  
  protected ColorUIResource getPrimary2() { return primary2; } 
  protected ColorUIResource getPrimary3() { return primary3; } 

  // ���oSecondary�D�D�C��
  protected ColorUIResource getSecondary1() { return secondary1; }
  protected ColorUIResource getSecondary2() { return secondary2; }
  protected ColorUIResource getSecondary3() { return secondary3; }
}
