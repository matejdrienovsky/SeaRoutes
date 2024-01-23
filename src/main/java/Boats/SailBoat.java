package Boats;
import DesignPattern.*;

/**
 * Class SailBoat
 * with information about sailboat and method to set picture of sailboat that overrides method from class boat
 * extends class Boat
 */
public class SailBoat extends Boat{
    private int sailArea;
    private int maxSpeed;

    public SailBoat(String name, int capacity, int sailArea, int maxSpeed) {
        super(name, capacity);
        this.sailArea = sailArea;
        this.maxSpeed = maxSpeed;
        calculateMaxSpeed();
    }

    public int getSailArea() {
        return sailArea;
    }

    public void setSailArea(int sailArea) {
        this.sailArea = sailArea;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Method calculateMaxSpeed
     * that calculate max speed of sailboat depending on sail area
     */
    public void calculateMaxSpeed() {
        maxSpeed = (int) (Math.sqrt(maxSpeed) * sailArea);
    }

    /**
     * Method getInformation
     * print information about sailboat
     */
    @Override
    public void getInformation() {
        super.getInformation();
        System.out.println("Sail area: " + sailArea);
        System.out.println("Max speed: " + maxSpeed);
    }

    /**
     * Method setPicture
     * @return path to picture of sailboat
     */
    @Override
    public String setPicture(){
        String boat = "/com/example/searoutes/images/sailingBoat.png";
        return boat;
    }
}

