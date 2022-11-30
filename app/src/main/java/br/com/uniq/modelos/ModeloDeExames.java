package br.com.uniq.modelos;

import java.sql.Date;

public class ModeloDeExames extends ModeloDeComunicado {

    private String nomeDoMedico;
    private String especialidadeDoMedico;
    private String tipoDoExame;
    private Date data;
    private String conclusao;
    private String status;
    private String nomeDaClinica;
    private String cpfPaciente;

    public ModeloDeExames(String nomeDoMedico, String especialidadeDoMedico,
                          String tipoDoExame, Date data, String conclusao,
                          String status, String nomeDaClinica, String cpfPaciente) {
        this.nomeDoMedico = nomeDoMedico;
        this.especialidadeDoMedico = especialidadeDoMedico;
        this.tipoDoExame = tipoDoExame;
        this.data = data;
        this.conclusao = conclusao;
        this.status = status;
        this.nomeDaClinica = nomeDaClinica;
        this.cpfPaciente = cpfPaciente;
    }

    public String getNomeDoMedico() {
        return nomeDoMedico;
    }

    public void setNomeDoMedico(String nomeDoMedico) {
        this.nomeDoMedico = nomeDoMedico;
    }

    public String getEspecialidadeDoMedico() {
        return especialidadeDoMedico;
    }

    public void setEspecialidadeDoMedico(String especialidadeDoMedico) {
        this.especialidadeDoMedico = especialidadeDoMedico;
    }

    public String getTipoDoExame() {
        return tipoDoExame;
    }

    public void setTipoDoExame(String tipoDoExame) {
        this.tipoDoExame = tipoDoExame;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getConclusao() {
        return conclusao;
    }

    public void setConclusao(String conclusao) {
        this.conclusao = conclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeDaClinica() {
        return nomeDaClinica;
    }

    public void setNomeDaClinica(String nomeDaClinica) {
        this.nomeDaClinica = nomeDaClinica;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }
}
