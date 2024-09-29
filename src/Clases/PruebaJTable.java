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
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Ejemplo de uso de TableRowSorter y RowFilter.
 * 
 * @author Chuidiang
 * 
 */
public class PruebaJTable {

	private TableRowSorter<TableModel> modeloOrdenado;

	/**
	 * main del ejemplo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new PruebaJTable();
	}

	/**
	 * Instancia un JFrame con un JTable dentro y diez filas de datos. Lleva un
	 * trozo de código comentado para poder reemplazar.
	 */
	public PruebaJTable() {
		JFrame v = new JFrame("Prueba JTable");

		// Modelo de datos, segunda columna Integer y primera String. Los
		// índices empiezan en cero.
		DefaultTableModel modelo = new DefaultTableModel() {
			@Override
			public Class getColumnClass(int columna) {
				if (columna == 1)
					return Integer.class;
				return String.class;
			}
		};

		// Añadimos unos datos.
		modelo.addColumn("columna 1");
		modelo.addColumn("columna 2");
		for (int i = 0; i < 1000; i++) {

                    String Datos[] = {String.valueOf(i),String.valueOf(100-i)};
                    modelo.addRow(Datos);

//                    modelo.addRow(new Object[] { "" + i, 100 - i });
                        System.out.println(i);
		}
                

		// Metemos el modelo ordenable en la tabla.
		JTable tabla = new JTable(modelo);
		modeloOrdenado = new TableRowSorter<TableModel>(modelo);
		tabla.setRowSorter(modeloOrdenado);
		modeloOrdenado.setRowFilter(RowFilter.regexFilter("2", 1));

		// Lo pintamos todo en la ventana y la mostramos.
		JScrollPane scroll = new JScrollPane(tabla);
		v.getContentPane().add(scroll);
		v.pack();
		v.setVisible(true);
		v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}