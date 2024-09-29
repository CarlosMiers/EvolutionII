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
 * @author ADMIN
 */
public class rescision {

    String idpagos;
    BigDecimal numero;
    sucursal sucursal;
    cobrador cobrador;
    Date fecha;
    cliente cliente;
    moneda moneda;
    BigDecimal cotizacionmoneda;
    BigDecimal valores;
    BigDecimal totalpago;
    String observacion;
    BigDecimal asiento;
    int cierre;
    BigDecimal sucambio;
    BigDecimal descuentos;
    BigDecimal totalcobrado;
    BigDecimal nrofactura;
    int cobrar_visita;
    usuario codusuario;
    int enviocobrador;
    int turno;
    caja caja;
    String estado;
    BigDecimal pago;
    BigDecimal amortiza;
    BigDecimal minteres;
    BigDecimal gastos_cobranzas;
    BigDecimal mora;
    BigDecimal punitorio;
    int numerocuota;
    int cuota;
    int diamora;
    String nombrecomprobante;
    BigDecimal aporte;
    int tipo;
    detalle_cobranza detalle_cobranza;
    String formatofactura;
    edificio edificio;
    propietario propietario;
    inmueble inmueble;
    Double alquiler;
    Double garage;
    Double expensa;
    Double comision;
    Double multa;
    detalle_forma_cobro detalle_forma_cobro;
   

    public rescision() {

    }

    public rescision(String idpagos, BigDecimal numero, sucursal sucursal, cobrador cobrador, Date fecha, cliente cliente, moneda moneda, BigDecimal cotizacionmoneda, BigDecimal valores, BigDecimal totalpago, String observacion, BigDecimal asiento, int cierre, BigDecimal sucambio, BigDecimal descuentos, BigDecimal totalcobrado, BigDecimal nrofactura, int cobrar_visita, usuario codusuario, int enviocobrador, int turno, caja caja, String estado, BigDecimal pago, BigDecimal amortiza, BigDecimal minteres, BigDecimal gastos_cobranzas, BigDecimal mora, BigDecimal punitorio, int numerocuota, int cuota, int diamora, String nombrecomprobante, BigDecimal aporte, int tipo, detalle_cobranza detalle_cobranza, String formatofactura, edificio edificio, propietario propietario, inmueble inmueble, Double alquiler, Double garage, Double expensa, Double comision, Double multa, detalle_forma_cobro detalle_forma_cobro) {
        this.idpagos = idpagos;
        this.numero = numero;
        this.sucursal = sucursal;
        this.cobrador = cobrador;
        this.fecha = fecha;
        this.cliente = cliente;
        this.moneda = moneda;
        this.cotizacionmoneda = cotizacionmoneda;
        this.valores = valores;
        this.totalpago = totalpago;
        this.observacion = observacion;
        this.asiento = asiento;
        this.cierre = cierre;
        this.sucambio = sucambio;
        this.descuentos = descuentos;
        this.totalcobrado = totalcobrado;
        this.nrofactura = nrofactura;
        this.cobrar_visita = cobrar_visita;
        this.codusuario = codusuario;
        this.enviocobrador = enviocobrador;
        this.turno = turno;
        this.caja = caja;
        this.estado = estado;
        this.pago = pago;
        this.amortiza = amortiza;
        this.minteres = minteres;
        this.gastos_cobranzas = gastos_cobranzas;
        this.mora = mora;
        this.punitorio = punitorio;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.diamora = diamora;
        this.nombrecomprobante = nombrecomprobante;
        this.aporte = aporte;
        this.tipo = tipo;
        this.detalle_cobranza = detalle_cobranza;
        this.formatofactura = formatofactura;
        this.edificio = edificio;
        this.propietario = propietario;
        this.inmueble = inmueble;
        this.alquiler = alquiler;
        this.garage = garage;
        this.expensa = expensa;
        this.comision = comision;
        this.multa = multa;
        this.detalle_forma_cobro = detalle_forma_cobro;
    }

    public String getIdpagos() {
        return idpagos;
    }

    public void setIdpagos(String idpagos) {
        this.idpagos = idpagos;
    }

    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public cobrador getCobrador() {
        return cobrador;
    }

