package br.com.uniq;

public class MyString extends Comunicado {
    private String cpf;

    public MyString(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
