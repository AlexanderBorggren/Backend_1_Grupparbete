package com.example.grupparbete_backend_1;
import com.example.grupparbete_backend_1.controllers.ContractCustomerControllerTH;
import com.example.grupparbete_backend_1.models.EmailingTemplates;
import com.example.grupparbete_backend_1.repositories.EmailTemplateRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Objects;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;


@SpringBootApplication
public class GrupparbeteBackend1Application {

    public static void main(String[] args) {

        if (args.length == 0) {
            SpringApplication.run(GrupparbeteBackend1Application.class, args);
        } else if (Objects.equals(args[0], "fetchShippers")) {
            SpringApplication fetchShippers = new SpringApplication(FetchShippers.class);
            fetchShippers.setWebApplicationType(WebApplicationType.NONE);
            fetchShippers.run(args);

        } else if (Objects.equals(args[0], "fetchContractCustomers")) {
            SpringApplication application = new SpringApplication(FetchContractCustomers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        } else if (Objects.equals(args[0], "queueStreamer")) {
            SpringApplication application = new SpringApplication(QueueStreamer.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        }

    }

/*
    //BEAN for adding default emailTemplates
    @Bean
    public CommandLineRunner addDefaultEmailTemplates(EmailTemplateRepo emailTemplateRepo) {

        return (args) -> {

            EmailingTemplates emailingTemplate1 = new EmailingTemplates();
            EmailingTemplates emailingTemplate2 = new EmailingTemplates();


            emailingTemplate1.setTemplateName("BookingConfirmation");
            emailingTemplate1.setTemplateDescription("Booking confirmation template used for when a booking has been completed ");
            emailingTemplate1.setFromEmail("autoreply@booking.pensionatet.com");
            emailingTemplate1.setSubject("Booking confirmation!");
            emailingTemplate1.setBody("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Booking Confirmation</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "    <p>Dear [[${customerName}]],</p>\n" +
                    "    <p>Thank you for your booking! We are pleased to confirm your reservation as follows:</p>\n" +
                    "    <ul>\n" +
                    "        <li>Guests: [[${guestQuantity}]]</li>\n" +
                    "        <li>Room: [[${room}]]</li>\n" +
                    "        <li>Room Type: [[${roomType}]]</li>\n" +
                    "        <li>Extra beds: [[${extraBedsQuantity}]]</li>\n" +
                    "        <li>Check-in Date: [[${startDate}]]</li>\n" +
                    "        <li>Check-out Date: [[${endDate}]]</li>\n" +
                    "    </ul>\n" +
                    "    <p>We look forward to welcoming you soon. If you have any questions or need to make changes to your reservation, please do not hesitate to contact us.</p>\n" +
                    "    <p>Best Regards,</p>\n" +
                    "    <p>Pensionatet</p>\n" +
                    "</body>\n" +
                    "</html>");

            emailingTemplate2.setTemplateName("PasswordRecovery");
            emailingTemplate2.setTemplateDescription("Template used for reseting password for login to hotelbooking site");
            emailingTemplate2.setFromEmail("autoreply@passwordrecovery.pensionatet.com");
            emailingTemplate2.setSubject("Password recovery email");
            emailingTemplate2.setBody("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Password recovery email</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "    <p>Hi [[${userName}]],</p>\n" +
                    "    <p>There was a request to change your password!</p>\n" +
                    "\t<p>If you did not make this request then please ignore this email.</p>\n" +
                    "\t<p>Otherwise, please click this link to change your password: <a th:href=\"${link}\">password reset</a></p>\n" +
                    "\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");

                        emailTemplateRepo.save(emailingTemplate1);
                        emailTemplateRepo.save(emailingTemplate2);

        };
    }*/



    /*@Bean
    public CommandLineRunner addUsersRoles(UserRepo userRepo, RoleRepo roleRepo) {
        return (args) -> {

            Role role = new Role("ADMIN");
            Role role2 = new Role("RECEPTIONIST");
            roleRepo.save(role);
            roleRepo.save(role2);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode("admin");
            String hash2 = encoder.encode("receptionist");

            List<Role> rolesAdmin = List.of(role, role2);
            List<Role> roleReceptionist = List.of(role2);
            User user1 = new User("admin@admin.se", hash, true, rolesAdmin);
            User user2 = new User("receptionist@receptionist.se", hash2, true, roleReceptionist);
            userRepo.save(user1);
            userRepo.save(user2);
        };
    }*/


/*
    @Bean
    public CommandLineRunner addDefaultData(EventRepo eventRepo) {
        return (args) -> {
            EventBase eventBase = new EventBase();
            eventRepo.save(eventBase);
            eventRepo.save(new RoomClosed());
        };


    /*
    @Bean
    public CommandLineRunner addDefaultData(CustomerRepo customerRepo, RoomTypeRepo roomTypeRepo, RoomRepo roomRepo, BookingRepo bookingRepo) {
        return (args) -> {

            Customer customer1 = new Customer("Anna Johansson", "1234567890", "anna.johansson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer2 = new Customer("Karl Nilsson", "2345678901", "karl.nilsson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer3 = new Customer("Eva Eriksson", "3456789012", "eva.eriksson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer4 = new Customer("Lars Gustafsson", "4567890123", "lars.gustafsson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer5 = new Customer("Maria Pettersson", "5678901234", "maria.pettersson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer6 = new Customer("Peter Andersson", "6789012345", "peter.andersson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer7 = new Customer("Sofia Karlsson", "7890123456", "sofia.karlsson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer8 = new Customer("Mikael Johansson", "8901234567", "mikael.johansson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer9 = new Customer("Emma Svensson", "9012345678", "emma.svensson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer10 = new Customer("Andreas Gustafsson", "0123456789", "andreas.gustafsson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer11 = new Customer("Sara Nilsson", "1234567891", "sara.nilsson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer12 = new Customer("Fredrik Pettersson", "2345678912", "fredrik.pettersson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer13 = new Customer("Elin Andersson", "3456789123", "elin.andersson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer14 = new Customer("Daniel Karlsson", "4567891234", "daniel.karlsson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Customer customer15 = new Customer("Linda Johansson", "5678912345", "linda.johansson@example.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

            customerRepo.save(customer1);
            customerRepo.save(customer2);
            customerRepo.save(customer3);
            customerRepo.save(customer4);
            customerRepo.save(customer5);
            customerRepo.save(customer6);
            customerRepo.save(customer7);
            customerRepo.save(customer8);
            customerRepo.save(customer9);
            customerRepo.save(customer10);
            customerRepo.save(customer11);
            customerRepo.save(customer12);
            customerRepo.save(customer13);
            customerRepo.save(customer14);
            customerRepo.save(customer15);

            RoomType singleRoom = new RoomType("Single", 0, 300.0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            RoomType doubleRoom1 = new RoomType("Double room 1", 1, 600.0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            RoomType doubleRoom2 = new RoomType("Double room 2", 2, 685.0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));


            roomTypeRepo.save(singleRoom);
            roomTypeRepo.save(doubleRoom1);
            roomTypeRepo.save(doubleRoom2);

            Room room1 = new Room(singleRoom, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room2 = new Room(singleRoom, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room3 = new Room(singleRoom, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room4 = new Room(doubleRoom1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room5 = new Room(doubleRoom1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room6 = new Room(doubleRoom1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room7 = new Room(doubleRoom2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room8 = new Room(doubleRoom2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room9 = new Room(doubleRoom2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Room room10 = new Room(doubleRoom2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));


            roomRepo.save(room1);
            roomRepo.save(room2);
            roomRepo.save(room3);
            roomRepo.save(room4);
            roomRepo.save(room5);
            roomRepo.save(room6);
            roomRepo.save(room7);
            roomRepo.save(room8);
            roomRepo.save(room9);
            roomRepo.save(room10);


            Booking booking1 = new Booking(LocalDate.parse("2024-06-02"), LocalDate.parse("2024-06-03"), 2, 0, customer1, room1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking2 = new Booking(LocalDate.parse("2024-06-02"), LocalDate.parse("2024-06-03"), 3, 1, customer2, room5, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking3 = new Booking(LocalDate.parse("2024-06-02"), LocalDate.parse("2024-06-03"), 4, 2, customer3, room7, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking4 = new Booking(LocalDate.parse("2024-06-02"), LocalDate.parse("2024-06-03"), 1, 0, customer4, room2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking5 = new Booking(LocalDate.parse("2024-06-02"), LocalDate.parse("2024-06-03"), 2, 0, customer5, room3, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

            Booking booking11 = new Booking(LocalDate.parse("2024-06-03"), LocalDate.parse("2024-06-08"), 4, 2, customer11, room9, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking12 = new Booking(LocalDate.parse("2024-06-03"), LocalDate.parse("2024-06-08"), 1, 0, customer12, room7, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking13 = new Booking(LocalDate.parse("2024-06-03"), LocalDate.parse("2024-06-08"), 2, 0, customer13, room8, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking14 = new Booking(LocalDate.parse("2024-06-03"), LocalDate.parse("2024-06-08"), 3, 1, customer14, room10, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking15 = new Booking(LocalDate.parse("2024-06-03"), LocalDate.parse("2024-06-08"), 1, 0, customer1, room1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

            Booking booking16 = new Booking(LocalDate.parse("2024-06-15"), LocalDate.parse("2024-06-18"), 1, 0, customer2, room10, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking17 = new Booking(LocalDate.parse("2024-06-15"), LocalDate.parse("2024-06-18"), 2, 0, customer3, room9, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking18 = new Booking(LocalDate.parse("2024-06-15"), LocalDate.parse("2024-06-18"), 3, 1, customer4, room8, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking19 = new Booking(LocalDate.parse("2024-06-15"), LocalDate.parse("2024-06-18"), 4, 2, customer5, room7, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            Booking booking20 = new Booking(LocalDate.parse("2024-06-15"), LocalDate.parse("2024-06-18"), 1, 0, customer6, room6, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

            bookingRepo.save(booking1);
            bookingRepo.save(booking2);
            bookingRepo.save(booking3);
            bookingRepo.save(booking4);
            bookingRepo.save(booking5);
            bookingRepo.save(booking11);
            bookingRepo.save(booking12);
            bookingRepo.save(booking13);
            bookingRepo.save(booking14);
            bookingRepo.save(booking15);
            bookingRepo.save(booking16);
            bookingRepo.save(booking17);
            bookingRepo.save(booking18);
            bookingRepo.save(booking19);
            bookingRepo.save(booking20);

        };

    }
*/

}








        

