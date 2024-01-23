package Orders;

import Captains.Captain;
import User.User;

/**
 * Interface for the Order class
 */
public interface OrderInterface {
    User getUser();
    void setUser(User user);
    String getUsername();
    Rout getRout();
    void setRout(Rout rout);
    String getDate();
    Captain getCaptain();
    void setUsername(String username);

    void setDate(String date);
    void setCaptain(Captain captain);
    String weatherPicture();
    double price();
}
