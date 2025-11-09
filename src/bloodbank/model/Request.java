package bloodbank.model;

public class Request {
    private String bloodGroup;
    private String location;

    public Request(String bloodGroup, String location) {
        this.bloodGroup = bloodGroup;
        this.location = location;
    }

    public String getBloodGroup() { return bloodGroup; }
    public String getLocation() { return location; }
}
