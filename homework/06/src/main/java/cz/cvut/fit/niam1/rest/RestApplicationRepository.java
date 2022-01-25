package cz.cvut.fit.niam1.rest;


import cz.cvut.fit.niam1.rest.data.Tour;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationRepository {
    private static final List<Tour> tours = new ArrayList<>();
    private static final List<Tour> awaitingDeleteTours = new ArrayList<>();

    @PostConstruct
    public void initRepo() {
        tours.add(new Tour("1", "Beach tour"));
        tours.add(new Tour("2", "Ski tour"));
        tours.add(new Tour("3", "FIT CTU"));
    }

    public void addTour(Tour t) {
        tours.add(t);
    }

    public List<Tour> getTours() {
        return tours;
    }

    public List<Tour> getAwaitingDeleteTours() {
        return awaitingDeleteTours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void deleteTour(String id) {
        Tour tour = getTourById(id);
        if (tour == null) return;

        awaitingDeleteTours.add(tour);
        tours.removeIf(c -> c.getId().equals(id));

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        awaitingDeleteTours.removeIf(c -> c.getId().equals(id));
                    }
                },
                10 * 1000
        );
    }
}
