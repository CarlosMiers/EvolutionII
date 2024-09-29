/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author Pc_Server
 */
public class alquilerxlocatarioreport {
  
    String documento;
    int codigo;
    String nombrelocatario;
    String ruclocatario;
    String locatarioDirec;
    String locatarioTelef;
    int idinmueble;
    String nomedif;
    String diredif;
    String inmctactral;
    String vencimiento;
    Double alquiler;
    Double garage;
    Double expensa;
    Double comision;
    Double multa;
    int numerocuota;
    int cuota;
   
 
    public void alquilerxlocatarioreport(){
        
    }

    public alquilerxlocatarioreport(String documento, int codigo, String nombrelocatario, String ruclocatario, String locatarioDirec, String locatarioTelef, int idinmueble, String nomedif, String diredif, String inmctactral, String vencimiento, Double alquiler, Double garage, Double expensa, Double comision, Double multa, int numerocuota, int cuota) {
        this.documento = documento;
        this.codigo = codigo;
        this.nombrelocatario = nombrelocatario;
        this.ruclocatario = ruclocatario;
        this.locatarioDirec = locatarioDirec;
        this.locatarioTelef = locatarioTelef;
        this.idinmueble = idinmueble;
        this.nomedif = nomedif;
        this.diredif = diredif;
        this.inmctactral = inmctactral;
        this.vencimiento = vencimiento;
        this.alquiler = alquiler;
        this.garage = garage;
        this.expensa = expensa;
        this.comision = comision;
        this.multa = multa;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombrelocatario() {
        return nombrelocatario;
    }

    public void setNombrelocatario(String nombrelocatario) {
        this.nombrelocatario = nombrelocatario;
    }

    public String getRuclocatario() {
        return ruclocatario;
    }

    public void setRuclocatario(String ruclocatario) {
        this.ruclocatario = ruclocatario;
    }

    public String getLocatarioDirec() {
        return locatarioDirec;
    }

    public void setLocatarioDirec(String locatarioDirec) {
        this.locatarioDirec = locatarioDirec;
    }

    public String getLocatarioTelef() {
        return locatarioTelef;
    }

    public void setLocatarioTelef(String locatarioTelef) {
        this.locatarioTelef = locatarioTelef;
    }

    public int getIdinmueble() {
        return idinmueble;
    }

    public void setIdinmueble(int idinmueble) {
        this.idinmueble = idinmueble;
    }

    public String getNomedif() {
        return nomedif;
    }

    public void setNomedif(String nomedif) {
        this.nomedif = nomedif;
    }

    public String getDiredif() {
        return diredif;
    }

    public void setDiredif(String diredif) {
        this.diredif = diredif;
    }

    public String getInmctactral() {
        return inmctactral;
    }

    public void setInmctactral(String inmctactral) {
        this.inmctactral = inmctactral;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Double alquiler) {
        this.alquiler = alquiler;
    }

    public Double getGarage() {
        return garage;
    }

    public void setGarage(Double garage) {
        this.garage = garage;
    }

    public Double getExpensa() {
        return expensa;
    }

    public void setExpensa(Double expensa) {
        this.expensa = expensa;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
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

   
    
}
