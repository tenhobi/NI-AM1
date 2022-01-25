package cz.cvut.fit.niam1.rest;

import cz.cvut.fit.niam1.rest.data.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestApplicationController {
    final
    RestApplicationRepository repository;

    public RestApplicationController(RestApplicationRepository repository) {
        this.repository = repository;
    }

    private static SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

    @GetMapping(value = "/tour")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Tour>> getTours(@RequestHeader Map<String, String> headers) throws ParseException {
        long lastModifiedMiliseconds = 0;

        if (headers.containsKey("If-Modified-Since")) {
            String modifiedSince = headers.get("If-Modified-Since");

            lastModifiedMiliseconds = format.parse(modifiedSince).getTime();
        }

        if (repository.lastModified > lastModifiedMiliseconds) {
            return ResponseEntity.status(HttpStatus.OK).lastModified(repository.lastModified).body(repository.getTours());
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    // https://en.wikipedia.org/wiki/HTTP_ETag
    // "123456789"   – A strong ETag validator
    // W/"123456789" – A weak ETag validator

    @GetMapping(value = "/tour/weak")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Tour>> getToursWeakEtag(@RequestHeader Map<String, String> headers) {
        List<Tour> tours = repository.getTours(); // Get all items.
        int hash = Objects.hash(tours.stream().map(Tour::getWeakHash).toArray()); // Compute hash of all hashes.
        String etag = String.format("W/\"%d\"", hash);

        // If contains etag.
        if (headers.containsKey("If-None-Match")) {
            String oldEtag = headers.get("If-None-Match");

            if (etag.equals(oldEtag)) {
                // Nothing changed.
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
        }

        // Set up etag and return data.
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(etag)
                .body(tours);
    }

    @GetMapping(value = "/tour/strong")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> createToursStrongEtag() {
        // Strong etag is implemented by `shallowEtagHeaderFilter` bean.
        return repository.getTours();
    }

    @PostMapping(value = "/tour")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tour getTour(@PathVariable String id) {
        return repository.getTourById(id);
    }

    @PostMapping(value = "/tour/{id}/customer")
    public ResponseEntity addCustomer(@PathVariable String id, @RequestBody String customer) {
        repository.addCustomer(id, customer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
