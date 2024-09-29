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
public class detalle_descuento_documento {

    BigDecimal nrodocumento;
    Date emision;
    Date vencimiento;
    Date compra;
   
    public detalle_descuento_documento() {

    }

    public detalle_descuento_documento(BigDecimal nrodocumento, Date emision, Date vencimiento, Date compra) {
        this.nrodocumento = nrodocumento;
        this.emision = emision;
        this.vencimiento = vencimiento;
        this.compra = compra;
    }

    public BigDecimal getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(BigDecimal nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getCompra() {
        return compra;
    }

    public void setCompra(Date compra) {
        this.compra = compra;
    }

    
}
