package cz.cvut.fit.niam1.rest;


import cz.cvut.fit.niam1.rest.data.Tour;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RestApplicationRepository {
    long lastModified;

    private static final List<Tour> tours = new ArrayList<>();

    @PostConstruct
    public void initRepo() {
        List<String> list = new ArrayList<String>();
        list.add("Honza");
        tours.add(new Tour("1", "Beach tour", new ArrayList<String>()));
        tours.add(new Tour("2", "Ski tour", new ArrayList<String>()));
        tours.add(new Tour("3", "FIT CTU", list));

        updateStuff();
    }

    private void updateStuff() {
        lastModified = System.currentTimeMillis();
    }

    public void addTour(Tour t) {
        tours.add(t);
        updateStuff();
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void addCustomer(String id, String customer) {
        Tour tour = getTourById(id);
        tour.addCustomer(customer);
        updateStuff();
    }
}
