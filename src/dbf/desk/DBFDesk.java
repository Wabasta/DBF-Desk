package dbf.desk;

/**
 * @author Nawaz Sarwar
 */
public class DBFDesk {
    
    static String host = "";
    static String port = "";
    static String dbuser = "";
    static String dbpassword = "";
    static String dbname = "";

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
