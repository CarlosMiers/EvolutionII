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

public class formatoFecha {

    public static void main(String[] ARGUMENTOS) {
        Scanner TECLADO = new Scanner(System.in);
        String PALABRA;
        System.out.print("INGRESE UNA CADENA : ");
        PALABRA = TECLADO.nextLine();
        int n = PALABRA.length();
        System.out.println("Digitos " + n);
        String cCadena1 = PALABRA.substring(0, 2);
        System.out.println("Día " + cCadena1);
        String cCadena2 = PALABRA.substring(2,4);
        System.out.println("Mes " + cCadena2);
        String cCadena3 = PALABRA.substring(4, 8);
        System.out.println("Año " + cCadena3);
        System.out.println("Fecha " + cCadena1+"/"+ cCadena2+"/"+ cCadena3);
    }
}
