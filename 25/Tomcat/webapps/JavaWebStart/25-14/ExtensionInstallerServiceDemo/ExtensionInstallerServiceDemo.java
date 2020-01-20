// JNLP API
import javax.jnlp.*;

public class ExtensionInstallerServiceDemo {
  // JNLP Service
  private ExtensionInstallerService eis = null;

  public static void main(String[] args) {
    ExtensionInstallerServiceDemo JNLPInstaller1 = new ExtensionInstallerServiceDemo();
  }

  // 建構函式
  public ExtensionInstallerServiceDemo() {
    try {
      // 查詢ExtensionInstallerService服務
      eis = (ExtensionInstallerService)ServiceManager.lookup("javax.jnlp.ExtensionInstallerService");
    } 
    catch(UnavailableServiceException e) {
      // 無法查詢到指定服務，回傳UnavailableServiceException
      eis = null;
      e.printStackTrace();
    }

    if (eis != null) {
      // 設定啟動畫面中之標題
      eis.setHeading("Extension Installer Service測試");
      
      // 更新啟動畫面中之安裝訊息
      eis.setStatus("開始安裝...");

      for (int i=0; i<100; i++) {
        // 更新啟動畫面中之下載進度
        eis.updateProgress(i);

        // 更新啟動畫面中之安裝訊息
        eis.setStatus("安裝進度 (已完成 " + i + "%)");
        
        try {
          Thread.currentThread().sleep(100);
        } 
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      
      // 設定啟動畫面中之標題
      eis.setHeading("Extension Installer Service測試完成");

      // 更新啟動畫面中之安裝訊息
      eis.setStatus("安裝完成");
      
      // 隱藏啟動畫面中之下載進度
      eis.hideProgressBar();

      try {
        Thread.currentThread().sleep(400);
      } 
      catch (Exception ex) {
        ex.printStackTrace();
      }

      // 是否需要重新啟動用戶端電腦
      eis.installSucceeded(true);
    }
  }
}
