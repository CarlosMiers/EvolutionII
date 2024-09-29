/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import javax.swing.JTextField;


public class Autocomplete {
    public static int getPosisionVector(String datobuscar, String[] vbdatos) {
        
  try{
           for (int i = 0; i < vbdatos.length; i++) {
               if (datobuscar.equals(vbdatos[i].substring(0, datobuscar.length()))){
                   return i;
  
                 
                }
            }
        } catch (Exception e) {
            
        }
        return -1;
       
    }

   
    
    //Recibe el texto completo a partir de la busqueda 
    
    public static String getTextoapartirVector(String datobuscar, String[]vecdato ){
        int nroposicion = getPosisionVector(datobuscar, vecdato);
        if (nroposicion ==-1){
            return datobuscar;
        }
        return vecdato[nroposicion];
    }
    
    
    
    public static void addTextandSelectToResl(JTextField textfield, String newDato){
        String datoBuscado = "";
        int nroActual = textfield.getText().length();
        datoBuscado = newDato.substring(nroActual, newDato.length());
        if (newDato.isEmpty() || datoBuscado.isEmpty())
            return;
        try{
            textfield.getDocument().insertString(textfield.getCaretPosition(), datoBuscado, null);
        }catch(Exception e){
            
        }
        textfield.select(nroActual, textfield.getText().length());
    }
    
    
}
