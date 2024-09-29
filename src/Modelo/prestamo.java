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
public class prestamo {

    String idprestamos;
    int numero;
    Date fecha;
    moneda moneda;
    sucursal sucursal;
    int acreedor;
    BigDecimal porcentaje_acreedor;
    BigDecimal comision_acreedor;
    int solicitud;
    cliente socio;
    BigDecimal monto_entregar;
    BigDecimal porcentaje_deudor;
    BigDecimal comision_deudor;
    int plazo;
    BigDecimal deducciones;
    BigDecimal importe;
    BigDecimal monto_cuota;
    Date primer_vence;
    comprobante tipo;
    BigDecimal tasa;
    BigDecimal interes;
    BigDecimal ivainteres;
    int cierre;
    int asiento;
    BigDecimal totalamortizacion;
    BigDecimal totalprestamo;
    int tipopago;
    int garante;
    BigDecimal gastos_escritura;
    int garante2;
    vendedor asesor;
    BigDecimal comision_asesor;
    int codusuario;
    int generariva;
    BigDecimal cotizacionmoneda;
    BigDecimal interesnocobrado;
    vendedor oficial;
    int destino;
    int garantia;
    String estado;
    BigDecimal entregarneto;
    String observaciones;
    int aprobadopor;
    String idfactura;
    giraduria giraduria;
    String nrocuenta;
    BigDecimal segurovida;
    BigDecimal montoaprobado;
    BigDecimal capitalizacion;
    BigDecimal aporte;
    BigDecimal solidaridad;
    BigDecimal fondoproteccion;
    BigDecimal serviciocobrador;
    BigDecimal seguro;
    String fecha_inicio;
    String fecha_final;
    int dia_debito;

    
    public prestamo() {

    }

    public prestamo(String idprestamos, int numero, Date fecha, moneda moneda, sucursal sucursal, int acreedor, BigDecimal porcentaje_acreedor, BigDecimal comision_acreedor, int solicitud, cliente socio, BigDecimal monto_entregar, BigDecimal porcentaje_deudor, BigDecimal comision_deudor, int plazo, BigDecimal deducciones, BigDecimal importe, BigDecimal monto_cuota, Date primer_vence, comprobante tipo, BigDecimal tasa, BigDecimal interes, BigDecimal ivainteres, int cierre, int asiento, BigDecimal totalamortizacion, BigDecimal totalprestamo, int tipopago, int garante, BigDecimal gastos_escritura, int garante2, vendedor asesor, BigDecimal comision_asesor, int codusuario, int generariva, BigDecimal cotizacionmoneda, BigDecimal interesnocobrado, vendedor oficial, int destino, int garantia, String estado, BigDecimal entregarneto, String observaciones, int aprobadopor, String idfactura, giraduria giraduria, String nrocuenta, BigDecimal segurovida, BigDecimal montoaprobado, BigDecimal capitalizacion, BigDecimal aporte, BigDecimal solidaridad, BigDecimal fondoproteccion, BigDecimal serviciocobrador, BigDecimal seguro, String fecha_inicio, String fecha_final, int dia_debito) {
        this.idprestamos = idprestamos;
        this.numero = numero;
        this.fecha = fecha;
        this.moneda = moneda;
        this.sucursal = sucursal;
        this.acreedor = acreedor;
        this.porcentaje_acreedor = porcentaje_acreedor;
        this.comision_acreedor = comision_acreedor;
        this.solicitud = solicitud;
        this.socio = socio;
        this.monto_entregar = monto_entregar;
        this.porcentaje_deudor = porcentaje_deudor;
        this.comision_deudor = comision_deudor;
        this.plazo = plazo;
        this.deducciones = deducciones;
        this.importe = importe;
        this.monto_cuota = monto_cuota;
        this.primer_vence = primer_vence;
        this.tipo = tipo;
        this.tasa = tasa;
        this.interes = interes;
        this.ivainteres = ivainteres;
        this.cierre = cierre;
        this.asiento = asiento;
        this.totalamortizacion = totalamortizacion;
        this.totalprestamo = totalprestamo;
        this.tipopago = tipopago;
        this.garante = garante;
        this.gastos_escritura = gastos_escritura;
        this.garante2 = garante2;
        this.asesor = asesor;
        this.comision_asesor = comision_asesor;
        this.codusuario = codusuario;
        this.generariva = generariva;
        this.cotizacionmoneda = cotizacionmoneda;
        this.interesnocobrado = interesnocobrado;
        this.oficial = oficial;
        this.destino = destino;
        this.garantia = garantia;
        this.estado = estado;
        this.entregarneto = entregarneto;
        this.observaciones = observaciones;
        this.aprobadopor = aprobadopor;
        this.idfactura = idfactura;
        this.giraduria = giraduria;
        this.nrocuenta = nrocuenta;
        this.segurovida = segurovida;
        this.montoaprobado = montoaprobado;
        this.capitalizacion = capitalizacion;
        this.aporte = aporte;
        this.solidaridad = solidaridad;
        this.fondoproteccion = fondoproteccion;
        this.serviciocobrador = serviciocobrador;
        this.seguro = seguro;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.dia_debito = dia_debito;
    }

    public String getIdprestamos() {
        return idprestamos;
    }

