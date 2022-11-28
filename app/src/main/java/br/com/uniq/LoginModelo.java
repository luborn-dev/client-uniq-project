package br.com.uniq;

public class LoginModelo extends Comunicado {
    private String cpf;
    private String senha;

    public LoginModelo(String cpf, String senha) {
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
