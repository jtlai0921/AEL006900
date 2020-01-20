import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

// 實作ChangeListener介面
public class BlueEditor extends JLabel implements ChangeListener {
  public BlueEditor(JSpinner spinner) {
    setOpaque(true);

    BlueModel model = (BlueModel)(spinner.getModel());
    // 設定背景顏色
    setBackground(model.getColor());
    // 設定最小尺寸
    setMinimumSize(new Dimension(60, 15));
    // 設定最佳尺寸
    setPreferredSize(new Dimension(60, 15));

    // 註冊 ChangeListener
    spinner.addChangeListener(this);
  }

  // 當物件狀態改變時
  public void stateChanged(ChangeEvent e) {
    // 判斷事件的來源
    JSpinner spinner = (JSpinner)(e.getSource());
    // 取得Spinner編輯物件的MVC Model
    BlueModel model = (BlueModel)(spinner.getModel());
    // 設定背景顏色
    setBackground(model.getColor());
  }
}