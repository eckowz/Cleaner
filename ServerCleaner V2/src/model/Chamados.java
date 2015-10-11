package model;

import java.util.Date;

/**
 *
 * @author gserafini
 */
public class Chamados {

    private String chamado;
    private Date dataEncerramento;

    public String getChamado() {
        return chamado;
    }

    public void setChamado(String chamado) {
        this.chamado = chamado;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Chamados(String chamado, Date dataEncerramento) {
        this.chamado = chamado;
        this.dataEncerramento = dataEncerramento;
    }

    public Chamados(String chamado) {
        this.chamado = chamado;
    }

    public Chamados() {
    }

}
