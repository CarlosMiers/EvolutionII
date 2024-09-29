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
public class credito_salario {

    double idcontrol;
    Date fecha;
    ficha_empleado funcionario;
    concepto_salario concepto;
    BigDecimal importe;
    Date fechagrabado;
    int idusuario;
    int giraduria;
    String observacion;
    Double saldoanterior,debitos,creditos,saldoactual;
    public credito_salario() {

    }

    public credito_salario(double idcontrol, Date fecha, ficha_empleado funcionario, concepto_salario concepto, BigDecimal importe, Date fechagrabado, int idusuario, int giraduria, String observacion, Double saldoanterior, Double debitos, Double creditos, Double saldoactual) {
        this.idcontrol = idcontrol;
        this.fecha = fecha;
        this.funcionario = funcionario;
        this.concepto = concepto;
        this.importe = importe;
        this.fechagrabado = fechagrabado;
        this.idusuario = idusuario;
        this.giraduria = giraduria;
        this.observacion = observacion;
        this.saldoanterior = saldoanterior;
        this.debitos = debitos;
        this.creditos = creditos;
        this.saldoactual = saldoactual;
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

    public concepto_salario getConcepto() {
        return concepto;
    }

    public void setConcepto(concepto_salario concepto) {
        this.concepto = concepto;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getSaldoanterior() {
        return saldoanterior;
    }

    public void setSaldoanterior(Double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    public Double getDebitos() {
        return debitos;
    }

    public void setDebitos(Double debitos) {
        this.debitos = debitos;
    }

    public Double getCreditos() {
        return creditos;
    }

    public void setCreditos(Double creditos) {
        this.creditos = creditos;
    }

    public Double getSaldoactual() {
        return saldoactual;
    }

    public void setSaldoactual(Double saldoactual) {
        this.saldoactual = saldoactual;
    }

    
    
}
