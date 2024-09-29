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
public class novedades {

double numero;
Date fecha;
cliente cliente;
cobrador gestor;
Date proxima_llamada;
String estado;
String observacion;
String idcuenta;
double numeroanterior;
String accionrealizada;
String situacion;
String proxima_accion;

public novedades(){
    
    
}

    public novedades(double numero, Date fecha, cliente cliente, cobrador gestor, Date proxima_llamada, String estado, String observacion, String idcuenta, double numeroanterior, String accionrealizada, String situacion, String proxima_accion) {
        this.numero = numero;
        this.fecha = fecha;
        this.cliente = cliente;
        this.gestor = gestor;
        this.proxima_llamada = proxima_llamada;
        this.estado = estado;
        this.observacion = observacion;
        this.idcuenta = idcuenta;
        this.numeroanterior = numeroanterior;
        this.accionrealizada = accionrealizada;
        this.situacion = situacion;
        this.proxima_accion = proxima_accion;
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

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public cobrador getGestor() {
        return gestor;
    }

    public void setGestor(cobrador gestor) {
        this.gestor = gestor;
    }

    public Date getProxima_llamada() {
        return proxima_llamada;
    }

    public void setProxima_llamada(Date proxima_llamada) {
        this.proxima_llamada = proxima_llamada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(String idcuenta) {
        this.idcuenta = idcuenta;
    }

    public double getNumeroanterior() {
        return numeroanterior;
    }

    public void setNumeroanterior(double numeroanterior) {
        this.numeroanterior = numeroanterior;
    }

    public String getAccionrealizada() {
        return accionrealizada;
    }

    public void setAccionrealizada(String accionrealizada) {
        this.accionrealizada = accionrealizada;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getProxima_accion() {
        return proxima_accion;
    }

    public void setProxima_accion(String proxima_accion) {
        this.proxima_accion = proxima_accion;
    }


}
