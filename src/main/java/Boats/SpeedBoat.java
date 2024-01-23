package Boats;

/**
 * Class SpeedBoat
 * extend Boat and add maxSpeed
 * override method setPicture from Boat and set picture of speedboat
 */
public class SpeedBoat extends Boat{
    private int maxSpeed;

    public SpeedBoat(String name, int capacity, int maxSpeed) {
        super(name, capacity);
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }


    /**
     * Method getInformation
     * print information about speedboat
     */
    @Override
    public void getInformation() {
        super.getInformation();
        System.out.println("Max speed: " + maxSpeed);
    }

    /**
     * Method setPicture
     * @return path to picture of speedboat
     */
    @Override
    public String setPicture(){
        String boat = "/com/example/searoutes/images/speedboat.png";
        return boat;
    }

}
