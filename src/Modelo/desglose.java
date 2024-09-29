/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Pc_Server
 */
public class desglose {

    double iditem;
    String iddesglose;
    String idcupones;
    int cantidad;
    double importe;
    String serie;
    String nro_titulo;
    int desde_acci;
    int hasta_acci;
    int cod_compra;
    int cod_vende;
    int cantidadcupones;
    Date primervencimiento;
    int tipoaccion;
    
    public desglose(){
        
    }

    public desglose(double iditem, String iddesglose, String idcupones, int cantidad, double importe, String serie, String nro_titulo, int desde_acci, int hasta_acci, int cod_compra, int cod_vende, int cantidadcupones, Date primervencimiento, int tipoaccion) {
        this.iditem = iditem;
        this.iddesglose = iddesglose;
        this.idcupones = idcupones;
        this.cantidad = cantidad;
        this.importe = importe;
        this.serie = serie;
        this.nro_titulo = nro_titulo;
        this.desde_acci = desde_acci;
        this.hasta_acci = hasta_acci;
        this.cod_compra = cod_compra;
        this.cod_vende = cod_vende;
        this.cantidadcupones = cantidadcupones;
        this.primervencimiento = primervencimiento;
        this.tipoaccion = tipoaccion;
    }

    public double getIditem() {
        return iditem;
    }

    public void setIditem(double iditem) {
        this.iditem = iditem;
    }

    public String getIddesglose() {
        return iddesglose;
    }

    public void setIddesglose(String iddesglose) {
        this.iddesglose = iddesglose;
    }

    public String getIdcupones() {
        return idcupones;
    }

    public void setIdcupones(String idcupones) {
        this.idcupones = idcupones;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNro_titulo() {
        return nro_titulo;
    }

    public void setNro_titulo(String nro_titulo) {
        this.nro_titulo = nro_titulo;
    }

    public int getDesde_acci() {
        return desde_acci;
    }

    public void setDesde_acci(int desde_acci) {
        this.desde_acci = desde_acci;
    }

    public int getHasta_acci() {
        return hasta_acci;
    }

    public void setHasta_acci(int hasta_acci) {
        this.hasta_acci = hasta_acci;
    }

    public int getCod_compra() {
        return cod_compra;
    }

    public void setCod_compra(int cod_compra) {
        this.cod_compra = cod_compra;
    }

    public int getCod_vende() {
        return cod_vende;
    }

    public void setCod_vende(int cod_vende) {
        this.cod_vende = cod_vende;
    }

    public int getCantidadcupones() {
        return cantidadcupones;
    }

    public void setCantidadcupones(int cantidadcupones) {
        this.cantidadcupones = cantidadcupones;
    }

    public Date getPrimervencimiento() {
        return primervencimiento;
    }

    public void setPrimervencimiento(Date primervencimiento) {
        this.primervencimiento = primervencimiento;
    }

    public int getTipoaccion() {
        return tipoaccion;
    }

    public void setTipoaccion(int tipoaccion) {
        this.tipoaccion = tipoaccion;
    }
    
    
}
