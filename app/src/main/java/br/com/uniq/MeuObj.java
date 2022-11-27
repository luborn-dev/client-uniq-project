package br.com.uniq;

public class MeuObj extends Comunicado{
    private String nome;
    private String cpf;
    private int idade;
    private String senha;

    public MeuObj(String nome, String cpf, int idade, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    public String getSenha() {
        return senha;
    }
}


