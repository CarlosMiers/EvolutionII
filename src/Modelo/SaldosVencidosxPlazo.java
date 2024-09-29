/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Pc_Server
 */
public class SaldosVencidosxPlazo {
    String nombrecliente;
    Double n30dias;
    Double n60dias;
    Double n90dias;
    Double n180dias;
    Double masde180dias;
    Double saldo;
    
    public void SaldosVencidosxPlazo(){
        
    }

    public SaldosVencidosxPlazo(String nombrecliente, Double n30dias, Double n60dias, Double n90dias, Double n180dias, Double masde180dias, Double saldo) {
        this.nombrecliente = nombrecliente;
        this.n30dias = n30dias;
        this.n60dias = n60dias;
        this.n90dias = n90dias;
        this.n180dias = n180dias;
        this.masde180dias = masde180dias;
        this.saldo = saldo;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public Double getN30dias() {
        return n30dias;
    }

    public void setN30dias(Double n30dias) {
        this.n30dias = n30dias;
    }

    public Double getN60dias() {
        return n60dias;
    }

    public void setN60dias(Double n60dias) {
        this.n60dias = n60dias;
    }

    public Double getN90dias() {
        return n90dias;
    }

    public void setN90dias(Double n90dias) {
        this.n90dias = n90dias;
    }

    public Double getN180dias() {
        return n180dias;
    }

    public void setN180dias(Double n180dias) {
        this.n180dias = n180dias;
    }

    public Double getMasde180dias() {
        return masde180dias;
    }

    public void setMasde180dias(Double masde180dias) {
        this.masde180dias = masde180dias;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

           
}
