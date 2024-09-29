/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class cabecera_salida_mercaderia {

    String idreferencia;
    int numero;
    Date fecha;
    sucursal sucursal;
    comprobante tipo;
    int cierre;
    String observacion;
    double asiento;

    public cabecera_salida_mercaderia(){
        
    }

    public cabecera_salida_mercaderia(String idreferencia, int numero, Date fecha, sucursal sucursal, comprobante tipo, int cierre, String observacion, double asiento) {
        this.idreferencia = idreferencia;
        this.numero = numero;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.tipo = tipo;
        this.cierre = cierre;
        this.observacion = observacion;
        this.asiento = asiento;
    }

    public String getIdreferencia() {
        return idreferencia;
    }

    public void setIdreferencia(String idreferencia) {
        this.idreferencia = idreferencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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

    public comprobante getTipo() {
        return tipo;
    }

    public void setTipo(comprobante tipo) {
        this.tipo = tipo;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getAsiento() {
        return asiento;
    }

    public void setAsiento(double asiento) {
        this.asiento = asiento;
    }
    

}
