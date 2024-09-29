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
public class consultas_servicios {

    int numero;
    Date fecha;
    cliente cliente;
    int periodopago;
    Date vencimiento;
    double totales;
    int usuarioalta;
    String tipovencimiento;

    public consultas_servicios() {

    }

    public consultas_servicios(int numero, Date fecha, cliente cliente, int periodopago, Date vencimiento, double totales, int usuarioalta, String tipovencimiento) {
        this.numero = numero;
        this.fecha = fecha;
        this.cliente = cliente;
        this.periodopago = periodopago;
        this.vencimiento = vencimiento;
        this.totales = totales;
        this.usuarioalta = usuarioalta;
        this.tipovencimiento = tipovencimiento;
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

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public int getPeriodopago() {
        return periodopago;
    }

    public void setPeriodopago(int periodopago) {
        this.periodopago = periodopago;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public double getTotales() {
        return totales;
    }

    public void setTotales(double totales) {
        this.totales = totales;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public String getTipovencimiento() {
        return tipovencimiento;
    }

    public void setTipovencimiento(String tipovencimiento) {
        this.tipovencimiento = tipovencimiento;
    }


}
