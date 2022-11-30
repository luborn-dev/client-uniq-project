package br.com.uniq.modelos;

public class ModeloDeLogin extends ModeloDeComunicado {
    private String cpf;
    private String senha;

    public ModeloDeLogin(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }
    public String getSenha() {
        return senha;
    }
}
