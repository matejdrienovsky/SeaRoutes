package com.example.searoutes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This is the main class of the application
 * Starts the application and loads the login screen
 */
public class HelloApplication extends Application {
    /**
     * Starts the application and loads the login screen
     * @param stage
     * @throws IOException
     * @throws URISyntaxException
     */
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));
        Image icon = new Image(getClass().getResource("images/sailing.png").toURI().toString());
        stage.getIcons().add(icon);
        Scene scene = new Scene(root);
        stage.setTitle("Sea Routes");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}