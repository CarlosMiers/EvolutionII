/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Pc_Server
 */
public class RedondearImporte {
    public double Redondear(double p_importe){
        double base=1000;
        double nRedondeo= (double) (Math.ceil((double) p_importe / base) * base);      
        return nRedondeo;
    }
}
