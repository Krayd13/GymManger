package com.example.gymmanager.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.gymmanager.DatabaseHandler;
import com.example.gymmanager.Roles;
import com.example.gymmanager.User;
import com.example.gymmanager.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fathersnameField;

    @FXML
    private Label loginText;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField surnameField;

    @FXML
    private Label warningText;

    @FXML
    void onLoginTextClick(MouseEvent event) {
        loginText.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gymmanager/login.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Parent parent = fxmlLoader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @FXML
    void onRegistrationClick(ActionEvent event) {
        if(!nameField.getText().isEmpty() &&
            !surnameField.getText().isEmpty() &&
            !fathersnameField.getText().isEmpty() &&
            !emailField.getText().isEmpty() &&
            !passwordField.getText().isEmpty()
        ){
            if(emailField.getText().contains("@")){
                User user = new User(nameField.getText(),
                        surnameField.getText(),
                        fathersnameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        Roles.MANAGER.toString());
                DatabaseHandler handler = new DatabaseHandler();
                try(Connection connection = handler.getDbConnection()){
                    UserDAO userDAO = new UserDAO(handler);
                    if(!userDAO.userExist(user)){
                        userDAO.addUser(user);
                    }else{
                        warningText.setVisible(true);
                        warningText.setText("Користувач вже існує!!!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else{
                warningText.setVisible(true);
                warningText.setText("Неправильнй email!!!");
            }
        } else{
            warningText.setVisible(true);
            warningText.setText("Заповніть всі поля!!!");
        }
    }

    @FXML
    void initialize() {

    }

}
