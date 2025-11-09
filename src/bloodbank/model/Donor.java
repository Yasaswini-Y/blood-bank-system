package bloodbank.model;

import java.time.LocalDate;

public class Donor {
    private int donorId;
    private String name;
    private int age;
    private double weight;
    private String bloodGroup;
    private String location;
    private String healthCondition;
    private LocalDate lastDonationDate;
    private String contact;

    // Full constructor
    public Donor(String name, int age, double weight, String bloodGroup,
                 String location, String healthCondition,
                 LocalDate lastDonationDate, String contact) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.healthCondition = healthCondition;
        this.lastDonationDate = lastDonationDate;
        this.contact = contact;
    }

    // Simple constructor for quick registration
    public Donor(String name, int age, String bloodGroup, String location) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.location = location;
    }

    // Empty constructor
    public Donor() {}

    // Getters
    public int getDonorId() { return donorId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getWeight() { return weight; }
    public String getBloodGroup() { return bloodGroup; }
    public String getLocation() { return location; }
    public String getHealthCondition() { return healthCondition; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public String getContact() { return contact; }

    // Setters
    public void setDonorId(int donorId) { this.donorId = donorId; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setLocation(String location) { this.location = location; }
    public void setHealthCondition(String healthCondition) { this.healthCondition = healthCondition; }
    public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }
    public void setContact(String contact) { this.contact = contact; }
}
