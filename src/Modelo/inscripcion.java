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
public class inscripcion {
    String idreferencia;
    double contrato;
    periodo_lectivo periodo;
    Date fecha;
    sucursal sede;
    cliente codalumno;
    carrera carrera;
    pensum pensum;
    int curso;
    String turno;
    String codcuota;
    Date fechabaja;
    String codmatricula;
    double importecuota;
    double importematricula;
    usuario codusuario;
    String nombrecuota;
    String nombrematricula;
    Date primeracuota;
    int semestre;
    int calendario;

    public inscripcion(){
        
    }

    public inscripcion(String idreferencia, double contrato, periodo_lectivo periodo, Date fecha, sucursal sede, cliente codalumno, carrera carrera, pensum pensum, int curso, String turno, String codcuota, Date fechabaja, String codmatricula, double importecuota, double importematricula, usuario codusuario, String nombrecuota, String nombrematricula, Date primeracuota, int semestre, int calendario) {
        this.idreferencia = idreferencia;
        this.contrato = contrato;
        this.periodo = periodo;
        this.fecha = fecha;
        this.sede = sede;
        this.codalumno = codalumno;
        this.carrera = carrera;
        this.pensum = pensum;
        this.curso = curso;
        this.turno = turno;
        this.codcuota = codcuota;
        this.fechabaja = fechabaja;
        this.codmatricula = codmatricula;
        this.importecuota = importecuota;
        this.importematricula = importematricula;
        this.codusuario = codusuario;
        this.nombrecuota = nombrecuota;
        this.nombrematricula = nombrematricula;
        this.primeracuota = primeracuota;
        this.semestre = semestre;
        this.calendario = calendario;
    }

    public String getIdreferencia() {
        return idreferencia;
    }

    public void setIdreferencia(String idreferencia) {
        this.idreferencia = idreferencia;
    }

    public double getContrato() {
        return contrato;
    }

    public void setContrato(double contrato) {
        this.contrato = contrato;
    }

    public periodo_lectivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(periodo_lectivo periodo) {
        this.periodo = periodo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public sucursal getSede() {
        return sede;
    }

    public void setSede(sucursal sede) {
        this.sede = sede;
    }

    public cliente getCodalumno() {
        return codalumno;
    }

    public void setCodalumno(cliente codalumno) {
        this.codalumno = codalumno;
    }

    public carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(carrera carrera) {
        this.carrera = carrera;
    }

    public pensum getPensum() {
        return pensum;
    }

    public void setPensum(pensum pensum) {
        this.pensum = pensum;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCodcuota() {
        return codcuota;
    }

    public void setCodcuota(String codcuota) {
        this.codcuota = codcuota;
    }

    public Date getFechabaja() {
        return fechabaja;
    }

    public void setFechabaja(Date fechabaja) {
        this.fechabaja = fechabaja;
    }

    public String getCodmatricula() {
        return codmatricula;
    }

    public void setCodmatricula(String codmatricula) {
        this.codmatricula = codmatricula;
    }

    public double getImportecuota() {
        return importecuota;
    }

    public void setImportecuota(double importecuota) {
        this.importecuota = importecuota;
    }

    public double getImportematricula() {
        return importematricula;
    }

    public void setImportematricula(double importematricula) {
        this.importematricula = importematricula;
    }

    public usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(usuario codusuario) {
        this.codusuario = codusuario;
    }

    public String getNombrecuota() {
        return nombrecuota;
    }

    public void setNombrecuota(String nombrecuota) {
        this.nombrecuota = nombrecuota;
    }

    public String getNombrematricula() {
        return nombrematricula;
    }

    public void setNombrematricula(String nombrematricula) {
        this.nombrematricula = nombrematricula;
    }

    public Date getPrimeracuota() {
        return primeracuota;
    }

    public void setPrimeracuota(Date primeracuota) {
        this.primeracuota = primeracuota;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getCalendario() {
        return calendario;
    }

    public void setCalendario(int calendario) {
        this.calendario = calendario;
    }


    
}
