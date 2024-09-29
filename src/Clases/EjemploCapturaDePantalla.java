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
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Ejemplo de un captura de pantalla( screenshot ) basado en Java.
 * 1.- Obtiene el tamaño del rectangulo
 * 2.- Genera el screenshot basado en el tamaño del rectangulo.
 * 3.- Esbribe la imagen en un fichero.
 *
 * @author Felix Garcia Borrego (borrego@gmail.com)
 */
public class EjemploCapturaDePantalla {

    /**
     * @param args
     */
    public static void main(String... args) {
        // obtenemos el tamaño del rectangulo
        Rectangle rectangleTam = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            Robot robot = new Robot();
            // tomamos una captura de pantalla( screenshot )
            BufferedImage bufferedImage = robot.createScreenCapture(rectangleTam);

            String nombreFichero=System.getProperty("user.home")+File.separatorChar+"captura.jpg";
            System.out.println("Generando el fichero: "+nombreFichero );
            FileOutputStream out = new FileOutputStream(nombreFichero);

            // esbribe la imagen a fichero
            ImageIO.write(bufferedImage, "jpg", out);

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}