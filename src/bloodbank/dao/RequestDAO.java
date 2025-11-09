package bloodbank.dao;
import bloodbank.model.Request;
import bloodbank.db.DBConnection;
import java.sql.*;

public class RequestDAO {
    public void addRequest(Request r) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO request(blood_group, location) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, r.getBloodGroup());
            ps.setString(2, r.getLocation());
            ps.executeUpdate();
            System.out.println("✅ Blood request added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error adding request: " + e.getMessage());
        }
    }
}
