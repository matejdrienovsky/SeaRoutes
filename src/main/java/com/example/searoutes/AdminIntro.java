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
 * This class is the controller for the admin menu scene
 * Admin can choose which option he wants to do
 */
public class AdminIntro {
    @FXML javafx.scene.control.Button adminAddBoat;
    @FXML javafx.scene.control.Button usersButton;

    /**
     * This method is called when the user clicks on the "Add Boat" button.
     * It changes the scene to the "admin_menu.fxml" scene.
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onAddBoat(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin_menu.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is called when the user clicks on the "Users" button.
     * It changes the scene to the "admin_users.fxml" scene.
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onUsers(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin_users.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is called when the user clicks on the "Captain" button.
     * It changes the scene to the "admin_captains.fxml" scene.
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onCaptain(javafx.event.ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin_captains.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is called when the user clicks on the "Log Out" button.
     * It changes the scene to the "login.fxml" scene.
     * back to the login page
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onLogOut(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is called when the user clicks on the "Jack" button.
     * button of Jack Sparrow icon
     * Pop up window with easteregg
     * @throws IOException
     */
    @FXML
    protected void jackButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/jackPopUp.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
