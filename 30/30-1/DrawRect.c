/* 以C語言開發OpenGL程式 */
/* DrawRect.c            */

#include <windows.h>
#include <gl/glut.h>

/* 主程式 */
void main(void) {
  /* 設定顯示模式 */
  glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
  /* 建立視窗 */
  glutCreateWindow("DrawRect");
  /* 定義繪圖函式為自定之RenderScene */
  glutDisplayFunc(RenderScene);
  /* 定義當視窗大小改變時所呼叫之函式 */
  glutReshapeFunc(ChangeSize);
  /* 自定函式以初始化環境 */
  Initialize();
  /* 進入OpenGL迴圈 */
  glutMainLoop();
}

/* 初始化環境 */
void Initialize(void) {
  /* 以指定顏色清除背景 */
  glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
}

/* 繪圖 */
void RenderScene(void) {
  /* 以目前之顏色清除視窗 */
  glClear(GL_COLOR_BUFFER_BIT);
  /* 設定目前繪圖顏色為紅色 */
  glColor3f(1.0f, 0.0f, 0.0f);
  /* 繪製矩形並以目前顏色填滿面積 */
  glRectf(100.0f, 150.0f, 150.0f, 100.0f);
  /* 即刻執行OpenGL指令 */
  glFlush();
}

/* 當視窗大小改變時 */
void ChangeSize(GLsizei w, GLsizei h) {
  /* 設定視界大小為視窗之大小 */
  glViewport(0, 0, w, h);
  /* 重設座標系統*/
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();

  if (w <= h) 
    glOrtho (0.0f, 250.0f, 0.0f, 250.0f*h/w, 1.0, -1.0);
  else 
    glOrtho (0.0f, 250.0f*w/h, 0.0f, 250.0f, 1.0, -1.0);

  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
}
