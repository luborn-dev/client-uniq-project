package br.com.uniq.controllers;

import br.com.uniq.Cliente;
import br.com.uniq.ModeloDeExames;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TelaDeExamesController implements Initializable {

    private ArrayList<ModeloDeExames> listaDeExames;
    private ObservableList<ModeloDeExames> modeloDeExamesObservableList;
    private Socket socket;
    private String cpfDoUsuarioLogado;
    private String nomeDoUsuario;

    @FXML
    private TableColumn<ModeloDeExames, Date> dataCOL;

    @FXML
    private TableColumn<ModeloDeExames, String> especialidadeCOL;

    @FXML
    private Label labelBoasVindas;

    @FXML
    private TableColumn<ModeloDeExames, String> nomeDoMedicoCOL;

    @FXML
    private Button btnRecarregar;

    @FXML
    private TableView<ModeloDeExames> tabelaDeExames;

    @FXML
    private TableColumn<?, ?> tipoDoExameCOL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();
        btnRecarregar.setOnAction(event -> {
            try {
                refreshTable();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void loadDate(){
        especialidadeCOL.setCellValueFactory(new PropertyValueFactory<>("especialidadeDoMedico"));
        tipoDoExameCOL.setCellValueFactory(new PropertyValueFactory<>("tipoDoExame"));
        nomeDoMedicoCOL.setCellValueFactory(new PropertyValueFactory<>("nomeDoMedico"));
        dataCOL.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    private void refreshTable() throws InterruptedException, IOException {
        Cliente runnable = new Cliente(socket, getCpfDoUsuarioLogado(),3);
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
            listaDeExames = runnable.getRespostaDoServidor().getPayload2();
            System.out.println("Sucesso ao encontrar exames");
            modeloDeExamesObservableList = FXCollections.observableArrayList(listaDeExames);
            tabelaDeExames.setItems(modeloDeExamesObservableList);
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setNomeDoUsuario(String nomeDoUsuario) {
        this.nomeDoUsuario = nomeDoUsuario;
    }

    public void setCpfDoUsuarioLogado(String cpfDoUsuarioLogado) {
        this.cpfDoUsuarioLogado = cpfDoUsuarioLogado;
    }

    public String getCpfDoUsuarioLogado() {
        return cpfDoUsuarioLogado;
    }

}
