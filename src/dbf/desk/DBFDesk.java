package dbf.desk;

/**
 * @author Nawaz Sarwar
 */
public class DBFDesk {
    
    static String host = "localhost";
    static String port = "3306";
    static String dbuser = "root";
    static String dbpassword = "";
    static String dbname = "test";

    public static void main(String[] args) {
        if(!checkConnectionInfo()){
        } else {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        }
    }

    private static Boolean checkConnectionInfo() {
        return true;
    }

}
