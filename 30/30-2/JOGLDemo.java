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

  // 建構函式
  public JOGLDemo() {
    super("JOGL with AWT");

    // 建立GLCanvas物件 
    GLCanvas canvas = new GLCanvas();

    // 註冊GLEventListener
    canvas.addGLEventListener(this);

    // 將GLCanvas物件加入Frame中
    this.add(canvas);

    // 建立選單功能列
    MenuBar menubar = createMenuBar();
    
    // 定義視窗使用者介面之選單功能列
    this.setMenuBar(menubar);

    this.validate();
    // 設定視窗的大小
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
    // 建立選單列
    MenuBar menuBar = new MenuBar();

    // 建立選單
    for (int i=0; i<menulabel.length; i++){
      // 建立選單
      menu[i] = new Menu(menulabel[i]);
      //menu[i].setFont(new Font("dialog", Font.PLAIN, 11));

      // 加入選單至選單列
      menuBar.add(menu[i]);
    }

    // 建立選單項目
    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // 加入分隔線
          menu[i].addSeparator();
        }
        else {
          // 建立選單項目
          menuitem[i][j] = new MenuItem(menuitemlabel[i][j]);
          //menuitem[i][j].setFont(new Font("dialog", Font.PLAIN, 11));

          // 註冊 ActionListener
          menuitem[i][j].addActionListener(this);

          // 加入選單項目
          menu[i].add(menuitem[i][j]);
        }          
      }
    }

    return menuBar;
  }

  // 實作GLEventListener介面之方法
  // 初始化JOGL
  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();

    // 以黑色清除背景 
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    // 以平滑曲線描邊 
    gl.glShadeModel(GL.GL_SMOOTH);

    // 啟用光源 
    gl.glEnable(GL.GL_LIGHTING);
    // 啟用光源0 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }
    
  // 繪製圖形
  public void display(GLAutoDrawable drawable) {
    // 設定光源位置
    float position[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    javax.media.opengl.GL gl = drawable.getGL();
    com.sun.opengl.util.GLUT glut = new GLUT();

    // 以目前之顏色清除視窗
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // 複製目前之矩陣至堆疊最上層
    gl.glPushMatrix();

    // 沿x,y,z座標旋轉指定角度
    gl.glRotatef(30.0f, 1.0f, 1.0f, 0.0f);

    // 設定光源0
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);

    // 啟用光源 
    gl.glEnable(GL.GL_LIGHTING);

    // 繪製環形曲面 
    glut.glutSolidTorus(0.3f, 0.8f, 50, 50);

    // 自堆疊最上層移除矩陣
    gl.glPopMatrix();
    
    // 即刻執行JOGL指令 
    gl.glFlush();
  }

  // 當視窗大小改變時
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    if(height == 0)
      height = 1;
    
    double ratio = 1.0 * (double) width/(double) height;

    // 設定座標系統
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    // 設定視界大小為視窗之大小
    gl.glViewport(0, 0, width, height);

    // 設定透視度
    glu.gluPerspective(45.0, ratio, 1.0, 1000.0);

    // 設定座標系統
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();

    glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0);
  }

  // 當顯示模式或裝置改變時
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
  
  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    MenuItem menuitem = (MenuItem)e.getSource();

    if (menuitem.getLabel().equals("Exit")) { // Exit
      System.exit(0);
    }
  }
}
