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
public class ordencredito {
   String idordencredito;
   String nrooden;
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
   BigDecimal interesordinario;
   BigDecimal montoentregar;
   BigDecimal descuentos;
   BigDecimal netoentregado;
   Date primer_vence;
   int asiento;
   int cierre;
   int usuarioalta;
   int usuarionulo;
   Date fechaalta;
   int usuariomod;
   Date fechamod;
   Date fechanulo;
   BigDecimal tasa;
   comprobante tipo;
   banco cargobanco;
   String nrocheque;
   Date emisioncheque;
   int aprobado;
   BigDecimal importecheque;
   BigDecimal retenciones;   
   String nombregarante;
   String estado;
   BigDecimal saldo;   
   double capital;
   double ivainteres;
   double gastos;
   
   public ordencredito(){
       
   }

    public ordencredito(String idordencredito, String nrooden, int numero, Date fecha, sucursal sucursal, giraduria giraduria, casa casas, cliente cliente, int garante, int plazo, BigDecimal importe, BigDecimal monto_cuota, BigDecimal interesordinario, BigDecimal montoentregar, BigDecimal descuentos, BigDecimal netoentregado, Date primer_vence, int asiento, int cierre, int usuarioalta, int usuarionulo, Date fechaalta, int usuariomod, Date fechamod, Date fechanulo, BigDecimal tasa, comprobante tipo, banco cargobanco, String nrocheque, Date emisioncheque, int aprobado, BigDecimal importecheque, BigDecimal retenciones, String nombregarante, String estado, BigDecimal saldo, double capital, double ivainteres, double gastos) {
        this.idordencredito = idordencredito;
        this.nrooden = nrooden;
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
        this.interesordinario = interesordinario;
        this.montoentregar = montoentregar;
        this.descuentos = descuentos;
        this.netoentregado = netoentregado;
        this.primer_vence = primer_vence;
        this.asiento = asiento;
        this.cierre = cierre;
        this.usuarioalta = usuarioalta;
        this.usuarionulo = usuarionulo;
        this.fechaalta = fechaalta;
        this.usuariomod = usuariomod;
        this.fechamod = fechamod;
        this.fechanulo = fechanulo;
        this.tasa = tasa;
        this.tipo = tipo;
        this.cargobanco = cargobanco;
        this.nrocheque = nrocheque;
        this.emisioncheque = emisioncheque;
        this.aprobado = aprobado;
        this.importecheque = importecheque;
        this.retenciones = retenciones;
        this.nombregarante = nombregarante;
        this.estado = estado;
        this.saldo = saldo;
        this.capital = capital;
        this.ivainteres = ivainteres;
        this.gastos = gastos;
    }

    public String getIdordencredito() {
        return idordencredito;
    }

    public void setIdordencredito(String idordencredito) {
        this.idordencredito = idordencredito;
    }

    public String getNrooden() {
        return nrooden;
    }

    public void setNrooden(String nrooden) {
        this.nrooden = nrooden;
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

    public BigDecimal getInteresordinario() {
        return interesordinario;
    }

    public void setInteresordinario(BigDecimal interesordinario) {
        this.interesordinario = interesordinario;
    }

    public BigDecimal getMontoentregar() {
        return montoentregar;
    }

    public void setMontoentregar(BigDecimal montoentregar) {
        this.montoentregar = montoentregar;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getNetoentregado() {
        return netoentregado;
    }

    public void setNetoentregado(BigDecimal netoentregado) {
        this.netoentregado = netoentregado;
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

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public comprobante getTipo() {
        return tipo;
    }

    public void setTipo(comprobante tipo) {
        this.tipo = tipo;
    }

    public banco getCargobanco() {
        return cargobanco;
    }

    public void setCargobanco(banco cargobanco) {
        this.cargobanco = cargobanco;
    }

    public String getNrocheque() {
        return nrocheque;
    }

    public void setNrocheque(String nrocheque) {
        this.nrocheque = nrocheque;
    }

    public Date getEmisioncheque() {
        return emisioncheque;
    }

    public void setEmisioncheque(Date emisioncheque) {
        this.emisioncheque = emisioncheque;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public BigDecimal getImportecheque() {
        return importecheque;
    }

    public void setImportecheque(BigDecimal importecheque) {
        this.importecheque = importecheque;
    }

    public BigDecimal getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(BigDecimal retenciones) {
        this.retenciones = retenciones;
    }

    public String getNombregarante() {
        return nombregarante;
    }

    public void setNombregarante(String nombregarante) {
        this.nombregarante = nombregarante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getIvainteres() {
        return ivainteres;
    }

    public void setIvainteres(double ivainteres) {
        this.ivainteres = ivainteres;
    }

    public double getGastos() {
        return gastos;
    }

    public void setGastos(double gastos) {
        this.gastos = gastos;
    }


    
}