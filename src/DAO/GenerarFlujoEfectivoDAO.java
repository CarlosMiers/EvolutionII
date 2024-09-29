package DAO;

import Conexion.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class GenerarFlujoEfectivoDAO {

    Conexion con = null;
    Statement st = null;

    public HashMap generar(int anio) {
        System.out.println("Generando flujo de caja del periodo " + anio);
        HashMap datos = new HashMap();
        BigDecimal cobros = BigDecimal.ZERO;
        BigDecimal pagos = BigDecimal.ZERO;
        BigDecimal prestamos_clientes = BigDecimal.ZERO;
        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            String sql = "SELECT " +
                                    "(SELECT IFNULL(SUM(monto), 0) FROM pagoexpress WHERE YEAR(fechapago) = ?) + " +
                                    "(SELECT IFNULL(SUM(totalpago), 0) FROM cobranzas WHERE YEAR(fecha) = ?) AS cobros, " +
                                    "(SELECT IFNULL(SUM(monto_entregar), 0) FROM prestamos WHERE YEAR(fecha) = ?) AS prestamos_clientes, " +
                                    "IFNULL(SUM(totalpago), 0) AS pagos FROM pagos " +
                         "WHERE YEAR(fecha) = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, anio);
            ps.setInt(2, anio);
            ps.setInt(3, anio);
            ps.setInt(4, anio);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cobros = rs.getBigDecimal("cobros");
                pagos = rs.getBigDecimal("pagos");
                prestamos_clientes = rs.getBigDecimal("prestamos_clientes");
            }
            datos.put("cobros", cobros);
            datos.put("prestamos_clientes", prestamos_clientes);
            datos.put("pagos", pagos);
            sql = "SELECT representante_legal, contador FROM config_contable";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if (rs.next()) {
                datos.put("representante", rs.getString("representante_legal"));
                datos.put("contador", rs.getString("contador"));
            }
            st.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("ERRO: "+e.getMessage());
        }
        return datos;
    }
}
