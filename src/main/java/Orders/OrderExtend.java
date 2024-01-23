package Orders;

import Boats.Boat;
import Captains.Captain;
import java.io.Serializable;
import User.*;

/**
 * OrderExtend class extends Order class to add weather conditions
 * Override price() and weatherPicture() methods based on weather conditions
 */
public class OrderExtend extends Order{
    private boolean weather;

    /**
     * Constructor for OrderExtend class
     * @param user
     * @param rout
     * @param captain
     * @param date
     * @param boat
     * @param price
     * @param wheather
     * @param age
     */
    public OrderExtend(User user, Rout rout, Captain captain, String date, Boat boat, Double price, boolean wheather, int age) {
        super(user, rout, captain, date, boat, price, age);
        this.weather = wheather;
    }
    public boolean getWeather() {
        return weather;
    }
    public void setWheather(boolean weather) {
        this.weather = weather;
    }

    /**
     * Override price() method based on weather conditions
     * @return price
     */
    @Override
    public double price() {
        if (getWeather() == true){
            return super.getPrice() * 0.7;
        }
        return super.getPrice();
    }

    /**
     * Override weatherPicture() method based on weather conditions
     * @return weather picture path
     */
    @Override
    public String weatherPicture(){
        if (getWeather() == true){
            String weather = "/com/example/searoutes/order/storm.png";
            return weather;
        }
        String weather = "/com/example/searoutes/order/sun.png";
        return weather;
    }
}
