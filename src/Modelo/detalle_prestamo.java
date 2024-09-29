/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class detalle_prestamo {

    int nprestamo;
    int nrocuota;
    double capital;
    Date emision;
    Date inicio;
    int dias;
    double amortiza;
    double minteres;
    Date vence;
    double monto;
    double ivaxinteres;
    double aporte;

    public detalle_prestamo() {

    }

    public detalle_prestamo(int nprestamo, int nrocuota, double capital, Date emision, Date inicio, int dias, double amortiza, double minteres, Date vence, double monto, double ivaxinteres, double aporte) {
        this.nprestamo = nprestamo;
        this.nrocuota = nrocuota;
        this.capital = capital;
        this.emision = emision;
        this.inicio = inicio;
        this.dias = dias;
        this.amortiza = amortiza;
        this.minteres = minteres;
        this.vence = vence;
        this.monto = monto;
        this.ivaxinteres = ivaxinteres;
        this.aporte = aporte;
    }

    public int getNprestamo() {
        return nprestamo;
    }

    public void setNprestamo(int nprestamo) {
        this.nprestamo = nprestamo;
    }

    public int getNrocuota() {
        return nrocuota;
    }

    public void setNrocuota(int nrocuota) {
        this.nrocuota = nrocuota;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
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

    public Date getVence() {
        return vence;
    }

    public void setVence(Date vence) {
        this.vence = vence;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getIvaxinteres() {
        return ivaxinteres;
    }

    public void setIvaxinteres(double ivaxinteres) {
        this.ivaxinteres = ivaxinteres;
    }

    public double getAporte() {
        return aporte;
    }

    public void setAporte(double aporte) {
        this.aporte = aporte;
    }

}
