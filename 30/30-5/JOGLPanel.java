import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// JOGL
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

public class JOGLPanel extends JPanel implements GLEventListener, ActionListener {

  JApplet MainApplet;
  
  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[1][1];

  String menu[]={"Help|H"};

  String menuItem[][]={{"About|A|about.gif"}};

  static {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);
    
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception exception) { }
  }

  // �غc�禡
  public JOGLPanel(JApplet MainApplet) {
    this.MainApplet = MainApplet;

    // �إ�GLJPanel���� 
    GLJPanel glpanel = new GLJPanel();

    // ���UGLEventListener
    glpanel.addGLEventListener(this);

    this.setLayout(new BorderLayout());
    // �NGLJPanel����[�JJPanel��
    this.add(glpanel, BorderLayout.CENTER);

    // �إ߿��\��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������\��C
    MainApplet.setJMenuBar(jmenubar);

    this.validate();
  }

  private JMenuBar createMenuBar() {
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �إ߿��\��C
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menu.length];
    
    // �إ߿��
    for (int i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      // �]�w���U�O�X
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      // �[�J���ܿ��\��C
      jmenubar.add(jmenu[i]);
    }

    for(int i=0; i<menu.length; i++){
      for(int j=0; j<menuItem[i].length; j++){
        if (menuItem[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
        }
        else {
          // �إ߿�涵��
          jmenuItem[i][j] = new JMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
          // �]�w��涵�اU�O�X
          jmenuItem[i][j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));

          // �إ߿�涵�عϹ�
          if (menuItem[i][j].endsWith(".gif")) 
            jmenuItem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));

          // ���U ActionListener
          jmenuItem[i][j].addActionListener(this);

          // �[�J��涵��
          jmenu[i].add(jmenuItem[i][j]);
        }
      }
    }

    return jmenubar;
  }

  // ��@GLEventListener��������k
  // ��l��JOGL
  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    // �H�¦�M���I�� 
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    // �H���Ʀ��u�y�� 
    gl.glShadeModel(GL.GL_SMOOTH);

    // �ҥΥ��� 
    gl.glEnable(GL.GL_LIGHTING);
    // �ҥΥ���0 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }
    
  // ø�s�ϧ�
  public void display(GLAutoDrawable drawable) {
    // �]�w������m
    float position[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    javax.media.opengl.GL gl = drawable.getGL();
    com.sun.opengl.util.GLUT glut = new GLUT();

    // �H�ثe���C��M������
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // �ƻs�ثe���x�}�ܰ��|�̤W�h
    gl.glPushMatrix();

    // �ux,y,z�y�б�����w����
    gl.glRotatef(30.0f, 1.0f, 1.0f, 0.0f);

    // �]�w����0
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);

    // �ҥΥ��� 
    gl.glEnable(GL.GL_LIGHTING);

    // ø�s���Φ��� 
    glut.glutSolidTorus(0.3f, 0.8f, 50, 50);

    // �۰��|�̤W�h�����x�}
    gl.glPopMatrix();
    
    // �Y�����JOGL���O 
    gl.glFlush();
  }

  // ������j�p���ܮ�
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    if(height == 0)
      height = 1;
    
    double ratio = 1.0 * (double) width/(double) height;

    // �]�w�y�Шt��
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    // �]�w���ɤj�p���������j�p
    gl.glViewport(0, 0, width, height);

    // �]�w�z����
    glu.gluPerspective(45.0, ratio, 1.0, 1000.0);

    // �]�w�y�Шt��
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();

    glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0);
  }

  // ����ܼҦ��θ˸m���ܮ�
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
  
  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == jmenuItem[0][0]){  // About
      JOptionPane.showMessageDialog(null, "JOGL with Swing", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
