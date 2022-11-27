package br.com.uniq;

import java.io.Serializable;

public class CastingToDb implements Serializable {
    private String payload;
    private String status;

    public CastingToDb(String payload, String status) {
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
