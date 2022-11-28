package br.com.uniq;

import java.io.Serializable;

public class RespostaDoServidor implements Serializable {
    private String payload;
    private String status;

    public RespostaDoServidor(String payload, String status) {
        this.payload = payload;
        this.status = status;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }
}
