import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.io.*;
import javax.accessibility.*;

public class FontList extends JPanel implements ListSelectionListener, ActionListener {

  JLabel jlabel;
  JTextField jtextfield;
  JList jlist;
  JScrollPane jscrollpane;

  public FontList(String as[], String s) {
    setLayout(null);
    jlabel = new FontListLabel(s, 2);
    add(jlabel);

    jtextfield = new FontListText();
    jtextfield.addActionListener(this);
    jlabel.setLabelFor(jtextfield);
    add(jtextfield);

    jlist = new FontListList(as);
    jlist.setVisibleRowCount(4);
    jlist.addListSelectionListener(this);
    jscrollpane = new JScrollPane(jlist);

    add(jscrollpane);
  }

  public FontList(String s, int i) {
    setLayout(null);
    jlabel = new FontListLabel(s, 2);
    add(jlabel);
    jtextfield = new FontListText(i);
    jtextfield.addActionListener(this);
    jlabel.setLabelFor(jtextfield);
    add(jtextfield);
    jlist = new FontListList();
    jlist.setVisibleRowCount(4);
    jlist.addListSelectionListener(this);
    jscrollpane = new JScrollPane(jlist);
    add(jscrollpane);
  }

  class AccessibleFontList extends javax.swing.JComponent.AccessibleJComponent {
    public AccessibleFontList() {
    }

    public String getAccessibleName() {
      if(super.accessibleName != null)
        return super.accessibleName;
      else
        return jlabel.getText();
    }

    public AccessibleRole getAccessibleRole() {
      return AccessibleRole.LIST;
    }
  }

  class FontListList extends JList {
    public FontListList(){
    }

    public FontListList(String str[]) {
      super(str);
    }

    public AccessibleContext getAccessibleContext() {
      return FontList.this.getAccessibleContext();
    }
  }

  class FontListText extends JTextField {
    public FontListText(){
    }

    public FontListText(int i) {
      super(i);
    }

    public AccessibleContext getAccessibleContext(){
      return FontList.this.getAccessibleContext();
    }
  }

  class FontListLabel extends JLabel {
    public FontListLabel(String s, int i) {
      super(s, i);
    }

    public AccessibleContext getAccessibleContext() {
      return FontList.this.getAccessibleContext();
    }
  }

  public void setToolTipText(String tip) {
    super.setToolTipText(tip);
    jlabel.setToolTipText(tip);
    jtextfield.setToolTipText(tip);
    jlist.setToolTipText(tip);
  }

  public void setDisplayedMnemonic(char c) {
    jlabel.setDisplayedMnemonic(c);
  }

  public void setSelected(String s) {
    jlist.setSelectedValue(s, true);
    jtextfield.setText(s);
  }

  public String getSelected() {
    return jtextfield.getText();
  }

  public void setSelectedInt(int i) {
    setSelected(Integer.toString(i));
  }

  public int getSelectedInt() {
    try {
      return Integer.parseInt(getSelected());
    }
    catch(NumberFormatException ex) {
      return -1;
    }
  }

  public void valueChanged(ListSelectionEvent e) {
    Object obj = jlist.getSelectedValue();

    if(obj != null)
      jtextfield.setText(obj.toString());
  }

  public void actionPerformed(ActionEvent e){
    ListModel listmodel = jlist.getModel();
    String s = jtextfield.getText().toLowerCase();

    for(int i = 0; i < listmodel.getSize(); i++) {
      String s1 = (String)listmodel.getElementAt(i);

      if(!s1.toLowerCase().startsWith(s))
        continue;

      jlist.setSelectedValue(s1, true);

      break;
    }
  }

  public void addListSelectionListener(ListSelectionListener listselectionlistener) {
    jlist.addListSelectionListener(listselectionlistener);
  }

  public Dimension getPreferredSize() {
    Insets insets = getInsets();
    Dimension dimension = jlabel.getPreferredSize();
    Dimension dimension1 = jtextfield.getPreferredSize();
    Dimension dimension2 = jscrollpane.getPreferredSize();
    int i = Math.max(Math.max(dimension.width, dimension1.width), dimension2.width);
    int j = dimension.height + dimension1.height + dimension2.height;

    return new Dimension(i + insets.left + insets.right, j + insets.top + insets.bottom);
  }

  public Dimension getMaximumSize() {
    Insets insets = getInsets();
    Dimension dimension = jlabel.getMaximumSize();
    Dimension dimension1 = jtextfield.getMaximumSize();
    Dimension dimension2 = jscrollpane.getMaximumSize();
    int i = Math.max(Math.max(dimension.width, dimension1.width), dimension2.width);
    int j = dimension.height + dimension1.height + dimension2.height;

    return new Dimension(i + insets.left + insets.right, j + insets.top + insets.bottom);
  }

  public Dimension getMinimumSize() {
    Insets insets = getInsets();
    Dimension dimension = jlabel.getMinimumSize();
    Dimension dimension1 = jtextfield.getMinimumSize();
    Dimension dimension2 = jscrollpane.getMinimumSize();
    int i = Math.max(Math.max(dimension.width, dimension1.width), dimension2.width);
    int j = dimension.height + dimension1.height + dimension2.height;

    return new Dimension(i + insets.left + insets.right, j + insets.top + insets.bottom);
  }

  public void doLayout() {
    Insets insets = getInsets();
    Dimension dimension = getSize();
    int i = insets.left;
    int j = insets.top;
    int k = dimension.width - insets.left - insets.right;
    int l = dimension.height - insets.top - insets.bottom;
    Dimension dimension1 = jlabel.getPreferredSize();
    jlabel.setBounds(i, j, k, dimension1.height);
    j += dimension1.height;
    Dimension dimension2 = jtextfield.getPreferredSize();
    jtextfield.setBounds(i, j, k, dimension2.height);
    j += dimension2.height;
    jscrollpane.setBounds(i, j, k, l - j);
  }

  public AccessibleContext getAccessibleContext() {
    if(super.accessibleContext == null)
      super.accessibleContext = new AccessibleFontList();

    return super.accessibleContext;
  }
}