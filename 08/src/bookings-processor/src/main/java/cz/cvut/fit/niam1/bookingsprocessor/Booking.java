package cz.cvut.fit.niam1.bookingsprocessor;

import java.io.Serializable;

public class Booking {
    public int id;
    public int days;
    public int price;
    public String address;

    public Booking(int id, int days, int price, String address) {
        this.id = id;
        this.days = days;
        this.price = price;
        this.address = address;
    }

    @Override
    public String toString() {
        return id + ";" + days + ";" + price + ";" + address;
    }

    static Booking fromString(String data) {
        String[] parts = data.trim().split(";");

        try {
            return new Booking(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), parts[4]);
        } catch (Exception ignored) {
            return null;
        }
    }
}
