/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERVIDOR
 */
public class libro_diario_report {

    int asiento;
    int numero;
    String fecha;
    String asi_numero;
    String asi_codigo;
    String nombrecuenta;
    String asi_descri;
    Double impdebe;
    Double imphaber;
    String nombresucursal;
   
    
    public libro_diario_report() {

    }

    public libro_diario_report(int asiento, int numero, String fecha, String asi_numero, String asi_codigo, String nombrecuenta, String asi_descri, Double impdebe, Double imphaber, String nombresucursal) {
        this.asiento = asiento;
        this.numero = numero;
        this.fecha = fecha;
        this.asi_numero = asi_numero;
        this.asi_codigo = asi_codigo;
        this.nombrecuenta = nombrecuenta;
        this.asi_descri = asi_descri;
        this.impdebe = impdebe;
        this.imphaber = imphaber;
        this.nombresucursal = nombresucursal;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsi_numero() {
        return asi_numero;
    }

    public void setAsi_numero(String asi_numero) {
        this.asi_numero = asi_numero;
    }

    public String getAsi_codigo() {
        return asi_codigo;
    }

    public void setAsi_codigo(String asi_codigo) {
        this.asi_codigo = asi_codigo;
    }

    public String getNombrecuenta() {
        return nombrecuenta;
    }

    public void setNombrecuenta(String nombrecuenta) {
        this.nombrecuenta = nombrecuenta;
    }

    public String getAsi_descri() {
        return asi_descri;
    }

    public void setAsi_descri(String asi_descri) {
        this.asi_descri = asi_descri;
    }

    public Double getImpdebe() {
        return impdebe;
    }

    public void setImpdebe(Double impdebe) {
        this.impdebe = impdebe;
    }

    public Double getImphaber() {
        return imphaber;
    }

    public void setImphaber(Double imphaber) {
        this.imphaber = imphaber;
    }

    public String getNombresucursal() {
        return nombresucursal;
    }

    public void setNombresucursal(String nombresucursal) {
        this.nombresucursal = nombresucursal;
    }

   
}
