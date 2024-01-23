package User;


import java.io.Serializable;


/**
 * Admin class
 */
public class Admin implements Serializable {
    private String username;
    private String password;


    /**
     * Constructor for Admin
     * @param username
     * @param password
     */
    public Admin(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public String getusername() {
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setusername(String username) {
        this.username = username;
    }

}
