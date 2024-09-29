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
public class centro_costo_reporte {

    String formatofactura;
    Date fecha;
    double monto;
    int idcentro;
    int tipo;
    String nombrecentro;
    String nombreproveedor;
    String nombremoneda;
    int moneda;

    public centro_costo_reporte() {

    }

    public centro_costo_reporte(String formatofactura, Date fecha, double monto, int idcentro, int tipo, String nombrecentro, String nombreproveedor, String nombremoneda, int moneda) {
        this.formatofactura = formatofactura;
        this.fecha = fecha;
        this.monto = monto;
        this.idcentro = idcentro;
        this.tipo = tipo;
        this.nombrecentro = nombrecentro;
        this.nombreproveedor = nombreproveedor;
        this.nombremoneda = nombremoneda;
        this.moneda = moneda;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(int idcentro) {
        this.idcentro = idcentro;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombrecentro() {
        return nombrecentro;
    }

    public void setNombrecentro(String nombrecentro) {
        this.nombrecentro = nombrecentro;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getNombremoneda() {
        return nombremoneda;
    }

    public void setNombremoneda(String nombremoneda) {
        this.nombremoneda = nombremoneda;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

}
