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
public class orden_trabajo {

    double numero;
    sucursal sucursal;
    Date fechaemision;
    Date fechainicio;
    Date fechaentregaprevista;
    Date fechaentrega;
    ficha_empleado solicitadopor;
    seccion seccion;
    String galpon;
    String tipo;
    String aprobado;
    ficha_empleado aprobadopor;
    int alerta;
    String trabajoarealizar;
    usuario codusuario;
    String alertarme;
    String estado;
    double totalpresupuesto;

    public orden_trabajo() {

    }

    public orden_trabajo(double numero, sucursal sucursal, Date fechaemision, Date fechainicio, Date fechaentregaprevista, Date fechaentrega, ficha_empleado solicitadopor, seccion seccion, String galpon, String tipo, String aprobado, ficha_empleado aprobadopor, int alerta, String trabajoarealizar, usuario codusuario, String alertarme, String estado, double totalpresupuesto) {
        this.numero = numero;
        this.sucursal = sucursal;
        this.fechaemision = fechaemision;
        this.fechainicio = fechainicio;
        this.fechaentregaprevista = fechaentregaprevista;
        this.fechaentrega = fechaentrega;
        this.solicitadopor = solicitadopor;
        this.seccion = seccion;
        this.galpon = galpon;
        this.tipo = tipo;
        this.aprobado = aprobado;
        this.aprobadopor = aprobadopor;
        this.alerta = alerta;
        this.trabajoarealizar = trabajoarealizar;
        this.codusuario = codusuario;
        this.alertarme = alertarme;
        this.estado = estado;
        this.totalpresupuesto = totalpresupuesto;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechaentregaprevista() {
        return fechaentregaprevista;
    }

    public void setFechaentregaprevista(Date fechaentregaprevista) {
        this.fechaentregaprevista = fechaentregaprevista;
    }

    public Date getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(Date fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public ficha_empleado getSolicitadopor() {
        return solicitadopor;
    }

    public void setSolicitadopor(ficha_empleado solicitadopor) {
        this.solicitadopor = solicitadopor;
    }

    public seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(seccion seccion) {
        this.seccion = seccion;
    }

    public String getGalpon() {
        return galpon;
    }

    public void setGalpon(String galpon) {
        this.galpon = galpon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }

    public ficha_empleado getAprobadopor() {
        return aprobadopor;
    }

    public void setAprobadopor(ficha_empleado aprobadopor) {
        this.aprobadopor = aprobadopor;
    }

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
    }

    public String getTrabajoarealizar() {
        return trabajoarealizar;
    }

    public void setTrabajoarealizar(String trabajoarealizar) {
        this.trabajoarealizar = trabajoarealizar;
    }

    public usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(usuario codusuario) {
        this.codusuario = codusuario;
    }

    public String getAlertarme() {
        return alertarme;
    }

    public void setAlertarme(String alertarme) {
        this.alertarme = alertarme;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalpresupuesto() {
        return totalpresupuesto;
    }

    public void setTotalpresupuesto(double totalpresupuesto) {
        this.totalpresupuesto = totalpresupuesto;
    }

}
