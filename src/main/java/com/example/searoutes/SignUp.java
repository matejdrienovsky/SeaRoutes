package com.example.searoutes;

import Database.DatabaseFacade;
import OwnException.OwnException;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mongodb.client.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import org.bson.Document;
import org.slf4j.LoggerFactory;
import User.*;

/**
 * Class that is the controller for the sign up screen
 * Where the user can create a new account
 */
public class SignUp {
    User user;
    @FXML javafx.scene.control.TextField username_input;
    @FXML javafx.scene.control.TextField password_input;
    @FXML javafx.scene.control.TextField sign_up_email;
    @FXML javafx.scene.control.Label check_all;

    //Hides all the logs from the console
    static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    static {
        root.setLevel(Level.ERROR);
    }

    /**
     * Method that is called when the sign up button is clicked
     * Checks if all the fields are filled and if the username is taken
     * Creates a new user and adds it to the database
     * @param event
     * @return
     * @throws IOException
     */
    @FXML
    protected int sign_up_click(javafx.event.ActionEvent event) throws IOException {
        String username = get_Username();
        if (check_if_username_is_taken() == true) return 0;
        String password = get_password();
        String email = get_email();
        if (check_if_all_fields_are_filled()== false) return 0;
        user_set();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");
        Document sampleDoc = new Document("_id", username);
        sampleDoc.append("email", email);
        sampleDoc.append("password", password);
        database8.getCollection().insertOne(sampleDoc);

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
     * Method for the back button that returns to the previous screen
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
     * Function that sets the user and creates a new user object
     */
    public void user_set(){
        String username = get_Username();
        String password = get_password();
        String email = get_email();
        user = new User(username, password,email);
    }

    ////////////////////////////////////////////////////HELPER FUNCTIONS/////////////////////////////////////////////

    /**
     * Helper function to get the username
     * @return username
     */
    @FXML
    protected String get_Username(){
        String username = username_input.getText();
        return username;
    }

    /**
     * Helper function to get the password
     * @return password
     */
    @FXML
    protected String get_password(){
        String password = password_input.getText();
        return password;
    }

    /**
     * Helper function to get the email
     * @return email
     */
    protected String get_email(){
        String email = sign_up_email.getText();
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if (email.matches(emailPattern)){
            System.out.println("Email is valid");
        }
        else {
            check_all.setText("Email is not valid");
            throw new OwnException("Email is not valid");
        }
        return email;
    }

    /**
     * Method that checks if all the fields are filled
     * @return true if all the fields are filled and false if not
     */
    @FXML
    protected boolean check_if_all_fields_are_filled(){
        if(username_input.getText().equals("") || password_input.getText().equals("") || sign_up_email.getText().equals("")){
            if (username_input.getText().equals("")){
                username_input.setPromptText("Username is required");
                username_input.setStyle("-fx-prompt-text-fill: rgb(82,4,148)");
            }
            if (password_input.getText().equals("")) {
                password_input.setPromptText("Password is required");
                password_input.setStyle("-fx-prompt-text-fill: #520494");
            }
            if (sign_up_email.getText().equals("")){
                sign_up_email.setPromptText("Email is required");
                sign_up_email.setStyle("-fx-prompt-text-fill: #520494");
            }
            return false;
        }
        return true;
    }

    /**
     * Method that checks if the username is taken
     * @return
     */
    @FXML
    protected boolean check_if_username_is_taken() {
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");
        FindIterable<Document> documents = database8.find();
        Document sampleDoc = new Document("_id", username_input.getText());
        if (database8.getCollection().find(sampleDoc).first() != null) {
            check_all.setText("Username is taken");
            return true;
        }   else {
            check_all.setText("");
            return false;
        }
    }
}
