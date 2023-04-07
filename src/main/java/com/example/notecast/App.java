package com.example.notecast;

import com.example.notecast.controllers.LoginController;
import com.example.notecast.utils.StateManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            StateManager.push(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
            stage.setTitle("NoteCast");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}