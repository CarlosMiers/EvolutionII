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
public class llenar {

    public void llenar(){
        
    }
    private static String adicion(String string, int largo) {
        String espacio = "";

        int cantidad = largo - string.length();

        if (cantidad >= 1) {
            for (int i = 0; i < cantidad; i++) {
                espacio += " ";
            }

            return (espacio + string);
        } else {
            return string;
        }
    }
}
