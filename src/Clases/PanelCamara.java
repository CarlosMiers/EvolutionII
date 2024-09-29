/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import JPanelWebCam.JPanelWebCam;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tiernito
 */

public class PanelCamara extends JPanelWebCam{
    
    private JComboBox<Webcam> combo;
    private Webcam webcam;
    
    @Override
    public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 2 && this.getImage() != null) {
            try {
              ImageIO.write((RenderedImage)this.getImage(), "jpg", new File("temp.jpg"));
              //Desktop.getDesktop().open(new File("temp.jpg"));
              apagar();
            } catch (IOException ex) {
              Logger.getLogger(JPanelWebCam.class.getName()).log(Level.SEVERE, (String)null, ex);
            } 
          }
          if (isACTIVARCAMARA()) {
            this.webcam = Webcam.getDefault();
            if (this.webcam == null) {
              JOptionPane.showMessageDialog(this, "NO HAY CAMARAS DISPONIBLES", "CAMARA NO ENCONTRADA", 1);
            } else if (this.webcam.isOpen()) {

              this.webcam.close();
            } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
              this.combo = new JComboBox<>();
              for (Webcam cam : Webcam.getWebcams()) {
                this.combo.addItem(cam);
              }

              JOptionPane opciones = new JOptionPane();
              opciones.setMessage(this.combo);
              JDialog dialog = opciones.createDialog("Seleccione una camara");
              dialog.setVisible(true);

              if (null != opciones.getValue()) {
                this.webcam = this.combo.getItemAt(this.combo.getSelectedIndex());
                this.webcam.setViewSize(WebcamResolution.VGA.getSize());
                setImagen((new ImageIcon(getClass().getResource("/icons/cargandocamara.gif"))).getImage());
                
                t.setName("Iniciando camara");
                t.setDaemon(true);
                t.start();
              } 
            } 
        }
    }
    /*public void encender(){
        MouseEvent e = new MouseEvent(
                this, // qué elemento
                MouseEvent.MOUSE_CLICKED, // qué evento
                System.currentTimeMillis(), // cuándo
                0, // no modifiers
                10, 10, // en que posicion x,y
                2, // numero de clicks 
                false
        );
        mouseClicked(e);
    }*/
    public void apagar(){
        if (t.isAlive()){
            webcam.close();
            t.interrupt();
            this.setImagenNull();
        }
    }
    Thread t = new Thread(){
        public void run(){
          if (webcam.open()) {
            while (webcam.isOpen()) {
                Clases.PanelCamara.this.setImagen(Clases.PanelCamara.this.webcam.getImage());
                Clases.PanelCamara.this.repaint();
            }
          }
        }
    };
}
