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
public class BalanceImpositivoReport {
    String cuenta;
    String descripcion;
    String debito;
    String credito;
    String deudor;
    String acreedor;
    String activo;
    String pasivo;
    String perdidas;
    String ganancias;

    public void SumasSaldos(){     
        
    }

    public BalanceImpositivoReport(String cuenta, String descripcion, String debito, String credito, String deudor, String acreedor, String activo, String pasivo, String perdidas, String ganancias) {
        this.cuenta = cuenta;
        this.descripcion = descripcion;
        this.debito = debito;
        this.credito = credito;
        this.deudor = deudor;
        this.acreedor = acreedor;
        this.activo = activo;
        this.pasivo = pasivo;
        this.perdidas = perdidas;
        this.ganancias = ganancias;
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

    public String getDebito() {
        return debito;
    }

    public void setDebito(String debito) {
        this.debito = debito;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
        this.deudor = deudor;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getPasivo() {
        return pasivo;
    }

    public void setPasivo(String pasivo) {
        this.pasivo = pasivo;
    }

    public String getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(String perdidas) {
        this.perdidas = perdidas;
    }

    public String getGanancias() {
        return ganancias;
    }

    public void setGanancias(String ganancias) {
        this.ganancias = ganancias;
    }

    
}
