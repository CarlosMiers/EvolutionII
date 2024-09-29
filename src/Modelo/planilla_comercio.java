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
public class planilla_comercio {
    
    
double  numero;
sucursal sucursal;
moneda moneda;
Date fecha;
casa casa;
comprobante servicio;
Date vencimiento;
double totales;
int  asiento;
double comision;
double monto_comision;
double iva_comision;
double monto_iva;
double totalcomision;
double saldo_a_pagar;
int cierre;
double pago;
banco banco;
String nrocheque;
Date fechapago; 
int pagadopor;
int usuarionulo;
Date fechanulo;
int usuarioalta;
Date fechaalta;
int usuariomodi;
Date fechamodi;
String creferencia;

public planilla_comercio(){
    
}

    public planilla_comercio(double numero, sucursal sucursal, moneda moneda, Date fecha, casa casa, comprobante servicio, Date vencimiento, double totales, int asiento, double comision, double monto_comision, double iva_comision, double monto_iva, double totalcomision, double saldo_a_pagar, int cierre, double pago, banco banco, String nrocheque, Date fechapago, int pagadopor, int usuarionulo, Date fechanulo, int usuarioalta, Date fechaalta, int usuariomodi, Date fechamodi, String creferencia) {
        this.numero = numero;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.fecha = fecha;
        this.casa = casa;
        this.servicio = servicio;
        this.vencimiento = vencimiento;
        this.totales = totales;
        this.asiento = asiento;
        this.comision = comision;
        this.monto_comision = monto_comision;
        this.iva_comision = iva_comision;
        this.monto_iva = monto_iva;
        this.totalcomision = totalcomision;
        this.saldo_a_pagar = saldo_a_pagar;
        this.cierre = cierre;
        this.pago = pago;
        this.banco = banco;
        this.nrocheque = nrocheque;
        this.fechapago = fechapago;
        this.pagadopor = pagadopor;
        this.usuarionulo = usuarionulo;
        this.fechanulo = fechanulo;
        this.usuarioalta = usuarioalta;
        this.fechaalta = fechaalta;
        this.usuariomodi = usuariomodi;
        this.fechamodi = fechamodi;
        this.creferencia = creferencia;
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

    public casa getCasa() {
        return casa;
    }

    public void setCasa(casa casa) {
        this.casa = casa;
    }

    public comprobante getServicio() {
        return servicio;
    }

    public void setServicio(comprobante servicio) {
        this.servicio = servicio;
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

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getMonto_comision() {
        return monto_comision;
    }

    public void setMonto_comision(double monto_comision) {
        this.monto_comision = monto_comision;
    }

    public double getIva_comision() {
        return iva_comision;
    }

    public void setIva_comision(double iva_comision) {
        this.iva_comision = iva_comision;
    }

    public double getMonto_iva() {
        return monto_iva;
    }

    public void setMonto_iva(double monto_iva) {
        this.monto_iva = monto_iva;
    }

    public double getTotalcomision() {
        return totalcomision;
    }

    public void setTotalcomision(double totalcomision) {
        this.totalcomision = totalcomision;
    }

    public double getSaldo_a_pagar() {
        return saldo_a_pagar;
    }

    public void setSaldo_a_pagar(double saldo_a_pagar) {
        this.saldo_a_pagar = saldo_a_pagar;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public banco getBanco() {
        return banco;
    }

    public void setBanco(banco banco) {
        this.banco = banco;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public int getPagadopor() {
        return pagadopor;
    }

    public void setPagadopor(int pagadopor) {
        this.pagadopor = pagadopor;
    }

    public int getUsuarionulo() {
        return usuarionulo;
    }

    public void setUsuarionulo(int usuarionulo) {
        this.usuarionulo = usuarionulo;
    }

    public Date getFechanulo() {
        return fechanulo;
    }

    public void setFechanulo(Date fechanulo) {
        this.fechanulo = fechanulo;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public Date getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(Date fechaalta) {
        this.fechaalta = fechaalta;
    }

    public int getUsuariomodi() {
        return usuariomodi;
    }

    public void setUsuariomodi(int usuariomodi) {
        this.usuariomodi = usuariomodi;
    }

    public Date getFechamodi() {
        return fechamodi;
    }

    public void setFechamodi(Date fechamodi) {
        this.fechamodi = fechamodi;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }
    
    

}
