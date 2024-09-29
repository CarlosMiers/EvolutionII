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
 * @author Usuario
 */
public class contrato {

    String idordencompra;
    sucursal sucursal;
    int numero;
    giraduria giraduria;
    Date fecha;
    casa comercio;
    int moneda;
    cliente socio;
    int garante;
    comprobante tipo;
    int plazo;
    BigDecimal importe;
    BigDecimal monto_cuota;
    Date primer_vence;
    int cierre;
    int asiento;
    int usuarionulo;
    int usuarioalta;
    Date fechaalta;
    int usuariomod;
    Date fechamod;
    Date fechanulo;
    String observacion;

    public contrato() {
    }

    public contrato(String idordencompra, sucursal sucursal, int numero, giraduria giraduria, Date fecha, casa comercio, int moneda, cliente socio, int garante, comprobante tipo, int plazo, BigDecimal importe, BigDecimal monto_cuota, Date primer_vence, int cierre, int asiento, int usuarionulo, int usuarioalta, Date fechaalta, int usuariomod, Date fechamod, Date fechanulo, String observacion) {
        this.idordencompra = idordencompra;
        this.sucursal = sucursal;
        this.numero = numero;
        this.giraduria = giraduria;
        this.fecha = fecha;
        this.comercio = comercio;
        this.moneda = moneda;
        this.socio = socio;
        this.garante = garante;
        this.tipo = tipo;
        this.plazo = plazo;
        this.importe = importe;
        this.monto_cuota = monto_cuota;
        this.primer_vence = primer_vence;
        this.cierre = cierre;
        this.asiento = asiento;
        this.usuarionulo = usuarionulo;
        this.usuarioalta = usuarioalta;
        this.fechaalta = fechaalta;
        this.usuariomod = usuariomod;
        this.fechamod = fechamod;
        this.fechanulo = fechanulo;
        this.observacion = observacion;
    }

    public String getIdordencompra() {
        return idordencompra;
    }

    public void setIdordencompra(String idordencompra) {
        this.idordencompra = idordencompra;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public casa getComercio() {
        return comercio;
    }

    public void setComercio(casa comercio) {
        this.comercio = comercio;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public cliente getSocio() {
        return socio;
    }

    public void setSocio(cliente socio) {
        this.socio = socio;
    }

    public int getGarante() {
        return garante;
    }

    public void setGarante(int garante) {
        this.garante = garante;
    }

    public comprobante getTipo() {
        return tipo;
    }

    public void setTipo(comprobante tipo) {
        this.tipo = tipo;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getMonto_cuota() {
        return monto_cuota;
    }

    public void setMonto_cuota(BigDecimal monto_cuota) {
        this.monto_cuota = monto_cuota;
    }

    public Date getPrimer_vence() {
        return primer_vence;
    }

    public void setPrimer_vence(Date primer_vence) {
        this.primer_vence = primer_vence;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getUsuarionulo() {
        return usuarionulo;
    }

    public void setUsuarionulo(int usuarionulo) {
        this.usuarionulo = usuarionulo;
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

    public int getUsuariomod() {
        return usuariomod;
    }

    public void setUsuariomod(int usuariomod) {
        this.usuariomod = usuariomod;
    }

    public Date getFechamod() {
        return fechamod;
    }

    public void setFechamod(Date fechamod) {
        this.fechamod = fechamod;
    }

    public Date getFechanulo() {
        return fechanulo;
    }

    public void setFechanulo(Date fechanulo) {
        this.fechanulo = fechanulo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
