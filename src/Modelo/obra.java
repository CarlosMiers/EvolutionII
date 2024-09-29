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
public class obra {

    int codigo;
    String nombre;
    String contacto;
    cliente propietario;
    String direccion;
    localidad localidad;
    String telefonocontacto;
    String fax;
    int estado;

    public obra() {

    }

    public obra(int codigo, String nombre, String contacto, cliente propietario, String direccion, localidad localidad, String telefonocontacto, String fax, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.contacto = contacto;
        this.propietario = propietario;
        this.direccion = direccion;
        this.localidad = localidad;
        this.telefonocontacto = telefonocontacto;
        this.fax = fax;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(cliente propietario) {
        this.propietario = propietario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(localidad localidad) {
        this.localidad = localidad;
    }

    public String getTelefonocontacto() {
        return telefonocontacto;
    }

    public void setTelefonocontacto(String telefonocontacto) {
        this.telefonocontacto = telefonocontacto;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

   
}
