package com.example.searoutes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is controller for login.fxml that is the first page of the application that user sees
 * it has two buttons one for sign in and one for sign up and one for admin login
 */
public class Login {

    /**
     * This method is called when the user clicks on the sign in button and it changes the scene to sign in page
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onSignInButtonClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sign_in/sign_in.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is called when the user clicks on the sign up button and it changes the scene to sign up page
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onSignUpButtonClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sign_up/sign_up.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is called when the user clicks on the admin button and it changes the scene to admin login page
     * @param event
     * @throws IOException
     */
    @FXML
    protected  void adminButtonClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_login/admin_login.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
