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
 * @author SERVIDOR
 */
public class ventalote {

double idventa;
ar_prop fraccion;
String manzana;
String lote;
cliente cliente;
vendedor vendedor;
Date fecha;
Date primeracuota;
int cuotas;
int cu2;
BigDecimal imp2;
int cu3;
BigDecimal imp3;
int cu4;
BigDecimal imp4;
int cu5;
BigDecimal imp5;
int cu6;
BigDecimal imp6;
int cu7;
BigDecimal imp7;
int cu8;
BigDecimal imp8;
int cu9;
BigDecimal imp9;
Double comadmin;
Double comvende;
BigDecimal totalventa;

public ventalote(){
    
}

    public ventalote(double idventa, ar_prop fraccion, String manzana, String lote, cliente cliente, vendedor vendedor, Date fecha, Date primeracuota, int cuotas, int cu2, BigDecimal imp2, int cu3, BigDecimal imp3, int cu4, BigDecimal imp4, int cu5, BigDecimal imp5, int cu6, BigDecimal imp6, int cu7, BigDecimal imp7, int cu8, BigDecimal imp8, int cu9, BigDecimal imp9, Double comadmin, Double comvende, BigDecimal totalventa) {
        this.idventa = idventa;
        this.fraccion = fraccion;
        this.manzana = manzana;
        this.lote = lote;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.primeracuota = primeracuota;
        this.cuotas = cuotas;
        this.cu2 = cu2;
        this.imp2 = imp2;
        this.cu3 = cu3;
        this.imp3 = imp3;
        this.cu4 = cu4;
        this.imp4 = imp4;
        this.cu5 = cu5;
        this.imp5 = imp5;
        this.cu6 = cu6;
        this.imp6 = imp6;
        this.cu7 = cu7;
        this.imp7 = imp7;
        this.cu8 = cu8;
        this.imp8 = imp8;
        this.cu9 = cu9;
        this.imp9 = imp9;
        this.comadmin = comadmin;
        this.comvende = comvende;
        this.totalventa = totalventa;
    }

    public double getIdventa() {
        return idventa;
    }

    public void setIdventa(double idventa) {
        this.idventa = idventa;
    }

    public ar_prop getFraccion() {
        return fraccion;
    }

    public void setFraccion(ar_prop fraccion) {
        this.fraccion = fraccion;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getPrimeracuota() {
        return primeracuota;
    }

    public void setPrimeracuota(Date primeracuota) {
        this.primeracuota = primeracuota;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getCu2() {
        return cu2;
    }

    public void setCu2(int cu2) {
        this.cu2 = cu2;
    }

    public BigDecimal getImp2() {
        return imp2;
    }

    public void setImp2(BigDecimal imp2) {
        this.imp2 = imp2;
    }

    public int getCu3() {
        return cu3;
    }

    public void setCu3(int cu3) {
        this.cu3 = cu3;
    }

    public BigDecimal getImp3() {
        return imp3;
    }

    public void setImp3(BigDecimal imp3) {
        this.imp3 = imp3;
    }

    public int getCu4() {
        return cu4;
    }

    public void setCu4(int cu4) {
        this.cu4 = cu4;
    }

    public BigDecimal getImp4() {
        return imp4;
    }

    public void setImp4(BigDecimal imp4) {
        this.imp4 = imp4;
    }

    public int getCu5() {
        return cu5;
    }

    public void setCu5(int cu5) {
        this.cu5 = cu5;
    }

    public BigDecimal getImp5() {
        return imp5;
    }

    public void setImp5(BigDecimal imp5) {
        this.imp5 = imp5;
    }

    public int getCu6() {
        return cu6;
    }

    public void setCu6(int cu6) {
        this.cu6 = cu6;
    }

    public BigDecimal getImp6() {
        return imp6;
    }

    public void setImp6(BigDecimal imp6) {
        this.imp6 = imp6;
    }

    public int getCu7() {
        return cu7;
    }

    public void setCu7(int cu7) {
        this.cu7 = cu7;
    }

    public BigDecimal getImp7() {
        return imp7;
    }

    public void setImp7(BigDecimal imp7) {
        this.imp7 = imp7;
    }

    public int getCu8() {
        return cu8;
    }

    public void setCu8(int cu8) {
        this.cu8 = cu8;
    }

    public BigDecimal getImp8() {
        return imp8;
    }

    public void setImp8(BigDecimal imp8) {
        this.imp8 = imp8;
    }

    public int getCu9() {
        return cu9;
    }

    public void setCu9(int cu9) {
        this.cu9 = cu9;
    }

    public BigDecimal getImp9() {
        return imp9;
    }

    public void setImp9(BigDecimal imp9) {
        this.imp9 = imp9;
    }

    public Double getComadmin() {
        return comadmin;
    }

    public void setComadmin(Double comadmin) {
        this.comadmin = comadmin;
    }

    public Double getComvende() {
        return comvende;
    }

    public void setComvende(Double comvende) {
        this.comvende = comvende;
    }

    public BigDecimal getTotalventa() {
        return totalventa;
    }

    public void setTotalventa(BigDecimal totalventa) {
        this.totalventa = totalventa;
    }


}
