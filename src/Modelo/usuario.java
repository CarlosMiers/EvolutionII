/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERVIDOR
 */
public class usuario {

    private int employee_id;
    private String last_name;
    private String first_name;
    private String password;
    private int nivel;
    private int parametros;
    private int mercaderias;
    private int compras;
    private int ventas;
    private int rrhh;
    private int importaciones;
    private int exportaciones;
    private int producciones;
    private int finanzas;
    private int contabilidad;
    private int herramientas;
    private int operaciones;
    private int asociacion;
    private int construcciones;
    private int informes;
    private int clientes;
    private int asesores;
    private int emisores;
    private int gerenciales;
    private int codasesor;
    private String nombreasesor;
    
    public usuario(){

    }

    public usuario(int employee_id, String last_name, String first_name, String password, int nivel, int parametros, int mercaderias, int compras, int ventas, int rrhh, int importaciones, int exportaciones, int producciones, int finanzas, int contabilidad, int herramientas, int operaciones, int asociacion, int construcciones, int informes, int clientes, int asesores, int emisores, int gerenciales, int codasesor, String nombreasesor) {
        this.employee_id = employee_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.password = password;
        this.nivel = nivel;
        this.parametros = parametros;
        this.mercaderias = mercaderias;
        this.compras = compras;
        this.ventas = ventas;
        this.rrhh = rrhh;
        this.importaciones = importaciones;
        this.exportaciones = exportaciones;
        this.producciones = producciones;
        this.finanzas = finanzas;
        this.contabilidad = contabilidad;
        this.herramientas = herramientas;
        this.operaciones = operaciones;
        this.asociacion = asociacion;
        this.construcciones = construcciones;
        this.informes = informes;
        this.clientes = clientes;
        this.asesores = asesores;
        this.emisores = emisores;
        this.gerenciales = gerenciales;
        this.codasesor = codasesor;
        this.nombreasesor = nombreasesor;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getParametros() {
        return parametros;
    }

    public void setParametros(int parametros) {
        this.parametros = parametros;
    }

    public int getMercaderias() {
        return mercaderias;
    }

    public void setMercaderias(int mercaderias) {
        this.mercaderias = mercaderias;
    }

    public int getCompras() {
        return compras;
    }

    public void setCompras(int compras) {
        this.compras = compras;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getRrhh() {
        return rrhh;
    }

    public void setRrhh(int rrhh) {
        this.rrhh = rrhh;
    }

    public int getImportaciones() {
        return importaciones;
    }

    public void setImportaciones(int importaciones) {
        this.importaciones = importaciones;
    }

    public int getExportaciones() {
        return exportaciones;
    }

    public void setExportaciones(int exportaciones) {
        this.exportaciones = exportaciones;
    }

    public int getProducciones() {
        return producciones;
    }

    public void setProducciones(int producciones) {
        this.producciones = producciones;
    }

    public int getFinanzas() {
        return finanzas;
    }

    public void setFinanzas(int finanzas) {
        this.finanzas = finanzas;
    }

    public int getContabilidad() {
        return contabilidad;
    }

    public void setContabilidad(int contabilidad) {
        this.contabilidad = contabilidad;
    }

    public int getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(int herramientas) {
        this.herramientas = herramientas;
    }

    public int getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(int operaciones) {
        this.operaciones = operaciones;
    }

    public int getAsociacion() {
        return asociacion;
    }

    public void setAsociacion(int asociacion) {
        this.asociacion = asociacion;
    }

    public int getConstrucciones() {
        return construcciones;
    }

    public void setConstrucciones(int construcciones) {
        this.construcciones = construcciones;
    }

    public int getInformes() {
        return informes;
    }

    public void setInformes(int informes) {
        this.informes = informes;
    }

    public int getClientes() {
        return clientes;
    }

    public void setClientes(int clientes) {
        this.clientes = clientes;
    }

    public int getAsesores() {
        return asesores;
    }

    public void setAsesores(int asesores) {
        this.asesores = asesores;
    }

    public int getEmisores() {
        return emisores;
    }

    public void setEmisores(int emisores) {
        this.emisores = emisores;
    }

    public int getGerenciales() {
        return gerenciales;
    }

    public void setGerenciales(int gerenciales) {
        this.gerenciales = gerenciales;
    }

    public int getCodasesor() {
        return codasesor;
    }

    public void setCodasesor(int codasesor) {
        this.codasesor = codasesor;
    }

    public String getNombreasesor() {
        return nombreasesor;
    }

    public void setNombreasesor(String nombreasesor) {
        this.nombreasesor = nombreasesor;
    }

    
    
}
