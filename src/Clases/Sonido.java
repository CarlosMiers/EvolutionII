package Clases;


import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {
   public static void main(String[] args) {
      try {
         Clip campana = AudioSystem.getClip();
         Clip ticket  = AudioSystem.getClip();
         Clip turno   = AudioSystem.getClip();
         Clip box     = AudioSystem.getClip();
         Clip nbox    = AudioSystem.getClip();
         
         String orden = null;
         String cBox = null;
         
         orden = "107";
         cBox = "2";
         
         File a = new File("c:\\media\\campana.wav");
         File b = new File("c:\\media\\ticket.wav");
         File c = new File("c:\\media\\"+orden.toString()+".wav");
         File d = new File("c:\\media\\box.wav");
         File e = new File("c:\\media\\"+cBox.toString()+".wav");
         
         //SE EJECUTA LA CAMPANA PARA ATRAER LA ATENCION
         campana.open(AudioSystem.getAudioInputStream(a));
         campana.start();
         System.out.println("Reproduciendo 10s. de sonido...");
         Thread.sleep(1000); // 1000 milisegundos (10 segundos)

         //SE EJECUTA TICKET
         ticket.open(AudioSystem.getAudioInputStream(b));
         ticket.start();
         System.out.println("Reproduciendo 10s. de sonido...");
         Thread.sleep(1000); // 1000 milisegundos (10 segundos)

        //SE EJECUTA el TURNO

         turno.open(AudioSystem.getAudioInputStream(c));
         turno.start();
         System.out.println("Reproduciendo 10s. de sonido...");
         Thread.sleep(1000); // 1000 milisegundos (10 segundos)

        //SE EJECUTA EL BOX 
         box.open(AudioSystem.getAudioInputStream(d));
         box.start();
         System.out.println("Reproduciendo 10s. de sonido...");
         Thread.sleep(1000); // 1000 milisegundos (10 segundos)

         //SE EJECUTA EL NRO DE BOX
         nbox.open(AudioSystem.getAudioInputStream(e));
         nbox.start();
         System.out.println("Reproduciendo 10s. de sonido...");
         Thread.sleep(1000); // 1000 milisegundos (10 segundos)


         campana.close();
         ticket.close();
         turno.close();
         box.close();
         nbox.close();
         
      } catch (Exception tipoError) {
         System.out.println("" + tipoError);
      }
   }
}