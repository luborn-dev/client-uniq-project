package br.com.uniq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Uniq!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // launch();
        // UNCOMMENT THE LINE ABOVE TO RUN JAVA.FX

        // BELOW CODE IS JUST TESTING THE CLIENT-SERVER
        try
        {
            Socket conexao = new Socket ("localhost",7777);
            PrintWriter transmissor =
                    new PrintWriter (
                            conexao.getOutputStream ());

            BufferedReader teclado =
                    new BufferedReader (
                            new InputStreamReader(
                                    System.in));

            String texto;

            do
            {
                texto = teclado.readLine ();
                transmissor.println (texto);
                transmissor.flush ();
            }
            while (!texto.equalsIgnoreCase("FIM"));

            transmissor.close();
            conexao.close();
        }
        catch (Exception erro)
        {
            System.err.println (erro.getMessage());
        }
    }
}