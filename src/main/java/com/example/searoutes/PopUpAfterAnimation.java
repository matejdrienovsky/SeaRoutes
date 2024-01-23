package com.example.searoutes;

import Captains.Captain;
import Orders.Order;
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
 * This class is used to create a pop up window after the animation
 * The user can rate the captain in this window
 * The rating will be added to the captain
 * The rating will be used to calculate the captain's rating
 */
public class PopUpAfterAnimation {
    @FXML javafx.scene.control.ChoiceBox choiceRating;
    @FXML javafx.scene.control.Button add;
    @FXML javafx.scene.control.Label label;
    protected boolean selected = false;

    /**
     * This method is used to initialize the choice box with the ratings
     */
    @FXML
    protected void initializeRatings() {
        choiceRating.getItems().clear();
        choiceRating.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    }

    /**
     * This method is used to add the rating to the captain
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    protected void addRating(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {

        if (choiceRating.getValue() == null) {
            label.setText("Please select a rating");
            return;
        }
        if (selected) {
            label.setText("You have already rated this captain");
            return;
        }
        File file = new File("currentCaptain.ser");
        // deserialize the order from the file
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Captain captain = (Captain) in.readObject();
        in.close();
        fileIn.close();
        // get the rating from the choice box
        String rating = (String) choiceRating.getValue();
        int ratingInt = Integer.parseInt(rating);
        captain.setRating(ratingInt);
        double newrating = captain.calculateRating(ratingInt);
        serializedCaptain(captain);
        selected = true;
    }

    /**
     * This method is used to serialize the captain back to the file
     * if the captain already exists, the rating will be updated instead
     * @param captain
     */
    public void serializedCaptain(Captain captain) {
        List<Captain> captains = new ArrayList<>();

        // Try to read the list from the file
        try {
            FileInputStream fileIn = new FileInputStream("captains.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            captains = (List<Captain>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
        }

        // Check if the captain already exists
        for (Captain a : captains) {
            if (captain.getName().equals(a.getName())) {
                double rating = a.calculateRating((int)captain.getRating());
                a.setRating(rating);

                // Serialize the list back to the file
                try {
                    FileOutputStream fileOut = new FileOutputStream("captains.ser"); // overwrite the existing file
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(captains);
                    out.close();
                    fileOut.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }
    }
}
