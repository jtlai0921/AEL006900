import java.awt.*;

public class HelloWorld extends java.awt.Frame { // �~��java.awt.Frame���O
  public static void main(String args[]){
    new HelloWorld();
  }

  // �غc�禡
  public HelloWorld() {
    // �]�w�������D
    super("Hello World");

    // �]�w�������j�p
    this.setSize(200, 200);
    // ��ܵ���
    this.setVisible(true);
  }
}