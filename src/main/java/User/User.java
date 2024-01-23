package User;

import java.io.Serializable;

/**
 * User class for storing user information
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String email;

    public String getusername()
    {
        return username;
    }
    public void setusername(String username)
    {
        this.username = username;
    }
    public String getpassword()
    {
        return password;
    }
    public void setpassword(String password)
    {
        this.password = password;
    }

    /**
     * Constructor for User class
     * @param username username of user
     * @param password password of user
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for User class
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public void setemail(String email) {this.email = email;}
    public String getemail() {return email;}
}
