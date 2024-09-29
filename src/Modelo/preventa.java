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
public class preventa {

    double numero;
    Date fecha;
    Date vencimiento;
    cliente cliente;
    sucursal sucursal;
    int moneda;
    comprobante comprobante;
    double cotizacion;
    vendedor vendedor;
    int caja;
    double totalneto;
    int cierre;
    String destinatario;
    String direccion_entrega;
    String telefono;
    String observacion;
    String mensaje;
    String referencia_lugar;
    int tipo;
    String firma;
    String horaentrega;
    int cuotas;
    String nombreobra;
    String nombrecaja;
    double totaldescuento;

    public preventa() {

    }

    public preventa(double numero, Date fecha, Date vencimiento, cliente cliente, sucursal sucursal, int moneda, comprobante comprobante, double cotizacion, vendedor vendedor, int caja, double totalneto, int cierre, String destinatario, String direccion_entrega, String telefono, String observacion, String mensaje, String referencia_lugar, int tipo, String firma, String horaentrega, int cuotas, String nombreobra, String nombrecaja, double totaldescuento) {
        this.numero = numero;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.moneda = moneda;
        this.comprobante = comprobante;
        this.cotizacion = cotizacion;
        this.vendedor = vendedor;
        this.caja = caja;
        this.totalneto = totalneto;
        this.cierre = cierre;
        this.destinatario = destinatario;
        this.direccion_entrega = direccion_entrega;
        this.telefono = telefono;
        this.observacion = observacion;
        this.mensaje = mensaje;
        this.referencia_lugar = referencia_lugar;
        this.tipo = tipo;
        this.firma = firma;
        this.horaentrega = horaentrega;
        this.cuotas = cuotas;
        this.nombreobra = nombreobra;
        this.nombrecaja = nombrecaja;
        this.totaldescuento = totaldescuento;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
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

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(double totalneto) {
        this.totalneto = totalneto;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDireccion_entrega() {
        return direccion_entrega;
    }

    public void setDireccion_entrega(String direccion_entrega) {
        this.direccion_entrega = direccion_entrega;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getReferencia_lugar() {
        return referencia_lugar;
    }

    public void setReferencia_lugar(String referencia_lugar) {
        this.referencia_lugar = referencia_lugar;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getHoraentrega() {
        return horaentrega;
    }

    public void setHoraentrega(String horaentrega) {
        this.horaentrega = horaentrega;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public String getNombreobra() {
        return nombreobra;
    }

    public void setNombreobra(String nombreobra) {
        this.nombreobra = nombreobra;
    }

    public String getNombrecaja() {
        return nombrecaja;
    }

    public void setNombrecaja(String nombrecaja) {
        this.nombrecaja = nombrecaja;
    }

    public double getTotaldescuento() {
        return totaldescuento;
    }

    public void setTotaldescuento(double totaldescuento) {
        this.totaldescuento = totaldescuento;
    }


}
