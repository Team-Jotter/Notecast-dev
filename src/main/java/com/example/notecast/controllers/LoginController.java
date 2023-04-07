package com.example.notecast.controllers;

import com.example.notecast.App;
import com.example.notecast.utils.DatabaseHandler;
import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import static com.example.notecast.utils.DatabaseHandler.getHash;

public class LoginController {

//    @FXML
//    private ImageView notecastImageview;
//    @FXML
//    private Button loginButton;
//    @FXML
//    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label Error;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField userpasswordField;

    private Stage stage;
    private Scene scene;

    public static void showNotification(String text) {
        Notifications notificationTest = Notifications.create();
        notificationTest.position(Pos.BASELINE_RIGHT);
        notificationTest.title("Notecast");
        notificationTest.text(text);
        notificationTest.show();
    }

    public void cancelbuttonAction(ActionEvent e)
    {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    public void loginButtonAction(ActionEvent e) throws IOException
    {
        if(usernameTextField.getText().isBlank() || userpasswordField.getText().isBlank()) System.out.println("No username or password given");
        else {

            // use hash function , use email as salt value

            var login = DatabaseHandler.login(usernameTextField.getText(), getHash(userpasswordField.getText()));
//            var login = true;

            System.out.println(login);

            if(login != null) {
                String temp = login.getName();

                try {
                    FileWriter myWriter = new FileWriter("filename.txt");
                    myWriter.write(temp);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException exx) {
                    System.out.println("An error occurred.");
                    exx.printStackTrace();
                }

                FXMLLoader loader = new FXMLLoader(App.class.getResource("browser.fxml"));
                Parent root = loader.load();
                BrowserController controller = loader.getController();
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                StateManager.push(scene);
                controller.setUser(login);
                stage.setScene(scene);
                showNotification("Successfully logged in");
                stage.show();

//                FXMLLoader loader = new FXMLLoader(App.class.getResource("editor.fxml"));
//                Parent root = loader.load();
//                EditorController controller = loader.getController();
//                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//                stage.setOnCloseRequest(windowEvent -> {
//                    windowEvent.consume();
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Exit");
//                    alert.setHeaderText("Save and Exit?");
//                    if(alert.showAndWait().get() == ButtonType.OK) {
//                        try {
//                            controller.exit();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                        stage.close();
//                    }
//                });
            }
        }
    }
    public void registerButtonAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("register.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        StateManager.push(scene);
        stage.setScene(scene);
        stage.show();
//

    }
}