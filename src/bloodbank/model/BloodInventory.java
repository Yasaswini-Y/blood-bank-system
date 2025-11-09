package bloodbank.model;

public class BloodInventory {
    private String bloodGroup;
    private int units;

    public BloodInventory(String bloodGroup, int units) {
        this.bloodGroup = bloodGroup;
        this.units = units;
    }

    public String getBloodGroup() { return bloodGroup; }
    public int getUnits() { return units; }
}
