package Modelo;

import javax.swing.table.DefaultTableModel;


public class Tablas extends DefaultTableModel  {
    @Override
   public boolean isCellEditable (int row, int column)
   {
     return false;
   }
}
