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
public class loteamiento {

    int codigo;
    String nombre;
    String hectarea;
    int manzanas;
    int lotesdisponibles;
    String metroxlotes;
    localidad localidad;
    propietario propietario;
    String observaciones;

    public loteamiento() {

    }

    public loteamiento(int codigo, String nombre, String hectarea, int manzanas, int lotesdisponibles, String metroxlotes, localidad localidad, propietario propietario, String observaciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.hectarea = hectarea;
        this.manzanas = manzanas;
        this.lotesdisponibles = lotesdisponibles;
        this.metroxlotes = metroxlotes;
        this.localidad = localidad;
        this.propietario = propietario;
        this.observaciones = observaciones;
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

    public String getHectarea() {
        return hectarea;
    }

    public void setHectarea(String hectarea) {
        this.hectarea = hectarea;
    }

    public int getManzanas() {
        return manzanas;
    }

    public void setManzanas(int manzanas) {
        this.manzanas = manzanas;
    }

    public int getLotesdisponibles() {
        return lotesdisponibles;
    }

    public void setLotesdisponibles(int lotesdisponibles) {
        this.lotesdisponibles = lotesdisponibles;
    }

    public String getMetroxlotes() {
        return metroxlotes;
    }

    public void setMetroxlotes(String metroxlotes) {
        this.metroxlotes = metroxlotes;
    }

    public localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(localidad localidad) {
        this.localidad = localidad;
    }

    public propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(propietario propietario) {
        this.propietario = propietario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
