package com.example.searoutes;

import Boats.Boat;
import Boats.SailBoat;
import Boats.SpeedBoat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.bson.Document;
import Database.*;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is the controller for the admin add boats page
 * It allows the admin to add boats to the database
 * It also allows the admin to go to the remove boat page
 */
public class AdminAddBoats {
    @FXML javafx.scene.control.Button addButton;
    @FXML javafx.scene.control.TextField sailAreaText;
    @FXML javafx.scene.control.TextField maxspeedText;
    @FXML javafx.scene.control.TextField capacityText;
    @FXML javafx.scene.control.ChoiceBox<String> typeBox;
    @FXML javafx.scene.control.TextField nameText;
    @FXML javafx.scene.control.Label labelAdd;
    static int counter = 0;

    /**
     * This method is for the add button that allows user to add a boat to the database
     */
    @FXML
    protected void onButton() {
        //if some of the fields are empty
        if (nameText.getText().isEmpty() || capacityText.getText().isEmpty() || typeBox.getValue() == null) {
            labelAdd.setText("Please fill all the fields");
            return;
        }
        try {
            Integer.parseInt(capacityText.getText());
        } catch (NumberFormatException e) {
            labelAdd.setText("Please enter a number for capacity");
            return;
        }
        labelAdd.setText("");
        String type = typeBox.getValue();
        String name = nameText.getText();
        if (checkIfBoatExists(name)) {
            labelAdd.setText("Boat already exists");
            return;
        }
        int capacity = Integer.parseInt(capacityText.getText());
        if (type == "Sailboat") {
            if (sailAreaText.getText().isEmpty() || maxspeedText.getText().isEmpty()) {
                labelAdd.setText("Please fill all the fields");
                return;
            }

            try {
                Integer.parseInt(sailAreaText.getText());
                Integer.parseInt(maxspeedText.getText());
            } catch (NumberFormatException e) {
                labelAdd.setText("Please enter a number for sail area and max speed");
                return;
            }
            labelAdd.setText("");
            int maxspeed = Integer.parseInt(maxspeedText.getText());
            int sailArea = Integer.parseInt(sailAreaText.getText());
            Boat boat = new SailBoat(name, capacity, maxspeed, sailArea);
            addSailBoat((SailBoat) boat);
        } else if (type == "Speedboat") {
            if (maxspeedText.getText().isEmpty()) {
                labelAdd.setText("Please fill all the fields");
                return;
            }

            try {
                Integer.parseInt(maxspeedText.getText());
            } catch (NumberFormatException e) {
                labelAdd.setText("Please enter a number for max speed");
                return;
            }
            labelAdd.setText("");
            int maxspeed = Integer.parseInt(maxspeedText.getText());
            Boat boat = new SpeedBoat(name, capacity, maxspeed);
            addSpeedBoat((SpeedBoat) boat);
        } else{
            Boat boat = new Boat(name, capacity);
            addBoat(boat);
        }
    }

    /**
     * This method adds a speedboat to the database
     * @param boat
     */
    protected void  addSpeedBoat(SpeedBoat boat){
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        Document sampleDoc = new Document("_id", boat.getName());
        sampleDoc.append("type", "SpeedBoat");
        sampleDoc.append("capacity", boat.getCapacity());
        sampleDoc.append("maxSpeed", boat.getMaxSpeed());
        database8.getCollection().insertOne(sampleDoc);
    }

    /**
     * This method adds a sailboat to the database
     * @param boat
     */
    protected void  addSailBoat(SailBoat boat){
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");

        Document sampleDoc = new Document("_id", boat.getName());
        sampleDoc.append("type", "SailBoat");
        sampleDoc.append("capacity", boat.getCapacity());
        sampleDoc.append("maxSpeed", boat.getMaxSpeed());
        sampleDoc.append("sailArea", boat.getSailArea());
        database8.getCollection().insertOne(sampleDoc);
    }

    /**
     * This method adds a boat to the database
     * @param boat
     */
    protected void  addBoat(Boat boat){
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        Document sampleDoc = new Document("_id", boat.getName());
        sampleDoc.append("capacity", boat.getCapacity());
        database8.getCollection().insertOne(sampleDoc);
    }

    /**
     * This method adds the types of boats to the choice box
     */
    @FXML
    protected void addToChoiceBox(){
        typeBox.getItems().clear();
        typeBox.getItems().add("Sailboat");
        typeBox.getItems().add("Speedboat");
        typeBox.getItems().add("Boat");
        counter++;
    }

    /**
     * This method opens the remove boat page
     * @throws IOException
     */
    @FXML
    protected void removeBoat() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin_menu/removeBoat.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is for the back button that allows user to go back to the admin menu
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


    /**
     * This method checks if a boat exists in the database
     * @param name
     * @return true if boat exists, false if it doesn't
     */
    protected boolean checkIfBoatExists(String name){
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        Document sampleDoc = new Document("_id", name);
        Document myDoc = database8.getCollection().find(sampleDoc).first();
        if (myDoc == null){
            return false;
        }
        return true;
    }
}
