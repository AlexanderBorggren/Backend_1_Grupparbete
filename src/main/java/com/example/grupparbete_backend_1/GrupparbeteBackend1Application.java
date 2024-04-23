package com.example.grupparbete_backend_1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GrupparbeteBackend1Application {

    public static void main(String[] args) {
        SpringApplication.run(GrupparbeteBackend1Application.class, args);
    }


/*
 @Bean
 public CommandLineRunner addDefaultData(CustomerRepo customerRepo, RoomTypeRepo roomTypeRepo,RoomRepo roomRepo, BookingRepo bookingRepo){
            return (args) -> {

                Customer customer1 = new Customer("Adde", "198604031234", "adde@hotmail.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
                Customer customer2 = new Customer("Berit", "195902031234", "gddhdr@hotmail.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
                Customer customer3 = new Customer("Cissi", "19720403114", "cissi@hotmail.com", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

                customerRepo.save(customer1);
                customerRepo.save(customer2);
                customerRepo.save(customer3);

                RoomType singleRoom = new RoomType("Single", 0,  Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
                RoomType doubleRoom1 = new RoomType("Double room 1", 1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
                RoomType doubleRoom2 = new RoomType("Double room 2", 2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));


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

                Booking booking = new Booking(LocalDate.parse(
                        "2020-01-08"), LocalDate.parse("2020-01-12"), 2,
                        0, customer1,room4,
                        Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

                bookingRepo.save(booking);

            };
        }

 */


}

