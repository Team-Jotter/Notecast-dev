package com.example.notecast.models.database;


import com.example.notecast.App;
import com.example.notecast.controllers.NotebookListController;
import com.example.notecast.controllers.NotebookMenuController;
import com.example.notecast.utils.StateManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Notebook {
    final int id;
    final String title;
    final int priority;
    final Timestamp timeCreated;
    final Timestamp lastEdited;
    final String userEmail;
    final ArrayList<Topic> topics;



    final Button open;

    public Notebook(int id, String title, int priority, Timestamp timeCreated, Timestamp lastEdited, String userEmail, ArrayList<Topic> topics) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.timeCreated = timeCreated;
        this.lastEdited = lastEdited;
        this.userEmail = userEmail;
        if(topics == null)
            this.topics = new ArrayList<>();
        else
            this.topics = topics;
        this.open = new Button("Open");
        open.setOnAction(e -> {
            System.out.println(title);
            FXMLLoader loader = new FXMLLoader(App.class.getResource("notebook_menu.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            NotebookMenuController controller = loader.getController();
            controller.setNotebook(this);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            assert root != null;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            StateManager.push(scene);
            stage.show();
        });
    }

    public Button getOpen() {
        return open;
    }
    public void addTopic(Topic topic)
    {
        topics.add(topic);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public Timestamp getLastEdited() {
        return lastEdited;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

}
