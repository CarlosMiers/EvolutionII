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
public class detalle_cobranza {
   
    
String iddetalle;
String idfactura;
String nrofactura;
Date emision;
Date vence;
comprobante comprobante;
double amortiza;
double minteres;
double saldo;
double pago;
double capital;
double importe_iva;
double mora;
double gastos_cobranzas;
double punitorio;
moneda moneda;
int numerocuota;
int cuota;
int diamora;
double  aportes;
double comisiones;
Date vencimiento;
Date fechacobro;
double alquiler;
double ivaalquiler;
double garage;
double expensa;
double ivaexpensa;
double comision;
double ivacomision;
double multa;
double ivamulta;
edificio idunidad;

public detalle_cobranza(){
    
}

    public detalle_cobranza(String iddetalle, String idfactura, String nrofactura, Date emision, Date vence, comprobante comprobante, double amortiza, double minteres, double saldo, double pago, double capital, double importe_iva, double mora, double gastos_cobranzas, double punitorio, moneda moneda, int numerocuota, int cuota, int diamora, double aportes, double comisiones, Date vencimiento, Date fechacobro, double alquiler, double ivaalquiler, double garage, double expensa, double ivaexpensa, double comision, double ivacomision, double multa, double ivamulta, edificio idunidad) {
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
        this.expensa = expensa;
        this.ivaexpensa = ivaexpensa;
        this.comision = comision;
        this.ivacomision = ivacomision;
        this.multa = multa;
        this.ivamulta = ivamulta;
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

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
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

    public double getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(double amortiza) {
        this.amortiza = amortiza;
    }

    public double getMinteres() {
        return minteres;
    }

    public void setMinteres(double minteres) {
        this.minteres = minteres;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getImporte_iva() {
        return importe_iva;
    }

    public void setImporte_iva(double importe_iva) {
        this.importe_iva = importe_iva;
    }

    public double getMora() {
        return mora;
    }

    public void setMora(double mora) {
        this.mora = mora;
    }

    public double getGastos_cobranzas() {
        return gastos_cobranzas;
    }

    public void setGastos_cobranzas(double gastos_cobranzas) {
        this.gastos_cobranzas = gastos_cobranzas;
    }

    public double getPunitorio() {
        return punitorio;
    }

    public void setPunitorio(double punitorio) {
        this.punitorio = punitorio;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
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

    public double getAportes() {
        return aportes;
    }

    public void setAportes(double aportes) {
        this.aportes = aportes;
    }

    public double getComisiones() {
        return comisiones;
    }

    public void setComisiones(double comisiones) {
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

    public double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(double alquiler) {
        this.alquiler = alquiler;
    }

    public double getIvaalquiler() {
        return ivaalquiler;
    }

    public void setIvaalquiler(double ivaalquiler) {
        this.ivaalquiler = ivaalquiler;
    }

    public double getGarage() {
        return garage;
    }

    public void setGarage(double garage) {
        this.garage = garage;
    }

    public double getExpensa() {
        return expensa;
    }

    public void setExpensa(double expensa) {
        this.expensa = expensa;
    }

    public double getIvaexpensa() {
        return ivaexpensa;
    }

    public void setIvaexpensa(double ivaexpensa) {
        this.ivaexpensa = ivaexpensa;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getIvacomision() {
        return ivacomision;
    }

    public void setIvacomision(double ivacomision) {
        this.ivacomision = ivacomision;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public double getIvamulta() {
        return ivamulta;
    }

    public void setIvamulta(double ivamulta) {
        this.ivamulta = ivamulta;
    }

    public edificio getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(edificio idunidad) {
        this.idunidad = idunidad;
    }

  
}
