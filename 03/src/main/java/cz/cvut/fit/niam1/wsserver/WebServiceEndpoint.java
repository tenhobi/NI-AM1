package cz.cvut.fit.niam1.wsserver;

import https.courses_fit_cvut_cz.ni_am1.hw._03.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/03/", localPart = "getBookingsRequest")
    @ResponsePayload
    public GetBookingsResponse getBookings(@RequestPayload GetBookingsRequest request) {
        GetBookingsResponse response = new GetBookingsResponse();
        response.getBooking().addAll(repository.getBookings());
        return response;
    }

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/03/", localPart = "addBookingRequest")
    @ResponsePayload
    public AddBookingResponse addBookings(@RequestPayload AddBookingRequest request) {
        AddBookingResponse response = new AddBookingResponse();
        repository.addBooking(request.getBooking());
        return response;
    }

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/03/", localPart = "deleteBookingRequest")
    @ResponsePayload
    public DeleteBookingResponse deleteBookings(@RequestPayload DeleteBookingRequest request) {
        DeleteBookingResponse response = new DeleteBookingResponse();
        repository.deleteBooking(request.getId());
        return response;
    }

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/03/", localPart = "updateBookingRequest")
    @ResponsePayload
    public UpdateBookingResponse updateBookings(@RequestPayload UpdateBookingRequest request) {
        UpdateBookingResponse response = new UpdateBookingResponse();
        repository.updateBooking(request.getBooking());
        return response;
    }
}
