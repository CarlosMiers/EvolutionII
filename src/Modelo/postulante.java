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
public class postulante {

    int codigo;
    String nombres;
    String apellidos;
    int cedula;
    concurso concurso;
    vacancias vacancia;
    Date fechanacimiento;
    String direccion;
    String telefono;
    String estado_civil;
    String conyugue;
    int sexo;
    int estado;
    String objetivos_laborales;
    String experiencia_laboral;
    String preparacion_academica;

    public postulante() {

    }

    public postulante(int codigo, String nombres, String apellidos, int cedula, concurso concurso, vacancias vacancia, Date fechanacimiento, String direccion, String telefono, String estado_civil, String conyugue, int sexo, int estado, String objetivos_laborales, String experiencia_laboral, String preparacion_academica) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.concurso = concurso;
        this.vacancia = vacancia;
        this.fechanacimiento = fechanacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado_civil = estado_civil;
        this.conyugue = conyugue;
        this.sexo = sexo;
        this.estado = estado;
        this.objetivos_laborales = objetivos_laborales;
        this.experiencia_laboral = experiencia_laboral;
        this.preparacion_academica = preparacion_academica;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(concurso concurso) {
        this.concurso = concurso;
    }

    public vacancias getVacancia() {
        return vacancia;
    }

    public void setVacancia(vacancias vacancia) {
        this.vacancia = vacancia;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
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

    public String getEstado_civil() {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getConyugue() {
        return conyugue;
    }

    public void setConyugue(String conyugue) {
        this.conyugue = conyugue;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getObjetivos_laborales() {
        return objetivos_laborales;
    }

    public void setObjetivos_laborales(String objetivos_laborales) {
        this.objetivos_laborales = objetivos_laborales;
    }

    public String getExperiencia_laboral() {
        return experiencia_laboral;
    }

    public void setExperiencia_laboral(String experiencia_laboral) {
        this.experiencia_laboral = experiencia_laboral;
    }

    public String getPreparacion_academica() {
        return preparacion_academica;
    }

    public void setPreparacion_academica(String preparacion_academica) {
        this.preparacion_academica = preparacion_academica;
    }



}
