/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author SERVIDOR
 */
import Conexion.Conexion;
import Modelo.ar_prop;
import Modelo.cliente;
import Modelo.vendedor;
import Modelo.ventalote;
import java.math.BigDecimal;
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
public class ventaloteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ventalote> mostrarxfecha(Date dInicio, Date dFin) throws SQLException {
        ArrayList<ventalote> lista = new ArrayList<ventalote>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT idventa,fraccion,manzana,lote,cliente,fecha,primeracuota,cuotas, "
                + "cu2,cu3,cu4,cu5,cu6,cu7,cu8,cu9,"
                + "imp2,imp3,imp4,imp5,imp6,imp7,imp8,imp9,"
                + "comadmin,comvende,totalventa,"
                + "clientes.nombre AS nombrecliente,ar_prop.ar_pro_nom AS nombrefraccion,vendedores.nombre as nombrevendedor "
                + " FROM venta_lotes "
                + " INNER JOIN clientes "
                + " ON clientes.codigo=venta_lotes.cliente "
                + "LEFT JOIN ar_prop "
                + "ON ar_prop.ar_pro_num=venta_lotes.fraccion "
                + "LEFT JOIN vendedores "
                + "ON vendedores.codigo=venta_lotes.vendedor "
                + " WHERE BETWEEN fecha ? AND ?"
                + " ORDER BY idventa ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dInicio);
            ps.setDate(2, dFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ventalote v = new ventalote();
                ar_prop p = new ar_prop();
                cliente c = new cliente();
                vendedor ve = new vendedor();
                v.setVendedor(ve);
                v.setCliente(c);
                v.setFraccion(p);
                v.setIdventa(rs.getInt("idventa"));
                v.setFecha(rs.getDate("fecha"));
                v.setCu2(rs.getInt("cu2"));
                v.setImp2(rs.getBigDecimal("imp2"));
                v.setCu3(rs.getInt("cu3"));
                v.setImp3(rs.getBigDecimal("imp3"));
                v.setCu4(rs.getInt("cu4"));
                v.setImp4(rs.getBigDecimal("imp4"));
                v.setCu5(rs.getInt("cu5"));
                v.setImp5(rs.getBigDecimal("imp5"));
                v.setCu6(rs.getInt("cu6"));
                v.setImp6(rs.getBigDecimal("imp6"));
                v.setCu7(rs.getInt("cu7"));
                v.setImp7(rs.getBigDecimal("imp7"));
                v.setCu8(rs.getInt("cu8"));
                v.setImp8(rs.getBigDecimal("imp8"));
                v.setCu9(rs.getInt("cu9"));
                v.setImp9(rs.getBigDecimal("imp9"));
                v.setComadmin(rs.getDouble("comadmin"));
                v.setComvende(rs.getDouble("comvende"));
                v.setCuotas(rs.getInt("cuotas"));
                v.setPrimeracuota(rs.getDate("primeracuota"));
                v.setLote(rs.getString("lote"));
                v.setManzana(rs.getString("manzana"));
                v.getCliente().setCodigo(rs.getInt("cliente"));
                v.getCliente().setNombre(rs.getString("nombrecliente"));
                v.getFraccion().setAr_pro_num(rs.getString("fraccion"));
                v.getFraccion().setAr_pro_nom(rs.getString("nombrefraccion"));
                v.getVendedor().setCodigo(rs.getInt("vendedor"));
                v.getVendedor().setNombre(rs.getString("nombrevendedor"));
                lista.add(v);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ventalote buscarId(int id) throws SQLException {
        ventalote v = new ventalote();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT idventa,fraccion,manzana,lote,cliente,fecha,primeracuota,cuotas, "
                    + "cu2,cu3,cu4,cu5,cu6,cu7,cu8,cu9,"
                    + "imp2,imp3,imp4,imp5,imp6,imp7,imp8,imp9,"
                    + "comadmin,comvende,totalventa,"
                    + "clientes.nombre AS nombrecliente,ar_prop.ar_pro_nom AS nombrefraccion,vendedores.nombre as nombrevendedor "
                    + " FROM venta_lotes "
                    + " INNER JOIN clientes "
                    + " ON clientes.codigo=venta_lotes.cliente "
                    + "LEFT JOIN ar_prop "
                    + "ON ar_prop.ar_pro_num=venta_lotes.fraccion "
                    + "LEFT JOIN vendedores "
                    + "ON vendedores.codigo=venta_lotes.vendedor "
                    + " WHERE BETWEEN idventa= ?"
                    + " ORDER BY idventa ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ar_prop p = new ar_prop();
                    cliente c = new cliente();
                    vendedor ve = new vendedor();
                    v.setCliente(c);
                    v.setFraccion(p);
                    v.setVendedor(ve);
                    v.setIdventa(rs.getInt("idventa"));
                    v.setFecha(rs.getDate("fecha"));
                    v.setCu2(rs.getInt("cu2"));
                    v.setImp2(rs.getBigDecimal("imp2"));
                    v.setCu3(rs.getInt("cu3"));
                    v.setImp3(rs.getBigDecimal("imp3"));
                    v.setCu4(rs.getInt("cu4"));
                    v.setImp4(rs.getBigDecimal("imp4"));
                    v.setCu5(rs.getInt("cu5"));
                    v.setImp5(rs.getBigDecimal("imp5"));
                    v.setCu6(rs.getInt("cu6"));
                    v.setImp6(rs.getBigDecimal("imp6"));
                    v.setCu7(rs.getInt("cu7"));
                    v.setImp7(rs.getBigDecimal("imp7"));
                    v.setCu8(rs.getInt("cu8"));
                    v.setImp8(rs.getBigDecimal("imp8"));
                    v.setCu9(rs.getInt("cu9"));
                    v.setImp9(rs.getBigDecimal("imp9"));
                    v.setComadmin(rs.getDouble("comadmin"));
                    v.setComvende(rs.getDouble("comvende"));
                    v.setCuotas(rs.getInt("cuotas"));
                    v.setPrimeracuota(rs.getDate("primeracuota"));
                    v.setLote(rs.getString("lote"));
                    v.setManzana(rs.getString("manzana"));
                    v.getCliente().setCodigo(rs.getInt("cliente"));
                    v.getCliente().setNombre(rs.getString("nombrecliente"));
                    v.getFraccion().setAr_pro_num(rs.getString("fraccion"));
                    v.getFraccion().setAr_pro_nom(rs.getString("nombrefraccion"));
                    v.getVendedor().setCodigo(rs.getInt("vendedor"));
                    v.getVendedor().setNombre(rs.getString("nombrevendedor"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return v;
    }

    public ventalote insertarVenta(ventalote g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO venta_lotes (fraccion,manzana,lote,cliente,fecha,primeracuota,cuotas,cu2,cu3,cu4,cu5,cu6,cu7,cu8,cu9,imp2,imp3,imp4,imp5,imp6,imp7,imp8,imp9,comadmin,comvende,totalventa,vendedor) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, g.getFraccion().getAr_pro_num());
        ps.setString(2, g.getManzana());
        ps.setString(3, g.getLote());
        ps.setInt(4, g.getCliente().getCodigo());
        ps.setDate(5, g.getFecha());
        ps.setDate(6, g.getPrimeracuota());
        ps.setInt(7, g.getCuotas());
        ps.setInt(8, g.getCu2());
        ps.setInt(9, g.getCu3());
        ps.setInt(10, g.getCu4());
        ps.setInt(11, g.getCu5());
        ps.setInt(12, g.getCu6());
        ps.setInt(13, g.getCu7());
        ps.setInt(14, g.getCu8());
        ps.setInt(15, g.getCu9());
        ps.setBigDecimal(16, g.getImp2());
        ps.setBigDecimal(17, g.getImp3());
        ps.setBigDecimal(18, g.getImp4());
        ps.setBigDecimal(19, g.getImp5());
        ps.setBigDecimal(20, g.getImp6());
        ps.setBigDecimal(21, g.getImp7());
        ps.setBigDecimal(22, g.getImp8());
        ps.setBigDecimal(23, g.getImp9());
        ps.setDouble(23, g.getComadmin());
        ps.setDouble(25, g.getComvende());
        ps.setBigDecimal(26, g.getTotalventa());
        ps.setInt(27, g.getVendedor().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return g;
    }

    public boolean actualizarVentaLote(ventalote g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE venta_lotes SET fraccion=?,manzana=?,lote=?,cliente=?,fecha=?,primeracuota=?,cuotas=?,cu2=?,cu3=?,cu4=?,cu5=?,cu6=?,cu7=?,cu8=?,cu9=?,imp2=?,imp3=?,imp4=?,imp5=?,imp6=?,imp7=?,imp8=?,imp9=?,comadmin=?,comvende=?,totalventa=?,vendedor=? WHERE codigo=" + g.getIdventa());
        ps.setString(1, g.getFraccion().getAr_pro_num());
        ps.setString(2, g.getManzana());
        ps.setString(3, g.getLote());
        ps.setInt(4, g.getCliente().getCodigo());
        ps.setDate(5, g.getFecha());
        ps.setDate(6, g.getPrimeracuota());
        ps.setInt(7, g.getCuotas());
        ps.setInt(8, g.getCu2());
        ps.setInt(9, g.getCu3());
        ps.setInt(10, g.getCu4());
        ps.setInt(11, g.getCu5());
        ps.setInt(12, g.getCu6());
        ps.setInt(13, g.getCu7());
        ps.setInt(14, g.getCu8());
        ps.setInt(15, g.getCu9());
        ps.setBigDecimal(16, g.getImp2());
        ps.setBigDecimal(17, g.getImp3());
        ps.setBigDecimal(18, g.getImp4());
        ps.setBigDecimal(19, g.getImp5());
        ps.setBigDecimal(20, g.getImp6());
        ps.setBigDecimal(21, g.getImp7());
        ps.setBigDecimal(22, g.getImp8());
        ps.setBigDecimal(23, g.getImp9());
        ps.setDouble(23, g.getComadmin());
        ps.setDouble(25, g.getComvende());
        ps.setBigDecimal(26, g.getTotalventa());
        ps.setInt(27,g.getVendedor().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarVentaLote(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM venta_lotes WHERE idventa=?");
        ps.setInt(1, cod);
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
