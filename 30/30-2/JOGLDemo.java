import java.awt.*;
import java.awt.event.*;

// JOGL
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

public class JOGLDemo extends Frame implements GLEventListener, ActionListener {

  Menu menu[] = new Menu[1];
  MenuItem menuitem[][] = new MenuItem[1][1];

  String menulabel[]={"File"};

  String menuitemlabel[][]={{"Exit"}};

  public static void main(String[] args) {
    new JOGLDemo();
  }

  // �غc�禡
  public JOGLDemo() {
    super("JOGL with AWT");

    // �إ�GLCanvas���� 
    GLCanvas canvas = new GLCanvas();

    // ���UGLEventListener
    canvas.addGLEventListener(this);

    // �NGLCanvas����[�JFrame��
    this.add(canvas);

    // �إ߿��\��C
    MenuBar menubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������\��C
    this.setMenuBar(menubar);

    this.validate();
    // �]�w�������j�p
    this.setSize(new Dimension(300, 300));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });  
  }

  private MenuBar createMenuBar() {
    // �إ߿��C
    MenuBar menuBar = new MenuBar();

    // �إ߿��
    for (int i=0; i<menulabel.length; i++){
      // �إ߿��
      menu[i] = new Menu(menulabel[i]);
      //menu[i].setFont(new Font("dialog", Font.PLAIN, 11));

      // �[�J���ܿ��C
      menuBar.add(menu[i]);
    }

    // �إ߿�涵��
    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // �[�J���j�u
          menu[i].addSeparator();
        }
        else {
          // �إ߿�涵��
          menuitem[i][j] = new MenuItem(menuitemlabel[i][j]);
          //menuitem[i][j].setFont(new Font("dialog", Font.PLAIN, 11));

          // ���U ActionListener
          menuitem[i][j].addActionListener(this);

          // �[�J��涵��
          menu[i].add(menuitem[i][j]);
        }          
      }
    }

    return menuBar;
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
    // ���o���Ͱʧ@�ƥ󪺿�涵��
    MenuItem menuitem = (MenuItem)e.getSource();

    if (menuitem.getLabel().equals("Exit")) { // Exit
      System.exit(0);
    }
  }
}
