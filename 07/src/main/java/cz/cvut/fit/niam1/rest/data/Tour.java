package cz.cvut.fit.niam1.rest.data;

import java.util.List;
import java.util.Objects;

public class Tour {
    String id;
    String name;
    List<String> customers;

    public Tour(String id, String name, List<String> customers) {
        this.id = id;
        this.name = name;
        this.customers = customers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCustomers() {
        return customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers = customers;
    }

    public void addCustomer(String customer) {
        this.customers.add(customer);
    }

    public int getWeakHash() {
        return Objects.hash(id, name);
    }
}
