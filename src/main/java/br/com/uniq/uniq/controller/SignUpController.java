package br.com.uniq.uniq.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private JFXButton btnBackToLogin;

    @FXML
    private JFXButton btnCadastrar;

    @FXML
    private JFXPasswordField cadastroCidade;

    @FXML
    private JFXTextField cadastroNome;

    @FXML
    private JFXPasswordField cadastroPassword;

    @FXML
    private JFXTextField cadastroUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackToLogin.setOnAction(event -> {
            backToLogin();
        });
    }

    public void backToLogin(){
        btnBackToLogin.getScene().getWindow().hide();
        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("/br/com/uniq/uniq/login-view.fxml"));
        try {
            loader3.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root3 = loader3.getRoot();
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root3));
        loginStage.setResizable(false);

        loginStage.show();

    }

}
