package com.example.notecast.controllers;

import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Stack;

public class InstructionsController {
    @FXML
    public Button returnButton;

    public void returnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)returnButton.getScene().getWindow();
        StateManager.pop();
        stage.setScene(StateManager.peek());
    }
}
