/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Pc_Server
 */
public class ventas_anuladas {
    
    String formatofactura;
    
    public ventas_anuladas(){
        
    }

    public ventas_anuladas(String formatofactura) {
        this.formatofactura = formatofactura;
    }

    public String getFormatofactura() {
        return formatofactura;
    }

    public void setFormatofactura(String formatofactura) {
        this.formatofactura = formatofactura;
    }
    
    
}
