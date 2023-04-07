package com.example.notecast.models.database;

import com.example.notecast.App;
import com.example.notecast.controllers.EditorController;
import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class Topic {
    final int id;
    final String title;
    final Timestamp timeCreated;
    final Timestamp lastEdited;
    final int notebookId;
    private Content content;

    final Button open;

    public Topic(int id, String title, Timestamp timeCreated, Timestamp lastEdited, int notebookId, Content content) {
        this.id = id;
        this.title = title;
        this.timeCreated = timeCreated;
        this.lastEdited = lastEdited;
        this.notebookId = notebookId;
        this.content = content;
        open = new Button("Open");

        open.setOnAction(e -> {

            //test
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open File");
//            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.html"));
//            File selectedFile = fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());

            //System.out.println(selectedFile.getParent() + "\\" + selectedFile.getName());

//            System.out.println(content.baseHTML);
            File selectedFile = new File(Content.getRootLocation(), Content.getBaseHTML());
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editor.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        });
    }

    public Button getOpen() {
        return open;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public Timestamp getLastEdited() {
        return lastEdited;
    }

    public int getNotebookId() {
        return notebookId;
    }

    public Content getContent() {
        return content;
    }
}
