package br.com.uniq.controllers;

import br.com.uniq.Cliente;
import br.com.uniq.ModeloDeLogin;
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
    private Button btnLogar;

    @FXML
    private TextField usuarioCpf;

    @FXML
    private PasswordField usuarioSenha;

    @FXML
    private Button btnCadastrar;

    private Socket socket;

    private String nomeDoUsuarioLogado;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnCadastrar.setOnAction(event -> {
            trocarParaTelaDeCadastro();
        });

        btnLogar.setOnAction(event -> {
            try {
                aoClicarNoBotaoDeLogin();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected void aoClicarNoBotaoDeLogin() throws IOException, InterruptedException {
        Cliente runnable = new Cliente(socket, new ModeloDeLogin(usuarioCpf.getText(),usuarioSenha.getText()),1);
        new Thread(runnable).start();
        Thread.currentThread().sleep(10000);
        if(runnable.getRespostaDoServidor().getStatus().equals("erro")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(runnable.getRespostaDoServidor().getPayload());
            alert.showAndWait();
            System.out.println("Falha ao logar");
            socket.close();
            this.socket = new Socket("localhost", 3000);
        }
        if(runnable.getRespostaDoServidor().getStatus().equals("ok")){
            nomeDoUsuarioLogado = runnable.getRespostaDoServidor().getPayload();
            System.out.println("Sucesso ao logar");
            socket.close();
            this.socket = new Socket("localhost", 3000);
            trocarParaTelaDeExames();
        }
    }

    public void trocarParaTelaDeExames(){
        btnLogar.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/br/com/uniq/exames-view.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage homeStage = new Stage();
        homeStage.setScene(new Scene(root));
        homeStage.setResizable(false);
        TelaDeExamesController telaDeExamesController = loader.getController();
        telaDeExamesController.setCpfDoUsuarioLogado(usuarioCpf.getText());
        telaDeExamesController.setSocket(socket);
        telaDeExamesController.setNomeDoUsuario(nomeDoUsuarioLogado);
        homeStage.show();
    }

    public void trocarParaTelaDeCadastro(){
        btnLogar.getScene().getWindow().hide();
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