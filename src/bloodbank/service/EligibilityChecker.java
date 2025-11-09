package bloodbank.service;

import bloodbank.model.Donor;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EligibilityChecker {

    public boolean isEligible(Donor d) {
        if (d.getAge() < 18 || d.getAge() > 60) {
            System.out.println("❌ Not eligible: age must be between 18 and 60.");
            return false;
        }

        if (d.getWeight() < 50) {
            System.out.println("❌ Not eligible: weight must be at least 50 kg.");
            return false;
        }

        if (!"Good".equalsIgnoreCase(d.getHealthCondition())) {
            System.out.println("❌ Not eligible: health condition must be good.");
            return false;
        }

        if (d.getLastDonationDate() != null) {
            long days = ChronoUnit.DAYS.between(d.getLastDonationDate(), LocalDate.now());
            if (days < 90) {
                System.out.println("❌ Not eligible: must wait 90 days between donations.");
                return false;
            }
        }

        System.out.println("✅ Donor is eligible to donate blood.");
        return true;
    }
}
