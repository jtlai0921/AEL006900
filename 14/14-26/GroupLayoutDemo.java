import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GroupLayoutDemo extends javax.swing.JFrame {

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

    new GroupLayoutDemo();
  }

  // 建構函式
  public GroupLayoutDemo() {
    super("Group Layout Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    JLabel lblDate = new JLabel("Date:");
    JLabel lblSubject = new JLabel("Subject:");
    JLabel lblCurrent = new JLabel("Subjects:");

    JTextField txtSubject = new JTextField();

    JTable tblSubject = new JTable();
    tblSubject.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {}, new String [] {"Date", "Subject"})
      {
        Class[] types = new Class [] {
          java.lang.String.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
          false, false
        };

        public Class getColumnClass(int columnIndex) {
          return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
          return canEdit [columnIndex];
        }
      }
    );

    JScrollPane jScrollPane1 = new JScrollPane();
    jScrollPane1.setViewportView(tblSubject);

    JButton btnAdd = new JButton("Add");
    JButton btnDelete = new JButton("Delete");

    JButton btnExit = new JButton("Exit");
    btnExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });

    JSpinner jSpinner1 = new JSpinner();
    jSpinner1.setModel(new SpinnerDateModel());
    jSpinner1.setEditor(new JSpinner.DateEditor(jSpinner1, "MM-dd-yyyy hh:mm"));

    JLabel lblLogo = new JLabel("GroupLayout");
    lblLogo.setIcon(new ImageIcon(cl.getResource("images/dukeswing.gif")));
    lblLogo.setFont(new Font("Dialog", 1, 14));
    lblLogo.setBackground(new Color(80, 110, 180));
    lblLogo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    lblLogo.setOpaque(true);
    lblLogo.setPreferredSize(new Dimension(375, 60));

    JSeparator jSeparator1 = new JSeparator();
    JSeparator jSeparator2 = new JSeparator();

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(btnAdd)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(btnDelete)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
        .addComponent(btnExit)
        .addContainerGap())
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
        .addContainerGap())
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(lblCurrent)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
          .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblSubject)
              .addComponent(lblDate))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
              .addComponent(txtSubject, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(10, 10, 10)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(lblDate)
          .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(lblSubject)
          .addComponent(txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lblCurrent)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnAdd)
          .addComponent(btnDelete)
          .addComponent(btnExit))
        .addContainerGap())
    );

    // 設定視窗的大小
    this.setSize(400, 350);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}