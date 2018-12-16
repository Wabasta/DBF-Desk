package dbf.desk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author Nawaz Sarwar
 */
public class MySQLConnection {

    static Connection conn = null;
    Statement stmt = null;

    static String host = "";
    static String port = "";
    static String dbuser = "";
    static String dbpassword = "";
    static String dbname = "";

    public MySQLConnection() {
        
    }

    public static Connection ConnectMySQL(String dbname) {
        host = DBFDesk.host;
        port = DBFDesk.port;
        dbuser = DBFDesk.dbuser;
        dbpassword = DBFDesk.dbpassword;
//        dbname = DBFDesk.dbname;
        if (!host.equals("") || !port.equals("") || !dbuser.equals("")) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname + "?verifyServerCertificate=false&useSSL=true&requireSSL=false", dbuser, dbpassword);
                return conn;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error establishing Database connection : " + e);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Database connection settings not found, Add Database settings");
            return null;
        }

    }

    public static boolean testConnectMySQL(String host, String port, String dbuser, String dbpassword, String dbname) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname + "?verifyServerCertificate=false&useSSL=true&requireSSL=false", dbuser, dbpassword);
            return true;
        } catch (Exception e) {
            System.err.println("Error establishing Database connection : " + e);
            return false;
        }
    }

}
