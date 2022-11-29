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
    private String cpfDoUsuarioLogado;

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

    // CONSTRUTOR PARA ENVIAR SOLICITACAO DE EXAMES
    public Cliente(Socket socket, String cpf, int opc){
        try{
            this.socket = socket;
            this.transmissor = new ObjectOutputStream(socket.getOutputStream());
            this.receptor    = new ObjectInputStream(socket.getInputStream());
            this.cpfDoUsuarioLogado = cpf;
            this.opc = opc;
        } catch (IOException e){
            fecharTodasConexoes(socket, transmissor, receptor);
        }
    }

    @Override
    public void run() {
        switch (this.opc){
            case 1:
                System.out.println("Cliente enviando solic. login");
                this.servidorRecebaLogin();
                break;
            case 2:
                System.out.println("Cliente enviando solic. cadastro");
                this.servidorRecebaCadastro();
                break;
            case 3:
                System.out.println("Cliente enviando solic. exames");
                this.servidorRecebaSolicitacaoDeExames();
                break;
            default:
                break;
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

    public void servidorRecebaSolicitacaoDeExames(){
        try{
            transmissor.writeObject(cpfDoUsuarioLogado);
            RespostaDoServidor recebi;
            recebi = (RespostaDoServidor) receptor.readObject();
            setRespostaDoServidor(recebi);
            System.out.println(recebi.getPayload2()+" "+recebi.getStatus());
//            Thread.currentThread().interrupt();
        } catch (IOException e){
            System.out.println(e);
            fecharTodasConexoes(socket,transmissor,receptor);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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