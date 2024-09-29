 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;


/**
 *
 * @author hp
 */
public class morasxcliente {

    private String documento;
    private String fecha;
    private String vencimiento;
    private String fechapago;
    private String concepto;
    private String cuota;
    private String numerocuota;
    private String amortizacion;
    private String interesordinario;
    private String interesmoratorio;
    private String gastosxcobros;
    private String interespunitorio;
    private String saldocuota;
    private String diasmora;
    //                           0               1              2                  3               4                 5             6                     7                           8                        9                10                    11                      12                   13

    public morasxcliente(String documento, String fecha, String vencimiento, String fechapago, String concepto, String cuota, String numerocuota, String amortizacion,String interesordinario, String interesmoratorio, String gastosxcobros, String interespunitorio, String saldocuota, String diasmora){
        this.documento = documento;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.fechapago = fechapago;
        this.concepto = concepto;
        this.cuota = cuota;
        this.numerocuota = numerocuota;
        this.amortizacion = amortizacion;
        this.interesordinario = interesordinario;
        this.interesmoratorio = interesmoratorio;
        this.gastosxcobros = gastosxcobros;
        this.interespunitorio = interespunitorio;
        this.saldocuota = saldocuota;
        this.diasmora = diasmora;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getFechapago() {
        return fechapago;
    }

    public void setFechapago(String fechapago) {
        this.fechapago = fechapago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(String numerocuota) {
        this.numerocuota = numerocuota;
    }

    public String getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(String amortizacion) {
        this.amortizacion = amortizacion;
    }

    public String getInteresordinario() {
        return interesordinario;
    }

    public void setInteresordinario(String interesordinario) {
        this.interesordinario = interesordinario;
    }

    public String getInteresmoratorio() {
        return interesmoratorio;
    }

    public void setInteresmoratorio(String interesmoratorio) {
        this.interesmoratorio = interesmoratorio;
    }

    public String getGastosxcobros() {
        return gastosxcobros;
    }

    public void setGastosxcobros(String gastosxcobros) {
        this.gastosxcobros = gastosxcobros;
    }

    public String getInterespunitorio() {
        return interespunitorio;
    }

    public void setInterespunitorio(String interespunitorio) {
        this.interespunitorio = interespunitorio;
    }

    public String getSaldocuota() {
        return saldocuota;
    }

    public void setSaldocuota(String saldocuota) {
        this.saldocuota = saldocuota;
    }

    public String getDiasmora() {
        return diasmora;
    }

    public void setDiasmora(String diasmora) {
        this.diasmora = diasmora;
    }
    
    
    
}
