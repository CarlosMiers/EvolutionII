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
public class alquilerlocatario {

    int codigo;
    String nombrelocatario;
    String ruclocatario;
    String locatarioDirec;
    String locatarioTelef;
    int codpro;
    String cedulaprop;
    String nombreprop;
    String direprop;
    String telefprop;
    String documento;
    String vencimiento;
    int idinmueble;
    String nomedif;
    String inmctactral;
    int numerocuota;
    int cuota;
    Double alquiler;
    Double garage;
    Double expensa;
    Double comision;
    Double multa;

    public void alquilerlocatario() {

    }

    public alquilerlocatario(int codigo, String nombrelocatario, String ruclocatario, String locatarioDirec, String locatarioTelef, int codpro, String cedulaprop, String nombreprop, String direprop, String telefprop, String documento, String vencimiento, int idinmueble, String nomedif, String inmctactral, int numerocuota, int cuota, Double alquiler, Double garage, Double expensa, Double comision, Double multa) {
        this.codigo = codigo;
        this.nombrelocatario = nombrelocatario;
        this.ruclocatario = ruclocatario;
        this.locatarioDirec = locatarioDirec;
        this.locatarioTelef = locatarioTelef;
        this.codpro = codpro;
        this.cedulaprop = cedulaprop;
        this.nombreprop = nombreprop;
        this.direprop = direprop;
        this.telefprop = telefprop;
        this.documento = documento;
        this.vencimiento = vencimiento;
        this.idinmueble = idinmueble;
        this.nomedif = nomedif;
        this.inmctactral = inmctactral;
        this.numerocuota = numerocuota;
        this.cuota = cuota;
        this.alquiler = alquiler;
        this.garage = garage;
        this.expensa = expensa;
        this.comision = comision;
        this.multa = multa;
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

    public int getCodpro() {
        return codpro;
    }

    public void setCodpro(int codpro) {
        this.codpro = codpro;
    }

    public String getCedulaprop() {
        return cedulaprop;
    }

    public void setCedulaprop(String cedulaprop) {
        this.cedulaprop = cedulaprop;
    }

    public String getNombreprop() {
        return nombreprop;
    }

    public void setNombreprop(String nombreprop) {
        this.nombreprop = nombreprop;
    }

    public String getDireprop() {
        return direprop;
    }

    public void setDireprop(String direprop) {
        this.direprop = direprop;
    }

    public String getTelefprop() {
        return telefprop;
    }

    public void setTelefprop(String telefprop) {
        this.telefprop = telefprop;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
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

    public String getInmctactral() {
        return inmctactral;
    }

    public void setInmctactral(String inmctactral) {
        this.inmctactral = inmctactral;
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

   
}
