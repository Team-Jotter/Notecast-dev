package com.example.notecast.models.dictionary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Definition {
    public final String definitionText;
    public final String example;
    public final ArrayList<String> synonyms;
    public final ArrayList<String> antonyms;

    public Definition(String definitionText, String example, ArrayList<String> synonyms, ArrayList<String> antonyms) {
        this.definitionText = definitionText;
        this.example = example;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public static Definition createDefinitionFromJSONObject(JSONObject json) {
        ArrayList<String> synList = new ArrayList<>();
        ArrayList<String> antList = new ArrayList<>();
        JSONArray syns = json.optJSONArray("synonyms");
        if(syns != null)
            for (int i = 0; i < syns.length(); i++) synList.add(syns.getString(i));
        JSONArray ants = json.optJSONArray("antonyms");
        if(ants != null)
            for (int i = 0; i < ants.length(); i++) antList.add(ants.getString(i));
        return new Definition(json.optString("definition"),json.optString("example", ""), synList, antList);
    }

}
