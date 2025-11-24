package bloodbank.dao;
import bloodbank.db.DBConnection;
import java.sql.*;

public class InventoryDAO {
    public void checkLowStock() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM blood_inventory WHERE units < 5";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            boolean found = false;
            System.out.println("\n=== Low Stock Blood Groups (less than 5 units) ===");
            while (rs.next()) {
                found = true;
                System.out.println(rs.getString("blood_group") + " → " + rs.getInt("units") + " units");
            }
            if (!found) {
                System.out.println("All blood groups have sufficient stock.");
            }
        } catch (Exception e) {
            System.out.println("Error checking stock: " + e.getMessage());
        }
    }
}
