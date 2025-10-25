package com.example.gymmanager.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.gymmanager.*;
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

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button loginButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label registrationText;

    @FXML
    private Label warningText;

    @FXML
    private URL location;

    @FXML
    void onLoginClick(ActionEvent event) {
        if(!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            if(emailField.getText().contains("@")){
                DatabaseHandler handler = new DatabaseHandler();
                try(Connection connection = handler.getDbConnection()){
                    UserDAO userDAO = new UserDAO(handler);
                    if(userDAO.userExist(new User(null, null, null,
                            emailField.getText(), passwordField.getText(), null))) {
                        String sql = "SELECT * FROM " + DbConst.USER_TABLE + " WHERE " +
                                DbConst.USERS_EMAIL + " = ?";
                        ResultSet resultSet = handler.executeQuery(connection, sql, emailField.getText());
                        while (resultSet.next()){
                            String password = resultSet.getString("password");
                            if(password.equals(passwordField.getText())){
                                //TODO
                            } else{
                                warningText.setVisible(true);
                                warningText.setText("Неправильний пароль!!");
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else{
                warningText.setVisible(true);
                warningText.setText("Неправильний email!!");
            }
        }
        else{
            warningText.setVisible(true);
            warningText.setText("Заповніть всі поля!!!");
        }
    }

    @FXML
    void onRegistrationTextClick(MouseEvent event){
        registrationText.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gymmanager/registration.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) registrationText.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void initialize() {

    }

}
