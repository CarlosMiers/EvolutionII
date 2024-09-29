/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.bancoplaza;
import Modelo.cliente;
import Modelo.ctactecliente;
import Modelo.moneda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ctacteclienteDAO {

    Conexion con = null;
    Statement st = null;


    public ArrayList<ctactecliente> todosxCliente(int ncliente) throws SQLException {
        ArrayList<ctactecliente> lista = new ArrayList<ctactecliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT iditem,bancos_plaza.nombre AS nombrebanco,nrocuenta,cliente,ctacteclientes.moneda,"
                + "ctacteclientes.bancos,clientes.nombre AS nombrecliente,monedas.nombre AS nombremoneda "
                + " FROM ctacteclientes "
                + " LEFT JOIN clientes"
                +" ON clientes.codigo=ctacteclientes.cliente "
                +" LEFT JOIN monedas "
                +" ON monedas.codigo=ctacteclientes.moneda "
                +" LEFT JOIN bancos_plaza "
                +" ON bancos_plaza.codigo = ctacteclientes.bancos "
                + " WHERE cliente=? "
                + " ORDER BY iditem ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1,ncliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ctactecliente cta = new ctactecliente();
                bancoplaza bco = new bancoplaza();
                cliente cl = new cliente();
                cta.setCliente(cl);
                moneda mn = new moneda();
                cta.setMoneda(mn);
                cta.setBancos(bco);
                cta.setIditem(rs.getInt("iditem"));
                cta.setNrocuenta(rs.getString("nrocuenta"));
                cta.getCliente().setCodigo(rs.getInt("cliente"));
                cta.getCliente().setNombre(rs.getString("nombrecliente"));
                cta.getMoneda().setCodigo(rs.getInt("moneda"));
                cta.getMoneda().setNombre(rs.getString("nombremoneda"));
                cta.getBancos().setCodigo(rs.getInt("bancos"));
                cta.getBancos().setNombre(rs.getString("nombrebanco"));
                
                lista.add(cta);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ctactecliente insertarCta(ctactecliente ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO ctacteclientes "
                + "(nrocuenta,bancos,cliente,moneda)"
                + "VALUES (?,?,?,?)");
        ps.setString(1, ca.getNrocuenta());
        ps.setInt(2, ca.getBancos().getCodigo());
        ps.setInt(3, ca.getCliente().getCodigo());
        ps.setInt(4, ca.getMoneda().getCodigo());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(null, "Es Probable que la Cuenta ya Exista");
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarCta(ctactecliente ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE ctacteclientes "
                + "SET nrocuenta=?, bancos=?, cliente=?,moneda=? WHERE iditem=" + ca.getIditem());
        ps.setString(1, ca.getNrocuenta());
        ps.setInt(2, ca.getBancos().getCodigo());
        ps.setInt(3, ca.getCliente().getCodigo());
        ps.setInt(4, ca.getMoneda().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarMateria(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM ctacteclientes WHERE iditem=?");
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


    public ArrayList<ctactecliente> todosxClienteMoneda(int ncliente,int nmoneda) throws SQLException {
        ArrayList<ctactecliente> lista = new ArrayList<ctactecliente>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT iditem,bancos_plaza.nombre AS nombrebanco,nrocuenta,cliente,ctacteclientes.moneda,"
                + "ctacteclientes.bancos,clientes.nombre AS nombrecliente,monedas.nombre AS nombremoneda "
                + " FROM ctacteclientes "
                + " LEFT JOIN clientes"
                +" ON clientes.codigo=ctacteclientes.cliente "
                +" LEFT JOIN monedas "
                +" ON monedas.codigo=ctacteclientes.moneda "
                +" LEFT JOIN bancos_plaza "
                +" ON bancos_plaza.codigo = ctacteclientes.bancos "
                + " WHERE ctacteclientes.cliente=? AND ctacteclientes.moneda=? "
                + " ORDER BY iditem ";
                System.out.println(ncliente);   
                System.out.println(nmoneda);
                System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1,ncliente);
            ps.setInt(2,nmoneda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ctactecliente cta = new ctactecliente();
                bancoplaza bco = new bancoplaza();
                cliente cl = new cliente();
                cta.setCliente(cl);
                moneda mn = new moneda();
                cta.setMoneda(mn);
                cta.setBancos(bco);
                cta.setIditem(rs.getInt("iditem"));
                cta.setNrocuenta(rs.getString("nrocuenta"));
                cta.getCliente().setCodigo(rs.getInt("cliente"));
                cta.getCliente().setNombre(rs.getString("nombrecliente"));
                cta.getMoneda().setCodigo(rs.getInt("moneda"));
                cta.getMoneda().setNombre(rs.getString("nombremoneda"));
                cta.getBancos().setCodigo(rs.getInt("bancos"));
                cta.getBancos().setNombre(rs.getString("nombrebanco"));
                
                lista.add(cta);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }




}