    public void setIdprestamos(String idprestamos) {
        this.idprestamos = idprestamos;
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

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(int acreedor) {
        this.acreedor = acreedor;
    }

    public BigDecimal getPorcentaje_acreedor() {
        return porcentaje_acreedor;
    }

    public void setPorcentaje_acreedor(BigDecimal porcentaje_acreedor) {
        this.porcentaje_acreedor = porcentaje_acreedor;
    }

    public BigDecimal getComision_acreedor() {
        return comision_acreedor;
    }

    public void setComision_acreedor(BigDecimal comision_acreedor) {
        this.comision_acreedor = comision_acreedor;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

    public cliente getSocio() {
        return socio;
    }

    public void setSocio(cliente socio) {
        this.socio = socio;
    }

    public BigDecimal getMonto_entregar() {
        return monto_entregar;
    }

    public void setMonto_entregar(BigDecimal monto_entregar) {
        this.monto_entregar = monto_entregar;
    }

    public BigDecimal getPorcentaje_deudor() {
        return porcentaje_deudor;
    }

    public void setPorcentaje_deudor(BigDecimal porcentaje_deudor) {
        this.porcentaje_deudor = porcentaje_deudor;
    }

    public BigDecimal getComision_deudor() {
        return comision_deudor;
    }

    public void setComision_deudor(BigDecimal comision_deudor) {
        this.comision_deudor = comision_deudor;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(BigDecimal deducciones) {
        this.deducciones = deducciones;
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

    public comprobante getTipo() {
        return tipo;
    }

    public void setTipo(comprobante tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getIvainteres() {
        return ivainteres;
    }

    public void setIvainteres(BigDecimal ivainteres) {
        this.ivainteres = ivainteres;
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

    public BigDecimal getTotalamortizacion() {
        return totalamortizacion;
    }

    public void setTotalamortizacion(BigDecimal totalamortizacion) {
        this.totalamortizacion = totalamortizacion;
    }

    public BigDecimal getTotalprestamo() {
        return totalprestamo;
    }

    public void setTotalprestamo(BigDecimal totalprestamo) {
        this.totalprestamo = totalprestamo;
    }

    public int getTipopago() {
        return tipopago;
    }

    public void setTipopago(int tipopago) {
        this.tipopago = tipopago;
    }

    public int getGarante() {
        return garante;
    }

    public void setGarante(int garante) {
        this.garante = garante;
    }

    public BigDecimal getGastos_escritura() {
        return gastos_escritura;
    }

    public void setGastos_escritura(BigDecimal gastos_escritura) {
        this.gastos_escritura = gastos_escritura;
    }

    public int getGarante2() {
        return garante2;
    }

    public void setGarante2(int garante2) {
        this.garante2 = garante2;
    }

    public vendedor getAsesor() {
        return asesor;
    }

    public void setAsesor(vendedor asesor) {
        this.asesor = asesor;
    }

    public BigDecimal getComision_asesor() {
        return comision_asesor;
    }

    public void setComision_asesor(BigDecimal comision_asesor) {
        this.comision_asesor = comision_asesor;
    }

    public int getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(int codusuario) {
        this.codusuario = codusuario;
    }

    public int getGenerariva() {
        return generariva;
    }

    public void setGenerariva(int generariva) {
        this.generariva = generariva;
    }

    public BigDecimal getCotizacionmoneda() {
        return cotizacionmoneda;
    }

    public void setCotizacionmoneda(BigDecimal cotizacionmoneda) {
        this.cotizacionmoneda = cotizacionmoneda;
    }

    public BigDecimal getInteresnocobrado() {
        return interesnocobrado;
    }

    public void setInteresnocobrado(BigDecimal interesnocobrado) {
        this.interesnocobrado = interesnocobrado;
    }

    public vendedor getOficial() {
        return oficial;
    }

    public void setOficial(vendedor oficial) {
        this.oficial = oficial;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getEntregarneto() {
        return entregarneto;
    }

    public void setEntregarneto(BigDecimal entregarneto) {
        this.entregarneto = entregarneto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getAprobadopor() {
        return aprobadopor;
    }

    public void setAprobadopor(int aprobadopor) {
        this.aprobadopor = aprobadopor;
    }

    public String getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public BigDecimal getSegurovida() {
        return segurovida;
    }

    public void setSegurovida(BigDecimal segurovida) {
        this.segurovida = segurovida;
    }

    public BigDecimal getMontoaprobado() {
        return montoaprobado;
    }

    public void setMontoaprobado(BigDecimal montoaprobado) {
        this.montoaprobado = montoaprobado;
    }

    public BigDecimal getCapitalizacion() {
        return capitalizacion;
    }

    public void setCapitalizacion(BigDecimal capitalizacion) {
        this.capitalizacion = capitalizacion;
    }

    public BigDecimal getAporte() {
        return aporte;
    }

    public void setAporte(BigDecimal aporte) {
        this.aporte = aporte;
    }

    public BigDecimal getSolidaridad() {
        return solidaridad;
    }

    public void setSolidaridad(BigDecimal solidaridad) {
        this.solidaridad = solidaridad;
    }

    public BigDecimal getFondoproteccion() {
        return fondoproteccion;
    }

    public void setFondoproteccion(BigDecimal fondoproteccion) {
        this.fondoproteccion = fondoproteccion;
    }

    public BigDecimal getServiciocobrador() {
        return serviciocobrador;
    }

    public void setServiciocobrador(BigDecimal serviciocobrador) {
        this.serviciocobrador = serviciocobrador;
    }

    public BigDecimal getSeguro() {
        return seguro;
    }

    public void setSeguro(BigDecimal seguro) {
        this.seguro = seguro;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public int getDia_debito() {
        return dia_debito;
    }

    public void setDia_debito(int dia_debito) {
        this.dia_debito = dia_debito;
    }


}
