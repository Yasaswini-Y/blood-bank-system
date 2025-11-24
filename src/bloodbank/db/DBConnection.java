package bloodbank.db;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bloodbankdb"; 
    private static final String USER = "root";  
    private static final String PASSWORD = "root"; 
    public static Connection getConnection() {
        Connection con = null;
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Database connected successfully!");
        } catch (Exception e) {
            System.out.println(" Database connection failed: " + e.getMessage());
        }
        return con;
    }
    // You can use this to test the connection directly
    public static void main(String[] args) {
        getConnection();
    }
}
