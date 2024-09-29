/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class plan {

    String codigo;
    String nombre;
    String naturaleza;
    int nivel;
    int asentable;
    int tipo_cuenta;
    String cta_flujo;
    moneda moneda;
    double credito;
    double debito;
    double saldo;
    String corte;

    public plan() {

    }

    public plan(String codigo, String nombre, String naturaleza, int nivel, int asentable, int tipo_cuenta, String cta_flujo, moneda moneda, double credito, double debito, double saldo, String corte) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.naturaleza = naturaleza;
        this.nivel = nivel;
        this.asentable = asentable;
        this.tipo_cuenta = tipo_cuenta;
        this.cta_flujo = cta_flujo;
        this.moneda = moneda;
        this.credito = credito;
        this.debito = debito;
        this.saldo = saldo;
        this.corte = corte;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
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

    public int getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(int tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public String getCta_flujo() {
        return cta_flujo;
    }

    public void setCta_flujo(String cta_flujo) {
        this.cta_flujo = cta_flujo;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }


    
}
