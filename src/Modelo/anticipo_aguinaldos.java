/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Pc_Server
 */
public class anticipo_aguinaldos {

    double idcontrol;
    Date fecha;
    ficha_empleado funcionario;
    BigDecimal importe;
    Date fechagrabado;
    int idusuario;
    int giraduria;

    public anticipo_aguinaldos() {

    }

    public anticipo_aguinaldos(double idcontrol, Date fecha, ficha_empleado funcionario, BigDecimal importe, Date fechagrabado, int idusuario, int giraduria) {
        this.idcontrol = idcontrol;
        this.fecha = fecha;
        this.funcionario = funcionario;
        this.importe = importe;
        this.fechagrabado = fechagrabado;
        this.idusuario = idusuario;
        this.giraduria = giraduria;
    }

    public double getIdcontrol() {
        return idcontrol;
    }

    public void setIdcontrol(double idcontrol) {
        this.idcontrol = idcontrol;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ficha_empleado getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(ficha_empleado funcionario) {
        this.funcionario = funcionario;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFechagrabado() {
        return fechagrabado;
    }

    public void setFechagrabado(Date fechagrabado) {
        this.fechagrabado = fechagrabado;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(int giraduria) {
        this.giraduria = giraduria;
    }



}
