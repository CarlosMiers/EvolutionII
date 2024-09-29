/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author hp
 */
public class ConvertirNumero {

    public static void main(String args[]) {

//        numero_a_letras numero = new numero_a_letras();
        NumeroLetra numero = new NumeroLetra();

        String num = "2376425";
        System.out.println( numero.Convertir(num, true,1));
    }
}
