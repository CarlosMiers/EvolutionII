/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class usuarioDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<usuario> todos() throws SQLException {
        ArrayList<usuario> lista = new ArrayList<usuario>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT * "
                + " FROM usuarios "
                + " ORDER BY employee_id ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario u = new usuario();
                u.setEmployee_id(rs.getInt("employee_id"));
                u.setFirst_name(rs.getString("first_name"));
                u.setLast_name(rs.getString("last_name"));
                u.setPassword(rs.getString("password"));
                u.setNivel(rs.getInt("nivel"));
                u.setParametros(rs.getInt("parametros"));
                u.setMercaderias(rs.getInt("mercaderias"));
                u.setCompras(rs.getInt("compras"));
                u.setVentas(rs.getInt("ventas"));
                u.setRrhh(rs.getInt("rrhh"));
                u.setFinanzas(rs.getInt("finanzas"));
                u.setOperaciones(rs.getInt("operaciones"));
                u.setContabilidad(rs.getInt("contabilidad"));
                u.setHerramientas(rs.getInt("herramientas"));
                u.setAsociacion(rs.getInt("asociacion"));
                u.setConstrucciones(rs.getInt("construcciones"));
                lista.add(u);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public usuario buscarId(int id) throws SQLException {
        usuario usua = new usuario();
        usua.setEmployee_id(0);
        usua.setLast_name("");
        usua.setFirst_name("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select * "
                    + "from usuarios "
                    + "where usuarios.employee_id = ? "
                    + "order by usuarios.employee_id ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    usua.setEmployee_id(rs.getInt("employee_id"));
                    usua.setFirst_name(rs.getString("first_name"));
                    usua.setLast_name(rs.getString("last_name"));
                    usua.setPassword(rs.getString("password"));
                    usua.setNivel(rs.getInt("nivel"));
                    usua.setParametros(rs.getInt("parametros"));
                    usua.setMercaderias(rs.getInt("mercaderias"));
                    usua.setCompras(rs.getInt("compras"));
                    usua.setVentas(rs.getInt("ventas"));
                    usua.setRrhh(rs.getInt("rrhh"));
                    usua.setFinanzas(rs.getInt("finanzas"));
                    usua.setOperaciones(rs.getInt("operaciones"));
                    usua.setContabilidad(rs.getInt("contabilidad"));
                    usua.setHerramientas(rs.getInt("herramientas"));
                    usua.setAsociacion(rs.getInt("asociacion"));
                    usua.setConstrucciones(rs.getInt("construcciones"));
                    usua.setImportaciones(rs.getInt("importaciones"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return usua;
    }

    public usuario buscarSupervisor(String id) throws SQLException {
        usuario usua = new usuario();
        usua.setEmployee_id(0);
        usua.setLast_name("");
        usua.setFirst_name("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select employee_id,password,last_name "
                    + "from usuarios "
                    + "where usuarios.password = ? "
                    + "and usuarios.nivel=4 "
                    + " order by usuarios.password ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    usua.setEmployee_id(rs.getInt("employee_id"));
                    usua.setPassword(rs.getString("password"));
                    usua.setLast_name(rs.getString("last_name"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return usua;
    }

    public usuario insertarUsuario(usuario u) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO usuarios (last_name,first_name,password,nivel,"
                + "parametros,mercaderias,compras,ventas,finanzas,"
                + "contabilidad,herramientas,operaciones,rrhh,construcciones,asociacion,importaciones)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, u.getLast_name());
        ps.setString(2, u.getFirst_name());
        ps.setString(3, u.getPassword());
        ps.setInt(4, u.getNivel());
        ps.setInt(5, u.getParametros());
        ps.setInt(6, u.getMercaderias());
        ps.setInt(7, u.getCompras());
        ps.setInt(8, u.getVentas());
        ps.setInt(9, u.getFinanzas());
        ps.setInt(10, u.getContabilidad());
        ps.setInt(11, u.getHerramientas());
        ps.setInt(12, u.getOperaciones());
        ps.setInt(13, u.getRrhh());
        ps.setInt(14, u.getConstrucciones());
        ps.setInt(15, u.getAsociacion());
        ps.setInt(16, u.getImportaciones());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return u;
    }

    public boolean actualizarUsuarios(usuario u) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        Connection conn = st.getConnection();

        ps = st.getConnection().prepareStatement("UPDATE usuarios SET last_name=?,"
                + "first_name=?,password=?,"
                + "nivel=?,parametros=?,mercaderias=?,compras=?,ventas=?,finanzas=?,"
                + "contabilidad=?,herramientas=?,operaciones=?,"
                +"rrhh=?,construcciones=?,asociacion=?,importaciones=? "
                + " WHERE employee_id=" + u.getEmployee_id());
        ps.setString(1, u.getLast_name());
        ps.setString(2, u.getFirst_name());
        ps.setString(3, u.getPassword());
        ps.setInt(4, u.getNivel());
        ps.setInt(5, u.getParametros());
        ps.setInt(6, u.getMercaderias());
        ps.setInt(7, u.getCompras());
        ps.setInt(8, u.getVentas());
        ps.setInt(9, u.getFinanzas());
        ps.setInt(10, u.getContabilidad());
        ps.setInt(11, u.getHerramientas());
        ps.setInt(12, u.getOperaciones());
        ps.setInt(13, u.getRrhh());
        ps.setInt(14, u.getConstrucciones());
        ps.setInt(15, u.getAsociacion());
        ps.setInt(16, u.getImportaciones());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarUsuario(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM usuarios WHERE employee_id=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    
    
    
        public usuario insertarUsuarioRRHH(usuario u) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO usuarios (last_name,first_name,password,nivel)"
                + " VALUES (?,?,?,?)");
        ps.setString(1, u.getLast_name());
        ps.setString(2, u.getFirst_name());
        ps.setString(3, u.getPassword());
        ps.setInt(4, u.getNivel());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return u;
    }

    public boolean actualizarUsuariosRRHH(usuario u) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        Connection conn = st.getConnection();

        ps = st.getConnection().prepareStatement("UPDATE usuarios SET last_name=?,"
                + "first_name=?,password=?,"
                + "nivel=? WHERE employee_id=" + u.getEmployee_id());
        ps.setString(1, u.getLast_name());
        ps.setString(2, u.getFirst_name());
        ps.setString(3, u.getPassword());
        ps.setInt(4, u.getNivel());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    
    
    public usuario insertarUsuarioFais(usuario u) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO usuarios"
                + " (last_name,first_name,password,nivel,"
                + "parametros,mercaderias,compras,ventas,finanzas,"
                + "contabilidad,herramientas,operaciones,rrhh,codasesor)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, u.getLast_name());
        ps.setString(2, u.getFirst_name());
        ps.setString(3, u.getPassword());
        ps.setInt(4, u.getNivel());
        ps.setInt(5, u.getParametros());
        ps.setInt(6, u.getMercaderias());
        ps.setInt(7, u.getCompras());
        ps.setInt(8, u.getVentas());
        ps.setInt(9, u.getFinanzas());
        ps.setInt(10, u.getContabilidad());
        ps.setInt(11, u.getHerramientas());
        ps.setInt(12, u.getOperaciones());
        ps.setInt(13, u.getRrhh());
        ps.setInt(14, u.getAsesores());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return u;
    }

    public boolean actualizarUsuariosFais(usuario u) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        Connection conn = st.getConnection();

        ps = st.getConnection().prepareStatement("UPDATE usuarios SET last_name=?,"
                + "first_name=?,password=?,"
                + "nivel=?,parametros=?,mercaderias=?,compras=?,ventas=?,finanzas=?,"
                + "contabilidad=?,herramientas=?,operaciones=?,"
                +"rrhh=?,codasesor=? "
                + " WHERE employee_id=" + u.getEmployee_id());
        ps.setString(1, u.getLast_name());
        ps.setString(2, u.getFirst_name());
        ps.setString(3, u.getPassword());
        ps.setInt(4, u.getNivel());
        ps.setInt(5, u.getParametros());
        ps.setInt(6, u.getMercaderias());
        ps.setInt(7, u.getCompras());
        ps.setInt(8, u.getVentas());
        ps.setInt(9, u.getFinanzas());
        ps.setInt(10, u.getContabilidad());
        ps.setInt(11, u.getHerramientas());
        ps.setInt(12, u.getOperaciones());
        ps.setInt(13, u.getRrhh());
        ps.setInt(14, u.getAsesores());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public usuario buscarIdFais(int id) throws SQLException {
        usuario usua = new usuario();
        usua.setEmployee_id(0);
        usua.setLast_name("");
        usua.setFirst_name("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select usuarios.*,vendedores.nombre as nombrevendedor "
                    + "from usuarios "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo = usuarios.codasesor "
                    + "where usuarios.employee_id = ? "
                    + "order by usuarios.employee_id ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    usua.setEmployee_id(rs.getInt("employee_id"));
                    usua.setFirst_name(rs.getString("first_name"));
                    usua.setLast_name(rs.getString("last_name"));
                    usua.setPassword(rs.getString("password"));
                    usua.setNivel(rs.getInt("nivel"));
                    usua.setParametros(rs.getInt("parametros"));
                    usua.setMercaderias(rs.getInt("mercaderias"));
                    usua.setCompras(rs.getInt("compras"));
                    usua.setVentas(rs.getInt("ventas"));
                    usua.setRrhh(rs.getInt("rrhh"));
                    usua.setFinanzas(rs.getInt("finanzas"));
                    usua.setOperaciones(rs.getInt("operaciones"));
                    usua.setContabilidad(rs.getInt("contabilidad"));
                    usua.setHerramientas(rs.getInt("herramientas"));
                    usua.setAsesores(rs.getInt("codasesor"));
                    usua.setNombreasesor(rs.getString("nombrevendedor"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return usua;
    }

    
}
