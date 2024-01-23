package com.example.searoutes;

import Captains.Captain;
import Database.DatabaseFacade;
import com.mongodb.client.FindIterable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.*;

/**
 * This class is responsible for adding and removing captains from the database
 * It is used by admin
 */
public class AdminCaptains {
    @FXML javafx.scene.control.TextField name;
    @FXML javafx.scene.control.TextField age;
    @FXML javafx.scene.control.TextField price;
    @FXML javafx.scene.control.Label labelAdd;


    /**
     * This method adds a captain to the database
     */
    @FXML
    protected void addCaptain(){
        if (name.getText().isEmpty() || age.getText().isEmpty() || price.getText().isEmpty()){
            labelAdd.setText("Please fill all the fields");
            return;
        }
        try {
            Integer.parseInt(age.getText());
            Integer.parseInt(price.getText());
        } catch (NumberFormatException e) {
            labelAdd.setText("Age and price must be numbers");
            return;
        }

        if (!check()){
            return;
        }
        labelAdd.setText("");
        String name = this.name.getText();
        String age = this.age.getText();
        String price = this.price.getText();
        int ageInt = Integer.parseInt(age);
        int priceInt = Integer.parseInt(price);

        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Captains", "captain");
        FindIterable<Document> documents = database8.find();

        if (name!=null && age!=null && price!=null){
            Document sampleDoc = new Document("_id", name);
            sampleDoc.append("age", ageInt);
            sampleDoc.append("price", priceInt);
            database8.getCollection().insertOne(sampleDoc);
        }
    }

    /**
     * This method checks if captain already exists in the database
     * @return true if captain doesn't exist, false otherwise
     */
    protected boolean check(){
        //check if captain already exists
        DatabaseFacade database = new DatabaseFacade();
        database.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Captains", "captain");
        FindIterable<Document> documents = database.find();
        for (Document document : documents) {
            if (document.get("_id").equals(name.getText())){
                labelAdd.setText("Captain already exists");
                return false;
            }
        }
        return true;
    }

    /**
     * This method on button open pop up window where you can remove captain
     * @throws IOException
     */
    @FXML
    protected void removeCaptain() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/captainsPopUp.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method on button click go back to admin menu
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onBackClick(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/admin.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
