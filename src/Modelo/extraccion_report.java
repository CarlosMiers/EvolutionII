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
public class extraccion_report {

    int idcontrol;
    String documento;
    Double importe;
    String fecha;
    String chequenro;
    String observaciones;
    
    public extraccion_report() {

    }

    public extraccion_report(int idcontrol, String documento, Double importe, String fecha, String chequenro, String observaciones) {
        this.idcontrol = idcontrol;
        this.documento = documento;
        this.importe = importe;
        this.fecha = fecha;
        this.chequenro = chequenro;
        this.observaciones = observaciones;
    }

    public int getIdcontrol() {
        return idcontrol;
    }

    public void setIdcontrol(int idcontrol) {
        this.idcontrol = idcontrol;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getChequenro() {
        return chequenro;
    }

    public void setChequenro(String chequenro) {
        this.chequenro = chequenro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

  
}
