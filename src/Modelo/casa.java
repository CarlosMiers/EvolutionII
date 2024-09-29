/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class casa {
   int codigo;
   String nombre;
   int localidad;
   String direccion;
   String ruc;
   String telefono;
   String fax;
   String email;
   String timbrado;
   Date vencimiento;
   int estado;
   String nombrelocalidad;
   String idcta;
   String contacto;
   double bonificacion;
   
    public casa(){
       
   } 

    public casa(int codigo, String nombre, int localidad, String direccion, String ruc, String telefono, String fax, String email, String timbrado, Date vencimiento, int estado, String nombrelocalidad, String idcta, String contacto, double bonificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.localidad = localidad;
        this.direccion = direccion;
        this.ruc = ruc;
        this.telefono = telefono;
        this.fax = fax;
        this.email = email;
        this.timbrado = timbrado;
        this.vencimiento = vencimiento;
        this.estado = estado;
        this.nombrelocalidad = nombrelocalidad;
        this.idcta = idcta;
        this.contacto = contacto;
        this.bonificacion = bonificacion;
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

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombrelocalidad() {
        return nombrelocalidad;
    }

    public void setNombrelocalidad(String nombrelocalidad) {
        this.nombrelocalidad = nombrelocalidad;
    }

    public String getIdcta() {
        return idcta;
    }

    public void setIdcta(String idcta) {
        this.idcta = idcta;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }
 
}
