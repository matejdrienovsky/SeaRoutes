package com.example.searoutes;

import GenericFilter.GenericFilter;
import User.User;
import com.mongodb.client.FindIterable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.bson.Document;
import Database.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import OwnException.*;

/**
 * This class is the controller for the adminUsers scene
 * It allows the admin to add and remove users from the database
 */
public class AdminUsers {
    @FXML javafx.scene.control.ChoiceBox users;
    @FXML javafx.scene.control.Button removeButton;
    @FXML javafx.scene.control.TextField usernameText;
    @FXML javafx.scene.control.TextField passwordText;
    @FXML javafx.scene.control.TextField emailText;
    @FXML javafx.scene.control.Label labelRemove;
    @FXML javafx.scene.control.Label labelAdd;

    /**
     * Initialize the choicebox with the users from the database
     * sort them by name
     */
    @FXML
    protected void loadUsers() {
        users.getItems().clear();
        DatabaseFacade database = new DatabaseFacade();
        database.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");
        FindIterable<Document> documents = database.find();

        // Convert iterable to list of User objects
        List<User> userList = new ArrayList<>();
        for (Document doc : documents) {
            User user = new User(doc.getString("_id"), doc.getString("password"), doc.getString("email"));
            userList.add(user);
        }

        // Create comparator to sort by name
        Comparator<User> nameComparator = (u1, u2) -> u1.getusername().compareToIgnoreCase(u2.getusername());

        // Create predicate to filter by alphabetical order of name
        Predicate<User> alphabeticalNamePredicate = (user) -> user.getusername().matches("[A-Za-z]+");
        GenericFilter<User> alphabeticalNameFilter = new GenericFilter<>(alphabeticalNamePredicate);
        List<User> alphabeticalUserList = alphabeticalNameFilter.filter(userList);

        // Sort using comparator
        Collections.sort(alphabeticalUserList, nameComparator);

        users.getItems().clear();
        // Add sorted names to users choicebox
        for (User user : alphabeticalUserList) {
            users.getItems().add(user.getusername());
        }
    }

    /**
     * Remove the selected user from the database and list
     */
    @FXML
    protected void removeUser(){
        //if no item is selected in choicebox then return
        if (users.getValue() == null){
            labelRemove.setText("Please select a user");
            return;
        }
        labelRemove.setText("");
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");

        String user = users.getValue().toString();
        database8.getCollection().deleteOne(new Document("_id", user));
        users.getItems().remove(user);
    }

    /**
     * Create a new user from the textfields
     * add it to the database
     */
    @FXML
    protected void addUser(){
        if (usernameText.getText().equals("") || passwordText.getText().equals("") || emailText.getText().equals("")){
            labelAdd.setText("Please fill all the fields");
            return;
        }
        labelAdd.setText("");
        if (checkifUserExists(usernameText.getText())) {
            labelAdd.setText("User already exists");
            return;
        }
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");

        User user = createNewUser();
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if (emailText.getText().matches(emailPattern)){
            System.out.println("Email is valid");
        }
        else {
            labelAdd.setText("Email is not valid");
            throw new OwnException("Email is not valid");
        }
        if (user!=null){
            Document sampleDoc = new Document("_id", user.getusername());
            sampleDoc.append("email", user.getemail());
            sampleDoc.append("password", user.getpassword());
            database8.getCollection().insertOne(sampleDoc);
        }
    }

    /**
     * Helper method
     * Create a new user from the textfields
     * @return the new user
     */
    protected User createNewUser(){
        if (passwordText.getText().equals("") || usernameText.getText().equals("") || emailText.getText().equals("")){
            return null;
        }
        else {
            String username = usernameText.getText();
            String password = passwordText.getText();
            String email = emailText.getText();
            User user = new User(username, password, email);
            return user;
        }
    }

    /**
     * Go to the scene where you can add a new and remove admins
     * @param event
     */
    @FXML
    protected void adminGo(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/adminAddScene.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * Go to the admin menu
     * @param event
     */
    @FXML //This method is for the back button that allows user to go back to the previous page
    protected void onBackClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Check if the user already exists in the database
     * @param username
     * @return true if the user exists in the database and false otherwise
     */
    protected boolean checkifUserExists(String username){
        DatabaseFacade database = new DatabaseFacade();
        database.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Users", "user");
        FindIterable<Document> documents = database.find();
        for (Document doc : documents) {
            if (doc.getString("_id").equals(username)){
                return true;
            }
        }
        return false;
    }
}
