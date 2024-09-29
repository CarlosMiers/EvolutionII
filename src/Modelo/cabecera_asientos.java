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
public class cabecera_asientos {

    double numero;
    double rownum;
    double registro;
    sucursal sucursal;
    plan plan;
    Date fecha;
    int periodo;
    double debe;
    double haber;
    double saldo;
    String grabado;
    String fondofijoreferencia;
    double numinicio;
    double numfinal;
    Date fechainicio;
    Date fechafinal;
    String asi_codigo;
    String nombrecuenta;
    String asi_numero;
    double impdebe;
    double imphaber;
    String asi_descri;
    double saldoanterior;

    public cabecera_asientos() {

    }

    public cabecera_asientos(double numero, double rownum, double registro, sucursal sucursal, plan plan, Date fecha, int periodo, double debe, double haber, double saldo, String grabado, String fondofijoreferencia, double numinicio, double numfinal, Date fechainicio, Date fechafinal, String asi_codigo, String nombrecuenta, String asi_numero, double impdebe, double imphaber, String asi_descri, double saldoanterior) {
        this.numero = numero;
        this.rownum = rownum;
        this.registro = registro;
        this.sucursal = sucursal;
        this.plan = plan;
        this.fecha = fecha;
        this.periodo = periodo;
        this.debe = debe;
        this.haber = haber;
        this.saldo = saldo;
        this.grabado = grabado;
        this.fondofijoreferencia = fondofijoreferencia;
        this.numinicio = numinicio;
        this.numfinal = numfinal;
        this.fechainicio = fechainicio;
        this.fechafinal = fechafinal;
        this.asi_codigo = asi_codigo;
        this.nombrecuenta = nombrecuenta;
        this.asi_numero = asi_numero;
        this.impdebe = impdebe;
        this.imphaber = imphaber;
        this.asi_descri = asi_descri;
        this.saldoanterior = saldoanterior;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public double getRownum() {
        return rownum;
    }

    public void setRownum(double rownum) {
        this.rownum = rownum;
    }

    public double getRegistro() {
        return registro;
    }

    public void setRegistro(double registro) {
        this.registro = registro;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public plan getPlan() {
        return plan;
    }

    public void setPlan(plan plan) {
        this.plan = plan;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getGrabado() {
        return grabado;
    }

    public void setGrabado(String grabado) {
        this.grabado = grabado;
    }

    public String getFondofijoreferencia() {
        return fondofijoreferencia;
    }

    public void setFondofijoreferencia(String fondofijoreferencia) {
        this.fondofijoreferencia = fondofijoreferencia;
    }

    public double getNuminicio() {
        return numinicio;
    }

    public void setNuminicio(double numinicio) {
        this.numinicio = numinicio;
    }

    public double getNumfinal() {
        return numfinal;
    }

    public void setNumfinal(double numfinal) {
        this.numfinal = numfinal;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getAsi_codigo() {
        return asi_codigo;
    }

    public void setAsi_codigo(String asi_codigo) {
        this.asi_codigo = asi_codigo;
    }

    public String getNombrecuenta() {
        return nombrecuenta;
    }

    public void setNombrecuenta(String nombrecuenta) {
        this.nombrecuenta = nombrecuenta;
    }

    public String getAsi_numero() {
        return asi_numero;
    }

    public void setAsi_numero(String asi_numero) {
        this.asi_numero = asi_numero;
    }

    public double getImpdebe() {
        return impdebe;
    }

    public void setImpdebe(double impdebe) {
        this.impdebe = impdebe;
    }

    public double getImphaber() {
        return imphaber;
    }

    public void setImphaber(double imphaber) {
        this.imphaber = imphaber;
    }

    public String getAsi_descri() {
        return asi_descri;
    }

    public void setAsi_descri(String asi_descri) {
        this.asi_descri = asi_descri;
    }

    public double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

}
