/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


/**
 *
 * @author SERVIDOR
 */
public class registro_operaciones_report {

    String fecha;
    String numero;
    String asi_numero;
    String asi_codigo;
    String nombrecuenta; 
    String asi_descri;
    Double impdebe;
    Double imphaber;
   
  
    public registro_operaciones_report() {

    }

    public registro_operaciones_report(String fecha, String numero, String asi_numero, String asi_codigo, String nombrecuenta, String asi_descri, Double impdebe, Double imphaber) {
        this.fecha = fecha;
        this.numero = numero;
        this.asi_numero = asi_numero;
        this.asi_codigo = asi_codigo;
        this.nombrecuenta = nombrecuenta;
        this.asi_descri = asi_descri;
        this.impdebe = impdebe;
        this.imphaber = imphaber;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

   
}
