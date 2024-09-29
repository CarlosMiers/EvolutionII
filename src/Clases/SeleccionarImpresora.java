/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javax.print.PrintService;

/**
 *
 * @author Pc_Server
 */
public class SeleccionarImpresora {
    public static String formatPrinter(PrintService[] services) {
        StringBuilder builder = new StringBuilder();
        builder.append("Impresoras Disponibles, Seleccione una").append("\n");
        for (int i = 0; i < services.length; i++) {
            builder.append(i).append("- ").append(services[i].getName()).append("\n");
        }
        return builder.toString();
    }
}
