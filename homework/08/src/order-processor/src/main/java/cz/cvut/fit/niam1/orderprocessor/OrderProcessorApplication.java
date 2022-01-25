package cz.cvut.fit.niam1.orderprocessor;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

@SpringBootApplication
@EnableJms
public class OrderProcessorApplication {

    private final Logger logger = LoggerFactory.getLogger(OrderProcessorApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue bookingQueue;

    @Bean
    public Queue bookingQueue() {
        return new ActiveMQQueue("bookingQueue");
    }

    @Autowired
    private Queue newTripQueue;

    @Bean
    public Queue newTripQueue() {
        return new ActiveMQQueue("newTripQueue");
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessorApplication.class, args);
    }

    @JmsListener(destination = "allBookingsQueue")
    public void readMessage(String message) throws InterruptedException {
        logger.info("Received confirmation message: {}", message);

        String[] parts = message.trim().split(";");

        if (parts[0].equals("booking")) {
            jmsTemplate.convertAndSend(bookingQueue, message);
            logger.info("sending to booking");
        } else if (parts[0].equals("trip")) {
            jmsTemplate.convertAndSend(newTripQueue, message);
            logger.info("sending to trip");
        } else {
            logger.error("invalid type");
        }
    }
}
