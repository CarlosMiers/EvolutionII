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
public class detalle_cupones_titulos {

    int titulo;
    Date fechainicio;
    int plazo;
    Date fechavencimiento;
    int numerocupon;
    int estadocupon;
    String formatofecha;
    
    
    public detalle_cupones_titulos() {

    }

    public detalle_cupones_titulos(int titulo, Date fechainicio, int plazo, Date fechavencimiento, int numerocupon, int estadocupon, String formatofecha) {
        this.titulo = titulo;
        this.fechainicio = fechainicio;
        this.plazo = plazo;
        this.fechavencimiento = fechavencimiento;
        this.numerocupon = numerocupon;
        this.estadocupon = estadocupon;
        this.formatofecha = formatofecha;
    }

    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public int getNumerocupon() {
        return numerocupon;
    }

    public void setNumerocupon(int numerocupon) {
        this.numerocupon = numerocupon;
    }

    public int getEstadocupon() {
        return estadocupon;
    }

    public void setEstadocupon(int estadocupon) {
        this.estadocupon = estadocupon;
    }

    
    public String getFormatofecha() {
        return formatofecha;
    }
    
    public void setFormatofecha(String formatofecha) {
        this.formatofecha = formatofecha;
    }

}
