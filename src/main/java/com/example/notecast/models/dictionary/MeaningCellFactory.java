package com.example.notecast.models.dictionary;

import com.example.notecast.controllers.MeaningCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MeaningCellFactory implements Callback<ListView<Meaning>, ListCell<Meaning>> {
    @Override
    public ListCell<Meaning> call(ListView<Meaning> meaningListView) {
        return new MeaningCell();
    }
}
