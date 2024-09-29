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
public class RedondearNumero {
    public static void main(String[] args) {
        int numero = 99285;
        int base = 1000; // Esto define a qué múltiplo quieres redondear
        int redondeado = redondearHaciaArriba(numero, base);
        System.out.println("Número original: " + numero);
        System.out.println("Número redondeado: " + redondeado);
    }

    public static int redondearHaciaArriba(int numero, int base) {
        return (int) (Math.ceil((double) numero / base) * base);
    }
}