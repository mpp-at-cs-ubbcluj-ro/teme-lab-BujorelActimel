package ro.mpp2024;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        UserRepository userRepo = new UsersDBRepository(props);

        User user1 = new User("ionica", "ionica@example.com", "parola123",
                java.sql.Date.valueOf("1992-03-25"));
        userRepo.add(user1);
        System.out.println("Utilizator adaugat: " + user1);

        User user2 = new User("georgel", "georgel@example.com", "parola456",
                java.sql.Date.valueOf("1988-11-10"));
        userRepo.add(user2);
        System.out.println("Utilizator adaugat: " + user2);

        user1.setEmail("ionica.popescu@example.com");
        userRepo.update(user1.getId(), user1);
        System.out.println("Utilizator actualizat: " + user1);

        System.out.println("\nToti utilizatorii din baza de date:");
        for (User user : userRepo.findAll()) {
            System.out.println(user);
        }

        String searchUsername = "georgel";
        System.out.println("\nUtilizatorii cu username-ul " + searchUsername + ":");
        for (User user : userRepo.findByUsername(searchUsername)) {
            System.out.println(user);
        }

        String searchEmail = "ionica.popescu@example.com";
        System.out.println("\nUtilizatorii cu email-ul " + searchEmail + ":");
        for (User user : userRepo.findByEmail(searchEmail)) {
            System.out.println(user);
        }
    }
}