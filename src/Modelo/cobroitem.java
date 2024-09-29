/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Fais
 */
public class cobroitem {

    String idpagos;
    Double iditem;
    Double pago;

    public cobroitem() {

    }

    public cobroitem(String idpagos, Double iditem, Double pago) {
        this.idpagos = idpagos;
        this.iditem = iditem;
        this.pago = pago;
    }

    public String getIdpagos() {
        return idpagos;
    }

    public void setIdpagos(String idpagos) {
        this.idpagos = idpagos;
    }

    public Double getIditem() {
        return iditem;
    }

    public void setIditem(Double iditem) {
        this.iditem = iditem;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

}
