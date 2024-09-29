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
public class historico_custodia {

    double id;
    String idmovimiento;
    int idnumero;
    Date fechaproceso;
    int origencustodia;
    int destinocustodia;
    String nombreorigen;
    String nombredestino;
    String pc;
    String hora;

    public historico_custodia() {

    }

    public historico_custodia(double id, String idmovimiento, int idnumero, Date fechaproceso, int origencustodia, int destinocustodia, String nombreorigen, String nombredestino, String pc, String hora) {
        this.id = id;
        this.idmovimiento = idmovimiento;
        this.idnumero = idnumero;
        this.fechaproceso = fechaproceso;
        this.origencustodia = origencustodia;
        this.destinocustodia = destinocustodia;
        this.nombreorigen = nombreorigen;
        this.nombredestino = nombredestino;
        this.pc = pc;
        this.hora = hora;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(String idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public int getIdnumero() {
        return idnumero;
    }

    public void setIdnumero(int idnumero) {
        this.idnumero = idnumero;
    }

    public Date getFechaproceso() {
        return fechaproceso;
    }

    public void setFechaproceso(Date fechaproceso) {
        this.fechaproceso = fechaproceso;
    }

    public int getOrigencustodia() {
        return origencustodia;
    }

    public void setOrigencustodia(int origencustodia) {
        this.origencustodia = origencustodia;
    }

    public int getDestinocustodia() {
        return destinocustodia;
    }

    public void setDestinocustodia(int destinocustodia) {
        this.destinocustodia = destinocustodia;
    }

    public String getNombreorigen() {
        return nombreorigen;
    }

    public void setNombreorigen(String nombreorigen) {
        this.nombreorigen = nombreorigen;
    }

    public String getNombredestino() {
        return nombredestino;
    }

    public void setNombredestino(String nombredestino) {
        this.nombredestino = nombredestino;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


}
