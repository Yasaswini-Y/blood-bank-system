package bloodbank;

import bloodbank.dao.DonorDAO;
import bloodbank.model.Donor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BloodBankApp extends JFrame {

    private DonorDAO donorDAO = new DonorDAO();

    public BloodBankApp() {
        setTitle("Blood Bank Management System");
        setSize(750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 25, 25)); // increased to 7 rows
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        // Title
        JLabel title = new JLabel("Blood Bank System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(new Color(47, 60, 126));
        add(title, BorderLayout.NORTH);

        // Buttons
        JButton registerBtn = createButton("Register Donor", new Color(255, 255, 255));
        JButton updateBtn = createButton("Update Donor Details", new Color(255, 255, 255));
        JButton requestBtn = createButton("Request Blood", new Color(255, 255, 255));
        JButton stockBtn = createButton("Check Low Stock", new Color(255, 255, 255));
        JButton eligibilityBtn = createButton("Eligibility Info", new Color(240, 248, 255)); // new button
        JButton exitBtn = createButton("Exit", new Color(255, 255, 255));

        panel.add(registerBtn);
        panel.add(updateBtn);
        panel.add(requestBtn);
        panel.add(stockBtn);
        panel.add(eligibilityBtn);
        panel.add(exitBtn);

        add(panel);

        // Button Actions
        registerBtn.addActionListener(e -> registerDonor());
        updateBtn.addActionListener(e -> updateDonor());
        requestBtn.addActionListener(e -> requestBlood());
        stockBtn.addActionListener(e -> checkLowStock());
        eligibilityBtn.addActionListener(e -> showEligibilityInfo());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(500, 90));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(color.darker()); }
            public void mouseExited(MouseEvent e) { button.setBackground(color); }
        });
        return button;
    }

    private JTextField createWideTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(280, 40));
        return field;
    }

    // 🧾 Register Donor (with eligibility check)
    private void registerDonor() {
        JTextField nameField = createWideTextField();
        JTextField ageField = createWideTextField();
        JTextField weightField = createWideTextField();
        JTextField bgField = createWideTextField();
        JTextField locField = createWideTextField();
        JTextField contactField = createWideTextField();

        // Health condition radio buttons
        JRadioButton goodBtn = new JRadioButton("Good");
        JRadioButton badBtn = new JRadioButton("Bad");
        goodBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        badBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        ButtonGroup healthGroup = new ButtonGroup();
        healthGroup.add(goodBtn);
        healthGroup.add(badBtn);

        JPanel healthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        healthPanel.setBackground(new Color(255, 255, 255));
        healthPanel.add(goodBtn);
        healthPanel.add(badBtn);

        Object[] form = {
                "Name:", nameField,
                "Age:", ageField,
                "Weight (kg):", weightField,
                "Blood Group:", bgField,
                "Location:", locField,
                "Health Condition:", healthPanel,
                "Contact Number:", contactField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Register Donor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double weight = Double.parseDouble(weightField.getText());
                String bloodGroup = bgField.getText().toUpperCase();
                String location = locField.getText();
                String contact = contactField.getText();
                String healthCondition = goodBtn.isSelected() ? "Good" : badBtn.isSelected() ? "Bad" : "Unknown";

                if (name.isEmpty() || bloodGroup.isEmpty() || location.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill in all required fields.");
                return;
            }

            // ✅ Blood group validation
            String[] validGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            boolean valid = false;
            for (String g : validGroups) {
                if (bloodGroup.equals(g)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                JOptionPane.showMessageDialog(this,
                        " Invalid blood group! Please enter a valid one:\nA+, A-, B+, B-, AB+, AB-, O+, O-",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

                // ✅ Eligibility Check
                if (age < 18 || age > 60) {
                    JOptionPane.showMessageDialog(this, " Not eligible: age must be between 18 and 60.");
                    return;
                }
                if (weight < 50) {
                    JOptionPane.showMessageDialog(this, " Not eligible: weight must be at least 50 kg.");
                    return;
                }
                if (!healthCondition.equalsIgnoreCase("Good")) {
                    JOptionPane.showMessageDialog(this, " Not eligible: health condition must be Good.");
                    return;
                }

                // ✅ If all conditions pass → Save donor
                Donor d = new Donor();
                d.setName(name);
                d.setAge(age);
                d.setWeight(weight);
                d.setBloodGroup(bloodGroup);
                d.setLocation(location);
                d.setHealthCondition(healthCondition);
                d.setContact(contact);
                d.setLastDonationDate(null);

                donorDAO.addDonor(d);
                JOptionPane.showMessageDialog(this, " Donor Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ✏️ Update Donor
    private void updateDonor() {
        JTextField idField = createWideTextField();
        JTextField contactField = createWideTextField();
        JTextField locField = createWideTextField();
        JTextField healthField = createWideTextField();
        JTextField weightField = createWideTextField();

        Object[] form = {
                "Donor ID:", idField,
                "New Contact:", contactField,
                "New Location:", locField,
                "Updated Health Condition:", healthField,
                "Updated Weight (kg):", weightField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Update Donor Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String contact = contactField.getText();
                String location = locField.getText();

                boolean success = donorDAO.updateDonor(id, contact, location);
                if (success)
                    JOptionPane.showMessageDialog(this, "Donor updated successfully!");
                else
                    JOptionPane.showMessageDialog(this, "Donor ID not found!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        }
    }

    // 🩸 Request Blood
    private void requestBlood() {
        JTextField bgField = createWideTextField();
        JTextField locField = createWideTextField();
        JTextField nearbyField = createWideTextField();
        JTextField urgencyField = createWideTextField();

        Object[] form = {
                "Required Blood Group:", bgField,
                "Your Location:", locField,
                "Nearby Area (Optional):", nearbyField,
                "Urgency Level (High/Medium/Low):", urgencyField
        };

        int option = JOptionPane.showConfirmDialog(this, form, "Request Blood", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String bg = bgField.getText().toUpperCase();
                String loc = locField.getText();

                List<Donor> donors = donorDAO.findByBloodAndLocation(bg, loc);
                if (donors.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No donors found for this location.");
                } else {
                    StringBuilder sb = new StringBuilder("Available Donors:\n\n");
                    for (Donor d : donors) {
                        sb.append("Name: ").append(d.getName()).append("\n")
                          .append("Contact: ").append(d.getContact()).append("\n")
                          .append("Location: ").append(d.getLocation()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(this, sb.toString(), "Available Donors", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // 📊 Check Low Stock
    private void checkLowStock() {
        try {
            List<Donor> donors = donorDAO.getAllDonors();
            if (donors.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No donors found in database.");
                return;
            }

            Map<String, Integer> bloodCount = new HashMap<>();
            for (Donor d : donors) {
                String bg = d.getBloodGroup().toUpperCase();
                bloodCount.put(bg, bloodCount.getOrDefault(bg, 0) + 1);
            }

            String[] allGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

            StringBuilder sb = new StringBuilder("Blood Group Stock Levels:\n\n");
            for (String bg : allGroups) {
                int count = bloodCount.getOrDefault(bg, 0);
                if (count == 0)
                    sb.append(bg).append(" → No donors available\n");
                else if (count < 3)
                    sb.append(bg).append(" → Only ").append(count).append(" donors (LOW)\n");
                else
                    sb.append(bg).append(" → ").append(count).append(" donors\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString(), "Blood Stock Report", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching stock data: " + ex.getMessage());
        }
    }

    // 🧠 NEW: Eligibility Info Popup
    private void showEligibilityInfo() {
        String info = """
                 BLOOD DONATION ELIGIBILITY RULES 
                
                Age: 18 to 60 years
                Weight: Minimum 50 kg
                Health Condition: Must be 'Good'
                No major illness in the past 6 months
                No alcohol or drugs within 24 hours before donation
                Minimum gap of 3 months since last donation

                These rules ensure the donor’s and recipient’s safety.
                """;
        JTextArea area = new JTextArea(info);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        area.setEditable(false);
        area.setBackground(new Color(245, 248, 255));
        area.setMargin(new Insets(10, 10, 10, 10));
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
                "Eligibility Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BloodBankApp::new);
    }
}
