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
public class transferencia_bancaria {

    Double numero;
    String creferencia;
    Date fecha;
    sucursal sucursal;
    int origenfondo;
    String nombrebanco_origen;
    Double importe;
    moneda moneda;
    String nrocheque;
    Double cotizacion;
    int destinofondo;
    String nombrebanco_destino;
    usuario usuarioalta;
    String fechaalta;
    String entregadopor;
    String recibidopor;
    int cierre;
    Double asiento;
    String observacion;

    public transferencia_bancaria(){
        
    }

    public transferencia_bancaria(Double numero, String creferencia, Date fecha, sucursal sucursal, int origenfondo, String nombrebanco_origen, Double importe, moneda moneda, String nrocheque, Double cotizacion, int destinofondo, String nombrebanco_destino, usuario usuarioalta, String fechaalta, String entregadopor, String recibidopor, int cierre, Double asiento, String observacion) {
        this.numero = numero;
        this.creferencia = creferencia;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.origenfondo = origenfondo;
        this.nombrebanco_origen = nombrebanco_origen;
        this.importe = importe;
        this.moneda = moneda;
        this.nrocheque = nrocheque;
        this.cotizacion = cotizacion;
        this.destinofondo = destinofondo;
        this.nombrebanco_destino = nombrebanco_destino;
        this.usuarioalta = usuarioalta;
        this.fechaalta = fechaalta;
        this.entregadopor = entregadopor;
        this.recibidopor = recibidopor;
        this.cierre = cierre;
        this.asiento = asiento;
        this.observacion = observacion;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public String getCreferencia() {
        return creferencia;
    }

    public void setCreferencia(String creferencia) {
        this.creferencia = creferencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getOrigenfondo() {
        return origenfondo;
    }

    public void setOrigenfondo(int origenfondo) {
        this.origenfondo = origenfondo;
    }

    public String getNombrebanco_origen() {
        return nombrebanco_origen;
    }

    public void setNombrebanco_origen(String nombrebanco_origen) {
        this.nombrebanco_origen = nombrebanco_origen;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public Double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public int getDestinofondo() {
        return destinofondo;
    }

    public void setDestinofondo(int destinofondo) {
        this.destinofondo = destinofondo;
    }

    public String getNombrebanco_destino() {
        return nombrebanco_destino;
    }

    public void setNombrebanco_destino(String nombrebanco_destino) {
        this.nombrebanco_destino = nombrebanco_destino;
    }

    public usuario getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(usuario usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public String getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(String fechaalta) {
        this.fechaalta = fechaalta;
    }

    public String getEntregadopor() {
        return entregadopor;
    }

    public void setEntregadopor(String entregadopor) {
        this.entregadopor = entregadopor;
    }

    public String getRecibidopor() {
        return recibidopor;
    }

    public void setRecibidopor(String recibidopor) {
        this.recibidopor = recibidopor;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public Double getAsiento() {
        return asiento;
    }

    public void setAsiento(Double asiento) {
        this.asiento = asiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    
}

