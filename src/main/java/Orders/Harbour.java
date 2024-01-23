package Orders;

/**
 * Class for harbour object
 */
public class Harbour {
    private String name;


    /**
     * Constructor for harbour object
     * @param name
     */
    public Harbour(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
