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
public class CrearDigitoRuc {
     public static void main(String args[]) {
        CalcularRuc ruc = new CalcularRuc();
        String num = "1541947";
        int base=11;
        System.out.println( ruc.CalcularDigito(num, base));
    }
}
