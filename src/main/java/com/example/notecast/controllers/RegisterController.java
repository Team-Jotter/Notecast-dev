package com.example.notecast.controllers;

import com.example.notecast.utils.DatabaseHandler;
import com.example.notecast.utils.StateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;
import java.util.regex.*;
import java.util.*;

import static com.example.notecast.utils.DatabaseHandler.getHash;
import static com.example.notecast.utils.DatabaseHandler.signup;

public class RegisterController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField professionTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmpasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    public void registerButtonAction(ActionEvent e) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmpasswordField.getText();
        String email = emailTextField.getText();

        var isRight = true;

        /*
            Username requirements:
            1. Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
            2. Username allowed of the dot (.), underscore (_), and hyphen (-).
            3. The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
            4. The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
            5. The number of characters must be between 5 to 20.
         */


        // check username validation
        if(username.length() <  5)
        {
            System.out.println("The number of character must be between 5 to 20");
            isRight = false;
        }
        else
        {
            String regex =  "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(username);
            System.out.println(matcher.matches());
            isRight = matcher.matches();
        }

        // check password validation

        if(password.length() < 6)
        {
            System.out.println("Password length must be at least 6 character long"); // show an alert
            isRight = false;
        }
        else
        {
            if(!password.equals(confirmPassword))
            {
                System.out.println("Passwords didn't match");
                isRight = false;
            }
        }

        // check email validation
        if(isRight)
        {
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            System.out.println(matcher.matches());
            isRight = matcher.matches();
        }

        if(isRight)
        {
            var user = signup(usernameTextField.getText(),emailTextField.getText(), getHash((passwordField.getText())), professionTextField.getText() );
        }

        //TODO: implement form validation
        System.out.println(usernameTextField.getText());
        System.out.println(emailTextField.getText());
        System.out.println(professionTextField.getText());
        System.out.println(passwordField.getText());
        System.out.println(confirmpasswordField.getText());
        //System.out.println(user);
        LoginController.showNotification("Successfully registered");
        cancelbuttonAction(e);
    }
    public void cancelbuttonAction(ActionEvent e)
    {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        StateManager.pop();
        stage.setScene(StateManager.peek());
    }

}
