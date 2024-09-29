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
public class ordencompra {
   String idordencompra;
   int numero;
   Date fecha;
   sucursal sucursal;
   giraduria giraduria;
   casa casas;
   cliente cliente;
   int garante;
   int plazo;
   BigDecimal importe;
   BigDecimal monto_cuota;
   Date primer_vence;
   int asiento;
   int cierre;
   int usuarioalta;
   int usuarionulo;
   Date fechaalta;
   int usuariomod;
   Date fechamod;
   Date fechanulo;
   comprobante tipo;
   String estado;
   String nombregarante;
   BigDecimal saldo;
   int pagado;
   
   
   public ordencompra(){
       
   }

    public ordencompra(String idordencompra, int numero, Date fecha, sucursal sucursal, giraduria giraduria, casa casas, cliente cliente, int garante, int plazo, BigDecimal importe, BigDecimal monto_cuota, Date primer_vence, int asiento, int cierre, int usuarioalta, int usuarionulo, Date fechaalta, int usuariomod, Date fechamod, Date fechanulo, comprobante tipo, String estado, String nombregarante, BigDecimal saldo, int pagado) {
        this.idordencompra = idordencompra;
        this.numero = numero;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.giraduria = giraduria;
        this.casas = casas;
        this.cliente = cliente;
        this.garante = garante;
        this.plazo = plazo;
        this.importe = importe;
        this.monto_cuota = monto_cuota;
        this.primer_vence = primer_vence;
        this.asiento = asiento;
        this.cierre = cierre;
        this.usuarioalta = usuarioalta;
        this.usuarionulo = usuarionulo;
        this.fechaalta = fechaalta;
        this.usuariomod = usuariomod;
        this.fechamod = fechamod;
        this.fechanulo = fechanulo;
        this.tipo = tipo;
        this.estado = estado;
        this.nombregarante = nombregarante;
        this.saldo = saldo;
        this.pagado = pagado;
    }

    public String getIdordencompra() {
        return idordencompra;
    }

    public void setIdordencompra(String idordencompra) {
        this.idordencompra = idordencompra;
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

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public casa getCasas() {
        return casas;
    }

    public void setCasas(casa casas) {
        this.casas = casas;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public int getGarante() {
        return garante;
    }

    public void setGarante(int garante) {
        this.garante = garante;
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

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public int getUsuarioalta() {
        return usuarioalta;
    }

    public void setUsuarioalta(int usuarioalta) {
        this.usuarioalta = usuarioalta;
    }

    public int getUsuarionulo() {
        return usuarionulo;
    }

    public void setUsuarionulo(int usuarionulo) {
        this.usuarionulo = usuarionulo;
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

    public comprobante getTipo() {
        return tipo;
    }

    public void setTipo(comprobante tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombregarante() {
        return nombregarante;
    }

    public void setNombregarante(String nombregarante) {
        this.nombregarante = nombregarante;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public int getPagado() {
        return pagado;
    }

    public void setPagado(int pagado) {
        this.pagado = pagado;
    }

   
}