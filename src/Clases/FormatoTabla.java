package Clases;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class FormatoTabla extends DefaultTableCellRenderer {

    private int columna_patron;
    private int columna_descuento;

    public FormatoTabla(int Colpatron, int ColDescuento) {
        this.columna_patron = Colpatron;
        this.columna_descuento = ColDescuento;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        String cDescuento = table.getValueAt(row, columna_descuento).toString();
        cDescuento = cDescuento.replace(".", "");
        setBackground(Color.WHITE);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fila con estado FALSE, se cambia el color de fondo a rojo
        if (Double.valueOf(cDescuento) > 0) {
            if (table.getValueAt(row, columna_patron).equals("")) {
                setBackground(Color.RED);
            } else {
                setBackground(Color.GREEN);
            }
        }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }

}
