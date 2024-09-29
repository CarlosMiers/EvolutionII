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
public class EtiquetaMultiple {

    String codprod;
    String nombreproducto;
    String cPrecio;
    
    public void EtiquetaMultiple() {

    }

    public EtiquetaMultiple(String codprod, String nombreproducto, String cPrecio) {
        this.codprod = codprod;
        this.nombreproducto = nombreproducto;
        this.cPrecio = cPrecio;
    }

    public String getCodprod() {
        return codprod;
    }

    public void setCodprod(String codprod) {
        this.codprod = codprod;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getcPrecio() {
        return cPrecio;
    }

    public void setcPrecio(String cPrecio) {
        this.cPrecio = cPrecio;
    }

    
}
