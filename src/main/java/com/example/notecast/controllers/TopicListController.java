package com.example.notecast.controllers;

import com.example.notecast.models.database.Notebook;
import com.example.notecast.models.database.Topic;
import com.example.notecast.utils.StateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.Instant;

public class TopicListController {

    Notebook notebook;
    public void setNotebook(Notebook nb){
        notebook = nb;
        loadData();
    }

    @FXML
    private Button back;

    @FXML
    private TableColumn<Topic, String> colLastEdited;

    @FXML
    private TableColumn<Topic, Button> colOpen;

    @FXML
    private TableColumn<Topic, String> colTimeCreated;

    @FXML
    private TableColumn<Topic, String> colTitle;

    @FXML
    private TableView<Topic> noteTableView;

    @FXML
    void backAction(ActionEvent event) {
        StateManager.pop();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(StateManager.peek());
    }

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
        colLastEdited.setCellValueFactory(new PropertyValueFactory<>("lastEdited"));
        colTimeCreated.setCellValueFactory(new PropertyValueFactory<>("timeCreated"));
        colOpen.setCellValueFactory(new PropertyValueFactory<>("open"));
    }

    private void loadData() {


        ObservableList<Topic> data = FXCollections.observableArrayList();

        data.addAll(notebook.getTopics());

        /*data.add(new Topic(1, "DLD", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), notebook.getId(), null));
        data.add(new Topic(2, "DS", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), notebook.getId(), null));
        data.add(new Topic(3, "COA", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), notebook.getId(), null));
        data.add(new Topic(4, "EEE", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), notebook.getId(), null));
        */

        noteTableView.setItems(data);
    }

}