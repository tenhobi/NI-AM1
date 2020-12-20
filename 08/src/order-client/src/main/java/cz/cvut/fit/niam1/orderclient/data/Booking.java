package cz.cvut.fit.niam1.orderclient.data;

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
        return "booking" + ";" + id + ";" + days + ";" + price + ";" + address;
    }
}
