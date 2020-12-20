package cz.cvut.fit.niam1.newtripsprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class NewTripProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(NewTripProcessorApplication.class);

    @JmsListener(destination = "newTripQueue")
    public void readMessage(String message) throws InterruptedException {
        Trip booking = Trip.fromString(message);
        if (booking != null) {
            this.process(booking);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(NewTripProcessorApplication.class, args);
    }

    private void process(Trip trip) {
        logger.info("Processing {}", trip.toString());
    }
}
