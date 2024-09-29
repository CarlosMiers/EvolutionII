/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class cambiarFecha {

    public cambiarFecha() {

    }

    public String ConvertirFecha(String numero) {
        String cCadena1 = numero.substring(0, 2);
        System.out.println("Día " + cCadena1);
        String cCadena2 = numero.substring(2, 4);
        System.out.println("Mes " + cCadena2);
        String cCadena3 = numero.substring(4, 8);
        System.out.println("Año " + cCadena3);
        numero = cCadena1 + "/" + cCadena2 + "/" + cCadena3;
        return numero;
    }
}
