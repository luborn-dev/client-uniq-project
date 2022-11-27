package br.com.uniq.controllers;

import br.com.uniq.Cliente;
import br.com.uniq.MeuObj;
import br.com.uniq.MyString;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField entryCpf;

    @FXML
    private TextField entryIdade;

    @FXML
    private TextField entryNome;

    @FXML
    private PasswordField entrySenha;

    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnVoltar.setOnAction( event ->{
            backToLogin();
        });

        btnCadastrar.setOnAction( event ->{
            cadastrarNovoCliente();
        });
    }

    public void backToLogin(){
        btnVoltar.getScene().getWindow().hide();
        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("/br/com/uniq/login-view.fxml"));
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

    public void cadastrarNovoCliente(){
        new Thread(new Cliente(socket, new MeuObj(entryNome.getText(), entryCpf.getText(),
                Integer.parseInt(entryIdade.getText()),entrySenha.getText()),2)).start();
    }
}
