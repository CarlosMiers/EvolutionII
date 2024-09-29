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
public class BalanceSumasSaldos {

    String cuenta;
    String descripcion;
    double debe;
    double haber;
    double saldo;
    int tipo_cuenta;
    int nivel;
    int asentable;
    String corte;

    public void BalanceGeneral() {

    }

    public BalanceSumasSaldos(String cuenta, String descripcion, double debe, double haber, double saldo, int tipo_cuenta, int nivel, int asentable, String corte) {
        this.cuenta = cuenta;
        this.descripcion = descripcion;
        this.debe = debe;
        this.haber = haber;
        this.saldo = saldo;
        this.tipo_cuenta = tipo_cuenta;
        this.nivel = nivel;
        this.asentable = asentable;
        this.corte = corte;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(int tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAsentable() {
        return asentable;
    }

    public void setAsentable(int asentable) {
        this.asentable = asentable;
    }

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }


}
