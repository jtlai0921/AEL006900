import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment.*;

public class JFindDialog extends JDialog {

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

    new JFindDialog(new JFrame());
  }

  // «Øºc¨ç¦¡
  public JFindDialog(JFrame jframe) {
    super(jframe, "Find");

    JLabel label = new JLabel("Find What:");;
    JTextField textField = new JTextField();
    JCheckBox chkMatchCase = new JCheckBox("Match Case");
    JCheckBox chkWrapAround = new JCheckBox("Wrap Around");
    JCheckBox chkWholeWords = new JCheckBox("Whole Words");
    JCheckBox chkSearchBackwards = new JCheckBox("Search Backwards");
    JButton btnFind = new JButton("Find");
    JButton btnCancel = new JButton("Cancel");

    chkMatchCase.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkWrapAround.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkWholeWords.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkSearchBackwards.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createSequentialGroup()
      .addComponent(label)
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(textField)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkMatchCase)
            .addComponent(chkWholeWords))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkWrapAround)
            .addComponent(chkSearchBackwards))))
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(btnFind)
        .addComponent(btnCancel))
    );
   
    layout.linkSize(SwingConstants.HORIZONTAL, btnFind, btnCancel);

    layout.setVerticalGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(label)
        .addComponent(textField)
        .addComponent(btnFind))
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(chkMatchCase)
            .addComponent(chkWrapAround))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(chkWholeWords)
            .addComponent(chkSearchBackwards)))
        .addComponent(btnCancel))
    );
    
    this.pack();
    this.setResizable(false);
    this.setVisible(true);

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
    this.setResizable(false);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}

