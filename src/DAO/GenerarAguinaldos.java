package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.config_contable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GenerarAguinaldos {

    Conexion con = null;
    Statement st = null;

    public boolean generarAguinaldo(Integer nperiodo) throws SQLException {
        System.out.println("Generando Aguinaldos");
        boolean valor = true;

        double nIva5 = 0, nIva10 = 0, nTotales = 0, nAsiento = 0;
        int nProveedor = 0, nItems = 0, nContador = 0;
        Date dFecha;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Generando asientos de Depositos- Inicio");

        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();
            // Verificar que existen ventas

            String sql = "SELECT funcionario,empleados.sucursal,planilla_salarios.fecha,"
                    + "SUM(salariobruto) AS totalingreso,empleados.giraduria  "
                    + "FROM planilla_salarios "
                    + " LEFT JOIN empleados "
                    + " ON empleados.codigo=planilla_salarios.funcionario "
                    + " WHERE periodo= " + nperiodo
                    + " AND empleados.estado=1 "
                    + " GROUP BY funcionario "
                    + " ORDER BY sucursal,funcionario ";
            System.out.println(sql);

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet depositos = ps.executeQuery(sql);

            sql = "DELETE FROM planilla_aguinaldos WHERE periodo=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, nperiodo);
            ps.executeUpdate();

            while (depositos.next()) {
                nContador++;
                int funcionario = depositos.getInt("funcionario");
                int sucursal = depositos.getInt("sucursal");
                double totalbruto = depositos.getDouble("totalingreso");
                double aguinaldo = Math.round(totalbruto / 12);
                int mes = 12;
                int giraduria = depositos.getInt("giraduria");

                // Borrar cabecera
                // Agregar cabecera
                // Agregar cabecera
                sql = "INSERT INTO planilla_aguinaldos (mes,periodo,sucursal,fecha,giraduria,funcionario,salario_bruto,aguinaldo) "
                        + "VALUES (" + mes + ",'" + nperiodo + "'," + sucursal + ",'" + depositos.getDate("fecha") + "'," + giraduria + ",'" + funcionario + "'," + totalbruto + ",'" + aguinaldo + "')";
                st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet keyset = st.getGeneratedKeys();
            }

            st.close();
            ps.close();
            System.out.println("Generando asientos de depositos- Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return valor;
    }

}
