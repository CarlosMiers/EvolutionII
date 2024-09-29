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
 * @author notebook
 */
public class titulo {

    int codigo;
    String nombre;
    int tipo;
    String resbvpasa;
    Date fechabvpasa;
    String rescnv;
    Date fechacnv;
    String nomalias;
    moneda moneda;
    instrumento instrumento;
    pais pais;
    emisor empresa;
    int base;
    String pagointeres;
    Date fecha;
    BigDecimal tasa;
    BigDecimal monto_emision;
    BigDecimal corte_minimo;
    Date fechaemision;
    Date vencimiento;
    Date fechacotizacion;
    Double preciocotizacion;
    BigDecimal nominal;
    BigDecimal comision_vendedor;
    String nombreprograma;
    String referencia;
    int rubrocontable;
    int negociable;
    int estado;
    int cupones;

    public titulo() {

    }

    public titulo(int codigo, String nombre, int tipo, String resbvpasa, Date fechabvpasa, String rescnv, Date fechacnv, String nomalias, moneda moneda, instrumento instrumento, pais pais, emisor empresa, int base, String pagointeres, Date fecha, BigDecimal tasa, BigDecimal monto_emision, BigDecimal corte_minimo, Date fechaemision, Date vencimiento, Date fechacotizacion, Double preciocotizacion, BigDecimal nominal, BigDecimal comision_vendedor, String nombreprograma, String referencia, int rubrocontable, int negociable, int estado, int cupones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.resbvpasa = resbvpasa;
        this.fechabvpasa = fechabvpasa;
        this.rescnv = rescnv;
        this.fechacnv = fechacnv;
        this.nomalias = nomalias;
        this.moneda = moneda;
        this.instrumento = instrumento;
        this.pais = pais;
        this.empresa = empresa;
        this.base = base;
        this.pagointeres = pagointeres;
        this.fecha = fecha;
        this.tasa = tasa;
        this.monto_emision = monto_emision;
        this.corte_minimo = corte_minimo;
        this.fechaemision = fechaemision;
        this.vencimiento = vencimiento;
        this.fechacotizacion = fechacotizacion;
        this.preciocotizacion = preciocotizacion;
        this.nominal = nominal;
        this.comision_vendedor = comision_vendedor;
        this.nombreprograma = nombreprograma;
        this.referencia = referencia;
        this.rubrocontable = rubrocontable;
        this.negociable = negociable;
        this.estado = estado;
        this.cupones = cupones;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getResbvpasa() {
        return resbvpasa;
    }

    public void setResbvpasa(String resbvpasa) {
        this.resbvpasa = resbvpasa;
    }

    public Date getFechabvpasa() {
        return fechabvpasa;
    }

    public void setFechabvpasa(Date fechabvpasa) {
        this.fechabvpasa = fechabvpasa;
    }

    public String getRescnv() {
        return rescnv;
    }

    public void setRescnv(String rescnv) {
        this.rescnv = rescnv;
    }

    public Date getFechacnv() {
        return fechacnv;
    }

    public void setFechacnv(Date fechacnv) {
        this.fechacnv = fechacnv;
    }

    public String getNomalias() {
        return nomalias;
    }

    public void setNomalias(String nomalias) {
        this.nomalias = nomalias;
    }

    public moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(moneda moneda) {
        this.moneda = moneda;
    }

    public instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public pais getPais() {
        return pais;
    }

    public void setPais(pais pais) {
        this.pais = pais;
    }

    public emisor getEmpresa() {
        return empresa;
    }

    public void setEmpresa(emisor empresa) {
        this.empresa = empresa;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public String getPagointeres() {
        return pagointeres;
    }

    public void setPagointeres(String pagointeres) {
        this.pagointeres = pagointeres;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public BigDecimal getMonto_emision() {
        return monto_emision;
    }

    public void setMonto_emision(BigDecimal monto_emision) {
        this.monto_emision = monto_emision;
    }

    public BigDecimal getCorte_minimo() {
        return corte_minimo;
    }

    public void setCorte_minimo(BigDecimal corte_minimo) {
        this.corte_minimo = corte_minimo;
    }

    public Date getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Date fechaemision) {
        this.fechaemision = fechaemision;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getFechacotizacion() {
        return fechacotizacion;
    }

    public void setFechacotizacion(Date fechacotizacion) {
        this.fechacotizacion = fechacotizacion;
    }

    public Double getPreciocotizacion() {
        return preciocotizacion;
    }

    public void setPreciocotizacion(Double preciocotizacion) {
        this.preciocotizacion = preciocotizacion;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public BigDecimal getComision_vendedor() {
        return comision_vendedor;
    }

    public void setComision_vendedor(BigDecimal comision_vendedor) {
        this.comision_vendedor = comision_vendedor;
    }

    public String getNombreprograma() {
        return nombreprograma;
    }

    public void setNombreprograma(String nombreprograma) {
        this.nombreprograma = nombreprograma;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getRubrocontable() {
        return rubrocontable;
    }

    public void setRubrocontable(int rubrocontable) {
        this.rubrocontable = rubrocontable;
    }

    public int getNegociable() {
        return negociable;
    }

    public void setNegociable(int negociable) {
        this.negociable = negociable;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCupones() {
        return cupones;
    }

    public void setCupones(int cupones) {
        this.cupones = cupones;
    }

    
}
