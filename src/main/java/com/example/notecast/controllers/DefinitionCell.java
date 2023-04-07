package com.example.notecast.controllers;

import com.example.notecast.App;
import com.example.notecast.models.dictionary.Definition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

import java.io.IOException;

public class DefinitionCell extends ListCell<Definition> {
    @FXML
    private Text definitionText;
    @FXML
    private Text exampleText;

    public DefinitionCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("definition_cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void updateItem(Definition definition, boolean empty) {
        super.updateItem(definition, empty);

        if(empty || definition == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            definitionText.setText(definition.definitionText);
            exampleText.setText(definition.example);

//            setMinWidth(getParent().getLayoutX());
//            setMaxWidth(getParent().getLayoutX());
//            setPrefWidth(getParent().getLayoutX());
//            setWrapText(true);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
