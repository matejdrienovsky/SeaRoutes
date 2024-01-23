package Orders;
import Boats.*;
import Captains.Captain;
import User.User;

/**
 * Order class with all the basic information about the order
 * Method to get the price of the order
 * Method to get the weather picture
 */
public abstract class Order implements OrderInterface{
    private User user;
    private String username;
    private Rout rout;
    private String date;
    private Captain captain;
    private Boat boat;
    private double price;
    private int age;

    /**
     * Constructor for the order
     * abstract class
     * @param user
     * @param rout
     * @param captain
     * @param date
     * @param boat
     * @param price
     * @param age
     */
    public Order(User user,Rout rout, Captain captain, String date, Boat boat, Double price, int age) {
        this.user = user;
        this.rout = rout;
        this.username = user.getusername();
        this.captain = captain;
        this.date = date;
        this.boat = boat;
        this.price = price;
        this.age = age;
    }

    /**
     * Method to get the weather picture
     * @return weather picture path
     */
    public String weatherPicture(){
        String weather = "/com/example/searoutes/order/sun.png";
        return weather;
    }

    @Override
    public double price() {
        return price;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Rout getRout() {
        return rout;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public Captain getCaptain() {
        return captain;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setRout(Rout rout) {
        this.rout = rout;
    }


    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setCaptain(Captain captain) {
        this.captain = captain;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }



    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }


    public void setBoat(Boat boat) {this.boat = boat;}


    public void Boat(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
