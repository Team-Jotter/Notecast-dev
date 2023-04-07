package com.example.notecast.controllers;

import com.example.notecast.App;
import com.example.notecast.models.database.Content;
import com.example.notecast.models.database.Notebook;
import com.example.notecast.models.database.Topic;
import com.example.notecast.models.database.User;
import com.example.notecast.utils.DatabaseHandler;
import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;


public class NotebookMenuController {
    Notebook notebook;
    public void setNotebook(Notebook nb){
        notebook = nb;
        titleLabel.setText("Title: " + notebook.getTitle());
        priorityLabel.setText("Priority: " + notebook.getPriority());
        timeCreatedLabel.setText("Created: " + notebook.getTimeCreated().toLocalDateTime().toLocalDate().toString());
        lastEditedLabel.setText("Last Edited: " + notebook.getLastEdited().toLocalDateTime().toLocalDate().toString());
    }


    @FXML
    private Button back;

    @FXML
    private Button browseTopic;

    @FXML
    private Label lastEditedLabel;

    @FXML
    private Button newTopic;

    @FXML
    private Button openTopic;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label timeCreatedLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void backAction(ActionEvent e) {
        StateManager.pop();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(StateManager.peek());
    }

    @FXML
    void browseTopicAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("topic_list.fxml"));
        Parent root = loader.load();
        TopicListController controller = loader.getController();
        controller.setNotebook(notebook);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        StateManager.push(scene);
        stage.show();
    }

    @FXML
    void newTopicAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("editor.fxml"));
        Parent root = loader.load();
        EditorController controller = loader.getController();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        StateManager.push(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Exit");
            alert.setHeaderText("Do you want save the document?");
            ButtonType buttonType = alert.showAndWait().get();
            if (ButtonType.YES.equals(buttonType)) {
                try {
                    File selectedFile = controller.exit(e);
                    //TODO: save selectedFile information to Notebook data
                    Topic topic = DatabaseHandler.createTopic(selectedFile.getName(),Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), notebook.getId(), null);
                    Content content = DatabaseHandler.createContent(selectedFile.getName(),null, null, selectedFile.getParent(), topic.getId());
                    topic.setContent(content);
                    notebook.addTopic(topic);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                StateManager.pop();
                stage.setScene(StateManager.peek());
            } else if (ButtonType.NO.equals(buttonType)) {
                StateManager.pop();
                stage.setScene(StateManager.peek());
            }
            stage.setOnCloseRequest(null);
        });
    }

    @FXML
    void openTopicAction(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.html"));
        File selectedFile = fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editor.fxml"));
            Parent root = loader.load();
            EditorController controller = loader.getController();
            controller.openNotes(selectedFile);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            StateManager.push(scene);
            stage.show();
            stage.setOnCloseRequest(windowEvent -> {
                windowEvent.consume();
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.setTitle("Exit");
                alert.setHeaderText("Do you want save the document?");
                ButtonType buttonType = alert.showAndWait().get();
                if (ButtonType.YES.equals(buttonType)) {
                    try {
                        controller.exit(e);
                        //TODO: save selectedFile information to Notebook data
                        Topic topic = DatabaseHandler.createTopic(selectedFile.getName(),Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), notebook.getId(), null);
                        Content content = DatabaseHandler.createContent(selectedFile.getName(),null, null, selectedFile.getParent(), topic.getId());
                        topic.setContent(content);
                        notebook.addTopic(topic);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    StateManager.pop();
                    stage.setScene(StateManager.peek());
                } else if (ButtonType.NO.equals(buttonType)) {
                    StateManager.pop();
                    stage.setScene(StateManager.peek());
                }
                stage.setOnCloseRequest(null);
            });
        }
    }

}
