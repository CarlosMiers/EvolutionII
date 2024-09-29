/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.detalle_derecho_examen;
import Modelo.item_derecho_examen;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class item_derecho_examenDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<item_derecho_examen> MostrarxActa(Double nacta) throws SQLException {
        ArrayList<item_derecho_examen> lista = new ArrayList<item_derecho_examen>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT clientes.codigo,clientes.nombre,clientes.carrera,"
                    + "clientes.semestre,cabecera_derecho_examen.importe,"
                    + "cabecera_derecho_examen.codturno,cabecera_derecho_examen.periodo"
                    + "FROM cabecera_derecho_examen,clientes,cabecera_ventas "
                    + "WHERE clientes.carrera=cabecera_derecho_examen.codcarrera "
                    + "AND clientes.semestre=cabecera_derecho_examen.semestre "
                    + "AND clientes.codigo=cabecera_ventas.cliente "
                    + "AND YEAR(cabecera_ventas.fecha)=cabecera_derecho_examen.periodo "
                    + "AND clientes.estado=1 "
                    +" cabecera_derecho_examen.idcerficado=?"
                    +" GROUP BY clientes.codigo"
                    +" ORDER BY clientes.codigo";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, nacta);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    item_derecho_examen cer = new item_derecho_examen();

                    cer.setCodigo(rs.getInt("codigo"));
                    cer.setNombre(rs.getString("nombre"));
                    cer.setCarrera(rs.getInt("carrera"));
                    cer.setSemestre(rs.getInt("semestre"));
                    cer.setPeriodo(rs.getInt("periodo"));
                    cer.setImporte(rs.getDouble("importe"));
                    lista.add(cer);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

}
