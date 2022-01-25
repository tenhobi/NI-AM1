package cz.cvut.fit.niam1.wsserver;

import cz.cvut.fit.niam1.webservices.hw04.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @Autowired
    private WebServiceClient validationClient;

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/04/", localPart = "getPaymentsRequest")
    @ResponsePayload
    public GetPaymentsResponse getPayments(@RequestPayload GetPaymentsRequest request) {
        GetPaymentsResponse response = new GetPaymentsResponse();
        response.getPayment().addAll(repository.getPayments());
        return response;
    }

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/04/", localPart = "addPaymentRequest")
    @ResponsePayload
    public AddPaymentResponse addPayments(@RequestPayload AddPaymentRequest request) {
        AddPaymentResponse response = new AddPaymentResponse();
        Payment payment = request.getPayment();
        ValidateCardResponse validationResponse = validationClient.validateCard(payment.getCreditCardNumber(), payment.getCreditCardOwner());

        if (validationResponse.isResult()) {
            repository.addPayment(request.getPayment());
        }

        return response;
    }
}
