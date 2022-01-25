package cz.cvut.fit.niam1.bookingsprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class BookingProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(BookingProcessorApplication.class);

    @JmsListener(destination = "bookingQueue")
    public void readMessage(String message) throws InterruptedException {
        Booking booking = Booking.fromString(message);
        if (booking != null) {
            this.process(booking);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingProcessorApplication.class, args);
    }

    private void process(Booking booking) {
        logger.info("Processing {}", booking.toString());
    }

}
