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
public class GenerarEan8 {
    public static String ean8(javax.swing.JTextField jTextfieldS) {
        String codigo = (jTextfieldS.getText()).toUpperCase();
        Random random = new Random();
        IntStream intStream = random.ints(10, 1, 7);
        String sCadena = java.util.UUID.randomUUID().toString().substring(0, 7);
        codigo = "";
        for (int x = 0; x < sCadena.length(); x++) {
            codigo = codigo + String.valueOf(sCadena.codePointAt(x)).toString();
        }
        codigo = codigo.substring(0, 7);
        ;
        int lenCod = codigo.length();
        int sumaPares = 0;
        int sumaImpares = 0;
        int codigoVerif = 0;
        int pos = 0;
        try {
            for (int i = 0; i < lenCod; i++) {
                int numero = Integer.parseInt(codigo.substring(i, i + 1));
                pos = i + 1;
                if (pos % 2 == 0) {
                    sumaPares = sumaPares + numero;
                } else {
                    sumaImpares = sumaImpares + numero;
                }
            }
            int resImpares = sumaImpares * 3;
            int sumaParImp = resImpares + sumaPares;
            codigoVerif = ((sumaParImp / 10) + 1) * 10 - sumaParImp;
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
