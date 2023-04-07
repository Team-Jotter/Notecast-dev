package com.example.notecast.controllers;

import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.Stack;

public class SettingsController {
    @FXML
    public Button returnbtn;
    @FXML
    ChoiceBox<String> fontFamily;
    @FXML
    ChoiceBox<Integer> fontSize;
    @FXML
    ChoiceBox<String> theme;

    public void returnbtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)returnbtn.getScene().getWindow();
        StateManager.pop();
        stage.setScene(StateManager.peek());
    }
}
