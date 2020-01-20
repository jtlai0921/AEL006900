import java.awt.*;
import java.awt.event.*;

public class GridBagLayoutDemo extends java.awt.Frame {

  public static void main(String args[]){
    new GridBagLayoutDemo();
  }
  
  // �غc�禡
  public GridBagLayoutDemo() {
    super("Grid Bag Layout Demo");

    Button button;
    GridBagLayout gridbaglayout = new GridBagLayout();
    GridBagConstraints gbConstraints = new GridBagConstraints();
    
    // �w�q Layout Manager �� GridBagLayout
    setLayout(gridbaglayout);
    
    // �P�ɧ��ܪ���e�׻P���ץH����ܰϰ줧�����P������V
    gbConstraints.fill = GridBagConstraints.BOTH;
    
    // �̥[�v��Ҥ��t���󶡤�����V�B�~���ϰ�
    gbConstraints.weightx = 1.0;
    button = new Button("1");
    // �]�wGrid Bag Layout�����󪺭���
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    button = new Button("2");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    // ����N�񺡩ҳѾl���ϰ�C�ƩΦ��
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    button = new Button("3");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    // �̥[�v��Ҥ��t���󶡤�����V�B�~���ϰ�
    gbConstraints.weightx = 0.0;       
    button = new Button("4");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    // ����N�񺡰��F�Ҧb�C���̫�@�ӳ��ϰ�H�~���ϰ�C��
    gbConstraints.gridwidth = GridBagConstraints.RELATIVE; 
    button = new Button("5");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    // ����N�񺡩ҳѾl���ϰ�C�ƩΦ��
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    button = new Button("6");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    // �]�w����t�m�ɩҦ��ڰϰ�C���ƥ�
    gbConstraints.gridwidth = 1;          
    // �]�w����t�m�ɩҦ��ڰϰ�檺�ƥ�
    gbConstraints.gridheight = 2;
    // �̥[�v��Ҥ��t���󶡫�����V�B�~���ϰ�
    gbConstraints.weighty = 1.0;
    button = new Button("7");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);
    
    // �̥[�v��Ҥ��t���󶡫�����V�B�~���ϰ�
    gbConstraints.weighty = 0.0;       
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    gbConstraints.gridheight = 1;      
    button = new Button("8");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);

    button = new Button("9");
    gridbaglayout.setConstraints(button, gbConstraints);
    add(button);

    // �]�w�������j�p
    this.setSize(300, 300);

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
}