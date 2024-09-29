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
public class edificio_sede {

    int codigo;
    String nombre;
    sucursal idsede;
    String direccion;
    String telefono;
    int nropisos;
    int nroaulas;
    int estado;

    public edificio_sede() {

    }

    public edificio_sede(int codigo, String nombre, sucursal idsede, String direccion, String telefono, int nropisos, int nroaulas, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idsede = idsede;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nropisos = nropisos;
        this.nroaulas = nroaulas;
        this.estado = estado;
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

    public sucursal getIdsede() {
        return idsede;
    }

    public void setIdsede(sucursal idsede) {
        this.idsede = idsede;
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

    public int getNropisos() {
        return nropisos;
    }

    public void setNropisos(int nropisos) {
        this.nropisos = nropisos;
    }

    public int getNroaulas() {
        return nroaulas;
    }

    public void setNroaulas(int nroaulas) {
        this.nroaulas = nroaulas;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
