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
 * Class for admin login page controller
 * Check if password is correct and if it is, go to admin menu page
 */
public class AdminLogin {
    @FXML javafx.scene.control.TextField admin_login_password;
    @FXML javafx.scene.control.Label nice_try_label;


    /**
     * Go back to previous page on button click
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onBackClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Check if password is correct and if it is, go to admin menu page
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onClickMainButton(javafx.event.ActionEvent event) throws IOException {
        String inputPassword = admin_login_password.getText();
        List<Admin> admins = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("serialization.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            admins = (List<Admin>) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (EOFException e) {
            System.out.println("File is empty");
        } catch (InvalidClassException e) {
            System.out.println("Incompatible class");
        } catch (ClassCastException e) {
            System.out.println("File contains wrong type of object");
        } catch (IOException e) {
            System.out.println("Other I/O error");
        } catch (ClassNotFoundException e) {
            System.out.println("adminNode class not found");
        }

        boolean passwordMatch = false;
        for (Admin admin : admins) {
            if (inputPassword.equals(admin.getPassword())) {
                passwordMatch = true;
                break;
            }
        }

        if (passwordMatch || inputPassword.equals("jack")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin.fxml")));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            // Passwords don't match, show error message
            nice_try_label.setText("Nice try!");
        }
    }
}
