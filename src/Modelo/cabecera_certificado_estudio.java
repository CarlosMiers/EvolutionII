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
public class cabecera_certificado_estudio {

    Double idcertificado;
    sucursal sede;
    periodo_lectivo periodo;
    semestres semestre;
    String codturno;
    carrera codcarrera;
    ficha_empleado codprofesor;
    tipo_examen tipoexamen;
    Date fechaexamen;
    int acta;
    materias materia;

    public cabecera_certificado_estudio() {

    }

    public cabecera_certificado_estudio(Double idcertificado, sucursal sede, periodo_lectivo periodo, semestres semestre, String codturno, carrera codcarrera, ficha_empleado codprofesor, tipo_examen tipoexamen, Date fechaexamen, int acta, materias materia) {
        this.idcertificado = idcertificado;
        this.sede = sede;
        this.periodo = periodo;
        this.semestre = semestre;
        this.codturno = codturno;
        this.codcarrera = codcarrera;
        this.codprofesor = codprofesor;
        this.tipoexamen = tipoexamen;
        this.fechaexamen = fechaexamen;
        this.acta = acta;
        this.materia = materia;
    }

    public Double getIdcertificado() {
        return idcertificado;
    }

    public void setIdcertificado(Double idcertificado) {
        this.idcertificado = idcertificado;
    }

    public sucursal getSede() {
        return sede;
    }

    public void setSede(sucursal sede) {
        this.sede = sede;
    }

    public periodo_lectivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(periodo_lectivo periodo) {
        this.periodo = periodo;
    }

    public semestres getSemestre() {
        return semestre;
    }

    public void setSemestre(semestres semestre) {
        this.semestre = semestre;
    }

    public String getCodturno() {
        return codturno;
    }

    public void setCodturno(String codturno) {
        this.codturno = codturno;
    }

    public carrera getCodcarrera() {
        return codcarrera;
    }

    public void setCodcarrera(carrera codcarrera) {
        this.codcarrera = codcarrera;
    }

    public ficha_empleado getCodprofesor() {
        return codprofesor;
    }

    public void setCodprofesor(ficha_empleado codprofesor) {
        this.codprofesor = codprofesor;
    }

    public tipo_examen getTipoexamen() {
        return tipoexamen;
    }

    public void setTipoexamen(tipo_examen tipoexamen) {
        this.tipoexamen = tipoexamen;
    }

    public Date getFechaexamen() {
        return fechaexamen;
    }

    public void setFechaexamen(Date fechaexamen) {
        this.fechaexamen = fechaexamen;
    }

    public int getActa() {
        return acta;
    }

    public void setActa(int acta) {
        this.acta = acta;
    }

    public materias getMateria() {
        return materia;
    }

    public void setMateria(materias materia) {
        this.materia = materia;
    }
    
    
}
