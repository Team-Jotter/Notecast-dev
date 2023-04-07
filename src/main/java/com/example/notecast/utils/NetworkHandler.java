package com.example.notecast.utils;

import com.example.notecast.models.dictionary.SearchResult;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import java.io.IOException;

public class NetworkHandler {

    public static void getUnirestResponse(String word) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/"+word;
        HttpRequest request = new HttpRequest(HttpMethod.GET, url);
        request.asJsonAsync(new Callback<>() {
            @Override
            public void completed(HttpResponse<JsonNode> httpResponse) {
                SearchResult.getResultFromJSONObject(httpResponse.getBody().getArray().getJSONObject(0));
                try {
                    Unirest.shutdown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.out.println("The request is cancelled");
            }
        });
    }

//    public static HttpResponse<String> getHttpRequestResponse(String url) throws IOException, InterruptedException {
//        var request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .GET()
//                .build();
//        var client = HttpClient.newHttpClient();
//        return client.send(request, HttpResponse.BodyHandlers.ofString());
//    }

}
