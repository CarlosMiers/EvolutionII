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
 * @author Usuario
 */
public class SalarioLiquidacion {

    Date fecha;
    int mes, periodo, idDepartamento, idSeccion, idGiraduria, funcionario;
    sucursal sucursal;
    String tipo, departamento, seccion, giraduria;
    String ci, funcionario_nombre;
    BigDecimal salariobase, adicionalformacion, creditosvarios;
    BigDecimal vacaciones, anticipos,horasextra,bonificacionH, ipsaporte;
    BigDecimal descuentosvarios, llegadastardias, ausencias, embargos,salariobruto,salarioneto;

    public SalarioLiquidacion(){
        
    }

    public SalarioLiquidacion(Date fecha, int mes, int periodo, int idDepartamento, int idSeccion, int idGiraduria, int funcionario, sucursal sucursal, String tipo, String departamento, String seccion, String giraduria, String ci, String funcionario_nombre, BigDecimal salariobase, BigDecimal adicionalformacion, BigDecimal creditosvarios, BigDecimal vacaciones, BigDecimal anticipos, BigDecimal horasextra, BigDecimal bonificacionH, BigDecimal ipsaporte, BigDecimal descuentosvarios, BigDecimal llegadastardias, BigDecimal ausencias, BigDecimal embargos, BigDecimal salariobruto, BigDecimal salarioneto) {
        this.fecha = fecha;
        this.mes = mes;
        this.periodo = periodo;
        this.idDepartamento = idDepartamento;
        this.idSeccion = idSeccion;
        this.idGiraduria = idGiraduria;
        this.funcionario = funcionario;
        this.sucursal = sucursal;
        this.tipo = tipo;
        this.departamento = departamento;
        this.seccion = seccion;
        this.giraduria = giraduria;
        this.ci = ci;
        this.funcionario_nombre = funcionario_nombre;
        this.salariobase = salariobase;
        this.adicionalformacion = adicionalformacion;
        this.creditosvarios = creditosvarios;
        this.vacaciones = vacaciones;
        this.anticipos = anticipos;
        this.horasextra = horasextra;
        this.bonificacionH = bonificacionH;
        this.ipsaporte = ipsaporte;
        this.descuentosvarios = descuentosvarios;
        this.llegadastardias = llegadastardias;
        this.ausencias = ausencias;
        this.embargos = embargos;
        this.salariobruto = salariobruto;
        this.salarioneto = salarioneto;
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

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public int getIdGiraduria() {
        return idGiraduria;
    }

    public void setIdGiraduria(int idGiraduria) {
        this.idGiraduria = idGiraduria;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }

    public sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getGiraduria() {
        return giraduria;
    }

    public void setGiraduria(String giraduria) {
        this.giraduria = giraduria;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFuncionario_nombre() {
        return funcionario_nombre;
    }

    public void setFuncionario_nombre(String funcionario_nombre) {
        this.funcionario_nombre = funcionario_nombre;
    }

    public BigDecimal getSalariobase() {
        return salariobase;
    }

    public void setSalariobase(BigDecimal salariobase) {
        this.salariobase = salariobase;
    }

    public BigDecimal getAdicionalformacion() {
        return adicionalformacion;
    }

    public void setAdicionalformacion(BigDecimal adicionalformacion) {
        this.adicionalformacion = adicionalformacion;
    }

    public BigDecimal getCreditosvarios() {
        return creditosvarios;
    }

    public void setCreditosvarios(BigDecimal creditosvarios) {
        this.creditosvarios = creditosvarios;
    }

    public BigDecimal getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(BigDecimal vacaciones) {
        this.vacaciones = vacaciones;
    }

    public BigDecimal getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(BigDecimal anticipos) {
        this.anticipos = anticipos;
    }

    public BigDecimal getHorasextra() {
        return horasextra;
    }

    public void setHorasextra(BigDecimal horasextra) {
        this.horasextra = horasextra;
    }

    public BigDecimal getBonificacionH() {
        return bonificacionH;
    }

    public void setBonificacionH(BigDecimal bonificacionH) {
        this.bonificacionH = bonificacionH;
    }

    public BigDecimal getIpsaporte() {
        return ipsaporte;
    }

    public void setIpsaporte(BigDecimal ipsaporte) {
        this.ipsaporte = ipsaporte;
    }

    public BigDecimal getDescuentosvarios() {
        return descuentosvarios;
    }

    public void setDescuentosvarios(BigDecimal descuentosvarios) {
        this.descuentosvarios = descuentosvarios;
    }

    public BigDecimal getLlegadastardias() {
        return llegadastardias;
    }

    public void setLlegadastardias(BigDecimal llegadastardias) {
        this.llegadastardias = llegadastardias;
    }

    public BigDecimal getAusencias() {
        return ausencias;
    }

    public void setAusencias(BigDecimal ausencias) {
        this.ausencias = ausencias;
    }

    public BigDecimal getEmbargos() {
        return embargos;
    }

    public void setEmbargos(BigDecimal embargos) {
        this.embargos = embargos;
    }

    public BigDecimal getSalariobruto() {
        return salariobruto;
    }

    public void setSalariobruto(BigDecimal salariobruto) {
        this.salariobruto = salariobruto;
    }

    public BigDecimal getSalarioneto() {
        return salarioneto;
    }

    public void setSalarioneto(BigDecimal salarioneto) {
        this.salarioneto = salarioneto;
    }


    
}
