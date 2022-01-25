package cz.cvut.fit.niam1.newtripsprocessor;

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
        return id + ";" + to + ";" + from + ";" + company + ";" + price;
    }

    static Trip fromString(String data) {
        String[] parts = data.trim().split(";");

        try {
            return new Trip(Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
        } catch (Exception ignored) {
            return null;
        }
    }
}
