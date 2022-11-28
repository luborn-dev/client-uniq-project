package br.com.uniq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente implements Runnable {
    private Socket socket;
    private ObjectOutputStream transmissor;
    private ObjectInputStream receptor;
    private MeuObj meuObj;
    private int opc;
    private LoginModelo loginModelo;
    private CastingToDb casted;

    public Cliente(Socket socket, MeuObj meuObj, int opc){
        try{
            this.socket = socket;
            this.transmissor = new ObjectOutputStream(socket.getOutputStream());
            this.receptor    = new ObjectInputStream(socket.getInputStream());
            this.meuObj = meuObj;
            this.opc = opc;
        } catch (IOException e){
            closeEverything(socket, transmissor, receptor);
        }
    }

    public Cliente(Socket socket, LoginModelo loginModelo, int opc){
        try{
            this.socket = socket;
            this.transmissor = new ObjectOutputStream(socket.getOutputStream());
            this.receptor    = new ObjectInputStream(socket.getInputStream());
            this.loginModelo = loginModelo;
            this.opc = opc;
        } catch (IOException e){
            closeEverything(socket, transmissor, receptor);
        }
    }

    public void closeEverything(Socket socket, ObjectOutputStream transmissor, ObjectInputStream receptor){
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

    public void userLogin(){
        try{
            transmissor.writeObject(loginModelo);
            CastingToDb recebi;
            recebi = (CastingToDb) receptor.readObject();
            setCasted(recebi);
            transmissor.close();
        } catch (IOException e){
            System.out.println(e);
            closeEverything(socket,transmissor,receptor);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void userSignUp(){
        try{
            transmissor.writeObject(meuObj);
            CastingToDb recebi;
            recebi = (CastingToDb) receptor.readObject();
            setCasted(recebi);
            System.out.println(recebi.getPayload()+" "+recebi.getStatus());
//            Thread.currentThread().interrupt(); @TODO
        } catch (IOException | ClassNotFoundException e){
            System.out.println(e);
            closeEverything(socket,transmissor,receptor);
        }
    }

    @Override
    public void run() {
        System.out.println(opc);
        if (opc==1){
            this.userLogin();
        }
        if (opc==2){
            this.userSignUp();
        }
        else{
            System.out.println("Usuario nao passou opcao corretamente");
        }
    }

    public void setCasted(CastingToDb casted) {
        this.casted = casted;
    }

    public CastingToDb getCasted() {
        return casted;
    }
}