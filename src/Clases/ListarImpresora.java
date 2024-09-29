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
public class ListarImpresora {
     public static void main(String args[]) {
        BuscadorImpresora printer = new BuscadorImpresora();
        System.out.println(printer.buscar("HP") );
    }

}
