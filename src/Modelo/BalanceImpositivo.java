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
public class BalanceImpositivo {
    String cuenta;
    String descripcion;
    int nivel;
    int tipo_cuenta;
    int asentable;
    long debitos;
    long creditos;
    long deudor;
    long acreedor;
    long activo;
    long pasivo_pn;
    long perdidas;
    long ganancias;
    int corte2;

    public void SumasSaldos(){     
        
    }

    public BalanceImpositivo(String cuenta, String descripcion, int nivel, int tipo_cuenta, int asentable, long debitos, long creditos, long deudor, long acreedor, long activo, long pasivo_pn, long perdidas, long ganancias, int corte2) {
        this.cuenta = cuenta;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.tipo_cuenta = tipo_cuenta;
        this.asentable = asentable;
        this.debitos = debitos;
        this.creditos = creditos;
        this.deudor = deudor;
        this.acreedor = acreedor;
        this.activo = activo;
        this.pasivo_pn = pasivo_pn;
        this.perdidas = perdidas;
        this.ganancias = ganancias;
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

    public long getDebitos() {
        return debitos;
    }

    public void setDebitos(long debitos) {
        this.debitos = debitos;
    }

    public long getCreditos() {
        return creditos;
    }

    public void setCreditos(long creditos) {
        this.creditos = creditos;
    }

    public long getDeudor() {
        return deudor;
    }

    public void setDeudor(long deudor) {
        this.deudor = deudor;
    }

    public long getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(long acreedor) {
        this.acreedor = acreedor;
    }

    public long getActivo() {
        return activo;
    }

    public void setActivo(long activo) {
        this.activo = activo;
    }

    public long getPasivo_pn() {
        return pasivo_pn;
    }

    public void setPasivo_pn(long pasivo_pn) {
        this.pasivo_pn = pasivo_pn;
    }

    public long getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(long perdidas) {
        this.perdidas = perdidas;
    }

    public long getGanancias() {
        return ganancias;
    }

    public void setGanancias(long ganancias) {
        this.ganancias = ganancias;
    }

    public int getCorte2() {
        return corte2;
    }

    public void setCorte2(int corte2) {
        this.corte2 = corte2;
    }        
}
