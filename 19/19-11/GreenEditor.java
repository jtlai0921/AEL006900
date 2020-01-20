import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

// ��@ChangeListener����
public class GreenEditor extends JLabel implements ChangeListener {
  public GreenEditor(JSpinner spinner) {
    setOpaque(true);

    GreenModel model = (GreenModel)(spinner.getModel());
    // �]�w�I���C��
    setBackground(model.getColor());
    // �]�w�̤p�ؤo
    setMinimumSize(new Dimension(60, 15));
    // �]�w�̨Τؤo
    setPreferredSize(new Dimension(60, 15));

    // ���U ChangeListener
    spinner.addChangeListener(this);
  }

  // ���󪬺A���ܮ�
  public void stateChanged(ChangeEvent e) {
    // �P�_�ƥ󪺨ӷ�
    JSpinner spinner = (JSpinner)(e.getSource());
    // ���oSpinner�s�誫��MVC Model
    GreenModel model = (GreenModel)(spinner.getModel());
    // �]�w�I���C��
    setBackground(model.getColor());
  }
}