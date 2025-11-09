package bloodbank.service;

import bloodbank.model.Donor;
import bloodbank.db.DBConnection;
import java.sql.*;
import java.util.List;

public class NotificationService {

    public void notifyDonors(List<Donor> donors) {
        if (donors == null || donors.isEmpty()) {
            System.out.println("⚠️ No donors to notify.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            for (Donor d : donors) {
                System.out.println("📢 Notification sent to: " + d.getName() + " (" + d.getContact() + ")");
                String sql = "INSERT INTO notifications (donor_id, message) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, d.getDonorId());
                ps.setString(2, "Blood donation request available for your group.");
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("❌ Error sending notifications: " + e.getMessage());
        }
    }
}
