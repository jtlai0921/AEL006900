import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GridBagLayoutDemo extends javax.swing.JApplet {
    
  // �غc�禡
  public GridBagLayoutDemo() {
  }

  public void init() {
    javax.swing.JButton jbutton;

    GridBagLayout gridbaglayout = new GridBagLayout();
    GridBagConstraints gbConstraints = new GridBagConstraints();
    
    // �����w�qJFrame��Layout Manager��GridBagLayout
    this.setLayout(gridbaglayout);

    // �P�ɧ��ܪ���e�׻P���ץH����ܰϰ줧�����P������V
    gbConstraints.fill = GridBagConstraints.BOTH;
    
    // �̥[�v��Ҥ��t���󶡤�����V�B�~���ϰ�
    gbConstraints.weightx = 1.0;
    jbutton = new JButton("1");
    // �]�wGrid Bag Layout�����󪺭���
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    // �����N����[��JFrame��
    this.add(jbutton);

    jbutton = new JButton("2");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);

    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    // ����N�񺡩ҳѾl���ϰ�C�ƩΦ��
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    jbutton = new JButton("3");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);

    // �̥[�v��Ҥ��t���󶡤�����V�B�~���ϰ�
    gbConstraints.weightx = 0.0;       
    jbutton = new JButton("4");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    // ����N�񺡰��F�Ҧb�C���̫�@�ӳ��ϰ�H�~���ϰ�C��
    gbConstraints.gridwidth = GridBagConstraints.RELATIVE; 
    jbutton = new JButton("5");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    // ����N�񺡩ҳѾl���ϰ�C�ƩΦ��
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    jbutton = new JButton("6");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    gbConstraints.gridwidth = 1;          
    // �]�w����t�m�ɩҦ��ڰϰ�檺�ƥ�
    gbConstraints.gridheight = 2;
    // �̥[�v��Ҥ��t���󶡫�����V�B�~���ϰ�
    gbConstraints.weighty = 1.0;
    jbutton = new JButton("7");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // �̥[�v��Ҥ��t���󶡫�����V�B�~���ϰ�
    gbConstraints.weighty = 0.0;       
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    gbConstraints.gridheight = 1;      
    jbutton = new JButton("8");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);

    jbutton = new JButton("9");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
  }

  static {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }
}
