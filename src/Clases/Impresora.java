/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.print.PrinterJob;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.swing.JOptionPane;

/**
 *
 * @author Pc_Server
 */
public class Impresora {

    public static void main(String[] args) {
        PrintService[] services = PrinterJob.lookupPrintServices();
        int value = Integer.valueOf(JOptionPane.showInputDialog(formatPrinter(services)));
        PrintService print = services[value];
        JOptionPane.showMessageDialog(null, String.format("La Impresora Seleccionada es :" + print.getName()), "Awsys", JOptionPane.INFORMATION_MESSAGE);
        DocPrintJob doc = print.createPrintJob();
    }

    private static String formatPrinter(PrintService[] services) {
        StringBuilder builder = new StringBuilder();
        builder.append("Impresoras Disponibles, Seleccione una").append("\n");
        for (int i = 0; i < services.length; i++) {
            builder.append(i).append("- ").append(services[i].getName()).append("\n");

        }
        return builder.toString();
    }
}
