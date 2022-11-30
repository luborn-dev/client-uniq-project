package br.com.uniq.controllers;

import br.com.uniq.ServerHandler;
import br.com.uniq.modelos.ModeloDeCadastro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
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

    public void voltarParaTelaDeLogin(){
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
        LoginController loginController = loader3.getController();
        loginController.setSocket(socket);
        loginStage.show();
    }

    public void aoClicarNoBotaoCadastrar() throws InterruptedException, IOException {
        ServerHandler runnable = new ServerHandler(socket, new ModeloDeCadastro(entryNome.getText(), entryCpf.getText(),
                Integer.parseInt(entryIdade.getText()),entrySenha.getText()),2);
        new Thread(runnable).start();
//        PARA CONEXAO CLOUD SLEEP 10000
        Thread.currentThread().sleep(500);
        if(runnable.getRespostaDoServidor().getStatus().equals("erro")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(runnable.getRespostaDoServidor().getPayload());
            alert.showAndWait();
            System.out.println("Falha ao cadastrar novo usuário");
            socket.close();
            this.socket = new Socket("localhost", 3002);
        }
        if(runnable.getRespostaDoServidor().getStatus().equals("ok")){
            System.out.println("Sucesso ao cadastrar novo usuário");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(runnable.getRespostaDoServidor().getPayload());
            Optional<ButtonType> resultadoDoClick = alert.showAndWait();
            if(resultadoDoClick.isPresent()){
                alert.setOnCloseRequest((event -> {
                    alert.close();
                }));
                socket.close();
                this.socket = new Socket("localhost", 3002);
                voltarParaTelaDeLogin();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnVoltar.setOnAction( event ->{
            voltarParaTelaDeLogin();
        });

        btnCadastrar.setOnAction( event ->{
            try {
                aoClicarNoBotaoCadastrar();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
