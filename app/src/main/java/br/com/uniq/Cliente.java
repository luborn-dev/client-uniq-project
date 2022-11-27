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
    private MyString cpfTeste;
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

    public Cliente(Socket socket, MyString cpfTeste, int opc){
        try{
            this.socket = socket;
            this.transmissor = new ObjectOutputStream(socket.getOutputStream());
            this.receptor    = new ObjectInputStream(socket.getInputStream());
            this.cpfTeste = cpfTeste;
            this.opc = opc;
        } catch (IOException e){
            closeEverything(socket, transmissor, receptor);
        }
    }

    public void sendMessage(){
//        while(socket.isConnected()){
//            try{
//                Scanner scanner = new Scanner(System.in);
//                System.out.println("Digite o nome: ");
//                String nome = scanner.nextLine();
//
//                System.out.println("Digite a idade: ");
//                int idade = scanner.nextInt();
//
//                MeuObj obj = new MeuObj(nome,idade);
//                System.out.println("Aqui ->" + obj.getNome().toString());
//                transmissor.writeObject(obj);
//            } catch (IOException e){
//                System.out.println(e);
//                closeEverything(socket,transmissor,receptor);
//            }
//        }

    }

    public void sendMessage2(String nome, String cpf, int idade, String senha){
        try{
            MeuObj obj = new MeuObj(nome,cpf,idade,senha);
            System.out.println("sm2 ->" + obj.getNome().toString());
            transmissor.writeObject(obj);
        } catch (IOException e){
            System.out.println(e);
            closeEverything(socket,transmissor,receptor);
        }
    }

    public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MeuObj msgFromGroupChat;
                while (socket.isConnected()){
                    try{
                        msgFromGroupChat = (MeuObj) receptor.readObject();
                        System.out.println("Aqui ->" + msgFromGroupChat.getNome().toString());
                    } catch (IOException | ClassNotFoundException e){
                        closeEverything(socket,transmissor,receptor);
                    }
                }
            }
        }).start();
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
            transmissor.writeObject(cpfTeste);
        } catch (IOException e){
            System.out.println(e);
            closeEverything(socket,transmissor,receptor);
        }
    }

    public void userSignUp(){
        try{
            transmissor.writeObject(meuObj);
            CastingToDb recebi;
            recebi = (CastingToDb) receptor.readObject();
            setCasted(recebi);
//            System.out.println(recebi.getPayload()+" "+recebi.getStatus());
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
        if(opc ==2){
            this.userSignUp();
        }
        else{
            System.out.println("Usuario nao passou opcao corretamente");
//            this.listenForMessages();
//            this.sendMessage2(this.name,this.cpf,this.idade,this.senha);
        }
//        this.listenForMessages();
//        this.sendMessage2(this.name,this.cpf,this.idade,this.senha);
    }

    public void setCasted(CastingToDb casted) {
        this.casted = casted;
    }

    public CastingToDb getCasted() {
        return casted;
    }
}