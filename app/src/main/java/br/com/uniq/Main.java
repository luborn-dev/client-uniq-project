package br.com.uniq;

import br.com.uniq.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    @Override
    public void start(Stage stage) throws Exception {
        Socket socket = new Socket("localhost", 3000);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        LoginController loginController = fxmlLoader.getController();
        loginController.setSocket(socket);
        stage.setTitle("Uniq!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
