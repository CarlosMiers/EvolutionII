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
public class rubro {

    int codigo;
    String nombre;
    String idcta,idctacosto,idctamercaderia;
    String ctacompra,cuentacosto,cuentaventa;
    
    public rubro() {

    }

    public rubro(int codigo, String nombre, String idcta, String idctacosto, String idctamercaderia, String ctacompra, String cuentacosto, String cuentaventa) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idcta = idcta;
        this.idctacosto = idctacosto;
        this.idctamercaderia = idctamercaderia;
        this.ctacompra = ctacompra;
        this.cuentacosto = cuentacosto;
        this.cuentaventa = cuentaventa;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdcta() {
        return idcta;
    }

    public void setIdcta(String idcta) {
        this.idcta = idcta;
    }

    public String getIdctacosto() {
        return idctacosto;
    }

    public void setIdctacosto(String idctacosto) {
        this.idctacosto = idctacosto;
    }

    public String getIdctamercaderia() {
        return idctamercaderia;
    }

    public void setIdctamercaderia(String idctamercaderia) {
        this.idctamercaderia = idctamercaderia;
    }

    public String getCtacompra() {
        return ctacompra;
    }

    public void setCtacompra(String ctacompra) {
        this.ctacompra = ctacompra;
    }

    public String getCuentacosto() {
        return cuentacosto;
    }

    public void setCuentacosto(String cuentacosto) {
        this.cuentacosto = cuentacosto;
    }

    public String getCuentaventa() {
        return cuentaventa;
    }

    public void setCuentaventa(String cuentaventa) {
        this.cuentaventa = cuentaventa;
    }


}
