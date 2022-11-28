package br.com.uniq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente implements Runnable {
    private Socket socket;
    private ObjectOutputStream transmissor;
    private ObjectInputStream receptor;
    private int opc;
    private ModeloDeCadastro modeloDeCadastro;
    private ModeloDeLogin modeloDeLogin;
    private RespostaDoServidor respostaDoServidor;

    // CONSTRUTOR PARA ENVIAR SOLICITACAO DE CADASTRO
    public Cliente(Socket socket, ModeloDeCadastro modeloDeCadastro, int opc){
        try{
            this.socket = socket;
            this.transmissor = new ObjectOutputStream(socket.getOutputStream());
            this.receptor    = new ObjectInputStream(socket.getInputStream());
            this.modeloDeCadastro = modeloDeCadastro;
            this.opc = opc;
        } catch (IOException e){
            fecharTodasConexoes(socket, transmissor, receptor);
        }
    }

    // CONSTRUTOR PARA ENVIAR SOLICITACAO DE LOGIN
    public Cliente(Socket socket, ModeloDeLogin modeloDeLogin, int opc){
        try{
            this.socket = socket;
            this.transmissor = new ObjectOutputStream(socket.getOutputStream());
            this.receptor    = new ObjectInputStream(socket.getInputStream());
            this.modeloDeLogin = modeloDeLogin;
            this.opc = opc;
        } catch (IOException e){
            fecharTodasConexoes(socket, transmissor, receptor);
        }
    }

    @Override
    public void run() {
        if (opc==1){
            this.servidorRecebaLogin();
        }
        if (opc==2){
            this.servidorRecebaCadastro();
        }
        else{
            System.out.println("Usuario nao passou opcao corretamente");
        }
    }

    public void servidorRecebaLogin(){
        try{
            transmissor.writeObject(modeloDeLogin);
            RespostaDoServidor recebi;
            recebi = (RespostaDoServidor) receptor.readObject();
            setRespostaDoServidor(recebi);
            transmissor.close();
        } catch (IOException e){
            System.out.println(e);
            fecharTodasConexoes(socket,transmissor,receptor);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void servidorRecebaCadastro(){
        try{
            transmissor.writeObject(modeloDeCadastro);
            RespostaDoServidor recebi;
            recebi = (RespostaDoServidor) receptor.readObject();
            setRespostaDoServidor(recebi);
            System.out.println(recebi.getPayload()+" "+recebi.getStatus());
//            Thread.currentThread().interrupt(); @TODO
        } catch (IOException | ClassNotFoundException e){
            System.out.println(e);
            fecharTodasConexoes(socket,transmissor,receptor);
        }
    }

    public void fecharTodasConexoes(Socket socket, ObjectOutputStream transmissor, ObjectInputStream receptor){
        try{
            if (receptor != null){
                receptor.close();
            }
            if (transmissor != null){
                transmissor.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setRespostaDoServidor(RespostaDoServidor respostaDoServidor) {
        this.respostaDoServidor = respostaDoServidor;
    }

    public RespostaDoServidor getRespostaDoServidor() {
        return respostaDoServidor;
    }
}