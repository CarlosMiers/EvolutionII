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
public class cabecera_gastos_unidad {

    double numero;
    Date fecha;
    sucursal sucursal;
    banco banco;
    double importe;
    String nrocheque;
    int usuarioalta;
    int cierre;
    double asiento;
    String observacion;

    public cabecera_gastos_unidad() {

    }

    public cabecera_gastos_unidad(double numero, Date fecha, sucursal sucursal, banco banco, double importe, String nrocheque, int usuarioalta, int cierre, double asiento, String observacion) {
        this.numero = numero;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.banco = banco;
        this.importe = importe;
        this.nrocheque = nrocheque;
        this.usuarioalta = usuarioalta;
        this.cierre = cierre;
        this.asiento = asiento;
        this.observacion = observacion;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public banco getBanco() {
        return banco;
    }

    public void setBanco(banco banco) {
        this.banco = banco;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public double getAsiento() {
        return asiento;
    }

    public void setAsiento(double asiento) {
        this.asiento = asiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
