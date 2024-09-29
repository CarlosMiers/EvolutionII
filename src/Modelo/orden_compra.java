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
public class orden_compra {

    Double numero;
    sucursal sucursal;
    moneda moneda;
    Date fecha;
    Date fechaentrega;
    proveedor proveedor;
    Double totalneto;
    String observaciones;
    int condicion;
    int usuario;
    String estado;
    int cierre;
    int obra;
    
    
    public orden_compra(){
        
    }

    public orden_compra(Double numero, sucursal sucursal, moneda moneda, Date fecha, Date fechaentrega, proveedor proveedor, Double totalneto, String observaciones, int condicion, int usuario, String estado, int cierre, int obra) {
        this.numero = numero;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.fecha = fecha;
        this.fechaentrega = fechaentrega;
        this.proveedor = proveedor;
        this.totalneto = totalneto;
        this.observaciones = observaciones;
        this.condicion = condicion;
        this.usuario = usuario;
        this.estado = estado;
        this.cierre = cierre;
        this.obra = obra;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(Date fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(Double totalneto) {
        this.totalneto = totalneto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getObra() {
        return obra;
    }

    public void setObra(int obra) {
        this.obra = obra;
    }

    

}
