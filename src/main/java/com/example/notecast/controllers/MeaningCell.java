package com.example.notecast.controllers;

import com.example.notecast.App;
import com.example.notecast.models.dictionary.Definition;
import com.example.notecast.models.dictionary.DefinitionCellFactory;
import com.example.notecast.models.dictionary.Meaning;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;

public class MeaningCell extends ListCell<Meaning> {
    @FXML
    private Text posText; // parts of speech label

    @FXML
    private ListView<Definition> definitionListView;

    @FXML
    private void initialize() {
        definitionListView.setCellFactory(new DefinitionCellFactory());
    }

    public MeaningCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("meaning_cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void updateItem(Meaning meaning, boolean empty) {
        super.updateItem(meaning, empty);

        if(empty || meaning == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            posText.setText(meaning.partOfSpeech);
            definitionListView.getItems().clear();
            for(Definition definition : meaning.definitions) definitionListView.getItems().add(definition);
            setWrapText(true);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
