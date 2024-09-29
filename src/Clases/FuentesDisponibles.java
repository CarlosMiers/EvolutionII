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
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
 
public class FuentesDisponibles {
 
    public static void main(String[] args) {
 
        String[] nombreFuentes = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                    .getAvailableFontFamilyNames();
 
        System.out.println("Nombre de las fuentes disponibles");
        System.out.println(Arrays.toString(nombreFuentes));
 
        System.out.println("\nFuentes disponibles");
        Font[] fuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        for (Font f : fuentes) {
            System.out.println(f.getName() + " - " + f.getFontName() + " - " + f.getFamily());
        }
 
    }
 
}
