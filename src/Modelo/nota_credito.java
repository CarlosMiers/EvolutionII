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
public class nota_credito {

    double iditem;
    String idnotacredito;
    String notacredito;
    int timbrado;
    String idfactura;
    String nrofactura;
    int timbradoasociado;
    int tipo;
    
    public nota_credito() {

    }

    public nota_credito(double iditem, String idnotacredito, String notacredito, int timbrado, String idfactura, String nrofactura, int timbradoasociado, int tipo) {
        this.iditem = iditem;
        this.idnotacredito = idnotacredito;
        this.notacredito = notacredito;
        this.timbrado = timbrado;
        this.idfactura = idfactura;
        this.nrofactura = nrofactura;
        this.timbradoasociado = timbradoasociado;
        this.tipo = tipo;
    }

    public double getIditem() {
        return iditem;
    }

    public void setIditem(double iditem) {
        this.iditem = iditem;
    }

    public String getIdnotacredito() {
        return idnotacredito;
    }

    public void setIdnotacredito(String idnotacredito) {
        this.idnotacredito = idnotacredito;
    }

    public String getNotacredito() {
        return notacredito;
    }

    public void setNotacredito(String notacredito) {
        this.notacredito = notacredito;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    public String getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public int getTimbradoasociado() {
        return timbradoasociado;
    }

    public void setTimbradoasociado(int timbradoasociado) {
        this.timbradoasociado = timbradoasociado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


}
