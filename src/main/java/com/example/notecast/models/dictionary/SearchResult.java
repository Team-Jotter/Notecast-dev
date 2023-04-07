package com.example.notecast.models.dictionary;

import com.example.notecast.utils.NetworkHandler;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchResult {

    private static ListView<Meaning> listView = null;

//    public void showResults(){
//        System.out.println(word);
//        System.out.println(phonetic);
//        System.out.println(origin);
//        for(Meaning meaning : meanings){
//            System.out.println("===========");
//            System.out.println("Parts of Speech: " + meaning.partOfSpeech);
//            for (Definition def : meaning.definitions) {
//                System.out.println("-------------");
//                System.out.println(def.definitionText);
//                System.out.println(def.example);
//            }
//        }
//    }

//    private void getResults(String word) {
//        var response = NetworkHandler.getHttpRequestResponse(url);
//        int len = response.body().length();
//        getResultFromJSONObject(new JSONObject(response.body().substring(1, len-1)));
//    }

    public SearchResult(String word, ListView<Meaning> listView) throws IOException, InterruptedException, UnirestException, ExecutionException {
        SearchResult.listView = listView;
        NetworkHandler.getUnirestResponse(word);
    }

    public static void getResultFromJSONObject(JSONObject json) {
        JSONArray means = json.optJSONArray("meanings");
        if(means == null) throw new RuntimeException(json.getString("title"));
        for(int i=0; i<means.length(); i++)
            listView.getItems().add(Meaning.createMeaningFromJSONObject(means.getJSONObject(i)));

//1        for (Meaning mean : meaningList) {
//            for (Definition def : mean.definitions)
//                listView.getItems().add(def.definitionText);
//        }
    }
}