    public void setCobrador(cobrador cobrador) {
        this.cobrador = cobrador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getCotizacionmoneda() {
        return cotizacionmoneda;
    }

    public void setCotizacionmoneda(BigDecimal cotizacionmoneda) {
        this.cotizacionmoneda = cotizacionmoneda;
    }

    public BigDecimal getValores() {
        return valores;
    }

    public void setValores(BigDecimal valores) {
        this.valores = valores;
    }

    public BigDecimal getTotalpago() {
        return totalpago;
    }

    public void setTotalpago(BigDecimal totalpago) {
        this.totalpago = totalpago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getAsiento() {
        return asiento;
    }

    public void setAsiento(BigDecimal asiento) {
        this.asiento = asiento;
    }

    public int getCierre() {
        return cierre;
    }

    public void setCierre(int cierre) {
        this.cierre = cierre;
    }

    public BigDecimal getSucambio() {
        return sucambio;
    }

    public void setSucambio(BigDecimal sucambio) {
        this.sucambio = sucambio;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getTotalcobrado() {
        return totalcobrado;
    }

    public void setTotalcobrado(BigDecimal totalcobrado) {
        this.totalcobrado = totalcobrado;
    }

    public BigDecimal getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(BigDecimal nrofactura) {
        this.nrofactura = nrofactura;
    }

    public int getCobrar_visita() {
        return cobrar_visita;
    }

    public void setCobrar_visita(int cobrar_visita) {
        this.cobrar_visita = cobrar_visita;
    }

    public usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(usuario codusuario) {
        this.codusuario = codusuario;
    }

    public int getEnviocobrador() {
        return enviocobrador;
    }

    public void setEnviocobrador(int enviocobrador) {
        this.enviocobrador = enviocobrador;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public caja getCaja() {
        return caja;
    }

    public void setCaja(caja caja) {
        this.caja = caja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getPago() {
        return pago;
    }

    public void setPago(BigDecimal pago) {
        this.pago = pago;
    }

    public BigDecimal getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(BigDecimal amortiza) {
        this.amortiza = amortiza;
    }

    public BigDecimal getMinteres() {
        return minteres;
    }

    public void setMinteres(BigDecimal minteres) {
        this.minteres = minteres;
    }

    public BigDecimal getGastos_cobranzas() {
        return gastos_cobranzas;
    }

    public void setGastos_cobranzas(BigDecimal gastos_cobranzas) {
        this.gastos_cobranzas = gastos_cobranzas;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public BigDecimal getPunitorio() {
        return punitorio;
    }

    public void setPunitorio(BigDecimal punitorio) {
        this.punitorio = punitorio;
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

    public int getDiamora() {
        return diamora;
    }

    public void setDiamora(int diamora) {
        this.diamora = diamora;
    }

    public String getNombrecomprobante() {
        return nombrecomprobante;
    }

    public void setNombrecomprobante(String nombrecomprobante) {
        this.nombrecomprobante = nombrecomprobante;
    }

    public BigDecimal getAporte() {
        return aporte;
    }

    public void setAporte(BigDecimal aporte) {
        this.aporte = aporte;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public detalle_cobranza getDetalle_cobranza() {
        return detalle_cobranza;
    }

    public void setDetalle_cobranza(detalle_cobranza detalle_cobranza) {
        this.detalle_cobranza = detalle_cobranza;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(edificio edificio) {
        this.edificio = edificio;
    }

    public propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(propietario propietario) {
        this.propietario = propietario;
    }

    public inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Double alquiler) {
        this.alquiler = alquiler;
    }

    public Double getGarage() {
        return garage;
    }

    public void setGarage(Double garage) {
        this.garage = garage;
    }

    public Double getExpensa() {
        return expensa;
    }

    public void setExpensa(Double expensa) {
        this.expensa = expensa;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public detalle_forma_cobro getDetalle_forma_cobro() {
        return detalle_forma_cobro;
    }

    public void setDetalle_forma_cobro(detalle_forma_cobro detalle_forma_cobro) {
        this.detalle_forma_cobro = detalle_forma_cobro;
    }

    

}
