/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openide.util.Exceptions;
import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Pc_Server
 */
public class graficoPrecios extends JFrame {
    
    JPanel panel;
    
    public graficoPrecios() {
        setTitle("Como Hacer Graficos con Java");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }
    
    private void init() {
        Conexion con = null;
        Statement st = null;
        ResultSet rs = null;
        con = new Conexion();
        st = con.conectar();
        
        panel = new JPanel();
        getContentPane().add(panel);
        // Fuente de Datos
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        
        String cSqlGrafico = "SELECT cabecera_compras.formatofactura,"
                + "cabecera_compras.fecha,"
                + "proveedores.codigo,"
                + "proveedores.nombre AS nombreproveedor,"
                + "detalle_compras.prcosto "
                + "FROM cabecera_compras "
                + "LEFT JOIN proveedores "
                + "ON proveedores.codigo=cabecera_compras.proveedor "
                + "LEFT JOIN detalle_compras "
                + "ON detalle_compras.dreferencia=cabecera_compras.creferencia "
                + "WHERE detalle_compras.codprod='30305' "
                + "ORDER BY cabecera_compras.fecha";
            System.out.println(cSqlGrafico);
                
        try {
            rs = st.executeQuery(cSqlGrafico);
            while (rs.next()) {
                line_chart_dataset.addValue(rs.getDouble("prcosto"), rs.getString("nombreproveedor"), rs.getDate("fecha"));
                System.out.println(rs.getString("nombreproveedor"));
            }
            
            st.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Creando el Grafico
        JFreeChart chart = ChartFactory.createLineChart("Histórico de Precios",
                "Fecha", "Precios", line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
    }
    
    public static void main(String args[]) {
        new graficoPrecios().setVisible(true);
    }
}
