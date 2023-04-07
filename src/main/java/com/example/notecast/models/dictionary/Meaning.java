package com.example.notecast.models.dictionary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Meaning {
    public final String partOfSpeech;
    public final ArrayList<Definition> definitions;

    public Meaning(String partOfSpeech, ArrayList<Definition> definitions) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
    }

    public static Meaning createMeaningFromJSONObject(JSONObject json) {
        ArrayList<Definition> defList = new ArrayList<>();
        JSONArray defs = json.getJSONArray("definitions");
        for (int i = 0; i < defs.length(); i++) {
            defList.add(Definition.createDefinitionFromJSONObject(defs.getJSONObject(i)));
        }
        return new Meaning(json.optString("partOfSpeech"), defList);
    }
}
