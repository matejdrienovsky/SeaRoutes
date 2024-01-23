package com.example.searoutes;

import Boats.Boat;
import Orders.Order;
import Orders.OrderExtend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.EventObject;
import java.util.Objects;

/**
 * Class that controls the order window
 * It is responsible for displaying the order details
 */
public class OrderController {
    @FXML javafx.scene.control.Label boatTypeLabel;
    @FXML javafx.scene.control.Label userLabel;
    @FXML javafx.scene.control.Label departureText;
    @FXML javafx.scene.control.Label arrivalText;
    @FXML javafx.scene.control.Label dateText;
    @FXML javafx.scene.control.Label captainNameText;
    @FXML javafx.scene.control.Label boatNameText;
    @FXML javafx.scene.control.Label type;
    @FXML javafx.scene.control.Label price;
    @FXML javafx.scene.image.ImageView weatherPicture;

    @FXML javafx.scene.image.ImageView boatPicture;
    private Order order;

    /**
     * Method that sets the order details and displays them
     * @param order
     * @throws IOException
     */
    public void setOrder(Order order) throws IOException {
        this.order = order;
        if (order instanceof OrderExtend) {
            String weather = order.weatherPicture();
            weatherPicture.setImage(new Image(Objects.requireNonNull(getClass().getResource(weather)).openStream()));
        }
        String weather2 = order.weatherPicture();
        weatherPicture.setImage(new Image(Objects.requireNonNull(getClass().getResource(weather2)).openStream()));
        boatTypeLabel.setText(order.getBoat().getClass().getSimpleName());
        userLabel.setText(order.getUsername());
        departureText.setText(order.getRout().getDeparture().getName());
        arrivalText.setText(order.getRout().getArrival().getName());
        dateText.setText(order.getDate());
        captainNameText.setText(order.getCaptain().getName());
        boatNameText.setText(order.getBoat().getName());
        String num1 = String.valueOf(order.getPrice());
        price.setText(num1+" €");

        //if the boat is class Boat, then the type of boat is not displayed because the Boat class does not have a types
        if (order.getBoat().getClass().equals(Boat.class)) {
            type.setVisible(false);
            boatTypeLabel.setVisible(false);
        }
        String boat = order.getBoat().setPicture();
        boatPicture.setImage(new Image(Objects.requireNonNull(getClass().getResource(boat)).openStream()));
    }

    /**
     * Method that transfers the order to the animation window on button click
     * @param event
     * @throws Exception
     */
    @FXML
    protected void onbuttonTravelClick(javafx.event.ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("animation/animation.fxml"));
        Parent root = loader.load();
        AnimationController controller = loader.getController();
        controller.setOrder(order);
        controller.animateBoat();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
