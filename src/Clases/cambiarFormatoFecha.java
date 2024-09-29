/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author faisc
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class cambiarFormatoFecha {
    public static void main(String[] args) {
        // Crear un JFrame
        JFrame frame = new JFrame("Ejemplo de JTable");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Crear un modelo de tabla con algunos datos
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Period");
        model.addColumn("Date"); // Columna para las fechas
        model.addRow(new Object[]{1, "29/07/2025"});
        model.addRow(new Object[]{2, "15/08/2025"});
        model.addRow(new Object[]{3, "01/09/2025"});
        model.addRow(new Object[]{4, "20/10/2025"});
        model.addRow(new Object[]{5, "25/11/2025"});
        
        // Crear un JTable con el modelo de tabla
        JTable table = new JTable(model);
        
        // Agregar el JTable a un JScrollPane y luego al JFrame
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        
        // Mostrar el JFrame
        frame.setVisible(true);
        
        // Definir el formato de fecha utilizado en el JTable
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Definir el formato de salida
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy, MM, dd");
        
        // Llenar el array LocalDate[] dates con los datos del JTable
        int rowCount = model.getRowCount();
        LocalDate[] dates = new LocalDate[rowCount];
        
        for (int i = 0; i < rowCount; i++) {
            String dateString = (String) model.getValueAt(i, 1); // Asumiendo que la columna de fecha está en la posición 1
            try {
                LocalDate date = LocalDate.parse(dateString, inputFormatter);
                dates[i] = date;
            } catch (DateTimeParseException e) {
                System.err.println("Formato de fecha no válido: " + dateString);
            }
        }
        
        // Imprimir los valores del array dates en el formato yyyy, MM, dd
        System.out.println("Fechas en el formato yyyy, MM, dd:");
        for (LocalDate date : dates) {
            if (date != null) {
                System.out.println(date.format(outputFormatter));
            }
        }
    }
}
