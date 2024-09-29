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
public class desvinculacion {

    Double numero;
    Date fecha;
    Date inicio;
    Date fin;
    Double antiguedad;
    ficha_empleado funcionario;
    int tipo_desvinculacion;
    int dias_preaviso;
    Double salario_ordinario;
    Double vacaciones;
    Double aguinaldo;
    Double preaviso;
    Double indemnizacion;
    Double ips;
    int idsucursal;

    public desvinculacion() {

    }

    public desvinculacion(Double numero, Date fecha, Date inicio, Date fin, Double antiguedad, ficha_empleado funcionario, int tipo_desvinculacion, int dias_preaviso, Double salario_ordinario, Double vacaciones, Double aguinaldo, Double preaviso, Double indemnizacion, Double ips, int idsucursal) {
        this.numero = numero;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.antiguedad = antiguedad;
        this.funcionario = funcionario;
        this.tipo_desvinculacion = tipo_desvinculacion;
        this.dias_preaviso = dias_preaviso;
        this.salario_ordinario = salario_ordinario;
        this.vacaciones = vacaciones;
        this.aguinaldo = aguinaldo;
        this.preaviso = preaviso;
        this.indemnizacion = indemnizacion;
        this.ips = ips;
        this.idsucursal = idsucursal;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Double getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Double antiguedad) {
        this.antiguedad = antiguedad;
    }

    public ficha_empleado getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(ficha_empleado funcionario) {
        this.funcionario = funcionario;
    }

    public int getTipo_desvinculacion() {
        return tipo_desvinculacion;
    }

    public void setTipo_desvinculacion(int tipo_desvinculacion) {
        this.tipo_desvinculacion = tipo_desvinculacion;
    }

    public int getDias_preaviso() {
        return dias_preaviso;
    }

    public void setDias_preaviso(int dias_preaviso) {
        this.dias_preaviso = dias_preaviso;
    }

    public Double getSalario_ordinario() {
        return salario_ordinario;
    }

    public void setSalario_ordinario(Double salario_ordinario) {
        this.salario_ordinario = salario_ordinario;
    }

    public Double getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(Double vacaciones) {
        this.vacaciones = vacaciones;
    }

    public Double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(Double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public Double getPreaviso() {
        return preaviso;
    }

    public void setPreaviso(Double preaviso) {
        this.preaviso = preaviso;
    }

    public Double getIndemnizacion() {
        return indemnizacion;
    }

    public void setIndemnizacion(Double indemnizacion) {
        this.indemnizacion = indemnizacion;
    }

    public Double getIps() {
        return ips;
    }

    public void setIps(Double ips) {
        this.ips = ips;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }


}
