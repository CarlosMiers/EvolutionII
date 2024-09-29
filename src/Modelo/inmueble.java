/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class inmueble {

    int idinmueble;
    tipo_inmueble tipinmueble;
    barrio coddis;
    localidad codloca;
    propietario codpro;
    String nombrepropietario;
    String nomedif;
    String diredif;
    String finca;
    String codbar;
    String ctactral;
    Date fecalta;
    Date fecmodi;
    String observacion;
    String reglainter;
    Double ivaexpensa;

    public inmueble() {

    }

    public inmueble(int idinmueble, tipo_inmueble tipinmueble, barrio coddis, localidad codloca, propietario codpro, String nombrepropietario, String nomedif, String diredif, String finca, String codbar, String ctactral, Date fecalta, Date fecmodi, String observacion, String reglainter, Double ivaexpensa) {
        this.idinmueble = idinmueble;
        this.tipinmueble = tipinmueble;
        this.coddis = coddis;
        this.codloca = codloca;
        this.codpro = codpro;
        this.nombrepropietario = nombrepropietario;
        this.nomedif = nomedif;
        this.diredif = diredif;
        this.finca = finca;
        this.codbar = codbar;
        this.ctactral = ctactral;
        this.fecalta = fecalta;
        this.fecmodi = fecmodi;
        this.observacion = observacion;
        this.reglainter = reglainter;
        this.ivaexpensa = ivaexpensa;
    }

    public int getIdinmueble() {
        return idinmueble;
    }

    public void setIdinmueble(int idinmueble) {
        this.idinmueble = idinmueble;
    }

    public tipo_inmueble getTipinmueble() {
        return tipinmueble;
    }

    public void setTipinmueble(tipo_inmueble tipinmueble) {
        this.tipinmueble = tipinmueble;
    }

    public barrio getCoddis() {
        return coddis;
    }

    public void setCoddis(barrio coddis) {
        this.coddis = coddis;
    }

    public localidad getCodloca() {
        return codloca;
    }

    public void setCodloca(localidad codloca) {
        this.codloca = codloca;
    }

    public propietario getCodpro() {
        return codpro;
    }

    public void setCodpro(propietario codpro) {
        this.codpro = codpro;
    }

    public String getNombrepropietario() {
        return nombrepropietario;
    }

    public void setNombrepropietario(String nombrepropietario) {
        this.nombrepropietario = nombrepropietario;
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

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    public String getCodbar() {
        return codbar;
    }

    public void setCodbar(String codbar) {
        this.codbar = codbar;
    }

    public String getCtactral() {
        return ctactral;
    }

    public void setCtactral(String ctactral) {
        this.ctactral = ctactral;
    }

    public Date getFecalta() {
        return fecalta;
    }

    public void setFecalta(Date fecalta) {
        this.fecalta = fecalta;
    }

    public Date getFecmodi() {
        return fecmodi;
    }

    public void setFecmodi(Date fecmodi) {
        this.fecmodi = fecmodi;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getReglainter() {
        return reglainter;
    }

    public void setReglainter(String reglainter) {
        this.reglainter = reglainter;
    }

    public Double getIvaexpensa() {
        return ivaexpensa;
    }

    public void setIvaexpensa(Double ivaexpensa) {
        this.ivaexpensa = ivaexpensa;
    }


}
