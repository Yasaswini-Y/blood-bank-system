package bloodbank.dao;

import bloodbank.db.DBConnection;
import bloodbank.model.Donor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {

    // Add a new donor
    public void addDonor(Donor donor) {
        String sql = "INSERT INTO donor (name, age, weight, blood_group, location, health_condition, last_donation_date, contact) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, donor.getName());
            ps.setInt(2, donor.getAge());
            ps.setDouble(3, donor.getWeight());
            ps.setString(4, donor.getBloodGroup());
            ps.setString(5, donor.getLocation());
            ps.setString(6, donor.getHealthCondition());
            if (donor.getLastDonationDate() != null)
                ps.setDate(7, Date.valueOf(donor.getLastDonationDate()));
            else
                ps.setNull(7, Types.DATE);
            ps.setString(8, donor.getContact());

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Donor added successfully!");
            else
                System.out.println("Failed to add donor.");

        } catch (SQLException e) {
            System.out.println("SQL Error adding donor: " + e.getMessage());
        }
    }

    // Get all donors
    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donor";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Donor d = new Donor();
                d.setDonorId(rs.getInt("donor_id"));
                d.setName(rs.getString("name"));
                d.setAge(rs.getInt("age"));
                d.setWeight(rs.getDouble("weight"));
                d.setBloodGroup(rs.getString("blood_group"));
                d.setLocation(rs.getString("location"));
                d.setHealthCondition(rs.getString("health_condition"));

                Date lastDonation = rs.getDate("last_donation_date");
                if (lastDonation != null)
                    d.setLastDonationDate(lastDonation.toLocalDate());

                d.setContact(rs.getString("contact"));
                donors.add(d);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error retrieving donors: " + e.getMessage());
        }
        return donors;
    }

    // Update donor details
    public boolean updateDonor(int id, String newContact, String newLocation) {
        boolean success = false;
        String sql = "UPDATE donor SET contact=?, location=? WHERE donor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newContact);
            ps.setString(2, newLocation);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            success = rows > 0;

            if (success)
                System.out.println("Donor updated successfully!");
            else
                System.out.println("Donor ID not found!");

        } catch (SQLException e) {
            System.out.println("SQL Error updating donor: " + e.getMessage());
        }
        return success;
    }

    // Find donors by blood group and location
    public List<Donor> findByBloodAndLocation(String bloodGroup, String location) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donor WHERE blood_group=? AND location=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bloodGroup);
            ps.setString(2, location);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donor d = new Donor();
                d.setDonorId(rs.getInt("donor_id"));
                d.setName(rs.getString("name"));
                d.setAge(rs.getInt("age"));
                d.setWeight(rs.getDouble("weight"));
                d.setBloodGroup(rs.getString("blood_group"));
                d.setLocation(rs.getString("location"));
                d.setHealthCondition(rs.getString("health_condition"));
                d.setContact(rs.getString("contact"));

                Date lastDonation = rs.getDate("last_donation_date");
                if (lastDonation != null)
                    d.setLastDonationDate(lastDonation.toLocalDate());

                donors.add(d);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error finding donors: " + e.getMessage());
        }
        return donors;
    }
}
