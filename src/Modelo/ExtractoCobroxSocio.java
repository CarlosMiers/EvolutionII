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
public class ExtractoCobroxSocio {

    String numero;
    String fecha;
    String vence;
    int socio;
    String nombresocio;
    String nombreservicio;
    String nombrecomercio;
    String cuota;
    Double importe;
    String nombregiraduria;
    int giraduria;
    String ruc;
    String direccion;
    String telefono;
    Double cobrado;
    Double amortiza;
    Double interes;

    public void ExtractoCobroxSocio() {

    }

    public ExtractoCobroxSocio(String numero, String fecha, String vence, int socio, String nombresocio, String nombreservicio, String nombrecomercio, String cuota, Double importe, String nombregiraduria, int giraduria, String ruc, String direccion, String telefono, Double cobrado, Double amortiza, Double interes) {
        this.numero = numero;
        this.fecha = fecha;
        this.vence = vence;
        this.socio = socio;
        this.nombresocio = nombresocio;
        this.nombreservicio = nombreservicio;
        this.nombrecomercio = nombrecomercio;
        this.cuota = cuota;
        this.importe = importe;
        this.nombregiraduria = nombregiraduria;
        this.giraduria = giraduria;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.cobrado = cobrado;
        this.amortiza = amortiza;
        this.interes = interes;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getVence() {
        return vence;
    }

    public void setVence(String vence) {
        this.vence = vence;
    }

    public int getSocio() {
        return socio;
    }

    public void setSocio(int socio) {
        this.socio = socio;
    }

    public String getNombresocio() {
        return nombresocio;
    }

    public void setNombresocio(String nombresocio) {
        this.nombresocio = nombresocio;
    }

    public String getNombreservicio() {
        return nombreservicio;
    }

    public void setNombreservicio(String nombreservicio) {
        this.nombreservicio = nombreservicio;
    }

    public String getNombrecomercio() {
        return nombrecomercio;
    }

    public void setNombrecomercio(String nombrecomercio) {
        this.nombrecomercio = nombrecomercio;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getNombregiraduria() {
        return nombregiraduria;
    }

    public void setNombregiraduria(String nombregiraduria) {
        this.nombregiraduria = nombregiraduria;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getCobrado() {
        return cobrado;
    }

    public void setCobrado(Double cobrado) {
        this.cobrado = cobrado;
    }

    public Double getAmortiza() {
        return amortiza;
    }

    public void setAmortiza(Double amortiza) {
        this.amortiza = amortiza;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }


    
    
}
