package Clases;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class FormatoTablaStock extends DefaultTableCellRenderer {

    private int columna_patron;
    private int columna_stock;

    public FormatoTablaStock(int Colpatron, int ColStock) {
        this.columna_patron = Colpatron;
        this.columna_stock = ColStock;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        String cStock = table.getValueAt(row, columna_stock).toString();
        cStock = cStock.replace(".", "").replace(",",".");
        setBackground(Color.WHITE);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fila con estado FALSE, se cambia el color de fondo a rojo
        if (Double.valueOf(cStock) > 0) {
            setBackground(Color.GREEN);
        }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }

}
