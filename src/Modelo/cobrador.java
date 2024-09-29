/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author ADMIN
 */
public class cobrador {

int codigo;
String nombre;
Date nacimiento;
int cedula;
String estadocivil;
String conyugue;
String direccion;
String telefono;
String celular;
String mail;
BigDecimal comision;
int estado;

public cobrador(){
    
}

    public cobrador(int codigo, String nombre, Date nacimiento, int cedula, String estadocivil, String conyugue, String direccion, String telefono, String celular, String mail, BigDecimal comision, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.cedula = cedula;
        this.estadocivil = estadocivil;
        this.conyugue = conyugue;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.mail = mail;
        this.comision = comision;
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

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getConyugue() {
        return conyugue;
    }

    public void setConyugue(String conyugue) {
        this.conyugue = conyugue;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


}
