package br.com.uniq;

import br.com.uniq.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    static Socket socket;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        LoginController loginController = fxmlLoader.getController();
        loginController.setSocket(socket);
        stage.setTitle("Uniq!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 3000);
        launch();
    }

}
