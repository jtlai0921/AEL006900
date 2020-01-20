import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.text.*;
import java.util.*;

public class JSpinnerDemo extends javax.swing.JFrame {

  JLabel[] jlabel = new JLabel[4];
  JSpinner[] jspinner = new JSpinner[4];

  SpinnerModel[] model = new SpinnerModel[4];

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JSpinnerDemo();
  }

  // �غc�禡
  public JSpinnerDemo() {
    super("JSpinner Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�qLayout Manager��BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    // �w�qJPanel��Layout Manager��SpringLayout
    jpanel.setLayout(new SpringLayout());

    // ���oJPanel��Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    /*   
     *  Year
     */
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    
    // �إ߼Ʀr�L�դu�㪺MVC Model
    model[0] = new SpinnerNumberModel(year, year - 100, year + 100, 1);
    
    // �HSpinnerNumberModel�إ�Spinner����
    jspinner[0] = new JSpinner(model[0]);
    // �]�wJSpinner���s�誫��
    // �Ʀr�榡��"#"
    jspinner[0].setEditor(new JSpinner.NumberEditor(jspinner[0], "#"));
    
    jlabel[0] = new JLabel("Year:");
    jlabel[0].setLabelFor(jspinner[0]);
    jpanel.add(jlabel[0]);
    jpanel.add(jspinner[0]);
    
    /*   
     *  Month
     */
    String[] months = getMonth(); 
    
    // �إߥѰ}�C��java.util.List�����ҩw�q���L�դu�㪺MVC Model
    model[1] = new SpinnerListModel(months);
    
    // �HSpinnerListModel�إ�Spinner����
    jspinner[1] = new JSpinner(model[1]);
    
    JFormattedTextField ftf = null;
    
    // ���oJSpinner���s�誫��
    JComponent editor = jspinner[1].getEditor();
    
    // �榡�ƽs�誫��
    if (editor instanceof JSpinner.DefaultEditor) {
      ftf = ((JSpinner.DefaultEditor)editor).getTextField();
    } 
    else {
      ftf = null;
    }
    if (ftf != null ) {
      // �]�w�s�誫����ܦ��
      ftf.setColumns(8); 
      // �]�w�s�誫�󪺤�������覡
      ftf.setHorizontalAlignment(JTextField.RIGHT);
    }
    
    jlabel[1] = new JLabel("Month:");
    jlabel[1].setLabelFor(jspinner[1]);
    jpanel.add(jlabel[1]);
    jpanel.add(jspinner[1]);
    
    /*   
     *  Date
     */
    Date value = calendar.getTime();
    calendar.add(Calendar.YEAR, -100);
    Date min = calendar.getTime();
    calendar.add(Calendar.YEAR, 200);
    Date max = calendar.getTime();
    
    // �إߤ���L�դu�㪺MVC Model
    model[2] = new SpinnerDateModel(value, min, max, Calendar.YEAR);
    
    // �HSpinnerDateModel�إ�Spinner����
    jspinner[2] = new JSpinner(model[2]);
    // �]�wJSpinner���s�誫��
    // �Ʀr�榡��"MM/dd/yyyy"
    jspinner[2].setEditor(new JSpinner.DateEditor(jspinner[2], "MM/dd/yyyy"));
    
    jlabel[2] = new JLabel("Date:");
    jlabel[2].setLabelFor(jspinner[2]);
    jpanel.add(jlabel[2]);
    jpanel.add(jspinner[2]);
    
    /*   
     *  Weekday
     */
    String[] weekday = new java.text.DateFormatSymbols().getWeekdays();
    
    // �إߥѰ}�C��java.util.List�����ҩw�q���L�դu�㪺MVC Model
    model[3] = new SpinnerListModel(Arrays.asList(weekday).subList(1, 8));
    
    // �HSpinnerListModel�إ�Spinner����
    jspinner[3] = new JSpinner(model[3]);
    // �]�wJSpinner���s�誫��
    jspinner[3].setEditor(new JSpinner.ListEditor(jspinner[3]));
    
    jlabel[3] = new JLabel("Weekday:");
    jlabel[3].setLabelFor(jspinner[3]);
    jpanel.add(jlabel[3]);
    jpanel.add(jspinner[3]);

    /*   
     *  �t�m����
     */
    Spring x = Spring.constant(10);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jspinner.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(10)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jspinner.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jspinner[i]);

      consL.setX(x);
      consR.setX(rX);

      rWidth = consR.getWidth(); 
      
      consR.setWidth(Spring.sum(Spring.sum(parentWidth, negRX), Spring.constant(-5)));

      if (i == 0) {
        consL.setY(y);
        consR.setY(y);
        maxHeightSpring = Spring.sum(y, Spring.max(consL.getHeight(), consR.getHeight()));
      } 
      else {  
        Spring y1 = Spring.sum(Spring.max(lastConsL.getConstraint(SpringLayout.SOUTH), lastConsR.getConstraint(SpringLayout.SOUTH)), Spring.constant(5));

        consL.setY(y1);
        consR.setY(y1);
        maxHeightSpring = Spring.sum(Spring.constant(5), Spring.sum(maxHeightSpring, Spring.max(consL.getHeight(), consR.getHeight())));
      }
      lastConsL = consL;
      lastConsR = consR;
    }  
    
    SpringLayout.Constraints consParent = layout.getConstraints(jpanel);
    consParent.setConstraint(SpringLayout.EAST, Spring.sum(rX, Spring.sum(rWidth, Spring.constant(5))));
    consParent.setConstraint(SpringLayout.SOUTH, Spring.sum(maxHeightSpring, Spring.constant(5)));

    this.add(jpanel, BorderLayout.CENTER);

    // �]�w�������j�p
    this.setSize(200, 150);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  String[] getMonth() {
    String[] month = new java.text.DateFormatSymbols().getMonths();
    int lastIndex = month.length - 1;
  
    if (month[lastIndex] == null || month[lastIndex].length() <= 0) { 
      String[] months = new String[lastIndex];
      System.arraycopy(month, 0, months, 0, lastIndex);
      return months;
    } 
    else { 
      return month;
    }
  }
}
