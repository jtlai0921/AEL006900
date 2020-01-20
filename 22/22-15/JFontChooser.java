import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class JFontChooser extends JDialog {

  JCheckBox chkBold;
  JCheckBox chkItalic;
  JCheckBox chkUnderline;
  JCheckBox chkStrikethrough;
  JCheckBox chkSubscript;
  JCheckBox chkSuperscript;
  JComboBox cboColor;

  int option;

  FontList lstFontName;
  FontList lstFontSize;

  MutableAttributeSet mutattributes;
  JLabel lblPreview;
  static String fontNames[];
  static String fontSizes[] = (new String[] {
     "8",  "9", "10", "11", "12", "14", "16", "18", 
    "20", "22", "24", "26", "28", "36", "48", "72"
  });

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    JFontChooser jfontchooser = new JFontChooser(new JFrame());
    
    SimpleAttributeSet set = new SimpleAttributeSet();
    StyleConstants.setFontFamily(set, "Verdana");
    StyleConstants.setFontSize(set, 12);
    jfontchooser.setAttributes(set);
    
    jfontchooser.setVisible(true);
  }

  // 建構函式
  public JFontChooser(JFrame jframe) {
    super(jframe, "JFontChooser");

    option = -1;

    this.setLayout(new BoxLayout(getContentPane(), 1));

    GraphicsEnvironment graphicsenvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    fontNames = graphicsenvironment.getAvailableFontFamilyNames();

    lstFontName = new FontList(fontNames, "Name:");
    lstFontName.setDisplayedMnemonic('N');
    lstFontName.setToolTipText("Font Name");
    lstFontName.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updatePreview();
      }
    });

    lstFontSize = new FontList(fontSizes, "Size:");
    lstFontSize.setDisplayedMnemonic('s');
    lstFontSize.setToolTipText("Font Size");
    lstFontSize.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updatePreview();
      }
    });

    JPanel jpanel = new JPanel(new GridLayout(1, 2, 10, 2));
    jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
    jpanel.add(lstFontName);
    jpanel.add(lstFontSize);

    this.add(jpanel);
    
    chkBold = new JCheckBox("Bold");
    chkBold.setMnemonic('B');
    chkBold.setToolTipText("Bold");
    chkBold.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionevent) {
        updatePreview();
      }
    });
            
    chkItalic = new JCheckBox("Italic");
    chkItalic.setMnemonic('I');
    chkItalic.setToolTipText("Italic");
    chkItalic.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionevent) {
        updatePreview();
      }
    });
    

    chkUnderline = new JCheckBox("Underline");
    chkUnderline.setMnemonic('U');
    chkUnderline.setToolTipText("Underline");

    chkStrikethrough = new JCheckBox("Strikethrough");
    chkStrikethrough.setMnemonic('R');
    chkStrikethrough.setToolTipText("Strikethrough");

    chkSubscript = new JCheckBox("Subscript");
    chkSubscript.setMnemonic('T');
    chkSubscript.setToolTipText("Subscript");

    chkSuperscript = new JCheckBox("Superscript");
    chkSuperscript.setMnemonic('P');
    chkSuperscript.setToolTipText("Superscript");

    jpanel = new JPanel(new GridLayout(2, 3, 10, 5));
    jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Effects"));
    jpanel.add(chkBold);
    jpanel.add(chkItalic);
    jpanel.add(chkUnderline);
    jpanel.add(chkStrikethrough);
    jpanel.add(chkSubscript);
    jpanel.add(chkSuperscript);

    this.add(jpanel);
    this.add(Box.createVerticalStrut(5));

    JLabel jlabel = new JLabel("Color:");
    jlabel.setDisplayedMnemonic('c');

    cboColor = new JComboBox();
    jlabel.setLabelFor(cboColor);
    cboColor.setToolTipText("Font Color");

    ToolTipManager.sharedInstance().registerComponent(cboColor);

    int ai[] = {0, 128, 192, 255};
    
    for(int i = 0; i < ai.length; i++){
      for(int j = 0; j < ai.length; j++){
        for(int k = 0; k < ai.length; k++){
          Color color = new Color(ai[i], ai[j], ai[k]);
          cboColor.addItem(color);
        }
      }
    }

    cboColor.setRenderer(new ColorRenderer());
    cboColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionevent) {
        updatePreview();
      }
    });

    jpanel = new JPanel();
    jpanel.setLayout(new BoxLayout(jpanel, 0));
    jpanel.add(Box.createHorizontalStrut(10));
    jpanel.add(jlabel);
    jpanel.add(Box.createHorizontalStrut(20));
    jpanel.add(cboColor);
    jpanel.add(Box.createHorizontalStrut(10));

    this.add(jpanel);

    lblPreview = new JLabel("Preview Font", 0);
    lblPreview.setBackground(Color.white);
    lblPreview.setForeground(Color.black);
    lblPreview.setOpaque(true);
    lblPreview.setBorder(new LineBorder(Color.black));
    lblPreview.setPreferredSize(new Dimension(120, 40));

    jpanel = new JPanel(new BorderLayout());
    jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Preview"));
    jpanel.add(lblPreview, "Center");

    this.add(jpanel);

    JButton btnOK = new JButton("OK");
    btnOK.setToolTipText("OK");

    // 設定預設按鈕
    this.getRootPane().setDefaultButton(btnOK);

    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        option = 0;
        System.exit(0);
      }
    });

    jpanel = new JPanel(new FlowLayout());
    JPanel jpanel1 = new JPanel(new GridLayout(1, 2, 10, 2));
    jpanel1.add(btnOK);

    JButton btnCancel = new JButton("Cancel");
    btnCancel.setToolTipText("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        option = 2;
        System.exit(0);
      }
    });

    jpanel1.add(btnCancel);
    jpanel.add(jpanel1);

    this.add(jpanel);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.pack();
    //this.setSize(new Dimension(200, 210));
    this.setResizable(false);
    //this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  public void setAttributes(AttributeSet attributeset) {
    mutattributes = new SimpleAttributeSet(attributeset);

    String s = StyleConstants.getFontFamily(attributeset);

    lstFontName.setSelected(s);
    int i = StyleConstants.getFontSize(attributeset);
    lstFontSize.setSelectedInt(i);

    chkBold.setSelected(StyleConstants.isBold(attributeset));
    chkItalic.setSelected(StyleConstants.isItalic(attributeset));
    chkUnderline.setSelected(StyleConstants.isUnderline(attributeset));
    chkStrikethrough.setSelected(StyleConstants.isStrikeThrough(attributeset));
    chkSubscript.setSelected(StyleConstants.isSubscript(attributeset));
    chkSuperscript.setSelected(StyleConstants.isSuperscript(attributeset));
    cboColor.setSelectedItem(StyleConstants.getForeground(attributeset));

    updatePreview();
  }

  public AttributeSet getAttributes(){
    if(mutattributes == null){
      return null;
    } 
    else {
      StyleConstants.setFontFamily(mutattributes, lstFontName.getSelected());
      StyleConstants.setFontSize(mutattributes, lstFontSize.getSelectedInt());
      StyleConstants.setBold(mutattributes, chkBold.isSelected());
      StyleConstants.setItalic(mutattributes, chkItalic.isSelected());
      StyleConstants.setUnderline(mutattributes, chkUnderline.isSelected());
      StyleConstants.setStrikeThrough(mutattributes, chkStrikethrough.isSelected());
      StyleConstants.setSubscript(mutattributes, chkSubscript.isSelected());
      StyleConstants.setSuperscript(mutattributes, chkSuperscript.isSelected());
      StyleConstants.setForeground(mutattributes, (Color)cboColor.getSelectedItem());

      return mutattributes;
    }
  }

  public int getOption() {
    return option;
  }

  protected void updatePreview() {
    String s = lstFontName.getSelected();
    int i = lstFontSize.getSelectedInt();

    if(i <= 0)
      return;

    int j = 0;

    if(chkBold.isSelected())
      j |= 1;

    if(chkItalic.isSelected())
      j |= 2;

    Font font = new Font(s, j, i);
    lblPreview.setFont(font);

    Color color = (Color)cboColor.getSelectedItem();
    lblPreview.setForeground(color);

    lblPreview.repaint();
  }
}
