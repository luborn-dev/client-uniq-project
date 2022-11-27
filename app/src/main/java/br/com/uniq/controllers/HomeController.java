package br.com.uniq.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {



    @FXML
    private Label labelWelcome;
    @FXML
    private JFXListView<?> homeListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    void setName(String name){
        labelWelcome.setText("Bem-vindo(a), "+ name);

    }
}
