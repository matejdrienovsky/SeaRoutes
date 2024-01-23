package Orders;

/**
 * Class Rout
 * With harbour arrival and harbour departure
 */
public class Rout {
    private Harbour arrival;
    private Harbour departure;

    /**
     * Constructor
     * @param arrival
     * @param departure
     */
    public Rout(Harbour arrival, Harbour departure) {
        this.arrival = arrival;
        this.departure = departure;

    }

    public Harbour getArrival() {
        return arrival;
    }

    public Harbour getDeparture() {
        return departure;
    }

    public void setArrival(Harbour arrival) {
        this.arrival = arrival;
    }

    public void setDeparture(Harbour departure) {
        this.departure = departure;
    }

}


