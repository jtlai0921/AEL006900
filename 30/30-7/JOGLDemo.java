import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// JOGL
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

public class JOGLDemo extends JFrame implements GLEventListener, ActionListener, MouseListener, MouseMotionListener {

  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[2][1];

  String menu[]={"File|F", "Help|H"};

  String menuItem[][]={{"Exit|X"}, {"About|A|about.gif"}};

  // JOGL
  private GLU glu = new GLU();
  private GLUT glut = new GLUT();
  private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
  private int ball;
  private float angle = 0.0f;

  private int prevMouseX, prevMouseY;
  private boolean mouseRButtonDown = false;

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

    new JOGLDemo();
  }

  // �غc�禡
  public JOGLDemo() {
    super("JOGL with Mouse Event");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // Set OpenGL Capabilities
    javax.media.opengl.GLCapabilities glcapabilities = new GLCapabilities();
    // Set Drawable Factory
    GLDrawableFactory gldrawablefactory = GLDrawableFactory.getFactory();
    GLDrawable gldrawable = gldrawablefactory.getGLDrawable(this, glcapabilities, null);
    // Set Double Buffer
    glcapabilities.setDoubleBuffered(true);
    
    // �إ�GLJPanel���� 
    GLJPanel glpanel = new GLJPanel(glcapabilities);

    // ���UGLEventListener
    glpanel.addGLEventListener(this);
    // ���UMouseListener
    glpanel.addMouseListener(this);
    // ���UMouseMotionListener
    glpanel.addMouseMotionListener(this);

    this.setLayout(new BorderLayout());
    // �NGLJPanel����[�JJFrame��
    this.add(glpanel, BorderLayout.CENTER);

    // �إ߿��\��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������\��C
    this.setJMenuBar(jmenubar);

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

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });  

    // Animator
    final Animator animator = new Animator(glpanel);

    glpanel.requestFocusInWindow();
    // �}�l�ʵe
    animator.start();
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

    gl.setSwapInterval(1);

    float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    gl.glEnable(GL.GL_CULL_FACE);
    // �ҥΥ��� 
    gl.glEnable(GL.GL_LIGHTING);
    // �ҥΥ���0 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);

    /* make the Ball */
    ball = gl.glGenLists(1);

    GLUquadric quad;
    quad = glu.gluNewQuadric();
    glu.gluQuadricOrientation(quad, GLU.GLU_OUTSIDE);
    glu.gluQuadricNormals    (quad, GLU.GLU_SMOOTH);
    glu.gluQuadricTexture    (quad, true);

    gl.glNewList(ball, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);

    glut.glutWireSphere(5, 25, 25);
    gl.glEndList();
            
    gl.glEnable(GL.GL_NORMALIZE);
  }

  // ø�s�ϧ�
  public void display(GLAutoDrawable drawable) {
    angle += 100.0f;

    GL gl = drawable.getGL();
    
    if ((drawable instanceof GLJPanel) &&
        !((GLJPanel) drawable).isOpaque() &&
        ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {

      // �H�ثe���C��M������
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    } 
    else {
      // �H�ثe���C��M������
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }
            
    // �ƻs�ثe���x�}�ܰ��|�̤W�h
    gl.glPushMatrix();
    // �ux�y�б�����w����
    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
    // �uy�y�б�����w����
    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
    // �uz�y�б�����w����
    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);
            
    // �ux,y,z�y�б�����w����
    gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(ball);
    // �۰��|�̤W�h�����x�}
    gl.glPopMatrix();
    // �Y�����JOGL���O 
    gl.glFlush();
  }

  // ������j�p���ܮ�
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    float h = (float)height / (float)width;
            
    // �]�w�y�Шt��
    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();
    gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 60.0f);
    // �]�w�y�Шt��
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glTranslatef(0.0f, 0.0f, -40.0f);
  }

  // ����ܼҦ��θ˸m���ܮ�
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  // ��@�ƹ��ƥ󪺤�k
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {
    prevMouseX = e.getX();
    prevMouseY = e.getY();
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = true;
    }
  }
    
  public void mouseReleased(MouseEvent e) {
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = false;
    }
  }
    
  // ��@�ƹ����ʨƥ󪺤�k
  public void mouseMoved(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    Dimension size = e.getComponent().getSize();

    float thetaY = 360.0f * ( (float)(x-prevMouseX)/(float)size.width);
    float thetaX = 360.0f * ( (float)(prevMouseY-y)/(float)size.height);
    
    prevMouseX = x;
    prevMouseY = y;

    view_rotx += thetaX;
    view_roty += thetaY;
  }
  
  // ��@ActionListener��������k
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == jmenuItem[0][0]){  // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        new Thread(new Runnable() {
          public void run() {
            System.exit(0);
          }
        }).start();
      }
    }
    else if(e.getSource() == jmenuItem[1][0]){  // About
      JOptionPane.showMessageDialog(null, "JOGL with Mouse Event", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
