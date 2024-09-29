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
public class cupones {

    String idprecierre;
    String idcupon;
    double numeroprecierre;
    int ncantidad;
    int ncupon;
    Date diaemision;
    Date fechainicial;
    int plazocupon;
    Date fechavencimiento;
    double valorfuturo;
    int dias;
    double valoractual;
    int estado;
    Date fechaventacupon;
    int custodia;
    int base;
    double interes;

    public cupones() {

    }

    public cupones(String idprecierre, String idcupon, double numeroprecierre, int ncantidad, int ncupon, Date diaemision, Date fechainicial, int plazocupon, Date fechavencimiento, double valorfuturo, int dias, double valoractual, int estado, Date fechaventacupon, int custodia, int base, double interes) {
        this.idprecierre = idprecierre;
        this.idcupon = idcupon;
        this.numeroprecierre = numeroprecierre;
        this.ncantidad = ncantidad;
        this.ncupon = ncupon;
        this.diaemision = diaemision;
        this.fechainicial = fechainicial;
        this.plazocupon = plazocupon;
        this.fechavencimiento = fechavencimiento;
        this.valorfuturo = valorfuturo;
        this.dias = dias;
        this.valoractual = valoractual;
        this.estado = estado;
        this.fechaventacupon = fechaventacupon;
        this.custodia = custodia;
        this.base = base;
        this.interes = interes;
    }

    public String getIdprecierre() {
        return idprecierre;
    }

    public void setIdprecierre(String idprecierre) {
        this.idprecierre = idprecierre;
    }

    public String getIdcupon() {
        return idcupon;
    }

    public void setIdcupon(String idcupon) {
        this.idcupon = idcupon;
    }

    public double getNumeroprecierre() {
        return numeroprecierre;
    }

    public void setNumeroprecierre(double numeroprecierre) {
        this.numeroprecierre = numeroprecierre;
    }

    public int getNcantidad() {
        return ncantidad;
    }

    public void setNcantidad(int ncantidad) {
        this.ncantidad = ncantidad;
    }

    public int getNcupon() {
        return ncupon;
    }

    public void setNcupon(int ncupon) {
        this.ncupon = ncupon;
    }

    public Date getDiaemision() {
        return diaemision;
    }

    public void setDiaemision(Date diaemision) {
        this.diaemision = diaemision;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public int getPlazocupon() {
        return plazocupon;
    }

    public void setPlazocupon(int plazocupon) {
        this.plazocupon = plazocupon;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public double getValorfuturo() {
        return valorfuturo;
    }

    public void setValorfuturo(double valorfuturo) {
        this.valorfuturo = valorfuturo;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public double getValoractual() {
        return valoractual;
    }

    public void setValoractual(double valoractual) {
        this.valoractual = valoractual;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaventacupon() {
        return fechaventacupon;
    }

    public void setFechaventacupon(Date fechaventacupon) {
        this.fechaventacupon = fechaventacupon;
    }

    public int getCustodia() {
        return custodia;
    }

    public void setCustodia(int custodia) {
        this.custodia = custodia;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }


}
