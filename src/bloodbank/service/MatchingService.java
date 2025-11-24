package bloodbank.service;

import bloodbank.dao.DonorDAO;
import bloodbank.model.Donor;
import java.util.List;

public class MatchingService {
    private DonorDAO dao = new DonorDAO();

    public List<Donor> matchDonors(String bg, String loc) {
        List<Donor> all = dao.findByBloodAndLocation(bg, loc);
        if (all.isEmpty()) {
            System.out.println("No matching donors found for " + bg + " in " + loc);
        } else {
            System.out.println("Matching donors:");
            for (Donor d : all) {
                System.out.println("- " + d.getName() + " (" + d.getBloodGroup() + ") at " + d.getLocation());
            }
        }
        return all;
    }
}
