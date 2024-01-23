package Orders;

import Boats.Boat;
import Captains.Captain;
import User.User;

import java.io.Serializable;

/**
 * Class for the order with discount for students
 */
public class OrderDiscountStudent extends OrderExtend implements Discountable{


    /**
     * Method to calculate the discount for students
     * Calculates the discount based on the age of the use using default method in the interface
     * @param age
     * @return discount
     */
    @Override
    public double calculateDiscount(int age) {
        double discount = Discountable.super.calculateDiscount(age);
        return discount;
    }

    /**
     * Constructor for the order with discount for students
     * @param user
     * @param rout
     * @param captain
     * @param date
     * @param boat
     * @param price
     * @param weather
     * @param age
     */
    public OrderDiscountStudent(User user,Rout rout, Captain captain, String date, Boat boat, Double price, boolean weather, int age) {
        super(user, rout, captain, date, boat, price, weather,age );
        this.setPrice(price());
    }


    /**
     * Method to calculate the price of the order with the student discount
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
