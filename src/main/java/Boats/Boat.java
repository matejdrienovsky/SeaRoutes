package Boats;


/**
 * Class for boat
 * with name and capacity of boat
 */
public class Boat implements BoatInterface{
    private String name;
    private int capacity;

    public Boat(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Method for getting information about boat
     */
    @Override
    public void getInformation() {
        System.out.println("Name: " + name);
        System.out.println("Capacity: " + capacity);
    }

    /**
     * Method for setting picture of boat
     * @return
     */
    @Override
    public String setPicture(){
        String boat = "/com/example/searoutes/images/boatBasic.png";
        return boat;
    }
}
