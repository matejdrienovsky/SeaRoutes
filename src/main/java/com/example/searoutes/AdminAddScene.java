package com.example.searoutes;

import User.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is the controller for the scene where an admin can add or remove other admins
 * It is used by the admin user
 */
public class AdminAddScene {
    @FXML javafx.scene.control.Button add;
    @FXML javafx.scene.control.Button logout;
    @FXML javafx.scene.control.TextField username;
    @FXML javafx.scene.control.TextField password;
    @FXML javafx.scene.control.ChoiceBox serAdmins;
    @FXML javafx.scene.control.Label labelAdd;
    @FXML javafx.scene.control.Label labelRemove;


    /**
     * Adds an admin to the serialization file when the add button is clicked
     */
    @FXML
    protected void onAdd(){
        String username = this.username.getText();
        String password = this.password.getText();
        if(username.equals("") || password.equals("")) {
            labelAdd.setText("Please fill in all fields");
            return;
        }
        labelAdd.setText("Admin added");
        Admin admin = new Admin(username, password);

        // Deserialize the list from the file, add to the list, then serialize it back
        List<Admin> admins = new ArrayList<>();

        // Try to read the list from the file
        try {
            FileInputStream fileIn = new FileInputStream("serialization.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            admins = (List<Admin>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
        }

        // Add the new admin to the list
        admins.add(admin);

        // Serialize the list back to the file
        try {
            FileOutputStream fileOut = new FileOutputStream("serialization.ser"); // overwrite the existing file
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(admins);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Adds the admins to the choice box when the scene is loaded
     * admins from serialization.ser
     */
    @FXML
    protected void addtoChoiceBox() {
        serAdmins.getItems().clear();
        List<Admin> admins = new ArrayList<>();
        // Deserialize the list from the file
        try {
            FileInputStream fileIn = new FileInputStream("serialization.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            admins = (List<Admin>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Add the admins to the choice box
        for (Admin admin : admins) {
            serAdmins.getItems().add(admin.getusername());
        }
    }

    /**
     * Removes the admin from serialization.ser
     */
    @FXML
    protected void removeAdmin() {
        if (serAdmins.getValue() == null) {
            labelRemove.setText("Please select an admin");
            return;
        }
        labelRemove.setText("Admin removed");
        String username = (String) serAdmins.getValue();
        // Deserialize the list from the file
        List<Admin> admins = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("serialization.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            admins = (List<Admin>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Remove the admin with the specified username
        admins.removeIf(admin -> admin.getusername().equals(username));

        // Serialize the list back to the file
        try {
            FileOutputStream fileOut = new FileOutputStream("serialization.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(admins);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Go back to the admin menu
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onBackButton(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin_users.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
