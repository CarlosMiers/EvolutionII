/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.print.PrinterJob;
import javax.print.PrintService;

/**
 *
 * @author Pc_Server
 */
public class BuscadorImpresora {

    public PrintService buscar(String nombre) {
        PrintService service = null;
        PrintService[] services = PrinterJob.lookupPrintServices();
        for (int i = 0; service == null && i < services.length; i++) {
            if (services[i].getName().indexOf(nombre) >= 0) {
                service = services[i];
            }
        }
        return service;
    }
}
