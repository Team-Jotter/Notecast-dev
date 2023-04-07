package com.example.notecast.controllers;

import com.example.notecast.models.database.Notebook;
import com.example.notecast.models.database.User;
import com.example.notecast.utils.StateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class NotebookListController {

    User user;
    public void setUser(User u){
        user = u;
        loadData();
    }

    @FXML
    private Button back;

    @FXML
    private TableColumn<Notebook, String> colLastEdited;

    @FXML
    private TableColumn<Notebook, Button> colOpen;

    @FXML
    private TableColumn<Notebook, Integer> colPriority;

    @FXML
    private TableColumn<Notebook, String> colTimeCreated;

    @FXML
    private TableColumn<Notebook, String> colTitle;

    @FXML
    private TableView<Notebook> noteTableView;

    @FXML
    public void initialize() {
        back.setOnAction(e -> {
            StateManager.pop();
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(StateManager.peek());
        });
        initTable();
    }

    private void initTable() {
        initCols();
    }

    private void initCols() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colLastEdited.setCellValueFactory(new PropertyValueFactory<>("lastEdited"));
        colTimeCreated.setCellValueFactory(new PropertyValueFactory<>("timeCreated"));
        colOpen.setCellValueFactory(new PropertyValueFactory<>("open"));
    }

    private void loadData() {


        ObservableList<Notebook> data = FXCollections.observableArrayList();

        data.addAll(user.getNotebooks());
//        data.add(new Notebook(1, "DLD", 0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), null, new ArrayList<>()));
//        data.add(new Notebook(2, "DS", 1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), null, new ArrayList<>()));
//        data.add(new Notebook(3, "COA", 2, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), null, new ArrayList<>()));
//        data.add(new Notebook(4, "EEE", 9, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), null, new ArrayList<>()));


        noteTableView.setItems(data);
    }

}
