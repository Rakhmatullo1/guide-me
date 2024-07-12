package uz.guideme.hotelbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelBookingApplication {

    //TODO Bookings:
    //      confirm/reject and send email
    //      cancel bookings
    //TODO test all endpoints and workflows


    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }

}
