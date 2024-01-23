package com.example.searoutes;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mongodb.client.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Objects;
import User.*;
import Database.*;

/**
 * Controller for sign_in.fxml that allows user to sign in to the application
 * Help to sign in to the application if you don't want to create an account
 * Username : test
 * Password : test
 */
public class SignIn {
    @FXML javafx.scene.control.TextField username_sign_in;
    @FXML javafx.scene.control.TextField password_sign_in;
    @FXML javafx.scene.control.Label error_label;
    @FXML javafx.scene.control.Button sign_button;
    User user;

    //Hides database connection logs from console
    static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    static {
        root.setLevel(Level.ERROR);
    }

    /**
     * This method is for the back button that allows user to go back to the previous page
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is for the sign in button that allows user to sign in to the application
     * @param event
     * @throws IOException
     */
    @FXML //This method is for the sign in button that allows user to sign in to the application
    protected int sign_In(javafx.event.ActionEvent event) throws IOException {
        //call check method to check if the username and password are correct
        if (check() == false) {
            error_label.setText("Wrong username or password");
            return 0;
        }
        user_set(); //set user
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_user/menu_user.fxml"));
        Parent root = loader.load();
        Travel travel = loader.getController();
        travel.userset(user);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        return 1;
    }

    /**
     * Function that checks if the username and password are correct
     * If they are correct, it returns true, otherwise it returns false
     * Check it by connecting to the database and checking if the username and password are correct
     * @return true or false depending on the username and password
     */
    @FXML
    protected boolean check(){
        DatabaseFacade database = new DatabaseFacade();
        database.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");
        FindIterable<Document> documents = database.find();
        for (Document doc : documents) {
            String usernameChecker = get_Username();
            String passwordChecker = get_password();
            if (usernameChecker.equals(doc.get("_id"))) {
                if(passwordChecker.equals(doc.get("password"))) return true;
                else return false;
            }
        }
        return false;
    }

    /**
     * Method that creates a new user object
     */
    public void user_set(){
        String username = get_Username();
        String password = get_password();
        user = new User(username, password);
    }

    /**
     * Helper method that returns the username from the text field
     * @return username
     */
    @FXML
    public String get_Username(){
        String username = username_sign_in.getText();
        return username;
    }

    /**
     * Helper method that returns the password from the text field
     * @return password
     */
    @FXML
    protected String get_password(){
        String password = password_sign_in.getText();
        return password;
    }
}
