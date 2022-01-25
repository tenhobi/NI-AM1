package cz.cvut.fit.niam1.wsserver;

import cz.cvut.fit.niam1.webservices.hw04.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Payment> payments = new ArrayList<>();

    @PostConstruct
    public void initRepo() throws DatatypeConfigurationException {
        Payment p1 = new Payment();
        p1.setId("111");
        p1.setCreditCardNumber("1234-1234-1234-1234");
        p1.setCreditCardOwner("CardOwner");

        payments.add(p1);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }
}
