/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Conexion.ConexionEspejo;
import Modelo.familia;
import Modelo.marca;
import Modelo.medida;
import Modelo.pais;
import Modelo.plan;
import Modelo.producto;
import Modelo.proveedor;
import Modelo.rubro;
import Modelo.stock;
import Modelo.sucursal;
import Modelo.ubicacion;
import java.math.BigDecimal;
import java.math.MathContext;
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
public class productoEspejoDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public producto insertarProducto(producto p, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();

        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("INSERT INTO productos (codigo,nombre,rubro,marca,medida,costo,ubicacion,"
                + "precio_maximo,precio_minimo,tipo_producto,stockminimo,estado,"
                + "conteomayorista,incremento1,incremento2,"
                + "codigobarra,conversion,paises,proveedor,cambiarprecio,"
                + "codfamilia,ivaporcentaje,observacion,precioventa,fecha_ingreso)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getCodigo());
        ps.setString(2, p.getNombre());
        ps.setInt(3, p.getRubro().getCodigo());
        ps.setInt(4, p.getMarca().getCodigo());
        ps.setInt(5, p.getMedida().getCodigo());
        ps.setBigDecimal(6, p.getCosto());
        ps.setInt(7, p.getUbicacion().getCodigo());
        ps.setBigDecimal(8, p.getPrecio_maximo()); //Precio Minorista
        ps.setBigDecimal(9, p.getPrecio_minimo()); //Precio Mayorista
        ps.setInt(10, p.getTipo_producto());
        ps.setBigDecimal(11, p.getStockminimo());
        ps.setInt(12, p.getEstado());
        ps.setInt(13, p.getConteomayorista());
        ps.setBigDecimal(14, p.getIncremento1()); //
        ps.setBigDecimal(15, p.getIncremento2()); //
        ps.setString(16, p.getCodigobarra());
        ps.setBigDecimal(17, p.getConversion());
        ps.setInt(18, p.getPaises().getCodigo());
        ps.setInt(19, p.getProveedor().getCodigo());
        ps.setInt(20, p.getCambiarprecio());
        ps.setInt(21, p.getFamilia().getCodigo());
        ps.setBigDecimal(22, p.getIvaporcentaje());
        ps.setString(23, p.getObservacion());
        ps.setBigDecimal(24, p.getPrecioventa());
        ps.setDate(25, p.getFecha_ingreso());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE PRODUCTOS " + ex.getLocalizedMessage());
        }
        ps.close();
        stEspejo.close();
        conne.close();
        return p;
    }

    public boolean actualizarProductos(producto p, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;
        ps = stEspejo.getConnection().prepareStatement("UPDATE productos SET nombre=?,rubro=?,marca=?,medida=?,costo=?,ubicacion=?,"
                + "precio_maximo=?,precio_minimo=?,tipo_producto=?,stockminimo=?,estado=?,"
                + "conteomayorista=?,incremento1=?,incremento2=?,"
                + "codigobarra=?,conversion=?,paises=?,proveedor=?,cambiarprecio=?,codfamilia=?,"
                + "ivaporcentaje=?,observacion=?,cambiarprecio=?,precioventa=?,fecha_ingreso=? "
                + "WHERE codigo= '" + p.getCodigo() + "'");
        ps.setString(1, p.getNombre());
        ps.setInt(2, p.getRubro().getCodigo());
        ps.setInt(3, p.getMarca().getCodigo());
        ps.setInt(4, p.getMedida().getCodigo());
        ps.setBigDecimal(5, p.getCosto());
        ps.setInt(6, p.getUbicacion().getCodigo());
        ps.setBigDecimal(7, p.getPrecio_maximo()); //Precio Minorista
        ps.setBigDecimal(8, p.getPrecio_minimo()); //Precio Mayorista
        ps.setInt(9, p.getTipo_producto());
        ps.setBigDecimal(10, p.getStockminimo());
        ps.setInt(11, p.getEstado());
        ps.setInt(12, p.getConteomayorista());
        ps.setBigDecimal(13, p.getIncremento1()); //
        ps.setBigDecimal(14, p.getIncremento2()); //
        ps.setString(15, p.getCodigobarra());
        ps.setBigDecimal(16, p.getConversion());
        ps.setInt(17, p.getPaises().getCodigo());
        ps.setInt(18, p.getProveedor().getCodigo());
        ps.setInt(19, p.getCambiarprecio());
        ps.setInt(20, p.getFamilia().getCodigo());
        ps.setBigDecimal(21, p.getIvaporcentaje());
        ps.setString(22, p.getObservacion());
        ps.setInt(23, p.getCambiarprecio());
        ps.setBigDecimal(24, p.getPrecioventa());
        ps.setDate(25, p.getFecha_ingreso());
        int rowsUpdated=0;
        try {
            rowsUpdated =  ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE PRODUCTOS " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        ps.close();
        conne.close();

        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarProducto(String cod, Integer nsuc) throws SQLException {
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();
        PreparedStatement ps = null;

        ps = stEspejo.getConnection().prepareStatement("DELETE FROM productos WHERE codigo='" + cod + "'");
        int rowsUpdated = ps.executeUpdate();
        stEspejo.close();
        ps.close();
        conne.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
}
