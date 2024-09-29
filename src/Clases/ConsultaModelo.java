package Clases;

import javax.swing.table.DefaultTableModel;


public class ConsultaModelo extends DefaultTableModel  {
    @Override
   public boolean isCellEditable (int row, int column)
   {
     return false;
   }
}
