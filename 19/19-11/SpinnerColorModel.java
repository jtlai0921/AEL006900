import java.awt.*;
import javax.swing.*;

// �~��SpinnerNumberModel���O
public class SpinnerColorModel extends SpinnerNumberModel {

  // �غc�禡
  public SpinnerColorModel(int value) {
    // �]�wSpinner���s�誫��ثe���Ʀr (value)
    // �]�w�}�l�Ʀr��0 (minimum)
    // �]�w�����Ʀr��255 (maximum)
    // �]�w�ǦC�����W�Ʀr��5 (stepSize)
    super(value, 0, 255, 5);
  }

  public int getIntValue() {
    Integer value = (Integer)getValue();
    return value.intValue();
  }

  public Color getColor() {
    int value = getIntValue();

    return new Color(value, value, value);
  }
}
