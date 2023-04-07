package com.example.notecast.controllers;
import com.example.notecast.models.database.Notebook;
import com.example.notecast.models.database.User;
import com.example.notecast.utils.StateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
public class ProfileInfoController  {

    User u;

    public void setUser(User user){ u = user;}
    @FXML
    public Button back;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label profession;
    @FXML
    private Label notes;

    @FXML
    public void initialize(){
        back.setOnAction(e -> {
            StateManager.pop();
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(StateManager.peek());
        });
    }
    @FXML
    public void display(String Name,String Email, String Profession, String Notes){
        name.setText(Name);
        email.setText(Email);
        profession.setText(Profession);
        notes.setText(Notes);
    }
}


