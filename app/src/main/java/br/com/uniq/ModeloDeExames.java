package br.com.uniq;

import java.sql.Date;

public class ModeloDeExames extends Comunicado {

    private String nomeDoMedico;
    private String especialidadeDoMedico;
    private String tipoDoExame;
    private Date data;

    public ModeloDeExames(String nomeDoMedico, String especialidadeDoMedico, String tipoDoExame, Date data) {
        this.nomeDoMedico = nomeDoMedico;
        this.especialidadeDoMedico = especialidadeDoMedico;
        this.tipoDoExame = tipoDoExame;
        this.data = data;
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
}
