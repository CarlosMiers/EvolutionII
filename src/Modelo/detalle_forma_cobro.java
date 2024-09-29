/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class detalle_forma_cobro {

    String idmovimiento;
    formapago forma;
    moneda codmoneda;
    bancoplaza banco;
    String nrocheque;
    Date confirmacion;
    Double importepago;
    Double conversion;
    Double netocobrado;
    Date fechaentrega;
    Date fechacobrocheque;

    public detalle_forma_cobro(){
        
    }

    public detalle_forma_cobro(String idmovimiento, formapago forma, moneda codmoneda, bancoplaza banco, String nrocheque, Date confirmacion, Double importepago, Double conversion, Double netocobrado, Date fechaentrega, Date fechacobrocheque) {
        this.idmovimiento = idmovimiento;
        this.forma = forma;
        this.codmoneda = codmoneda;
        this.banco = banco;
        this.nrocheque = nrocheque;
        this.confirmacion = confirmacion;
        this.importepago = importepago;
        this.conversion = conversion;
        this.netocobrado = netocobrado;
        this.fechaentrega = fechaentrega;
        this.fechacobrocheque = fechacobrocheque;
    }

    public String getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(String idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public formapago getForma() {
        return forma;
    }

    public void setForma(formapago forma) {
        this.forma = forma;
    }

    public moneda getCodmoneda() {
        return codmoneda;
    }

    public void setCodmoneda(moneda codmoneda) {
        this.codmoneda = codmoneda;
    }

    public bancoplaza getBanco() {
        return banco;
    }

    public void setBanco(bancoplaza banco) {
        this.banco = banco;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public Date getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(Date confirmacion) {
        this.confirmacion = confirmacion;
    }

    public Double getImportepago() {
        return importepago;
    }

    public void setImportepago(Double importepago) {
        this.importepago = importepago;
    }

    public Double getConversion() {
        return conversion;
    }

    public void setConversion(Double conversion) {
        this.conversion = conversion;
    }

    public Double getNetocobrado() {
        return netocobrado;
    }

    public void setNetocobrado(Double netocobrado) {
        this.netocobrado = netocobrado;
    }

    public Date getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(Date fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public Date getFechacobrocheque() {
        return fechacobrocheque;
    }

    public void setFechacobrocheque(Date fechacobrocheque) {
        this.fechacobrocheque = fechacobrocheque;
    }
    
    
}
