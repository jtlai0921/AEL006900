// JNLP API
import javax.jnlp.*;

public class ExtensionInstallerServiceDemo {
  // JNLP Service
  private ExtensionInstallerService eis = null;

  public static void main(String[] args) {
    ExtensionInstallerServiceDemo JNLPInstaller1 = new ExtensionInstallerServiceDemo();
  }

  // �غc�禡
  public ExtensionInstallerServiceDemo() {
    try {
      // �d��ExtensionInstallerService�A��
      eis = (ExtensionInstallerService)ServiceManager.lookup("javax.jnlp.ExtensionInstallerService");
    } 
    catch(UnavailableServiceException e) {
      // �L�k�d�ߨ���w�A�ȡA�^��UnavailableServiceException
      eis = null;
      e.printStackTrace();
    }

    if (eis != null) {
      // �]�w�Ұʵe���������D
      eis.setHeading("Extension Installer Service����");
      
      // ��s�Ұʵe�������w�˰T��
      eis.setStatus("�}�l�w��...");

      for (int i=0; i<100; i++) {
        // ��s�Ұʵe�������U���i��
        eis.updateProgress(i);

        // ��s�Ұʵe�������w�˰T��
        eis.setStatus("�w�˶i�� (�w���� " + i + "%)");
        
        try {
          Thread.currentThread().sleep(100);
        } 
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      
      // �]�w�Ұʵe���������D
      eis.setHeading("Extension Installer Service���է���");

      // ��s�Ұʵe�������w�˰T��
      eis.setStatus("�w�˧���");
      
      // ���ñҰʵe�������U���i��
      eis.hideProgressBar();

      try {
        Thread.currentThread().sleep(400);
      } 
      catch (Exception ex) {
        ex.printStackTrace();
      }

      // �O�_�ݭn���s�ҰʥΤ�ݹq��
      eis.installSucceeded(true);
    }
  }
}
