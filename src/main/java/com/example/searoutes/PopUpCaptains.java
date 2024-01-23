package com.example.searoutes;

import Database.DatabaseFacade;
import GenericFilter.GenericFilter;
import com.mongodb.client.FindIterable;
import javafx.fxml.FXML;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class is the controller for the popup window for captains
 * where admin can remove captains from the database and the list
 */
public class PopUpCaptains {
    @FXML javafx.scene.control.ChoiceBox captains;
    @FXML javafx.scene.control.Label labelRemove;
    int counter = 0;

    /**
     * This method initializes the choicebox with the captains from the database
     */
    @FXML
    protected void initializeCaptains() {
        if(counter ==0){
            DatabaseFacade database8 = new DatabaseFacade();
            database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Captains", "captain");
            FindIterable<Document> documents = database8.find();
            for (Document doc : documents) {
                captains.getItems().add(doc.get("_id"));
            }
            counter++;
        }
    }

    /**
     * This method sorts the captains by name and adds them to the choicebox
     * using streams and lambdas
     */
    @FXML
    protected void sortByName(){
        captains.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Captains", "captain");
        FindIterable<Document> documents = database8.find();

        // Convert iterable to list using Java Streams
        List<Document> docsList = StreamSupport.stream(documents.spliterator(), false)
                .collect(Collectors.toList());

        // Sort using Java Streams
        List<Document> sortedList = docsList.stream()
                .sorted(Comparator.comparing(d -> d.get("_id").toString()))
                .collect(Collectors.toList());

        captains.getItems().clear();
        // Add sorted id to users choicebox
        for (Document doc : sortedList) {
            captains.getItems().add(doc.get("_id").toString());
        }
    }

    /**
     * this method remove captains and also from list
     */
    @FXML
    protected void removeCaptains(){
        if (captains.getValue() == null){
            labelRemove.setText("Please select a captain");
            return;
        }
        labelRemove.setText("");
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Captains", "captain");
        String selectedCaptain = captains.getValue().toString();
        database8.getCollection().deleteOne(new Document("_id",selectedCaptain));
        captains.getItems().remove(selectedCaptain);
    }
}
