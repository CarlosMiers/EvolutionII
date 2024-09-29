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
public class detalle_rescision {

String iddetalle;
String idfactura;
Double nrofactura;
Date emision;
Date vence;
comprobante comprobante;
Double amortiza;
Double minteres;
Double saldo;
Double pago;
Double capital;
Double importe_iva;
Double mora;
Double gastos_cobranzas;
Double punitorio;
int moneda;
int numerocuota;
int cuota;
int diamora;
Double aportes;
Double comisiones;
Date vencimiento;
Date fechacobro;
Double alquiler;
Double ivaalquiler;
Double garage;
Double ivagarage;
Double expensa;
Double ivaexpensa;
Double comision;
Double ivacomision;
Double multa;
Double ivamulta;
Double garantia;
Double aire;
Double fondo;
Double llave;
Double otros;
int idunidad;


    public detalle_rescision() {

    }

    public detalle_rescision(String iddetalle, String idfactura, Double nrofactura, Date emision, Date vence, comprobante comprobante, Double amortiza, Double minteres, Double saldo, Double pago, Double capital, Double importe_iva, Double mora, Double gastos_cobranzas, Double punitorio, int moneda, int numerocuota, int cuota, int diamora, Double aportes, Double comisiones, Date vencimiento, Date fechacobro, Double alquiler, Double ivaalquiler, Double garage, Double ivagarage, Double expensa, Double ivaexpensa, Double comision, Double ivacomision, Double multa, Double ivamulta, Double garantia, Double aire, Double fondo, Double llave, Double otros, int idunidad) {
        this.iddetalle = iddetalle;
        this.idfactura = idfactura;
        this.nrofactura = nrofactura;
        this.emision = emision;
        this.vence = vence;
        this.comprobante = comprobante;
        this.amortiza = amortiza;
        this.minteres = minteres;
        this.saldo = saldo;
        this.pago = pago;
        this.capital = capital;
        this.importe_iva = importe_iva;
        this.mora = mora;
        this.gastos_cobranzas = gastos_cobranzas;
        this.punitorio = punitorio;
        this.moneda = moneda;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.diamora = diamora;
        this.aportes = aportes;
        this.comisiones = comisiones;
        this.vencimiento = vencimiento;
        this.fechacobro = fechacobro;
        this.alquiler = alquiler;
        this.ivaalquiler = ivaalquiler;
        this.garage = garage;
        this.ivagarage = ivagarage;
        this.expensa = expensa;
        this.ivaexpensa = ivaexpensa;
        this.comision = comision;
        this.ivacomision = ivacomision;
        this.multa = multa;
        this.ivamulta = ivamulta;
        this.garantia = garantia;
        this.aire = aire;
        this.fondo = fondo;
        this.llave = llave;
        this.otros = otros;
        this.idunidad = idunidad;
    }

    public String getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(String iddetalle) {
        this.iddetalle = iddetalle;
    }

    public String getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public Double getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(Double nrofactura) {
        this.nrofactura = nrofactura;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public Date getVence() {
        return vence;
    }

    public void setVence(Date vence) {
        this.vence = vence;
    }

    public comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Double getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(Double amortiza) {
        this.amortiza = amortiza;
    }

    public Double getMinteres() {
        return minteres;
    }

    public void setMinteres(Double minteres) {
        this.minteres = minteres;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Double getImporte_iva() {
        return importe_iva;
    }

    public void setImporte_iva(Double importe_iva) {
        this.importe_iva = importe_iva;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public Double getGastos_cobranzas() {
        return gastos_cobranzas;
    }

    public void setGastos_cobranzas(Double gastos_cobranzas) {
        this.gastos_cobranzas = gastos_cobranzas;
    }

    public Double getPunitorio() {
        return punitorio;
    }

    public void setPunitorio(Double punitorio) {
        this.punitorio = punitorio;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
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

    public Double getAportes() {
        return aportes;
    }

    public void setAportes(Double aportes) {
        this.aportes = aportes;
    }

    public Double getComisiones() {
        return comisiones;
    }

    public void setComisiones(Double comisiones) {
        this.comisiones = comisiones;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getFechacobro() {
        return fechacobro;
    }

    public void setFechacobro(Date fechacobro) {
        this.fechacobro = fechacobro;
    }

    public Double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Double alquiler) {
        this.alquiler = alquiler;
    }

    public Double getIvaalquiler() {
        return ivaalquiler;
    }

    public void setIvaalquiler(Double ivaalquiler) {
        this.ivaalquiler = ivaalquiler;
    }

    public Double getGarage() {
        return garage;
    }

    public void setGarage(Double garage) {
        this.garage = garage;
    }

    public Double getIvagarage() {
        return ivagarage;
    }

    public void setIvagarage(Double ivagarage) {
        this.ivagarage = ivagarage;
    }

    public Double getExpensa() {
        return expensa;
    }

    public void setExpensa(Double expensa) {
        this.expensa = expensa;
    }

    public Double getIvaexpensa() {
        return ivaexpensa;
    }

    public void setIvaexpensa(Double ivaexpensa) {
        this.ivaexpensa = ivaexpensa;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getIvacomision() {
        return ivacomision;
    }

    public void setIvacomision(Double ivacomision) {
        this.ivacomision = ivacomision;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getIvamulta() {
        return ivamulta;
    }

    public void setIvamulta(Double ivamulta) {
        this.ivamulta = ivamulta;
    }

    public Double getGarantia() {
        return garantia;
    }

    public void setGarantia(Double garantia) {
        this.garantia = garantia;
    }

    public Double getAire() {
        return aire;
    }

    public void setAire(Double aire) {
        this.aire = aire;
    }

    public Double getFondo() {
        return fondo;
    }

    public void setFondo(Double fondo) {
        this.fondo = fondo;
    }

    public Double getLlave() {
        return llave;
    }

    public void setLlave(Double llave) {
        this.llave = llave;
    }

    public Double getOtros() {
        return otros;
    }

    public void setOtros(Double otros) {
        this.otros = otros;
    }

    public int getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(int idunidad) {
        this.idunidad = idunidad;
    }


}
