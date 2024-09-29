/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author Pc_Server
 */
public class GenerarEan13 {
    public static String ean13(javax.swing.JTextField jTextfieldS) {
        String codigo = (jTextfieldS.getText()).toUpperCase();
        Random random = new Random();
        IntStream intStream = random.ints(10, 1, 12);
        String sCadena = java.util.UUID.randomUUID().toString().substring(0, 12);
        codigo = "";
        for (int x = 0; x < sCadena.length(); x++) {
            codigo = codigo + String.valueOf(sCadena.codePointAt(x)).toString();
        }
        codigo = codigo.substring(0, 12);
        ;
        int lenCod = codigo.length();
        int sumaPares = 0;
        int sumaImpares = 0;
        int codigoVerif = 0;
        int pos = 0;
        try {
           for (int i =0 ; i <lenCod; i++) {
                pos=i+1;
                int numero = Integer.valueOf(codigo.substring(i, i+1 ));
                if (pos % 2 == 0) {
                    sumaPares = sumaPares +(numero*3);
                    System.out.println(numero*3);
                    
                } else {
                    sumaImpares = sumaImpares + (numero*1);
                }
            }
            
            codigoVerif = ((Math.round(sumaPares+sumaImpares) / 10) + 1) * 10 - (sumaPares+sumaImpares);
            if (codigoVerif == 10) {
                codigoVerif = 0;
            }
         } catch (Exception e) {
            codigoVerif = 0;
        }
        codigo = codigo + String.valueOf(codigoVerif);
        return codigo;
    }
}
