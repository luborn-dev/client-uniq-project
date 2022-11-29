package br.com.uniq.controllers;

import br.com.uniq.Cliente;
import br.com.uniq.ModeloDeExames;
import br.com.uniq.ModeloDeLogin;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Socket socket;
    private String cpfDoUsuarioLogado;
    private String nomeDoUsuario;

    @FXML
    private TableColumn<ModeloDeExames, String> clinicaCol;

    @FXML
    private TableColumn<ModeloDeExames, String> dataCol;

    @FXML
    private TableColumn<ModeloDeExames, String> tipoDoExameCol;

    @FXML
    private Label labelBoasVindas;

    @FXML
    private Button refresh;

    private ArrayList<ModeloDeExames> listaDeExames;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh.setOnAction(event -> {
            try {
                refreshTable();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//        labelBoasVindas.setText(labelBoasVindas.getText()+nomeDoUsuario);

    }



    private void loadDate(){
        clinicaCol.setCellValueFactory(new PropertyValueFactory<>("clinica"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        tipoDoExameCol.setCellValueFactory(new PropertyValueFactory<>("tipoDoExame"));
    }

    public String getCpfDoUsuarioLogado() {
        return cpfDoUsuarioLogado;
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
            listaDeExames.forEach(modeloDeExames -> {
                System.out.println(modeloDeExames.getData()+", "+modeloDeExames.getTipoDoExame()+
                        ", "+modeloDeExames.getNomeDoMedico()+", "+modeloDeExames.getEspecialidadeDoMedico());
            });
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
}
