package com.example.searoutes;


import Boats.Boat;
import Boats.SailBoat;
import Boats.SpeedBoat;
import Captains.Captain;
import Orders.Order;
import Orders.OrderExtend;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the animation of the boat
 * The animation is based on the boat type and the weather
 * The animation is also based on the arrival and departure of the order
 * I implemented speed of the boat every boat has a different speed
 */
public class AnimationController implements Initializable {
    private Order orderforAnimation;
    @FXML javafx.scene.control.Label labelArrival;
    @FXML javafx.scene.control.Label labelDeparture;
    @FXML javafx.scene.image.ImageView weatherPic;

    @FXML
    private Group animationGroup;

    /**
     * function to transfer the order from the previous scene
     * @param order
     */
    public void setOrder(Order order) {
        this.orderforAnimation = order;
    }

    /**
     * function to initialize the scene
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * function to animate the boat
     * @throws IOException
     */
    @FXML
    public void animateBoat() throws IOException {
        if (orderforAnimation instanceof OrderExtend) {
            String weather = orderforAnimation.weatherPicture();
            weatherPic.setImage(new Image(Objects.requireNonNull(getClass().getResource(weather)).openStream()));
        }

        String weather2 = orderforAnimation.weatherPicture();
        weatherPic.setImage(new Image(Objects.requireNonNull(getClass().getResource(weather2)).openStream()));
        labelArrival.setText(orderforAnimation.getRout().getArrival().getName());
        labelDeparture.setText(orderforAnimation.getRout().getDeparture().getName());
        String boatPicture = orderforAnimation.getBoat().setPicture();

        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResource(boatPicture)).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(50);

        Path path = new Path();

        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new LineTo(150, 0));
        path.getElements().add(new LineTo(100, -350));
        path.getElements().add(new LineTo(150, -350));

        if (orderforAnimation.getBoat().getClass().getSimpleName().equals("SpeedBoat")) {
            Boat boat = orderforAnimation.getBoat();
            SpeedBoat speedBoat = (SpeedBoat) boat;
            int maxSpeed = speedBoat.getMaxSpeed();
            PathTransition pathTransition2 = new PathTransition(Duration.millis(maxSpeed), path, imageView);
            pathTransition2.setOnFinished(e -> {
                try {
                    setScene();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            pathTransition2.play();
            animationGroup.getChildren().add(imageView);

        }
        else if (orderforAnimation.getBoat().getClass().getSimpleName().equals("SailBoat")){
            Boat boat = orderforAnimation.getBoat();
            SailBoat sailBoat = (SailBoat) boat;
            int maxSpeed = sailBoat.getMaxSpeed();
            PathTransition pathTransition = new PathTransition(Duration.millis(maxSpeed), path, imageView);
            pathTransition.setOnFinished(e -> {
                try {
                    setScene();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            pathTransition.play();
            animationGroup.getChildren().add(imageView);
        }
        else {
            PathTransition pathTransition = new PathTransition(Duration.millis(20000), path, imageView);
            pathTransition.setOnFinished(e -> {
                try {
                    setScene();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            pathTransition.play();
            animationGroup.getChildren().add(imageView);
        }
    }

    /**
     * function to open the pop up window after the boat arrives to the destination
     * @throws IOException
     */
    @FXML
    public void setScene() throws IOException{
        orderAddForRating(orderforAnimation);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("animation/popUpAnimation.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        setSceneMainMenu(stage);
    }

    /**
     * function to serialize the captain of the order to transfer it to the next scene
     * @param order
     * @throws IOException
     */
    protected void orderAddForRating(Order order) throws IOException {
        Captain captain = order.getCaptain();
        File file = new File("currentCaptain.ser");
        // delete the contents of the file
        file.delete();
        file.createNewFile();

        // serialize the order and write it to the file
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(captain);
        out.close();
        fileOut.close();
    }


    /**
     * function to set the scene of the main menu after the pop up window is closed and the boat arrives to the destination
     * @param stage8
     */
    @FXML
    public void setSceneMainMenu(Stage stage8){
        stage8.setOnCloseRequest(e -> {
        try{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            orderAddForRating(orderforAnimation);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_user/menu_user.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) animationGroup.getScene().getWindow();
            stage.setScene(scene);}
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        });
    }
}