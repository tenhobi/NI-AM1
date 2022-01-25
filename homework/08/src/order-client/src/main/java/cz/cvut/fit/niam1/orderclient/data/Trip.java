package cz.cvut.fit.niam1.orderclient.data;

public class Trip {
    public int id;
    public String to;
    public String from;
    public String company;
    public int price;

    public Trip(int id, String to, String from, String company, int price) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.company = company;
        this.price = price;
    }

    @Override
    public String toString() {
        return "trip" + ";" + id + ";" + to + ";" + from + ";" + company + ";" + price;
    }
}
