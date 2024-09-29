package Clases;


import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.openide.util.Exceptions;

public class MyWorker extends JFrame {

    JPanel panel;

    public MyWorker() {
        setTitle("Como Hacer Graficos con Java");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }

    private void init() {
        panel = new JPanel();
        getContentPane().add(panel);
        // Fuente de Datos
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
        Conexion con = null;
        Statement st = null;
        ResultSet results = null;
        con = new Conexion();
        st = con.conectar();

        String cSqlGrafico = "SELECT comprobantes.nombre, ";
        cSqlGrafico = cSqlGrafico + "SUM(cuenta_clientes.saldo) AS total ";
        cSqlGrafico = cSqlGrafico + "FROM cuenta_clientes ";
        cSqlGrafico = cSqlGrafico + "INNER JOIN comprobantes ";
        cSqlGrafico = cSqlGrafico + "ON comprobantes.codigo=cuenta_clientes.comprobante  ";
        cSqlGrafico = cSqlGrafico + "INNER JOIN clientes ";
        cSqlGrafico = cSqlGrafico + "ON clientes.codigo=cuenta_clientes.cliente ";
        cSqlGrafico = cSqlGrafico + "WHERE comprobante IN (8,10) ";
        cSqlGrafico = cSqlGrafico + "AND cuenta_clientes.moneda=1 ";
        cSqlGrafico = cSqlGrafico + "AND inversionista=  80025879 ";
        cSqlGrafico = cSqlGrafico + "GROUP BY comprobante  ";

        try {
            results = st.executeQuery(cSqlGrafico);
            while (results.next()) {
                defaultpiedataset.setValue(results.getString("nombre"), results.getDouble("total"));
            }
            st.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Creando el Grafico
        JFreeChart chart = ChartFactory.createPieChart3D("Cartera Activa", defaultpiedataset, true, true, false);
        PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
        pieplot3d.setDepthFactor(0.5);
        pieplot3d.setStartAngle(290D);
        pieplot3d.setDirection(Rotation.CLOCKWISE);
        pieplot3d.setForegroundAlpha(0.5F);

        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
    }

    public static void main(String args[]) {
        new MyWorker().setVisible(true);
    }
}
