package Clases;

import Conexion.Conexion;
import Vista.Portada;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openide.util.Exceptions;

public class grafico extends JFrame {

    JPanel panel;

    public grafico() {
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
        ResultSet results = null;
        con = new Conexion();
        st = con.conectar();
        String cNombreEmpresa = "";
        panel = new JPanel();
        getContentPane().add(panel);
        // Fuente de Datos
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String cSqlGrafico = "SELECT cabecera_compras.formatofactura,"
                + "cabecera_compras.fecha,"
                + "proveedores.codigo,"
                + "proveedores.nombre AS nombreproveedor,"
                + "detalle_compras.prcosto"
                + "FROM cabecera_compras"
                + "LEFT JOIN proveedores"
                + "ON proveedores.codigo=cabecera_compras.proveedor"
                + "LEFT JOIN detalle_compras"
                + "ON detalle_compras.dreferencia=cabecera_compras.creferencia"
                + "WHERE detalle_compras.codprod='30305'"
                + "ORDER BY cabecera_compras.fecha";

        try {
            results = st.executeQuery(cSqlGrafico);
            while (results.next()) {
                cNombreEmpresa = "Ferremax";
                dataset.setValue(results.getInt("prcosto"), results.getString("nombreproveedor"), results.getString("nombre"));
            }

            st.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Creando el Grafico
        JFreeChart chart = ChartFactory.createBarChart3D(cNombreEmpresa, "Proveedores", "Monto",
                dataset, PlotOrientation.HORIZONTAL, true, true, false);
        chart.setBackgroundPaint(Color.getHSBColor(95, 100, 250));
        chart.getTitle().setPaint(Color.blue);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.yellow);
        p.setRangeCrosshairPaint(Color.BLUE);
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        BarRenderer render = (BarRenderer) p.getRenderer();
        render.setSeriesPaint(0, Color.getColor("200,100,95"));

        panel.add(chartPanel);
    }

    public static void main(String args[]) {
        new grafico().setVisible(true);
    }
}
