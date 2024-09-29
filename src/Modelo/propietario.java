/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author SERVIDOR
 */
public class propietario {

    int codpro;
    String nombre;
    String apellido;
    String nombreprop;
    tipo_documento tipodoc;
    String ruc;
    String cedula;
    Date fechanac;
    String dirparticular;
    String teleparticular;
    String dirlaboral;
    String telelaboral;
    String estadocivil;
    String email;
    int estado;
    String observacion;
    pais nacionalidad;
    int tipliq;
    double porchon;
    int cliqexp;
    int desmul;
    double pormul;
    double porcrete;
    double iva;
    double ivaexp;
    int destiva;
    String honalq;
    String hondep;
    String honmulta;
    String honindem;
    String hongaraj;
    String honexp;
    String honllave;
    int cliqdep;
    int ctipliq;
    String factura;
    String recibo;
    vendedor codger;
    public propietario() {

    }

    public propietario(int codpro, String nombre, String apellido, String nombreprop, tipo_documento tipodoc, String ruc, String cedula, Date fechanac, String dirparticular, String teleparticular, String dirlaboral, String telelaboral, String estadocivil, String email, int estado, String observacion, pais nacionalidad, int tipliq, double porchon, int cliqexp, int desmul, double pormul, double porcrete, double iva, double ivaexp, int destiva, String honalq, String hondep, String honmulta, String honindem, String hongaraj, String honexp, String honllave, int cliqdep, int ctipliq, String factura, String recibo, vendedor codger) {
        this.codpro = codpro;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreprop = nombreprop;
        this.tipodoc = tipodoc;
        this.ruc = ruc;
        this.cedula = cedula;
        this.fechanac = fechanac;
        this.dirparticular = dirparticular;
        this.teleparticular = teleparticular;
        this.dirlaboral = dirlaboral;
        this.telelaboral = telelaboral;
        this.estadocivil = estadocivil;
        this.email = email;
        this.estado = estado;
        this.observacion = observacion;
        this.nacionalidad = nacionalidad;
        this.tipliq = tipliq;
        this.porchon = porchon;
        this.cliqexp = cliqexp;
        this.desmul = desmul;
        this.pormul = pormul;
        this.porcrete = porcrete;
        this.iva = iva;
        this.ivaexp = ivaexp;
        this.destiva = destiva;
        this.honalq = honalq;
        this.hondep = hondep;
        this.honmulta = honmulta;
        this.honindem = honindem;
        this.hongaraj = hongaraj;
        this.honexp = honexp;
        this.honllave = honllave;
        this.cliqdep = cliqdep;
        this.ctipliq = ctipliq;
        this.factura = factura;
        this.recibo = recibo;
        this.codger = codger;
    }

    public int getCodpro() {
        return codpro;
    }

    public void setCodpro(int codpro) {
        this.codpro = codpro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreprop() {
        return nombreprop;
    }

    public void setNombreprop(String nombreprop) {
        this.nombreprop = nombreprop;
    }

    public tipo_documento getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(tipo_documento tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFechanac() {
        return fechanac;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public String getDirparticular() {
        return dirparticular;
    }

    public void setDirparticular(String dirparticular) {
        this.dirparticular = dirparticular;
    }

    public String getTeleparticular() {
        return teleparticular;
    }

    public void setTeleparticular(String teleparticular) {
        this.teleparticular = teleparticular;
    }

    public String getDirlaboral() {
        return dirlaboral;
    }

    public void setDirlaboral(String dirlaboral) {
        this.dirlaboral = dirlaboral;
    }

    public String getTelelaboral() {
        return telelaboral;
    }

    public void setTelelaboral(String telelaboral) {
        this.telelaboral = telelaboral;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public pais getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(pais nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getTipliq() {
        return tipliq;
    }

    public void setTipliq(int tipliq) {
        this.tipliq = tipliq;
    }

    public double getPorchon() {
        return porchon;
    }

    public void setPorchon(double porchon) {
        this.porchon = porchon;
    }

    public int getCliqexp() {
        return cliqexp;
    }

    public void setCliqexp(int cliqexp) {
        this.cliqexp = cliqexp;
    }

    public int getDesmul() {
        return desmul;
    }

    public void setDesmul(int desmul) {
        this.desmul = desmul;
    }

    public double getPormul() {
        return pormul;
    }

    public void setPormul(double pormul) {
        this.pormul = pormul;
    }

    public double getPorcrete() {
        return porcrete;
    }

    public void setPorcrete(double porcrete) {
        this.porcrete = porcrete;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getIvaexp() {
        return ivaexp;
    }

    public void setIvaexp(double ivaexp) {
        this.ivaexp = ivaexp;
    }

    public int getDestiva() {
        return destiva;
    }

    public void setDestiva(int destiva) {
        this.destiva = destiva;
    }

    public String getHonalq() {
        return honalq;
    }

    public void setHonalq(String honalq) {
        this.honalq = honalq;
    }

    public String getHondep() {
        return hondep;
    }

    public void setHondep(String hondep) {
        this.hondep = hondep;
    }

    public String getHonmulta() {
        return honmulta;
    }

    public void setHonmulta(String honmulta) {
        this.honmulta = honmulta;
    }

    public String getHonindem() {
        return honindem;
    }

    public void setHonindem(String honindem) {
        this.honindem = honindem;
    }

    public String getHongaraj() {
        return hongaraj;
    }

    public void setHongaraj(String hongaraj) {
        this.hongaraj = hongaraj;
    }

    public String getHonexp() {
        return honexp;
    }

    public void setHonexp(String honexp) {
        this.honexp = honexp;
    }

    public String getHonllave() {
        return honllave;
    }

    public void setHonllave(String honllave) {
        this.honllave = honllave;
    }

    public int getCliqdep() {
        return cliqdep;
    }

    public void setCliqdep(int cliqdep) {
        this.cliqdep = cliqdep;
    }

    public int getCtipliq() {
        return ctipliq;
    }

    public void setCtipliq(int ctipliq) {
        this.ctipliq = ctipliq;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public vendedor getCodger() {
        return codger;
    }

    public void setCodger(vendedor codger) {
        this.codger = codger;
    }

   

}
