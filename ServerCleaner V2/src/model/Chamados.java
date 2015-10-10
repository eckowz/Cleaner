package model;

import java.util.Date;

/**
 *
 * @author gserafini
 */
public class Chamados {

    private double chamado;
    private Date dataEncerramento;

    public double getChamado() {
        return chamado;
    }

    public void setChamado(double chamado) {
        this.chamado = chamado;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Chamados(double chamado, Date dataEncerramento) {
        this.chamado = chamado;
        this.dataEncerramento = dataEncerramento;
    }

    public Chamados(double chamado) {
        this.chamado = chamado;
    }

}
