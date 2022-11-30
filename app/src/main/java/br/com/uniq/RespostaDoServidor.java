package br.com.uniq;

import java.io.Serializable;
import java.util.ArrayList;

public class RespostaDoServidor implements Serializable {
    private String payload;
    private String status;
    private ArrayList<ModeloDeExames> payload2;


    public RespostaDoServidor(String payload, String status) {
        this.payload = payload;
        this.status = status;
    }

    public RespostaDoServidor(ArrayList<ModeloDeExames> payload2, String status) {
        this.payload2 = payload2;
        this.status = status;
    }

    public ArrayList<ModeloDeExames> getPayload2() {
        return payload2;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }
}
