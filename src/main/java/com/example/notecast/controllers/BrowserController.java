package com.example.notecast.controllers;

import com.example.notecast.App;
import com.example.notecast.models.database.User;
import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import javafx.scene.control.Button;


import java.io.IOException;

public class BrowserController {

    User user;
    public void setUser(User u){ user = u;}
    @FXML
    private Button newNote;

    @FXML
    private Button browseNotes;
    @FXML
    private Button settings;
    @FXML
    private Button instructions;

    @FXML
    private Button profile;
    @FXML
    private Button exit;

    public void showNotification() {
        Notifications notificationTest = Notifications.create();
        notificationTest.position(Pos.BASELINE_RIGHT);
        notificationTest.title("Test");
        notificationTest.text("Login done");
        notificationTest.show();
    }

    public void newNoteAction(ActionEvent e) throws IOException {
        System.out.println("New Note Created");
                FXMLLoader loader = new FXMLLoader(App.class.getResource("notebookdata.fxml"));
                Parent root = loader.load();
                NotebookDataController controller = loader.getController();
                controller.setUser(user);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
    }
    public void browseNoteAction(ActionEvent e) throws IOException {
        System.out.println("Browsing Notebooks");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("notebook_list.fxml"));
        Parent root = loader.load();
        NotebookListController controller = loader.getController();
        controller.setUser(user);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        StateManager.push(scene);
        stage.show();
    }

    public void profileAction(ActionEvent e) throws IOException {
        System.out.println("Profile opened");
        //Changes made
        String temp = user.getName();
        String em = user.getEmail(), pr= user.getProfession();
        String to = user.getSize().toString();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("profileinfo.fxml"));
        Parent root = loader.load();
        ProfileInfoController controller = loader.getController();
        controller.display(temp,em,pr,to);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        StateManager.push(scene);
        stage.show();
    }

    /*public void settingsAction(ActionEvent e) throws IOException {
        System.out.println("Settings opened");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("settings.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        StateManager.push(scene);
        stage.setScene(scene);
        stage.show();
    }*/
    public void instructionsAction(ActionEvent e) throws IOException {
        System.out.println("Instructions Showed");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("instructions.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        StateManager.push(scene);
        stage.setScene(scene);
        stage.show();
    }
    public void logoutAction()
    {
        Stage stage = (Stage)exit.getScene().getWindow();
        StateManager.pop();
        stage.setScene(StateManager.peek());
        stage.setOnCloseRequest(null);
    }


}
