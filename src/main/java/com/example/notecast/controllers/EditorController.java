package com.example.notecast.controllers;

import com.example.notecast.models.dictionary.Meaning;
import com.example.notecast.models.dictionary.MeaningCellFactory;
import com.example.notecast.models.dictionary.SearchResult;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import java.awt.MenuItem;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class EditorController {
    File openFile = null;
    @FXML
    private MenuItem close;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button dictionaryButton, googleButton, hideButton;
    @FXML
    private SplitPane leftRightSplitPane, topBottomSplitPane;
    @FXML
    private AnchorPane rightPane, rightTopPane, rightBottomPane;
    @FXML
    private TextField wordSearchInput;
    @FXML
    private ListView<Meaning> listView;
    @FXML
    private WebView searchView;



    @FXML
    void showDictionarySearchResults() {
        listView.getItems().clear();
        String word = wordSearchInput.getText();
        System.out.println(word);
        try {
            new SearchResult(word, listView);
        } catch (InterruptedException | IOException | UnirestException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hideRightPane() {
        rightPane.setMinWidth(0.0);
        rightPane.setMaxWidth(0.0);
        leftRightSplitPane.setDividerPosition(0, 1);
        hideButton.setVisible(false);
        dictionaryButton.setVisible(true);
        googleButton.setVisible(true);
        rightPane.setVisible(false);
    }

    @FXML
    void showDictionary() {
        rightPane.setVisible(true);
        rightTopPane.setVisible(true);
        rightBottomPane.setVisible(false);

        rightPane.setMinWidth(200.0);
        rightPane.setMaxWidth(Double.POSITIVE_INFINITY);
        rightBottomPane.setMaxHeight(0.0);
        rightTopPane.setMaxHeight(Double.POSITIVE_INFINITY);

        leftRightSplitPane.setDividerPosition(0, 0.6);
        topBottomSplitPane.setDividerPosition(0, 1);

        dictionaryButton.setVisible(false);
        googleButton.setVisible(true);
        hideButton.setVisible(true);
    }

    @FXML
    void showGoogleSearch() {
        rightPane.setVisible(true);
        rightBottomPane.setVisible(true);
        rightTopPane.setVisible(false);

        rightPane.setMinWidth(200.0);
        rightPane.setMaxWidth(Double.POSITIVE_INFINITY);
        rightBottomPane.setMaxHeight(Double.POSITIVE_INFINITY);
        rightTopPane.setMaxHeight(0);

        leftRightSplitPane.setDividerPosition(0, 0.6);
        topBottomSplitPane.setDividerPosition(0, 0);

        googleButton.setVisible(false);
        dictionaryButton.setVisible(true);
        hideButton.setVisible(true);

//        if(searchView.getEngine().getLocation() == null)
        searchView.getEngine().load("https://google.com");
    }


    public void openNotes(File file) {
        this.openFile = file;
        StringBuilder html = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert fileReader != null;
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line=bufferedReader.readLine())!= null){
                html.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlEditor.setHtmlText(html.toString());
    }

//    public void handleHTMLtoText(ActionEvent event) throws IOException {
//        textarea.setText(htmlEditor.getHtmlText());
//        PrintWriter writer = new PrintWriter("temp.html", StandardCharsets.UTF_8);
//        writer.println(htmlEditor.getHtmlText());
//        writer.close();
//    }
//    public void handleHTMLtoWeb(ActionEvent event) throws FileNotFoundException {
//        WebEngine webengine = webview.getEngine();
//        StringBuilder html = new StringBuilder();
//        FileReader fileReader = new FileReader("temp.html");
////        String result;
//        try {
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line;
//            while((line=bufferedReader.readLine())!= null){
//                html.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        webengine.loadContent(html.toString(), "text/html");
//    }

    @FXML
    private void initialize() {
        FileChooser fileChooser = new FileChooser();
        Node toolBarNode = htmlEditor.lookup(".top-toolbar");

        if (toolBarNode instanceof ToolBar) {
            ToolBar bar = (ToolBar) toolBarNode;
            Button btn = new Button("Hyperlink");
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/image/hyperlink.png")));
            btn.setMinSize(26.0, 22.0);
            btn.setMaxSize(26.0, 22.0);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            btn.setGraphic(iv);
            btn.setTooltip(new javafx.scene.control.Tooltip("Hyperlink"));
            btn.setOnAction(e -> {
                File selectedFile = fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());
                if(selectedFile != null) {
                    WebView webView = (WebView) htmlEditor.lookup("WebView");
                    String selected = (String) webView.getEngine().executeScript("window.getSelection().toString();");
                    String hyperlinkHtml = null;
                    try {
                        hyperlinkHtml = "<a href=\"" + selectedFile.toURI().toURL().toExternalForm() + "\" title=\"" + selected + "\" target=\"_blank\">" + selected + "</a>";
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    webView.getEngine().executeScript("insertHtmlAtCursor('" + hyperlinkHtml + "');"
                            + "function insertHtmlAtCursor(html) {\n"
                            + " var range, node;\n"
                            + " if (window.getSelection && window.getSelection().getRangeAt) {\n"
                            + " window.getSelection().deleteFromDocument();\n"
                            + " range = window.getSelection().getRangeAt(0);\n"
                            + " node = range.createContextualFragment(html);\n"
                            + " range.insertNode(node);\n"
                            + " } else if (document.selection && document.selection.createRange) {\n"
                            + " document.selection.createRange().pasteHTML(html);\n"
                            + " document.selection.clear();"
                            + " }\n"
                            + "}"
                    );
                }
            });
            bar.getItems().add(btn);
        }
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                NodeList nodeList = webView.getEngine().getDocument().getElementsByTagName("a");
                if(nodeList != null) {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        org.w3c.dom.Node node = nodeList.item(i);
                        ((EventTarget) node).
                                addEventListener("click", evt -> {
                                    EventTarget target = evt.getCurrentTarget();
                                    HTMLAnchorElement anchorElement = (HTMLAnchorElement) target;
                                    String href = anchorElement.getHref();
                                    File file = null;
                                    try {
                                        file = new File(new URI(href));
                                    } catch (URISyntaxException e) {
                                        e.printStackTrace();
                                    }
                                    //handle opening URL outside JavaFX WebView
                                    if(Desktop.isDesktopSupported() && Objects.requireNonNull(file).exists()) {
                                        try {
                                            Desktop.getDesktop().open(file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        System.out.println("Error");
                                    }
                                    System.out.println(href);
                                    evt.preventDefault();
                                }, false);
                    }
                }
            }
        });


        StringBuilder html = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("temp.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert fileReader != null;
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line=bufferedReader.readLine())!= null){
                html.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlEditor.setHtmlText(html.toString());

        listView.setCellFactory(new MeaningCellFactory());

    }

    public File exit(ActionEvent e) throws IOException {
        if(openFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File As");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.html"));
            openFile = fileChooser.showSaveDialog(((Node) e.getSource()).getScene().getWindow());
        }
        if (openFile != null) {
            PrintWriter writer = new PrintWriter(openFile.getAbsolutePath(), StandardCharsets.UTF_8);
            writer.println(htmlEditor.getHtmlText());
            writer.close();
        }
        return openFile;
    }
}


