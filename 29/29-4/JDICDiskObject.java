public class JDICDiskObject {
  public static String TYPE_COMPUTER = "Computer";
  public static String TYPE_DRIVER = "Driver";
  public static String TYPE_FOLDER = "Folder";
  public static String TYPE_FILE = "File";

  public String name;
  public String type;

  public JDICDiskObject(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String toString() {
    return name;
  }
}
