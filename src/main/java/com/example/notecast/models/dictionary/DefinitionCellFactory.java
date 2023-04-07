package com.example.notecast.models.dictionary;

import com.example.notecast.controllers.DefinitionCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DefinitionCellFactory implements Callback<ListView<Definition>, ListCell<Definition>> {
    @Override
    public ListCell<Definition> call(ListView<Definition> meaningListView) {
        return new DefinitionCell();
    }
}
