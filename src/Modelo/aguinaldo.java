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
public class aguinaldo {
    Double idnumero;
    Date fecha;
    int mes;
    int periodo;
    sucursal sucursal;
    giraduria giraduria;
    ficha_empleado funcionario;
    Double salario_bruto;
    Double aguinaldo;
    
    public aguinaldo(){
        
    }

    public aguinaldo(Double idnumero, Date fecha, int mes, int periodo, sucursal sucursal, giraduria giraduria, ficha_empleado funcionario, Double salario_bruto, Double aguinaldo) {
        this.idnumero = idnumero;
        this.fecha = fecha;
        this.mes = mes;
        this.periodo = periodo;
        this.sucursal = sucursal;
        this.giraduria = giraduria;
        this.funcionario = funcionario;
        this.salario_bruto = salario_bruto;
        this.aguinaldo = aguinaldo;
    }

    public Double getIdnumero() {
        return idnumero;
    }

    public void setIdnumero(Double idnumero) {
        this.idnumero = idnumero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public giraduria getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(giraduria giraduria) {
        this.giraduria = giraduria;
    }

    public ficha_empleado getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(ficha_empleado funcionario) {
        this.funcionario = funcionario;
    }

    public Double getSalario_bruto() {
        return salario_bruto;
    }

    public void setSalario_bruto(Double salario_bruto) {
        this.salario_bruto = salario_bruto;
    }

    public Double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(Double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }
    
}
