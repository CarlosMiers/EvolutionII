/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class hijos {

    Double iditem;
    ficha_empleado id_empleado;
    String nombrehijo;
    Date fecha_nacimiento;
    int edad;
    int sexo;
    int bonificacion;

    public hijos() {

    }

    public hijos(Double iditem, ficha_empleado id_empleado, String nombrehijo, Date fecha_nacimiento, int edad, int sexo, int bonificacion) {
        this.iditem = iditem;
        this.id_empleado = id_empleado;
        this.nombrehijo = nombrehijo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.edad = edad;
        this.sexo = sexo;
        this.bonificacion = bonificacion;
    }

    public Double getIditem() {
        return iditem;
    }

    public void setIditem(Double iditem) {
        this.iditem = iditem;
    }

    public ficha_empleado getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(ficha_empleado id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombrehijo() {
        return nombrehijo;
    }

    public void setNombrehijo(String nombrehijo) {
        this.nombrehijo = nombrehijo;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }


}
