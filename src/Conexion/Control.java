/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class Control {
   
    public int return_op(String mensaje){
         
        int seleccion = JOptionPane.showOptionDialog(null, mensaje, "Atención!!",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null, // null para icono por defecto.
                                    new Object[]{"Aceptar", "Cancelar"}, // null para YES, NO y CANCEL
                                    null);
         return seleccion;
    }
    
    public void mensaje_error(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
      public void mensaje_conf(String mensaje){
         JOptionPane.showMessageDialog(null, mensaje);
    }
      

}
