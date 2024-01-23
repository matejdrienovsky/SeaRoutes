package Orders;

import Boats.Boat;
import Captains.Captain;
import User.User;

import java.io.Serializable;

/**
 * Class for order with senior discount
 */
public class OrderDiscountSenior extends OrderExtend implements Discountable{


    /**
     * Override calculateDiscount method to calculate discount with the senior discount
     * @param age
     * @return discount
     */
    @Override
    public double calculateDiscount(int age) {
        double discount = Discountable.super.calculateDiscount(age);
        return discount;
    }

    /**
     * Constructor for senior discount
     * @param user
     * @param rout
     * @param captain
     * @param date
     * @param boat
     * @param price
     * @param weather
     * @param age
     */
    public OrderDiscountSenior(User user, Rout rout, Captain captain, String date, Boat boat, Double price, boolean weather, int age) {
        super(user, rout, captain, date, boat, price, weather,age);
        this.setPrice(price());
    }


    /**
     * Override price method to calculate price with the senior discount
     * Default implementation is provided
     * @return price
     */
    @Override
    public double price() {
        int age = super.getAge();
        double discount = calculateDiscount(age);
        if (getWeather() == true){
            return super.getPrice() *0.8 * discount;
        }
        return super.getPrice() * discount;
    }

}
