package com.example.searoutes;

import Boats.*;
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
 * Controller for the scene where the admin can remove boats from the database
 */
public class AdminRemoveBoats {
    @FXML javafx.scene.control.ChoiceBox boats;
    @FXML javafx.scene.control.Button capacityButton;
    @FXML javafx.scene.control.Button nameSortButton;
    @FXML javafx.scene.control.Button removeBoatButton;
    @FXML javafx.scene.control.Label labelRemove;

    /**
     * Initialize the choicebox with the boats from the database
     */
    @FXML
    public void initialize() {
        boats.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        FindIterable<Document> documents = database8.find();
        for (Document doc : documents) {
            boats.getItems().add(doc.get("_id")+ " " + doc.get("capacity"));
        }
    }


    /**
     * Remove the selected boat from the database and list
     */
    @FXML
    protected void removeBoat(){
        if (boats.getValue() == null) {
            labelRemove.setText("Please select a boat");
            return;
        }
        labelRemove.setText("");
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        String selectedBoat = boats.getValue().toString();
        String boatId = selectedBoat.split(" ")[0]; // extract the ID from the selected item
        database8.getCollection().deleteOne(new Document("_id", boatId));
        boats.getItems().remove(selectedBoat);
    }


    /**
     * Sort the boats by name
     */
    @FXML
    protected void sortByName() {
        boats.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        FindIterable<Document> documents = database8.find();

        // Convert iterable to list of Boat objects
        List<Boat> boatList = new ArrayList<>();
        for (Document doc : documents) {
            Boat boat = new Boat(doc.get("_id").toString(), (Integer) doc.get("capacity"));
            boatList.add(boat);
        }

        // Create generic filter and apply it to the list
        Predicate<Boat> alphabeticalIdPredicate = (boat) -> {
            String[] words = boat.getName().split("\\s+");
            if (words.length > 0) {
                String firstWord = words[0];
                return firstWord.matches("\\p{L}+");
            }
            return false;
        };
        GenericFilter<Boat> alphabeticalIdFilter = new GenericFilter<>(alphabeticalIdPredicate);
        List<Boat> alphabeticalBoatList = alphabeticalIdFilter.filter(boatList);

        // Sort using comparator
        Comparator<Boat> idComparator = (b1, b2) -> {
            String id1 = b1.getName();
            String id2 = b2.getName();
            String[] words1 = id1.split("\\s+");
            String[] words2 = id2.split("\\s+");
            int cmp = words1[0].compareToIgnoreCase(words2[0]);
            if (cmp == 0 && words1.length > 1 && words2.length > 1) {
                cmp = words1[1].compareToIgnoreCase(words2[1]);
            }
            return cmp;
        };
        Collections.sort(alphabeticalBoatList, idComparator);

        // Add sorted Boat objects to ListView
        boats.getItems().clear();
        for (Boat boat : alphabeticalBoatList) {
            boats.getItems().add(boat.getName() + " " + boat.getCapacity());
        }
    }

    /**
     * Sort the boats by capacity
     */
    @FXML
    protected void sortByCapacity(){
        boats.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        FindIterable<Document> documents = database8.find();

        // Convert iterable to list using Java Streams
        List<Document> docsList = StreamSupport.stream(documents.spliterator(), false)
                .collect(Collectors.toList());

        // Sort using Java Streams by 'capacity' field
        List<Document> sortedList = docsList.stream()
                .sorted(Comparator.comparing(d -> (int) d.get("capacity")))
                .collect(Collectors.toList());

        boats.getItems().clear();
        // Add sorted capacity to boats choicebox
        for (Document doc : sortedList) {
            boats.getItems().add(doc.get("_id").toString()+" "+doc.get("capacity").toString());
        }
    }
}
