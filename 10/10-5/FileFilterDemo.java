import java.io.*;

public class FileFilterDemo {
  public static void main(String args[]){
    
    // �ثe�ؿ�
    File file = new File(".");

    // �w�q�z�����: ���ɦW��java
    Filter filter = new Filter("java");

    // �u�C�X���ɦW��java���ɮצW��
    String filename[] = file.list(filter);

    for (int i=0; i<filename.length; i++) { 
      System.out.println(filename[i]); 
    }
  }
}
