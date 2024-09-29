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
public class extraccion {

    int idcontrol;
    String idmovimiento;
    String documento;
    int proveedor;
    Date fecha;
    banco banco;
    int concepto;
    sucursal sucursal;
    moneda moneda;
    String tipo;
    BigDecimal importe;
    BigDecimal cotizacion;
    String observaciones;
    String chequenro;
    Date vencimiento;
    Date retirado;
    Date cobrado;
    int asiento;
    String idcta;
    double credito;
    double debito;
    int cierre;
    
    public extraccion() {

    }

    public int getIdcontrol() {
        return idcontrol;
    }

    public void setIdcontrol(int idcontrol) {
        this.idcontrol = idcontrol;
    }

    public String getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(String idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public banco getBanco() {
        return banco;
    }

    public void setBanco(banco banco) {
        this.banco = banco;
    }

    public int getConcepto() {
        return concepto;
    }

    public void setConcepto(int concepto) {
        this.concepto = concepto;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getChequenro() {
        return chequenro;
    }

    public void setChequenro(String chequenro) {
        this.chequenro = chequenro;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getRetirado() {
        return retirado;
    }

    public void setRetirado(Date retirado) {
        this.retirado = retirado;
    }

    public Date getCobrado() {
        return cobrado;
    }

    public void setCobrado(Date cobrado) {
        this.cobrado = cobrado;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public String getIdcta() {
        return idcta;
    }

    public void setIdcta(String idcta) {
        this.idcta = idcta;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public extraccion(int idcontrol, String idmovimiento, String documento, int proveedor, Date fecha, banco banco, int concepto, sucursal sucursal, moneda moneda, String tipo, BigDecimal importe, BigDecimal cotizacion, String observaciones, String chequenro, Date vencimiento, Date retirado, Date cobrado, int asiento, String idcta, double credito, double debito, int cierre) {
        this.idcontrol = idcontrol;
        this.idmovimiento = idmovimiento;
        this.documento = documento;
        this.proveedor = proveedor;
        this.fecha = fecha;
        this.banco = banco;
        this.concepto = concepto;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.tipo = tipo;
        this.importe = importe;
        this.cotizacion = cotizacion;
        this.observaciones = observaciones;
        this.chequenro = chequenro;
        this.vencimiento = vencimiento;
        this.retirado = retirado;
        this.cobrado = cobrado;
        this.asiento = asiento;
        this.idcta = idcta;
        this.credito = credito;
        this.debito = debito;
        this.cierre = cierre;
    }

}
