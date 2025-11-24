package bloodbank;

import bloodbank.dao.DonorDAO;
import bloodbank.model.Donor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DonorDAO donorDAO = new DonorDAO();

        while (true) {
            System.out.println("\n=== BLOOD BANK SYSTEM ===");
            System.out.println("1. Register Donor");
            System.out.println("2. Update Donor Details");
            System.out.println("3. Request Blood");
            System.out.println("4. View All Donors");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Donor Registration ---");
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    System.out.print("Weight (kg): ");
                    double weight = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Blood Group: ");
                    String bloodGroup = sc.nextLine();
                    System.out.print("Location: ");
                    String location = sc.nextLine();
                    System.out.print("Health Condition (Good/Bad): ");
                    String health = sc.nextLine();
                    System.out.print("Last Donation Date (yyyy-mm-dd or leave blank): ");
                    String dateInput = sc.nextLine();
                    LocalDate date = dateInput.isEmpty() ? null : LocalDate.parse(dateInput);
                    System.out.print("Contact: ");
                    String contact = sc.nextLine();

                    Donor donor = new Donor(name, age, weight, bloodGroup, location, health, date, contact);
                    donorDAO.addDonor(donor);
                    break;

                case 2:
                    System.out.print("Enter Donor ID to update: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new contact number: ");
                    String newContact = sc.nextLine();
                    System.out.print("Enter new location: ");
                    String newLoc = sc.nextLine();
                    donorDAO.updateDonor(id, newContact, newLoc);
                    break;

                case 3:
                    System.out.print("Enter required Blood Group: ");
                    String bg = sc.nextLine();
                    System.out.print("Enter your Location: ");
                    String loc = sc.nextLine();
                    List<Donor> donors = donorDAO.findByBloodAndLocation(bg, loc);
                    if (donors.isEmpty()) {
                        System.out.println(" No donors found!");
                    } else {
                        System.out.println("\nAvailable Donors:");
                        for (Donor d : donors) {
                            System.out.println(d.getName() + " - 📞 " + d.getContact() + " - 📍 " + d.getLocation());
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n--- Donor List ---");
                    List<Donor> allDonors = donorDAO.getAllDonors();
                    if (allDonors.isEmpty()) {
                        System.out.println("No donors found.");
                    } else {
                        System.out.printf("%-5s %-15s %-5s %-7s %-10s %-15s %-15s %-15s%n",
                                "ID", "Name", "Age", "Weight", "Blood", "Location", "Health", "Contact");
                        System.out.println("---------------------------------------------------------------------------------------");
                        for (Donor d : allDonors) {
                            System.out.printf("%-5d %-15s %-5d %-7.1f %-10s %-15s %-15s %-15s%n",
                                    d.getDonorId(), d.getName(), d.getAge(), d.getWeight(),
                                    d.getBloodGroup(), d.getLocation(), d.getHealthCondition(), d.getContact());
                        }
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using Blood Bank System!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }
}
