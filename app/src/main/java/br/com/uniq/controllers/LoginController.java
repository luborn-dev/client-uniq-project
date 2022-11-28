package br.com.uniq.controllers;

import br.com.uniq.Cliente;
import br.com.uniq.LoginModelo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button loginButton;

    @FXML
    private Button loginForgetPass;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginSignUp;

    @FXML
    private TextField loginCpf;
    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginButton.setOnAction(event -> {
            try {
                onLoginButtonClick();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        loginSignUp.setOnAction(event -> {
            signUpUser();
        });
    }

    protected void onLoginButtonClick() throws IOException, InterruptedException {
        Cliente runnable = new Cliente(socket, new LoginModelo(loginCpf.getText(),loginPassword.getText()),1);
        new Thread(runnable).start();
        Thread.currentThread().sleep(10000);
        if(runnable.getCasted().getStatus().equals("erro")){
            System.out.println("Falhou");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(runnable.getCasted().getPayload());
            alert.showAndWait();
            socket.close();
            this.socket = new Socket("localhost", 3000);
        }
        if(runnable.getCasted().getStatus().equals("ok")){
            System.out.println("Sucesso");
            loginUser();
        }
        System.out.println("->"+runnable.getCasted().getPayload()+"->"+runnable.getCasted().getStatus());
    }

    public void loginUser(){
        if(!loginCpf.getText().toString().equals("")) {
            loginButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/br/com/uniq/exams-view.fxml"));
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


            homeStage.show();
        }
    }

    public void signUpUser(){
        loginButton.getScene().getWindow().hide();
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/br/com/uniq/cadastro-view.fxml"));
        try {
            loader2.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root2 = loader2.getRoot();
        Stage signUpStage = new Stage();
        signUpStage.setScene(new Scene(root2));
        signUpStage.setResizable(false);

        CadastroController cadastroController = loader2.getController();
        cadastroController.setSocket(socket);
        signUpStage.show();
    }
}