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
public class RptLibroVentaVendedor {

    Double iva5;
    Double iva10;
    String factura;
    String fecha;
    String nombrecliente;
    String ruc;
    Double gravada5;
    Double gravada10;
    Double exenta;
    Double total;
    String nombrecomprobante;
    int comprobante;
    int timbrado;
    int vendedor;
    String nombrevendedor;

    //String totalneto;
    public void RptLibroVentaConsolidado() {

    }

    public RptLibroVentaVendedor(Double iva5, Double iva10, String factura, String fecha, String nombrecliente, String ruc, Double gravada5, Double gravada10, Double exenta, Double total, String nombrecomprobante, int comprobante, int timbrado, int vendedor, String nombrevendedor) {
        this.iva5 = iva5;
        this.iva10 = iva10;
        this.factura = factura;
        this.fecha = fecha;
        this.nombrecliente = nombrecliente;
        this.ruc = ruc;
        this.gravada5 = gravada5;
        this.gravada10 = gravada10;
        this.exenta = exenta;
        this.total = total;
        this.nombrecomprobante = nombrecomprobante;
        this.comprobante = comprobante;
        this.timbrado = timbrado;
        this.vendedor = vendedor;
        this.nombrevendedor = nombrevendedor;
    }

    public Double getIva5() {
        return iva5;
    }

    public void setIva5(Double iva5) {
        this.iva5 = iva5;
    }

    public Double getIva10() {
        return iva10;
    }

    public void setIva10(Double iva10) {
        this.iva10 = iva10;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Double getGravada5() {
        return gravada5;
    }

    public void setGravada5(Double gravada5) {
        this.gravada5 = gravada5;
    }

    public Double getGravada10() {
        return gravada10;
    }

    public void setGravada10(Double gravada10) {
        this.gravada10 = gravada10;
    }

    public Double getExenta() {
        return exenta;
    }

    public void setExenta(Double exenta) {
        this.exenta = exenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
    }

    public int getComprobante() {
        return comprobante;
    }

    public void setComprobante(int comprobante) {
        this.comprobante = comprobante;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public String getNombrevendedor() {
        return nombrevendedor;
    }

    public void setNombrevendedor(String nombrevendedor) {
        this.nombrevendedor = nombrevendedor;
    }



}
