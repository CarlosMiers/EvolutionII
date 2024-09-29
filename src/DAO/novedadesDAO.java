/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.novedades;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class novedadesDAO {

    Conexion con = null;
    Statement st = null;
    int nAsesor = Integer.valueOf(Config.cCodigoAsesor);

    public ArrayList<novedades> todos() throws SQLException {
        ArrayList<novedades> lista = new ArrayList<novedades>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT novedades.numero,novedades.fecha,novedades.cliente,"
                + "novedades.gestor,novedades.proxima_llamada,novedades.estado,novedades.observacion,"
                + "clientes.nombre AS nombrecliente,cobradores.nombre AS nombregestor"
                + " FROM novedades "
                + " LEFT JOIN clientes "
                + " ON clientes.codigo=novedades.cliente "
                + " LEFT JOIN cobradores "
                + " ON cobradores.codigo=novedades.gestor "
                + " WHERE IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                cobrador cob = new cobrador();
                novedades nov = new novedades();
                nov.setCliente(cl);
                nov.setGestor(cob);
                nov.setNumero(rs.getDouble("numero"));
                nov.setFecha(rs.getDate("fecha"));
                nov.setNumero(rs.getDouble("numero"));
                nov.getCliente().setCodigo(rs.getInt("cliente"));
                nov.getCliente().setNombre(rs.getString("nombrecliente"));
                nov.getGestor().setCodigo(rs.getInt("gestor"));
                nov.getGestor().setNombre(rs.getString("nombregestor"));
                nov.setObservacion(rs.getString("observacion"));
                nov.setProxima_llamada(rs.getDate("proxima_llamada"));
                nov.setEstado(rs.getString("estado"));
                lista.add(nov);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public novedades buscarId(double id) throws SQLException {

        novedades nov = new novedades();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT novedades.numero,novedades.fecha,novedades.cliente,"
                    + "novedades.gestor,novedades.proxima_llamada,novedades.estado,novedades.observacion,"
                    + "clientes.nombre AS nombrecliente,cobradores.nombre AS nombregestor,"
                    + "accionrealizada,situacion,proxima_accion "
                    + " FROM novedades "
                    + " LEFT JOIN clientes "
                    + " ON clientes.codigo=novedades.cliente "
                    + " LEFT JOIN cobradores "
                    + " ON cobradores.codigo=novedades.gestor "
                    + " WHERE novedades.numero=? "
                    + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                    + " ORDER BY novedades.numero ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cliente cl = new cliente();
                    cobrador cob = new cobrador();
                    nov.setCliente(cl);
                    nov.setGestor(cob);
                    nov.setFecha(rs.getDate("fecha"));
                    nov.setNumero(rs.getDouble("numero"));
                    nov.getCliente().setCodigo(rs.getInt("cliente"));
                    nov.getCliente().setNombre(rs.getString("nombrecliente"));
                    nov.getGestor().setCodigo(rs.getInt("gestor"));
                    nov.getGestor().setNombre(rs.getString("nombregestor"));
                    nov.setObservacion(rs.getString("observacion"));
                    nov.setProxima_llamada(rs.getDate("proxima_llamada"));
                    nov.setEstado(rs.getString("estado"));
                    nov.setAccionrealizada(rs.getString("accionrealizada"));
                    nov.setSituacion(rs.getString("situacion"));
                    nov.setProxima_accion(rs.getString("proxima_accion"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return nov;
    }

    public ArrayList<novedades> MostrarporFechallamada(Date dFechai, Date dFechaf) throws SQLException {
        ArrayList<novedades> lista = new ArrayList<novedades>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT novedades.numero,novedades.fecha,novedades.cliente,"
                + "novedades.gestor,novedades.proxima_llamada,novedades.estado,novedades.observacion,"
                + "clientes.nombre AS nombrecliente,cobradores.nombre AS nombregestor,novedades.idcuenta,"
                + "accionrealizada,situacion,proxima_accion "
                + " FROM novedades "
                + " LEFT JOIN clientes "
                + " ON clientes.codigo=novedades.cliente "
                + " LEFT JOIN cobradores "
                + " ON cobradores.codigo=novedades.gestor "
                + " WHERE novedades.proxima_llamada BETWEEN ? AND ? AND novedades.estado='ACTIVO' "
                + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                + " ORDER BY novedades.numero";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFechai);
            ps.setDate(2, dFechaf);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                cobrador cob = new cobrador();
                novedades nov = new novedades();
                nov.setCliente(cl);
                nov.setGestor(cob);
                nov.setNumero(rs.getDouble("numero"));
                nov.setFecha(rs.getDate("fecha"));
                nov.setNumero(rs.getDouble("numero"));
                nov.getCliente().setCodigo(rs.getInt("cliente"));
                nov.getCliente().setNombre(rs.getString("nombrecliente"));
                nov.getGestor().setCodigo(rs.getInt("gestor"));
                nov.getGestor().setNombre(rs.getString("nombregestor"));
                nov.setObservacion(rs.getString("observacion"));
                nov.setProxima_llamada(rs.getDate("proxima_llamada"));
                nov.setIdcuenta(rs.getString("idcuenta"));
                nov.setEstado(rs.getString("estado"));
                nov.setAccionrealizada(rs.getString("accionrealizada"));
                nov.setSituacion(rs.getString("situacion"));
                nov.setProxima_accion(rs.getString("proxima_accion"));
                lista.add(nov);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<novedades> MostrarporFecha(Date dFechai, Date dFechaf) throws SQLException {
        ArrayList<novedades> lista = new ArrayList<novedades>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT novedades.numero,novedades.fecha,novedades.cliente,"
                + "novedades.gestor,novedades.proxima_llamada,novedades.estado,"
                + "novedades.observacion,"
                + "clientes.nombre AS nombrecliente,cobradores.nombre AS nombregestor,"
                + "accionrealizada,situacion,proxima_accion "
                + " FROM novedades "
                + " LEFT JOIN clientes "
                + " ON clientes.codigo=novedades.cliente "
                + " LEFT JOIN cobradores "
                + " ON cobradores.codigo=novedades.gestor "
                + " WHERE novedades.fecha BETWEEN '"+dFechai+"' AND '"+dFechaf
                +"' AND novedades.estado='ACTIVO' "
                + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE) "
                + " UNION "
                + " SELECT novedades.numero,novedades.fecha,novedades.cliente,"
                + "novedades.gestor,novedades.proxima_llamada,novedades.estado,"
                + "novedades.observacion,"
                + "clientes.nombre AS nombrecliente,cobradores.nombre AS nombregestor,"
                + "accionrealizada,situacion,proxima_accion "
                + " FROM novedades "
                + " LEFT JOIN clientes "
                + " ON clientes.codigo=novedades.cliente "
                + " LEFT JOIN cobradores "
                + " ON cobradores.codigo=novedades.gestor "
                + " WHERE novedades.proxima_llamada BETWEEN '"+dFechai+"' AND '"+dFechaf
                + "' AND novedades.estado='ACTIVO' "
                + " AND IF(" + nAsesor + "<>0,clientes.asesor=" + nAsesor + ",TRUE)"
                + " ORDER BY numero";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente cl = new cliente();
                cobrador cob = new cobrador();
                novedades nov = new novedades();
                nov.setCliente(cl);
                nov.setGestor(cob);
                nov.setNumero(rs.getDouble("numero"));
                nov.setFecha(rs.getDate("fecha"));
                nov.setNumero(rs.getDouble("numero"));
                nov.getCliente().setCodigo(rs.getInt("cliente"));
                nov.getCliente().setNombre(rs.getString("nombrecliente"));
                nov.getGestor().setCodigo(rs.getInt("gestor"));
                nov.getGestor().setNombre(rs.getString("nombregestor"));
                nov.setObservacion(rs.getString("observacion"));
                nov.setProxima_llamada(rs.getDate("proxima_llamada"));
                nov.setEstado(rs.getString("estado"));
                nov.setAccionrealizada(rs.getString("accionrealizada"));
                nov.setSituacion(rs.getString("situacion"));
                nov.setProxima_accion(rs.getString("proxima_accion"));

                lista.add(nov);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public novedades insertarNovedades(novedades nov) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO novedades (fecha,cliente,gestor,proxima_llamada,estado,observacion,idcuenta) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, nov.getFecha());
        ps.setInt(2, nov.getCliente().getCodigo());
        ps.setInt(3, nov.getGestor().getCodigo());
        ps.setDate(4, nov.getProxima_llamada());
        ps.setString(5, nov.getEstado());
        ps.setString(6, nov.getObservacion());
        ps.setString(7, nov.getIdcuenta());
        ps.executeUpdate();
        st.close();
        ps.close();
        return nov;
    }

    public boolean actualizarNovedades(novedades n) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE novedades SET fecha=?,cliente=?,gestor=?,proxima_llamada=?,observacion=?  WHERE numero=" + n.getNumero());
        ps.setDate(1, n.getFecha());
        ps.setInt(2, n.getCliente().getCodigo());
        ps.setInt(3, n.getGestor().getCodigo());
        ps.setDate(4, n.getProxima_llamada());
        ps.setString(5, n.getObservacion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean cerrarAgenda(novedades n) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE novedades SET estado='CIERRE'  WHERE numero=" + n.getNumeroanterior());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarNovedades(Double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM novedades WHERE numero=?");
        ps.setDouble(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public novedades insertarNovedadesFais(novedades nov) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO novedades (fecha,cliente,gestor,"
                + "proxima_llamada,estado,observacion,"
                + "accionrealizada,situacion,proxima_accion) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, nov.getFecha());
        ps.setInt(2, nov.getCliente().getCodigo());
        ps.setInt(3, nov.getGestor().getCodigo());
        ps.setDate(4, nov.getProxima_llamada());
        ps.setString(5, nov.getEstado());
        ps.setString(6, nov.getObservacion());
        ps.setString(7, nov.getAccionrealizada());
        ps.setString(8, nov.getSituacion());
        ps.setString(9, nov.getProxima_accion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return nov;
    }

    public boolean actualizarNovedadesFais(novedades n) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE novedades SET fecha=?,"
                + "cliente=?,gestor=?,proxima_llamada=?"
                + ",observacion=?,accionrealizada=?,situacion=?,proxima_accion=?"
                + "  WHERE numero=" + n.getNumero());
        ps.setDate(1, n.getFecha());
        ps.setInt(2, n.getCliente().getCodigo());
        ps.setInt(3, n.getGestor().getCodigo());
        ps.setDate(4, n.getProxima_llamada());
        ps.setString(5, n.getObservacion());
        ps.setString(6, n.getAccionrealizada());
        ps.setString(7, n.getSituacion());
        ps.setString(8, n.getProxima_accion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}
