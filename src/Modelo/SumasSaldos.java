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
public class SumasSaldos {
    String cuenta;
    String descripcion;
    int nivel;
    int tipo_cuenta;
    int asentable;
    long saldo_anterior;
    long debe;
    long haber;
    long saldo_periodo;
    long saldo_acumulado;
    int corte2;

    public void SumasSaldos(){     
        
    }

    public SumasSaldos(String cuenta, String descripcion, int nivel, int tipo_cuenta, int asentable, long saldo_anterior, long debe, long haber, long saldo_periodo, long saldo_acumulado, int corte2) {
        this.cuenta = cuenta;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.tipo_cuenta = tipo_cuenta;
        this.asentable = asentable;
        this.saldo_anterior = saldo_anterior;
        this.debe = debe;
        this.haber = haber;
        this.saldo_periodo = saldo_periodo;
        this.saldo_acumulado = saldo_acumulado;
        this.corte2 = corte2;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(int tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public int getAsentable() {
        return asentable;
    }

    public void setAsentable(int asentable) {
        this.asentable = asentable;
    }

    public long getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(long saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public long getDebe() {
        return debe;
    }

    public void setDebe(long debe) {
        this.debe = debe;
    }

    public long getHaber() {
        return haber;
    }

    public void setHaber(long haber) {
        this.haber = haber;
    }

    public long getSaldo_periodo() {
        return saldo_periodo;
    }

    public void setSaldo_periodo(long saldo_periodo) {
        this.saldo_periodo = saldo_periodo;
    }

    public long getSaldo_acumulado() {
        return saldo_acumulado;
    }

    public void setSaldo_acumulado(long saldo_acumulado) {
        this.saldo_acumulado = saldo_acumulado;
    }

    public int getCorte2() {
        return corte2;
    }

    public void setCorte2(int corte2) {
        this.corte2 = corte2;
    }
   
}
