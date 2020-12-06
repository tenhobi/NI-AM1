package cz.cvut.fit.niam1.rest;

import cz.cvut.fit.niam1.rest.data.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApplicationController {
    final
    RestApplicationRepository repository;

    public RestApplicationController(RestApplicationRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tour")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getTours() {
        return repository.getTours();
    }

    @GetMapping(value = "/tour/awaiting-delete")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getAwaitingDeleteTour() {
        return repository.getAwaitingDeleteTours();
    }

    @PostMapping(value = "/tour")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Tour> getTour(@PathVariable String id) {
        Tour tour = repository.getTourById(id);

        if (tour == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tour);
    }

    @DeleteMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTour(@PathVariable String id) {
        repository.deleteTour(id);
    }
}
