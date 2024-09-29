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
public class servicio {

    Double numero;
    Date fecha;
    caja cajero;
    producto servicio;
    String concepto;
    String destino;
    Double importe;
    Double redondeo;
    Double comision;
    Double total;
    int tipo;

    public servicio(){
        
    }

    public servicio(Double numero, Date fecha, caja cajero, producto servicio, String concepto, String destino, Double importe, Double redondeo, Double comision, Double total, int tipo) {
        this.numero = numero;
        this.fecha = fecha;
        this.cajero = cajero;
        this.servicio = servicio;
        this.concepto = concepto;
        this.destino = destino;
        this.importe = importe;
        this.redondeo = redondeo;
        this.comision = comision;
        this.total = total;
        this.tipo = tipo;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public caja getCajero() {
        return cajero;
    }

    public void setCajero(caja cajero) {
        this.cajero = cajero;
    }

    public producto getServicio() {
        return servicio;
    }

    public void setServicio(producto servicio) {
        this.servicio = servicio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getRedondeo() {
        return redondeo;
    }

    public void setRedondeo(Double redondeo) {
        this.redondeo = redondeo;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    
}
