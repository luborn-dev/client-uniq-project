package br.com.uniq.uniq.controllers;

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

public class LoginController implements Initializable {
    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton loginForgetPass;

    @FXML
    private JFXPasswordField loginPassword;

    @FXML
    private JFXButton loginSignUp;

    @FXML
    private JFXTextField loginCpf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginButton.setOnAction(event -> {
                loginUser();
        });

        loginSignUp.setOnAction(event -> {
            signUpUser();
        });

    }
    public void loginUser(){
        if(!loginCpf.getText().toString().equals("")) {
            loginButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/br/com/uniq/uniq/home-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.setResizable(false);

            HomeController homeController = loader.getController();
            homeController.setName(loginCpf.getText().trim());

            homeStage.show();
        }
    }

    public void signUpUser(){
        loginSignUp.getScene().getWindow().hide();
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/br/com/uniq/uniq/signup-view.fxml"));
        try {
            loader2.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root2 = loader2.getRoot();
        Stage signUpStage = new Stage();
        signUpStage.setScene(new Scene(root2));
        signUpStage.setResizable(false);

        SignUpController signUpController = loader2.getController();
        signUpStage.show();
    }
}