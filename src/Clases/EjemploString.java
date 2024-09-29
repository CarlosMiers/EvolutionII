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
import java.util.Scanner;

public class EjemploString {

    public static void main(String[] ARGUMENTOS) {
        Scanner TECLADO = new Scanner(System.in);
        String PALABRA;
        char PRIMERA_LETRA;

        System.out.print("INGRESE UNA CADENA : ");
        PALABRA = TECLADO.nextLine();
        String cCaracter = "/"; 
        int pos = PALABRA.indexOf(cCaracter);
        String cCantidad = PALABRA.substring(pos+1,PALABRA.length());
        String cNumeroCupon = PALABRA.substring(0,pos);
        
        System.out.println("CANTIDAD "+cCantidad);
        System.out.println("NCUPON "+cNumeroCupon);
        
        System.out.println("POSICION "+pos);

    }
}
