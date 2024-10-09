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
 * @author notebook
 */
public class ordenes_operaciones {

    double numero;
    Date fecha;
    Date validohasta;
    Date fechacierre;
    Date fechaemision;
    Date vencimiento;
    cliente cliente;
    vendedor  asesor;
    int operacion;
    int tipooperacion;
    int mercado;
    moneda moneda;
    emisor emisor;
    titulo titulo;
    acciones tipoaccion;
    BigDecimal valor_nominal;
    BigDecimal cantidad;
    BigDecimal precio;
    BigDecimal valor_inversion;
    BigDecimal tasa;
    int plazo;
    int tipoplazo;
    String observacion;
    int base;
    int periodopago;
    int cierre;
    int usuarioalta;
    String nombreusuarioalta;
    int usuarioupdate;
    String nombreusuarioupdate;
    int cupones;

    public ordenes_operaciones() {

    }

    public ordenes_operaciones(double numero, Date fecha, Date validohasta, Date fechacierre, Date fechaemision, Date vencimiento, cliente cliente, vendedor asesor, int operacion, int tipooperacion, int mercado, moneda moneda, emisor emisor, titulo titulo, acciones tipoaccion, BigDecimal valor_nominal, BigDecimal cantidad, BigDecimal precio, BigDecimal valor_inversion, BigDecimal tasa, int plazo, int tipoplazo, String observacion, int base, int periodopago, int cierre, int usuarioalta, String nombreusuarioalta, int usuarioupdate, String nombreusuarioupdate, int cupones) {
        this.numero = numero;
        this.fecha = fecha;
        this.validohasta = validohasta;
        this.fechacierre = fechacierre;
        this.fechaemision = fechaemision;
        this.vencimiento = vencimiento;
        this.cliente = cliente;
        this.asesor = asesor;
        this.operacion = operacion;
        this.tipooperacion = tipooperacion;
        this.mercado = mercado;
        this.moneda = moneda;
        this.emisor = emisor;
        this.titulo = titulo;
        this.tipoaccion = tipoaccion;
        this.valor_nominal = valor_nominal;
        this.cantidad = cantidad;
        this.precio = precio;
        this.valor_inversion = valor_inversion;
        this.tasa = tasa;
        this.plazo = plazo;
        this.tipoplazo = tipoplazo;
        this.observacion = observacion;
        this.base = base;
        this.periodopago = periodopago;
        this.cierre = cierre;
        this.usuarioalta = usuarioalta;
        this.nombreusuarioalta = nombreusuarioalta;
        this.usuarioupdate = usuarioupdate;
        this.nombreusuarioupdate = nombreusuarioupdate;
        this.cupones = cupones;
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

    public Date getValidohasta() {
        return validohasta;
    }

    public void setValidohasta(Date validohasta) {
        this.validohasta = validohasta;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
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

    public vendedor getAsesor() {
        return asesor;
    }

    public void setAsesor(vendedor asesor) {
        this.asesor = asesor;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public int getTipooperacion() {
        return tipooperacion;
    }

    public void setTipooperacion(int tipooperacion) {
        this.tipooperacion = tipooperacion;
    }

    public int getMercado() {
        return mercado;
    }

    public void setMercado(int mercado) {
        this.mercado = mercado;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(emisor emisor) {
        this.emisor = emisor;
    }

    public titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(titulo titulo) {
        this.titulo = titulo;
    }

    public acciones getTipoaccion() {
        return tipoaccion;
    }

    public void setTipoaccion(acciones tipoaccion) {
        this.tipoaccion = tipoaccion;
    }

    public BigDecimal getValor_nominal() {
        return valor_nominal;
    }

    public void setValor_nominal(BigDecimal valor_nominal) {
        this.valor_nominal = valor_nominal;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getValor_inversion() {
        return valor_inversion;
    }

    public void setValor_inversion(BigDecimal valor_inversion) {
        this.valor_inversion = valor_inversion;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public int getTipoplazo() {
        return tipoplazo;
    }

    public void setTipoplazo(int tipoplazo) {
        this.tipoplazo = tipoplazo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getPeriodopago() {
        return periodopago;
    }

    public void setPeriodopago(int periodopago) {
        this.periodopago = periodopago;
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

    public String getNombreusuarioalta() {
        return nombreusuarioalta;
    }

    public void setNombreusuarioalta(String nombreusuarioalta) {
        this.nombreusuarioalta = nombreusuarioalta;
    }

    public int getUsuarioupdate() {
        return usuarioupdate;
    }

    public void setUsuarioupdate(int usuarioupdate) {
        this.usuarioupdate = usuarioupdate;
    }

    public String getNombreusuarioupdate() {
        return nombreusuarioupdate;
    }

    public void setNombreusuarioupdate(String nombreusuarioupdate) {
        this.nombreusuarioupdate = nombreusuarioupdate;
    }

    public int getCupones() {
        return cupones;
    }

    public void setCupones(int cupones) {
        this.cupones = cupones;
    }

    

}
