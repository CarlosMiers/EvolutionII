/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author SERVIDOR
 */
public class cuenta_casascomerciales {
    String iddocumento;
    String documento;
    Date fecha;
    Date vencimiento;
    casa comercial;
    comprobante comprobante;
    cliente cliente;
    double importe;
    int numerocuota;
    int cuota;

    public cuenta_casascomerciales(){
        
    }

    public cuenta_casascomerciales(String iddocumento, String documento, Date fecha, Date vencimiento, casa comercial, comprobante comprobante, cliente cliente, double importe, int numerocuota, int cuota) {
        this.iddocumento = iddocumento;
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.comercial = comercial;
        this.comprobante = comprobante;
        this.cliente = cliente;
        this.importe = importe;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public casa getComercial() {
        return comercial;
    }

    public void setComercial(casa comercial) {
        this.comercial = comercial;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(int numerocuota) {
        this.numerocuota = numerocuota;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

   
}
