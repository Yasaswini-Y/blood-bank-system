package bloodbank.dao;

import bloodbank.db.DBConnection;
import java.sql.*;

public class HospitalDAO {
    public boolean login(String u, String p) throws SQLException {
        String q = "SELECT * FROM Hospital WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, u);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}
