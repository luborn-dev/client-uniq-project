package br.com.uniq.controllers;

import br.com.uniq.ServerHandler;
import br.com.uniq.modelos.ModeloDeExames;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TelaDeExamesController implements Initializable {

    private ArrayList<ModeloDeExames> listaDeExames;
    private ObservableList<ModeloDeExames> modeloDeExamesObservableList;
    private Socket socket;
    private String cpfDoUsuarioLogado;
    private String nomeDoUsuario;

    @FXML
    private TableView<ModeloDeExames> tabelaDeExames;

    @FXML
    private TableColumn<ModeloDeExames, LocalDateTime> dataCOL;

    @FXML
    private TableColumn<ModeloDeExames, String> clinicaCOL;

    @FXML
    private TableColumn<ModeloDeExames, String> tipoDoExameCOL;

    @FXML
    private Label labelBoasVindas;

    @FXML
    private Button btnRecarregar;

    public String getNomeDoUsuario() {
        return nomeDoUsuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();

        Platform.runLater(()->{
            labelBoasVindas.setText("Paciente "+getNomeDoUsuario());
            try {
                refreshTable();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        tabelaDeExames.setRowFactory(tv -> {
            TableRow<ModeloDeExames> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    ModeloDeExames clickedRow = row.getItem();

                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    VBox dialogVbox = new VBox(20);

                    Text tipoExame = new Text("Exame realizado: "+clickedRow.getTipoDoExame());
                    Text nomeMedico = new Text("Nome do médico: "+clickedRow.getNomeDoMedico());
                    Text especMedico = new Text("Especialidade do médico: "+clickedRow.getEspecialidadeDoMedico());
                    Text nomeClinica = new Text("Local do exame realizado: "+clickedRow.getNomeDaClinica());
                    Text dataExame = new Text("Data realização: "+clickedRow.getData());
                    Text conclusao = new Text("Parecer médico: "+clickedRow.getConclusao());

                    tipoExame.setWrappingWidth(300);
                    nomeMedico.setWrappingWidth(300);
                    especMedico.setWrappingWidth(300);
                    nomeClinica.setWrappingWidth(300);
                    dataExame.setWrappingWidth(300);
                    conclusao.setWrappingWidth(300);

                    dialogVbox.getChildren().add(tipoExame);
                    dialogVbox.getChildren().add(nomeMedico);
                    dialogVbox.getChildren().add(especMedico);
                    dialogVbox.getChildren().add(nomeClinica);
                    dialogVbox.getChildren().add(dataExame);
                    dialogVbox.getChildren().add(conclusao);

                    dialogVbox.setAlignment(Pos.CENTER);
                    Scene dialogScene = new Scene(dialogVbox, 400, 600);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
            });
            return row ;
        });

        btnRecarregar.setOnAction(event -> {
            try {
                refreshTable();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void loadDate(){
        clinicaCOL.setCellValueFactory(new PropertyValueFactory<>("nomeDaClinica"));
        tipoDoExameCOL.setCellValueFactory(new PropertyValueFactory<>("tipoDoExame"));
        dataCOL.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    private void refreshTable() throws InterruptedException, IOException {
        ServerHandler runnable = new ServerHandler(socket, getCpfDoUsuarioLogado(),3);
        new Thread(runnable).start();
//        PARA CONEXAO CLOUD SLEEP 10000
        Thread.currentThread().sleep(500);
        if(runnable.getRespostaDoServidor().getStatus().equals("erro")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(runnable.getRespostaDoServidor().getPayload());
            alert.showAndWait();
            System.out.println("Falha ao recarregar exames");
            socket.close();
            this.socket = new Socket("localhost", 3002);
        }
        if(runnable.getRespostaDoServidor().getStatus().equals("ok")){
            listaDeExames = runnable.getRespostaDoServidor().getPayload2();
            System.out.println("Sucesso ao encontrar exames");
            modeloDeExamesObservableList = FXCollections.observableArrayList(listaDeExames);
            tabelaDeExames.setItems(modeloDeExamesObservableList);
            socket.close();
            this.socket = new Socket("localhost", 3002);
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
