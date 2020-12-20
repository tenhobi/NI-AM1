package cz.cvut.fit.niam1.orderclient;

import cz.cvut.fit.niam1.orderclient.data.Booking;
import cz.cvut.fit.niam1.orderclient.data.Trip;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
@EnableJms
public class OrderClientApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(OrderClientApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("allBookingsQueue");
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        runTest();
    }

    private void runTest() throws Exception {
        int id = 1;

        int randPrice1 = new Random().nextInt(500) + 200;
        jmsTemplate.convertAndSend(queue, new Trip(id, "To somewhere","From somewhere", "Some company", randPrice1).toString());
        id++;

        int randPrice2 = new Random().nextInt(500) + 200;
        jmsTemplate.convertAndSend(queue, new Trip(id, "To somewhere","From somewhere", "Some company", randPrice2).toString());
        id++;

        int randDays3 = new Random().nextInt(20);
        int randPrice3 = new Random().nextInt(500) + 200;
        jmsTemplate.convertAndSend(queue, new Booking(id, randDays3, randPrice3, "Some address").toString());
        id++;

        int randDays4 = new Random().nextInt(20);
        int randPrice4 = new Random().nextInt(500) + 200;
        jmsTemplate.convertAndSend(queue, new Booking(id, randDays4, randPrice4, "Some address").toString());
        id++;

        int randPrice5 = new Random().nextInt(500) + 200;
        jmsTemplate.convertAndSend(queue, new Trip(id, "To somewhere","From somewhere", "Some company", randPrice5).toString());
        id++;

    }

    private void runRandom() throws Exception {
        int id = 0;

        while (true) {
            id++;
            Thread.sleep(5000);
            int random = new Random().nextInt(2);

            logger.info("rand={}", random);
            if (random == 1) {
                int randDays = new Random().nextInt(20);
                int randPrice = new Random().nextInt(500) + 200;
                jmsTemplate.convertAndSend(queue, new Booking(id, randDays, randPrice, "Some address").toString());
            } else {
                int randPrice = new Random().nextInt(500) + 200;
                jmsTemplate.convertAndSend(queue, new Trip(id, "To somewhere","From somewhere", "Some company", randPrice).toString());
            }

            LocalDateTime time = LocalDateTime.now();
            logger.info("Sending message to {} at {}", queue.getQueueName(), time);
        }
    }
}
