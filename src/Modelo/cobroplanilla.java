/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author SERVIDOR
 */
public class cobroplanilla {

    double idpago;
    double idplanilla;
    Date fechacobro;
    formapago formacobro;
    bancoplaza cargobanco;
    String nrocheque;
    Date vencecheque;
    BigDecimal importe;

    public cobroplanilla() {

    }

    public cobroplanilla(double idpago, double idplanilla, Date fechacobro, formapago formacobro, bancoplaza cargobanco, String nrocheque, Date vencecheque, BigDecimal importe) {
        this.idpago = idpago;
        this.idplanilla = idplanilla;
        this.fechacobro = fechacobro;
        this.formacobro = formacobro;
        this.cargobanco = cargobanco;
        this.nrocheque = nrocheque;
        this.vencecheque = vencecheque;
        this.importe = importe;
    }

    public double getIdpago() {
        return idpago;
    }

    public void setIdpago(double idpago) {
        this.idpago = idpago;
    }

    public double getIdplanilla() {
        return idplanilla;
    }

    public void setIdplanilla(double idplanilla) {
        this.idplanilla = idplanilla;
    }

    public Date getFechacobro() {
        return fechacobro;
    }

    public void setFechacobro(Date fechacobro) {
        this.fechacobro = fechacobro;
    }

    public formapago getFormacobro() {
        return formacobro;
    }

    public void setFormacobro(formapago formacobro) {
        this.formacobro = formacobro;
    }

    public bancoplaza getCargobanco() {
        return cargobanco;
    }

    public void setCargobanco(bancoplaza cargobanco) {
        this.cargobanco = cargobanco;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public Date getVencecheque() {
        return vencecheque;
    }

    public void setVencecheque(Date vencecheque) {
        this.vencecheque = vencecheque;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }


    
}
