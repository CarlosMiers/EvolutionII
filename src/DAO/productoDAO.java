/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
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
public class productoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<producto> todos() throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String sql = "SELECT productos.codigo,productos.nombre,paises,productos.rubro,marca,medida,ubicacion,proveedor,costo,precio_maximo,precio_minimo,precioventa,stockminimo,"
                + "incremento1,incremento2,incremento3,sugerido1,sugerido2,sugerido3,fechacompra,tipo_producto,conteomayorista,kit,codigobarra,"
                + "fecha_ingreso,productos.estado,conversion,stockactual,stock,observacion,stocksistema,ivaporcentaje,verificado,fechahecho,"
                + "paises.nombre AS nombrepais,rubros.nombre AS nombrerubro,marcas.nombre AS nombremarca,productos.codfamilia,productos.cambiarprecio,"
                + "unidades.nombre AS nombremedida,ubicaciones.nombre nombreubicacion,proveedores.nombre AS nombreproveedor,familias.nombre as nombrefamilia "
                + " FROM productos "
                + " LEFT OUTER JOIN paises "
                + " ON paises.codigo=productos.paises "
                + " LEFT OUTER JOIN rubros "
                + " ON rubros.codigo=productos.rubro "
                + " LEFT OUTER JOIN marcas "
                + " ON marcas.codigo=productos.marca "
                + " LEFT OUTER JOIN unidades "
                + " ON unidades.codigo=productos.medida "
                + " LEFT OUTER JOIN ubicaciones "
                + " ON ubicaciones.codigo=productos.ubicacion "
                + " LEFT OUTER JOIN familias "
                + " ON familias.codigo=productos.codfamilia "
                + " LEFT OUTER JOIN proveedores "
                + " ON proveedores.codigo=productos.proveedor "
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                pais pa = new pais();
                marca m = new marca();
                medida un = new medida();
                familia f = new familia();
                ubicacion ub = new ubicacion();
                proveedor pv = new proveedor();
                rubro ru = new rubro();

                pr.setRubro(ru);
                pr.setPaises(pa);
                pr.setMarca(m);
                pr.setMedida(un);
                pr.setFamilia(f);
                pr.setUbicacion(ub);
                pr.setProveedor(pv);

                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.getRubro().setCodigo(rs.getInt("rubro"));
                pr.getRubro().setNombre(rs.getString("nombrerubro"));
                pr.getFamilia().setCodigo(rs.getInt("codfamilia"));
                pr.getFamilia().setNombre(rs.getString("nombrefamilia"));
                pr.getMarca().setCodigo(rs.getInt("marca"));
                pr.getMarca().setNombre(rs.getString("nombremarca"));
                pr.getMedida().setCodigo(rs.getInt("medida"));
                pr.getMedida().setNombre(rs.getString("nombremedida"));
                pr.getUbicacion().setCodigo(rs.getInt("ubicacion"));
                pr.getUbicacion().setNombre(rs.getString("nombreubicacion"));
                pr.getProveedor().setCodigo(rs.getInt("proveedor"));
                pr.getProveedor().setNombre(rs.getString("nombreproveedor"));
                pr.getPaises().setCodigo(rs.getInt("paises"));
                pr.getPaises().setNombre(rs.getString("nombrepais"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setPrecioventa(rs.getBigDecimal("precioventa"));
                pr.setStockminimo(rs.getBigDecimal("stockminimo"));
                pr.setIncremento1(rs.getBigDecimal("incremento1"));
                pr.setIncremento2(rs.getBigDecimal("incremento2"));
                pr.setIncremento3(rs.getBigDecimal("incremento3"));
                pr.setSugerido1(rs.getBigDecimal("sugerido1"));
                pr.setSugerido2(rs.getBigDecimal("sugerido2"));
                pr.setSugerido3(rs.getBigDecimal("sugerido3"));
                pr.setFechacompra(rs.getDate("fechacompra"));
                pr.setTipo_producto(rs.getInt("tipo_producto"));
                pr.setConteomayorista(rs.getInt("conteomayorista"));
                pr.setKit(rs.getInt("kit"));
                pr.setCodigobarra(rs.getString("codigobarra"));
                pr.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                pr.setEstado(rs.getInt("estado"));
                pr.setConversion(rs.getBigDecimal("conversion"));
                pr.setObservacion(rs.getString("observacion"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        return lista;
    }

    public ArrayList<producto> todoslista() throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT productos.codigo,productos.nombre,productos.rubro,"
                + "productos.costo,productos.precio_maximo,productos.precio_minimo,productos.precioventa,"
                + "codigobarra,estado,rubros.nombre as nombrerubro,productos.rubro,"
                + "marcas.nombre as nombremarca,ubicaciones.nombre as nombreubicacion,"
                + "unidades.nombre as nombreunidad,productos.stock "
                + " FROM productos "
                + " LEFT JOIN rubros "
                + " ON rubros.codigo=productos.rubro "
                + " LEFT JOIN marcas "
                + " ON marcas.codigo=productos.marca "
                + " LEFT JOIN ubicaciones "
                + " ON ubicaciones.codigo=productos.ubicacion "
                + " LEFT JOIN unidades "
                + " ON unidades.codigo=productos.medida "
                + " ORDER BY productos.nombre ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                rubro ru = new rubro();
                marca ma = new marca();
                ubicacion ub = new ubicacion();
                medida me = new medida();
                pr.setRubro(ru);
                pr.setMarca(ma);
                pr.setUbicacion(ub);
                pr.setMedida(me);

                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setPrecioventa(rs.getBigDecimal("precioventa"));
                pr.setCodigobarra(rs.getString("codigobarra"));
                pr.setEstado(rs.getInt("estado"));
                pr.getRubro().setCodigo(rs.getInt("rubro"));
                pr.getRubro().setNombre(rs.getString("nombrerubro"));
                pr.getMarca().setNombre(rs.getString("nombremarca"));
                pr.getMedida().setNombre(rs.getString("nombreunidad"));
                pr.getUbicacion().setNombre(rs.getString("nombreubicacion"));
                pr.setStock(rs.getBigDecimal("stock"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();

        }
        return lista;
    }

    public ArrayList<producto> todosbasico() throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precio_maximo,"
                + "productos.precioventa,"
                + "productos.precio_minimo,stockminimo,"
                + "productos.conteomayorista,productos.codigobarra,productos.incremento1,"
                + "productos.incremento2,"
                + "productos.ivaporcentaje,productos.estado,productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.estado=1 "
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecioventa(rs.getBigDecimal("precioventa"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setIncremento1(rs.getBigDecimal("incremento1"));
                pr.setIncremento2(rs.getBigDecimal("incremento2"));
                pr.setCodigobarra(rs.getString("codigobarra"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setConteomayorista(rs.getInt("conteomayorista"));
                pr.setEstado(rs.getInt("estado"));
                System.out.println(rs.getBigDecimal("precioventa"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        return lista;
    }

    public producto BuscarProductoBasico(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre,"
                + "productos.costo,productos.precioventa,"
                + "productos.precio_maximo,productos.precio_minimo,"
                + "stockminimo,"
                + "productos.conteomayorista,productos.codigobarra,"
                + "productos.ivaporcentaje,productos.estado,productos.incremento1,"
                + "productos.incremento2,productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.estado=1 AND productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
//          ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setPrecioventa(rs.getBigDecimal("precioventa"));
                pr.setIncremento1(rs.getBigDecimal("incremento1"));
                pr.setIncremento2(rs.getBigDecimal("incremento2"));
                pr.setCodigobarra(rs.getString("codigobarra"));
                pr.setConteomayorista(rs.getInt("conteomayorista"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setEstado(rs.getInt("estado"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    public producto BuscarProductoBasicoBarra(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precioventa,"
                + "productos.precio_maximo,productos.precio_minimo,stockminimo,"
                + "productos.conteomayorista,productos.codigobarra,"
                + "productos.ivaporcentaje,productos.estado,productos.incremento1,"
                + "productos.incremento2,productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.estado=1 AND productos.codigobarra='" + id + "'"
                + " ORDER BY productos.codigobarra ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
//          ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setPrecioventa(rs.getBigDecimal("precioventa"));
                pr.setIncremento1(rs.getBigDecimal("incremento1"));
                pr.setIncremento2(rs.getBigDecimal("incremento2"));
                pr.setCodigobarra(rs.getString("codigobarra"));
                pr.setConteomayorista(rs.getInt("conteomayorista"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setEstado(rs.getInt("estado"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    public producto BuscarProducto(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre,paises,productos.rubro,marca,medida,ubicacion,proveedor,costo,precio_maximo,precio_minimo,precioventa,stockminimo,"
                + "incremento1,incremento2,incremento3,sugerido1,sugerido2,sugerido3,fechacompra,tipo_producto,conteomayorista,kit,codigobarra,"
                + "fecha_ingreso,productos.estado,conversion,stockactual,stock,observacion,stocksistema,ivaporcentaje,verificado,fechahecho,"
                + "paises.nombre AS nombrepais,rubros.nombre AS nombrerubro,marcas.nombre AS nombremarca,productos.codfamilia,productos.cambiarprecio,"
                + "unidades.nombre AS nombremedida,ubicaciones.nombre nombreubicacion,proveedores.nombre AS nombreproveedor,familias.nombre as nombrefamilia, "
                + "productos.amperaje,productos.voltios,productos.largo,productos.ancho,productos.alto "
                + " FROM productos "
                + " LEFT OUTER JOIN paises "
                + " ON paises.codigo=productos.paises "
                + " LEFT OUTER JOIN rubros "
                + " ON rubros.codigo=productos.rubro "
                + " LEFT OUTER JOIN marcas "
                + " ON marcas.codigo=productos.marca "
                + " LEFT OUTER JOIN unidades "
                + " ON unidades.codigo=productos.medida "
                + " LEFT OUTER JOIN ubicaciones "
                + " ON ubicaciones.codigo=productos.ubicacion "
                + " LEFT OUTER JOIN familias "
                + " ON familias.codigo=productos.codfamilia "
                + " LEFT OUTER JOIN proveedores "
                + " ON proveedores.codigo=productos.proveedor "
                + " WHERE productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pais pa = new pais();
                marca m = new marca();
                medida un = new medida();
                familia f = new familia();
                ubicacion ub = new ubicacion();
                rubro ru = new rubro();
                proveedor pv = new proveedor();

                pr.setPaises(pa);
                pr.setMarca(m);
                pr.setMedida(un);
                pr.setFamilia(f);
                pr.setUbicacion(ub);
                pr.setRubro(ru);
                pr.setProveedor(pv);

                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.getRubro().setCodigo(rs.getInt("rubro"));
                pr.getRubro().setNombre(rs.getString("nombrerubro"));
                pr.getFamilia().setCodigo(rs.getInt("codfamilia"));
                pr.getFamilia().setNombre(rs.getString("nombrefamilia"));
                pr.getMarca().setCodigo(rs.getInt("marca"));
                pr.getMarca().setNombre(rs.getString("nombremarca"));
                pr.getMedida().setCodigo(rs.getInt("medida"));
                pr.getMedida().setNombre(rs.getString("nombremedida"));
                pr.getUbicacion().setCodigo(rs.getInt("ubicacion"));
                pr.getUbicacion().setNombre(rs.getString("nombreubicacion"));
                pr.getProveedor().setCodigo(rs.getInt("proveedor"));
                pr.getProveedor().setNombre(rs.getString("nombreproveedor"));
                pr.getPaises().setCodigo(rs.getInt("paises"));
                pr.getPaises().setNombre(rs.getString("nombrepais"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setPrecioventa(rs.getBigDecimal("precioventa"));
                pr.setStockminimo(rs.getBigDecimal("stockminimo"));
                pr.setIncremento1(rs.getBigDecimal("incremento1"));
                pr.setIncremento2(rs.getBigDecimal("incremento2"));
                pr.setIncremento3(rs.getBigDecimal("incremento3"));
                pr.setSugerido1(rs.getBigDecimal("sugerido1"));
                pr.setSugerido2(rs.getBigDecimal("sugerido2"));
                pr.setSugerido3(rs.getBigDecimal("sugerido3"));
                pr.setFechacompra(rs.getDate("fechacompra"));
                pr.setTipo_producto(rs.getInt("tipo_producto"));
                pr.setConteomayorista(rs.getInt("conteomayorista"));
                pr.setKit(rs.getInt("kit"));
                pr.setCodigobarra(rs.getString("codigobarra"));
                pr.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                pr.setEstado(rs.getInt("estado"));
                pr.setConversion(rs.getBigDecimal("conversion"));
                pr.setObservacion(rs.getString("observacion"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setAmperaje(rs.getDouble("amperaje"));
                pr.setVoltios(rs.getDouble("voltios"));
                pr.setLargo(rs.getDouble("largo"));
                pr.setAncho(rs.getDouble("ancho"));
                pr.setAlto(rs.getDouble("alto"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    public producto insertarProducto(producto p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO productos (codigo,nombre,rubro,marca,medida,costo,ubicacion,"
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
        st.close();
        conn.close();
        return p;
    }

    public boolean actualizarProductos(producto p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE productos SET nombre=?,rubro=?,marca=?,medida=?,costo=?,ubicacion=?,"
                + "precio_maximo=?,precio_minimo=?,tipo_producto=?,stockminimo=?,estado=?,"
                + "conteomayorista=?,incremento1=?,incremento2=?,"
                + "codigobarra=?,conversion=?,paises=?,proveedor=?,cambiarprecio=?,codfamilia=?,"
                + "ivaporcentaje=?,observacion=?,cambiarprecio=?,precioventa=?,fecha_ingreso=? WHERE codigo= '" + p.getCodigo() + "'");
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

    public boolean eliminarProducto(String cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM productos WHERE codigo='" + cod + "'");
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

    public producto BuscarStock(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre,productos.costo,stock.stock,productos.tipo_producto"
                + " FROM productos "
                + " LEFT JOIN stock "
                + " ON stock.producto=productos.codigo "
                + " WHERE productos.estado=1 AND productos.tipo_producto<6 AND productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pr.setCodigo(rs.getString("codigo"));
                pr.setTipo_producto(rs.getInt("tipo_producto"));
                if (pr.getTipo_producto() < 6) {
                    pr.setStocksistema(rs.getDouble("stock"));
                } else {
                    pr.setStocksistema(0.00);
                }
                pr.setNombre(rs.getString("nombre"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    public ArrayList<producto> inventarioxrubro(int nSucursal, int nSuc, int nRubro, int nRub) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT stock.sucursal,stock.stock,productos.codigo AS codprod,productos.nombre AS codnombre,productos.precio_minimo AS mayorista,precio_maximo AS minorista,"
                    + " rubros.codigo AS codrubro, rubros.nombre AS nombrerubro,productos.costo,unidades.codigo AS codunidad,unidades.nombre AS nombreunidad  "
                    + "FROM productos "
                    + "INNER JOIN stock "
                    + "ON productos.codigo=stock.producto "
                    + "INNER JOIN unidades "
                    + "ON unidades.codigo=productos.medida "
                    + "INNER JOIN rubros "
                    + "ON rubros.codigo=productos.rubro "
                    + "INNER JOIN sucursales "
                    + "ON stock.sucursal=sucursales.codigo "
                    + " WHERE IF(?<>0,stock.sucursal=?,TRUE) "
                    + " AND IF(?<>0,productos.rubro=?,TRUE) "
                    + " AND productos.tipo_producto<7   and stock.stock<>0 "
                    + " ORDER BY stock.sucursal,productos.rubro,productos.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nSucursal);
                ps.setInt(2, nSuc);
                ps.setInt(3, nRubro);
                ps.setInt(4, nRub);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    stock stock = new stock();
                    medida medida = new medida();
                    rubro rubro = new rubro();
                    producto pro = new producto();
                    sucursal suc = new sucursal();

                    pro.setStocks(stock);
                    pro.setMedida(medida);
                    pro.setRubro(rubro);

                    pro.getStocks().setStock(rs.getDouble("stock"));
                    pro.setCodigo(rs.getString("codprod"));
                    pro.setNombre(rs.getString("codnombre"));
                    pro.setPrecio_minimo(rs.getBigDecimal("mayorista"));
                    pro.setPrecio_maximo(rs.getBigDecimal("minorista"));
                    pro.getRubro().setCodigo(rs.getInt("codrubro"));
                    pro.getRubro().setNombre(rs.getString("nombrerubro"));
                    pro.setCosto(rs.getBigDecimal("costo"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunidad"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));

                    lista.add(pro);
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

    public ArrayList<producto> inventarioxfamilia(int nSucursal, int nSuc, int nRubro, int nRub) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT stock.sucursal,stock.stock,productos.codigo AS codprod,productos.nombre AS codnombre,productos.precio_minimo AS mayorista,precio_maximo AS minorista,"
                    + "productos.codfamilia,familias.nombre as nombrefamilia,productos.costo,unidades.codigo AS codunidad,unidades.nombre AS nombreunidad  "
                    + "FROM productos "
                    + "INNER JOIN stock "
                    + "ON productos.codigo=stock.producto "
                    + "INNER JOIN unidades "
                    + "ON unidades.codigo=productos.medida "
                    + "INNER JOIN familias "
                    + "ON familias.codigo=productos.codfamilia "
                    + "INNER JOIN sucursales "
                    + "ON stock.sucursal=sucursales.codigo "
                    + " WHERE IF(?<>0,stock.sucursal=?,TRUE) "
                    + " AND IF(?<>0,productos.codfamilia=?,TRUE) "
                    + " AND productos.tipo_producto<7  and stock.stock<>0"
                    + " ORDER BY stock.sucursal,productos.codfamilia,productos.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nSucursal);
                ps.setInt(2, nSuc);
                ps.setInt(3, nRubro);
                ps.setInt(4, nRub);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    stock stock = new stock();
                    medida medida = new medida();
                    familia fam = new familia();
                    producto pro = new producto();
                    sucursal suc = new sucursal();

                    pro.setStocks(stock);
                    pro.setMedida(medida);
                    pro.setFamilia(fam);

                    pro.getStocks().setStock(rs.getDouble("stock"));
                    pro.setCodigo(rs.getString("codprod"));
                    pro.setNombre(rs.getString("codnombre"));
                    pro.setPrecio_minimo(rs.getBigDecimal("mayorista"));
                    pro.setPrecio_maximo(rs.getBigDecimal("minorista"));
                    pro.getFamilia().setCodigo(rs.getInt("codfamilia"));
                    pro.getFamilia().setNombre(rs.getString("nombrefamilia"));
                    pro.setCosto(rs.getBigDecimal("costo"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunidad"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));

                    lista.add(pro);
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

    public producto BuscarIdServer(String id) throws SQLException {
        producto verprod = new producto();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        String sql = "SELECT productos.codigo "
                + " FROM productos "
                + " WHERE productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                verprod.setCodigo(rs.getString("codigo"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return verprod;
    }

    public ArrayList<producto> inventariorubroxfecha(int nSucursal, int nRubro, Date fechafin, int ntipo) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = null;
            if (nSucursal != 0) {
                cSql = "SELECT a.rubro AS codrubro,a.medida AS codunidad,unidades.nombre AS nombreunid,"
                        + "COALESCE(a.costo,0) AS costo,"
                        + "COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,"
                        + "COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                        + "COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) as salidas,"
                        + "COALESCE(te.transEntrada,0) AS transEntrada,"
                        + "COALESCE(ts.transSalida,0) AS  transSalida,"
                        + "COALESCE(cp.costopromedio,0) AS costopromedio,"
                        + "rubros.nombre AS nombrerubro "
                        + "FROM productos a "
                        + "LEFT JOIN unidades "
                        + "ON unidades.codigo=a.medida "
                        + "LEFT JOIN rubros "
                        + "ON rubros.codigo=a.rubro "
                        + "LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, "
                        + "SUM(detalle_compras.cantidad) AS compras "
                        + "FROM detalle_compras "
                        + "INNER JOIN cabecera_compras "
                        + "ON cabecera_compras.creferencia=detalle_compras.dreferencia "
                        + " WHERE cabecera_compras.sucursal=" + nSucursal + " "
                        + " AND cabecera_compras.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, "
                        + "SUM(detalle_ventas.cantidad*-1) AS ventas "
                        + "FROM detalle_ventas "
                        + "INNER JOIN cabecera_ventas "
                        + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                        + " WHERE cabecera_ventas.sucursal=" + nSucursal + " AND cabecera_ventas.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo "
                        + "LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo,"
                        + " SUM(detalle_ajuste_mercaderias.cantidad) AS entradas "
                        + "FROM detalle_ajuste_mercaderias "
                        + "INNER JOIN ajuste_mercaderias "
                        + "ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                        + " WHERE ajuste_mercaderias.sucursal=" + nSucursal + " AND ajuste_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,"
                        + "detalle_salida_mercaderias.producto AS codigo,"
                        + " SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas "
                        + "FROM detalle_salida_mercaderias "
                        + "INNER JOIN cabecera_salida_mercaderias "
                        + "ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                        + " WHERE cabecera_salida_mercaderias.sucursal=" + nSucursal + " AND cabecera_salida_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo "
                        + "LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo,"
                        + " SUM(detalle_transferencias.cantidad) AS TransEntrada "
                        + "FROM detalle_transferencias "
                        + "INNER JOIN transferencias "
                        + "ON transferencias.idtransferencia=detalle_transferencias.dreferencia "
                        + " WHERE transferencias.destino=" + nSucursal + " AND transferencias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_transferencias.producto)te ON te.codigo=a.codigo "
                        + "LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, "
                        + "SUM(detalle_transferencias.cantidad*-1) AS TransSalida "
                        + "FROM detalle_transferencias "
                        + "INNER JOIN transferencias "
                        + "ON transferencias.idtransferencia=detalle_transferencias.dreferencia "
                        + " WHERE transferencias.origen=" + nSucursal + " AND transferencias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_transferencias.producto)ts ON ts.codigo=a.codigo "
                        + "LEFT JOIN (SELECT costo_compras.codprod, AVG(costo_compras.costo) AS costopromedio "
                        + "FROM costo_compras "
                        + "WHERE costo_compras.fecha<= '" + fechafin + "' "
                        + "GROUP BY costo_compras.codprod) cp ON cp.codprod=a.codigo "
                        + " WHERE a.estado=1 and a.tipo_producto<6 "
                        + " AND IF(" + nRubro + "<>0,a.rubro=" + nRubro + ",TRUE)"
                        + " GROUP BY a.codigo "
                        + " ORDER BY a.rubro,a.nombre ";
            } else {
                cSql = "SELECT a.rubro AS codrubro,a.medida AS codunidad,unidades.nombre AS nombreunid,"
                        + "COALESCE(a.costo,0) AS costo,"
                        + "COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,"
                        + "COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                        + "COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) as salidas,"
                        + "0.00 AS transEntrada,0.00 AS  transSalida,"
                        + "COALESCE(cp.costopromedio,0) AS costopromedio,rubros.nombre AS nombrerubro "
                        + "FROM productos a "
                        + "LEFT JOIN unidades "
                        + "ON unidades.codigo=a.medida "
                        + "LEFT JOIN rubros "
                        + "ON rubros.codigo=a.rubro "
                        + "LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, "
                        + "SUM(detalle_compras.cantidad) AS compras "
                        + "FROM detalle_compras "
                        + "INNER JOIN cabecera_compras "
                        + "ON cabecera_compras.creferencia=detalle_compras.dreferencia "
                        + " WHERE  cabecera_compras.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, "
                        + "SUM(detalle_ventas.cantidad*-1) AS ventas "
                        + "FROM detalle_ventas "
                        + "INNER JOIN cabecera_ventas "
                        + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                        + " WHERE cabecera_ventas.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo "
                        + "LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo,"
                        + " SUM(detalle_ajuste_mercaderias.cantidad) AS entradas "
                        + "FROM detalle_ajuste_mercaderias "
                        + "INNER JOIN ajuste_mercaderias "
                        + "ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                        + " WHERE ajuste_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,detalle_salida_mercaderias.producto AS codigo, "
                        + "SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas "
                        + "FROM detalle_salida_mercaderias "
                        + "INNER JOIN cabecera_salida_mercaderias "
                        + "ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                        + " WHERE  cabecera_salida_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo "
                        + "LEFT JOIN (SELECT costo_compras.codprod, AVG(costo_compras.costo) AS costopromedio "
                        + "FROM costo_compras "
                        + "WHERE costo_compras.fecha<= '" + fechafin + "' "
                        + "GROUP BY costo_compras.codprod) cp ON cp.codprod=a.codigo "
                        + " WHERE a.estado=1  and a.tipo_producto<6 "
                        + " AND IF(" + nRubro + "<>0,a.rubro=" + nRubro + ",TRUE)"
                        + " GROUP BY a.codigo "
                        + " ORDER BY a.rubro,a.nombre ";
            }
            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    medida medida = new medida();
                    rubro rubro = new rubro();
                    producto pro = new producto();

                    pro.setMedida(medida);
                    pro.setRubro(rubro);

                    pro.getRubro().setCodigo(rs.getInt("codrubro"));
                    pro.getRubro().setNombre(rs.getString("nombrerubro"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunid"));
                    pro.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                    pro.setCodigo(rs.getString("codigo"));
                    pro.setNombre(rs.getString("nombre"));
                    pro.setCosto(rs.getBigDecimal("costopromedio"));
                    if (pro.getCosto().doubleValue() == 0) {
                        pro.setCosto(rs.getBigDecimal("costo"));
                    }
                    pro.setCompras(rs.getDouble("compras"));
                    pro.setVentas(rs.getDouble("ventas"));
                    pro.setEntrada(rs.getDouble("entradas"));
                    pro.setSalida(rs.getDouble("salidas"));
                    pro.setTransEntrada(rs.getDouble("transEntrada"));
                    pro.setTransSalida(rs.getDouble("transSalida"));

                    pro.setStock(BigDecimal.valueOf(pro.getCompras())
                            .add(BigDecimal.valueOf(pro.getVentas())
                                    .add(BigDecimal.valueOf(pro.getEntrada()))
                                    .add(BigDecimal.valueOf(pro.getSalida()))
                                    .add(BigDecimal.valueOf(pro.getTransEntrada()))
                                    .add(BigDecimal.valueOf(pro.getTransSalida()))));

                    System.out.println(pro.getStock());
                    if (ntipo == 1) {
                        lista.add(pro);
                    } else {
                        if (pro.getStock().doubleValue() != 0) {
                            lista.add(pro);
                        }
                    }
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

    public ArrayList<producto> movimiento_de_mercaderia(int nSucursal, Date fechaini, Date fechafin) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT a.rubro AS codrubro,rubros.nombre AS nombrerubro,a.medida AS codunidad,unidades.nombre AS nombreunid,COALESCE(a.costo,0) AS costo,COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                    + "COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) as salidas,COALESCE(te.transEntrada,0) AS transEntrada,COALESCE(ts.transSalida,0) AS  transSalida "
                    + "FROM productos a "
                    + "LEFT JOIN unidades "
                    + "ON unidades.codigo=a.medida "
                    + "LEFT JOIN rubros "
                    + "ON rubros.codigo=a.rubro "
                    + "LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, SUM(detalle_compras.cantidad) AS compras "
                    + "FROM detalle_compras "
                    + "INNER JOIN cabecera_compras "
                    + "ON cabecera_compras.creferencia=detalle_compras.dreferencia "
                    + " WHERE cabecera_compras.sucursal=" + nSucursal + " AND cabecera_compras.fecha>='" + fechaini + "' AND cabecera_compras.fecha<='" + fechafin + "'"
                    + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo "
                    + "LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, SUM(detalle_ventas.cantidad*-1) AS ventas "
                    + "FROM detalle_ventas "
                    + "INNER JOIN cabecera_ventas "
                    + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                    + " WHERE cabecera_ventas.sucursal=" + nSucursal + " AND cabecera_ventas.fecha>='" + fechaini + "' AND cabecera_ventas.fecha<='" + fechafin + "'"
                    + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo "
                    + "LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo, SUM(detalle_ajuste_mercaderias.cantidad) AS entradas "
                    + "FROM detalle_ajuste_mercaderias "
                    + "INNER JOIN ajuste_mercaderias "
                    + "ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                    + " WHERE ajuste_mercaderias.sucursal=" + nSucursal + " AND ajuste_mercaderias.fecha>='" + fechaini + "' AND ajuste_mercaderias.fecha<='" + fechafin + "'"
                    + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo "
                    + "LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,detalle_salida_mercaderias.producto AS codigo, SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas "
                    + "FROM detalle_salida_mercaderias "
                    + "INNER JOIN cabecera_salida_mercaderias "
                    + "ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                    + " WHERE cabecera_salida_mercaderias.sucursal=" + nSucursal + " AND cabecera_salida_mercaderias.fecha>='" + fechaini + "' AND cabecera_salida_mercaderias.fecha<='" + fechafin + "'"
                    + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo "
                    + "LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, SUM(detalle_transferencias.cantidad) AS TransEntrada "
                    + "FROM detalle_transferencias "
                    + "INNER JOIN transferencias "
                    + "ON transferencias.idtransferencia=detalle_transferencias.dreferencia "
                    + " WHERE transferencias.destino=" + nSucursal + " AND transferencias.fecha>='" + fechaini + "' AND transferencias.fecha<='" + fechafin + "'"
                    + " GROUP BY detalle_transferencias.producto)te ON te.codigo=a.codigo "
                    + "LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, SUM(detalle_transferencias.cantidad*-1) AS TransSalida "
                    + "FROM detalle_transferencias "
                    + "INNER JOIN transferencias "
                    + "ON transferencias.idtransferencia=detalle_transferencias.dreferencia "
                    + " WHERE transferencias.origen=" + nSucursal + " AND transferencias.fecha>='" + fechaini + "' AND transferencias.fecha<='" + fechafin + "'"
                    + " GROUP BY detalle_transferencias.producto)ts ON ts.codigo=a.codigo "
                    + " WHERE a.estado=1 "
                    + " GROUP BY a.codigo "
                    + " ORDER BY a.rubro,a.nombre ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    medida medida = new medida();
                    rubro rubro = new rubro();
                    producto pro = new producto();

                    pro.setMedida(medida);
                    pro.setRubro(rubro);

                    pro.getRubro().setCodigo(rs.getInt("codrubro"));
                    pro.getRubro().setNombre(rs.getString("nombrerubro"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunid"));
                    pro.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                    pro.setCodigo(rs.getString("codigo"));
                    pro.setNombre(rs.getString("nombre"));
                    pro.setCosto(rs.getBigDecimal("costo"));
                    pro.setCompras(rs.getDouble("compras"));
                    pro.setVentas(rs.getDouble("ventas"));
                    pro.setEntrada(rs.getDouble("entradas"));
                    pro.setSalida(rs.getDouble("salidas"));
                    pro.setTransEntrada(rs.getDouble("transEntrada"));
                    pro.setTransSalida(rs.getDouble("transSalida"));

                    pro.setStock(BigDecimal.valueOf(pro.getCompras())
                            .add(BigDecimal.valueOf(pro.getVentas())
                                    .add(BigDecimal.valueOf(pro.getEntrada()))
                                    .add(BigDecimal.valueOf(pro.getSalida()))
                                    .add(BigDecimal.valueOf(pro.getTransEntrada()))
                                    .add(BigDecimal.valueOf(pro.getTransSalida()))));

                    lista.add(pro);
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

    public ArrayList<producto> inventarioxmarca(int nSucursal, int nSuc, int nMarca, int nMar) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT \n"
                    + "    stock.sucursal, \n"
                    + "    stock.stock, \n"
                    + "    productos.codigo AS codprod, \n"
                    + "    productos.nombre AS codnombre, \n"
                    + "    productos.precio_minimo AS mayorista, \n"
                    + "    precio_maximo AS minorista, \n"
                    + "    rubros.codigo AS codrubro, \n"
                    + "    rubros.nombre AS nombrerubro,\n"
                    + "    productos.costo,\n"
                    + "    unidades.codigo AS codunidad,\n"
                    + "    unidades.nombre AS nombreunidad,  \n"
                    + "    marcas.nombre AS marcanombre, \n"
                    + "    marcas.codigo AS marcacodigo  \n"
                    + "FROM productos \n"
                    + "	INNER JOIN stock ON productos.codigo=stock.producto \n"
                    + "	INNER JOIN unidades ON unidades.codigo=productos.medida \n"
                    + "	INNER JOIN rubros ON rubros.codigo=productos.rubro \n"
                    + "	INNER JOIN sucursales ON stock.sucursal=sucursales.codigo \n"
                    + "	INNER JOIN marcas ON marcas.codigo=productos.marca\n"
                    + "WHERE \n"
                    + "	IF(?<>0,stock.sucursal=?,TRUE) \n"
                    + "    AND IF(?<>0,productos.marca=?,TRUE) \n"
                    + "    AND productos.tipo_producto<7\n"
                    + "    and stock.stock<>0 \n"
                    + "ORDER BY stock.sucursal,productos.marca,productos.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nSucursal);
                ps.setInt(2, nSuc);
                ps.setInt(3, nMarca);
                ps.setInt(4, nMar);
                //System.out.println(ps.toString());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    stock stock = new stock();
                    medida medida = new medida();
                    rubro rubro = new rubro();
                    marca marca = new marca();
                    producto pro = new producto();
                    sucursal suc = new sucursal();

                    pro.setStocks(stock);
                    pro.setMedida(medida);
                    pro.setRubro(rubro);
                    pro.setMarca(marca);

                    pro.getStocks().setStock(rs.getDouble("stock"));
                    pro.setCodigo(rs.getString("codprod"));
                    pro.setNombre(rs.getString("codnombre"));
                    pro.setPrecio_minimo(rs.getBigDecimal("mayorista"));
                    pro.setPrecio_maximo(rs.getBigDecimal("minorista"));
                    pro.getRubro().setCodigo(rs.getInt("codrubro"));
                    pro.getRubro().setNombre(rs.getString("nombrerubro"));

                    pro.getMarca().setCodigo(rs.getInt("marcacodigo"));
                    pro.getMarca().setNombre(rs.getString("marcanombre"));

                    pro.setCosto(rs.getBigDecimal("costo"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunidad"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));

                    lista.add(pro);
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

    public ArrayList<producto> inventarioxubicacion(int nSucursal, int nSuc, int nUbicacion, int nUbi) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String cSql = "SELECT \n"
                    + "    stock.sucursal, \n"
                    + "    stock.stock, \n"
                    + "    productos.codigo AS codprod, \n"
                    + "    productos.nombre AS codnombre, \n"
                    + "    productos.precio_minimo AS mayorista, \n"
                    + "    precio_maximo AS minorista, \n"
                    + "    rubros.codigo AS codrubro, \n"
                    + "    rubros.nombre AS nombrerubro,\n"
                    + "    productos.costo,\n"
                    + "    unidades.codigo AS codunidad,\n"
                    + "    unidades.nombre AS nombreunidad,  \n"
                    + "    ubicaciones.nombre AS ubicacionnombre, \n"
                    + "    ubicaciones.codigo AS ubicacioncodigo  \n"
                    + "FROM productos \n"
                    + "	INNER JOIN stock ON productos.codigo=stock.producto \n"
                    + "	INNER JOIN unidades ON unidades.codigo=productos.medida \n"
                    + "	INNER JOIN rubros ON rubros.codigo=productos.rubro \n"
                    + "	INNER JOIN sucursales ON stock.sucursal=sucursales.codigo \n"
                    + "	INNER JOIN ubicaciones ON ubicaciones.codigo=productos.ubicacion\n"
                    + "WHERE \n"
                    + "	IF(?<>0,stock.sucursal=?,TRUE) \n"
                    + "    AND IF(?<>0,productos.marca=?,TRUE) \n"
                    + "    AND productos.tipo_producto<7\n"
                    + "    and stock.stock<>0 \n"
                    + "ORDER BY stock.sucursal,productos.marca,productos.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, nSucursal);
                ps.setInt(2, nSuc);
                ps.setInt(3, nUbicacion);
                ps.setInt(4, nUbi);
                //System.out.println(ps.toString());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    stock stock = new stock();
                    medida medida = new medida();
                    rubro rubro = new rubro();
                    ubicacion ubicacion = new ubicacion();
                    producto pro = new producto();
                    sucursal suc = new sucursal();

                    pro.setStocks(stock);
                    pro.setMedida(medida);
                    pro.setRubro(rubro);
                    pro.setUbicacion(ubicacion);

                    pro.getStocks().setStock(rs.getDouble("stock"));
                    pro.setCodigo(rs.getString("codprod"));
                    pro.setNombre(rs.getString("codnombre"));
                    pro.setPrecio_minimo(rs.getBigDecimal("mayorista"));
                    pro.setPrecio_maximo(rs.getBigDecimal("minorista"));
                    pro.getRubro().setCodigo(rs.getInt("codrubro"));
                    pro.getRubro().setNombre(rs.getString("nombrerubro"));

                    pro.getUbicacion().setCodigo(rs.getInt("ubicacioncodigo"));
                    pro.getUbicacion().setNombre(rs.getString("ubicacionnombre"));

                    pro.setCosto(rs.getBigDecimal("costo"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunidad"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));

                    lista.add(pro);
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

    public boolean actualizarCostos(producto p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE productos SET costo=?, precio_maximo=?,precio_minimo=?,incremento1=?,incremento2=? WHERE codigo= '" + p.getCodigo() + "'");
        ps.setBigDecimal(1, p.getCosto());
        ps.setBigDecimal(2, p.getPrecio_maximo()); //Precio Minorista
        ps.setBigDecimal(3, p.getPrecio_minimo()); //Precio Mayorista
        ps.setBigDecimal(4, p.getIncremento1()); //
        ps.setBigDecimal(5, p.getIncremento2()); //
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

    public ArrayList<producto> todosmini() throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT productos.codigo,productos.nombre,productos.costo,"
                + "productos.precio_maximo,productos.precioventa,"
                + "productos.precio_minimo,productos.stock,"
                + "productos.ivaporcentaje,productos.estado,"
                + "productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.estado=1 "
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setStock(rs.getBigDecimal("stock"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        return lista;
    }

    public ArrayList<producto> todosminixnombre() throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT productos.codigo,productos.nombre,productos.costo,"
                + "productos.precio_maximo,productos.precioventa,"
                + "productos.precio_minimo,productos.stock,"
                + "productos.ivaporcentaje,productos.estado,"
                + "productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.estado=1 "
                + " ORDER BY productos.nombre ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setStock(rs.getBigDecimal("stock"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        return lista;
    }

    
    
    public producto conteoxajuste(String cProducto) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        producto pro = new producto();
        Connection conn = st.getConnection();
        try {
            String cSql = "SELECT a.rubro AS codrubro,a.medida AS codunidad,unidades.nombre AS nombreunid,COALESCE(a.costo,0) AS costo,"
                    + "COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,"
                    + "COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                    + "COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) as salidas,"
                    + "0.00 AS transEntrada,0.00 AS  transSalida,"
                    + "rubros.nombre AS nombrerubro "
                    + "FROM productos a "
                    + "LEFT JOIN unidades "
                    + "ON unidades.codigo=a.medida "
                    + "LEFT JOIN rubros "
                    + "ON rubros.codigo=a.rubro "
                    + "LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, SUM(detalle_compras.cantidad) AS compras "
                    + "FROM detalle_compras "
                    + "INNER JOIN cabecera_compras "
                    + "ON cabecera_compras.creferencia=detalle_compras.dreferencia "
                    + " WHERE  detalle_compras.codprod='" + cProducto + "'"
                    + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo "
                    + "LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, SUM(detalle_ventas.cantidad*-1) AS ventas "
                    + "FROM detalle_ventas "
                    + "INNER JOIN cabecera_ventas "
                    + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                    + " WHERE detalle_ventas.codprod='" + cProducto + "'"
                    + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo "
                    + "LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo, SUM(detalle_ajuste_mercaderias.cantidad) AS entradas "
                    + "FROM detalle_ajuste_mercaderias "
                    + "INNER JOIN ajuste_mercaderias "
                    + "ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                    + " WHERE detalle_ajuste_mercaderias.producto='" + cProducto + "'"
                    + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo "
                    + "LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,detalle_salida_mercaderias.producto AS codigo, SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas "
                    + "FROM detalle_salida_mercaderias "
                    + "INNER JOIN cabecera_salida_mercaderias "
                    + "ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                    + " WHERE  detalle_salida_mercaderias.producto='" + cProducto + "'"
                    + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo "
                    + " WHERE a.codigo='" + cProducto + "'"
                    + " GROUP BY a.codigo "
                    + " ORDER BY a.codigo ";

            System.out.println(cSql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    pro.setCodigo(rs.getString("codigo"));
                    pro.setNombre(rs.getString("nombre"));
                    pro.setCompras(rs.getDouble("compras"));
                    pro.setVentas(rs.getDouble("ventas"));
                    pro.setEntrada(rs.getDouble("entradas"));
                    pro.setSalida(rs.getDouble("salidas"));

                    pro.setStock(BigDecimal.valueOf(pro.getCompras())
                            .add(BigDecimal.valueOf(pro.getVentas())
                                    .add(BigDecimal.valueOf(pro.getEntrada()))
                                    .add(BigDecimal.valueOf(pro.getSalida()))));

                    System.out.println(pro.getStock());
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {

            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pro;
    }

    public ArrayList<producto> inventarioxrubro(int nSucursal, int nRubro, Date fechafin, int ntipo) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String cSql = null;
            if (nSucursal != 0) {
                cSql = "SELECT a.rubro AS codrubro,a.medida AS codunidad,unidades.nombre AS nombreunid,COALESCE(a.costo,0) AS costo,"
                        + "COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,"
                        + "COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                        + "COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) as salidas,"
                        + "COALESCE(te.transEntrada,0) AS transEntrada,COALESCE(ts.transSalida,0) AS  transSalida,"
                        + "a.costo AS costopromedio,rubros.nombre AS nombrerubro "
                        + "FROM productos a "
                        + "LEFT JOIN unidades "
                        + "ON unidades.codigo=a.medida "
                        + "LEFT JOIN rubros "
                        + "ON rubros.codigo=a.rubro "
                        + "LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, SUM(detalle_compras.cantidad) AS compras "
                        + "FROM detalle_compras "
                        + "INNER JOIN cabecera_compras "
                        + "ON cabecera_compras.creferencia=detalle_compras.dreferencia "
                        + " WHERE cabecera_compras.sucursal=" + nSucursal + " AND cabecera_compras.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, SUM(detalle_ventas.cantidad*-1) AS ventas "
                        + "FROM detalle_ventas "
                        + "INNER JOIN cabecera_ventas "
                        + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                        + " WHERE cabecera_ventas.sucursal=" + nSucursal + " AND cabecera_ventas.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo "
                        + "LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo, SUM(detalle_ajuste_mercaderias.cantidad) AS entradas "
                        + "FROM detalle_ajuste_mercaderias "
                        + "INNER JOIN ajuste_mercaderias "
                        + "ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                        + " WHERE ajuste_mercaderias.sucursal=" + nSucursal + " AND ajuste_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,detalle_salida_mercaderias.producto AS codigo, SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas "
                        + "FROM detalle_salida_mercaderias "
                        + "INNER JOIN cabecera_salida_mercaderias "
                        + "ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                        + " WHERE cabecera_salida_mercaderias.sucursal=" + nSucursal + " AND cabecera_salida_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo "
                        + "LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, SUM(detalle_transferencias.cantidad) AS TransEntrada "
                        + "FROM detalle_transferencias "
                        + "INNER JOIN transferencias "
                        + "ON transferencias.idtransferencia=detalle_transferencias.dreferencia "
                        + " WHERE transferencias.destino=" + nSucursal + " AND transferencias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_transferencias.producto)te ON te.codigo=a.codigo "
                        + "LEFT JOIN (SELECT transferencias.fecha,detalle_transferencias.producto AS codigo, SUM(detalle_transferencias.cantidad*-1) AS TransSalida "
                        + "FROM detalle_transferencias "
                        + "INNER JOIN transferencias "
                        + "ON transferencias.idtransferencia=detalle_transferencias.dreferencia "
                        + " WHERE transferencias.origen=" + nSucursal + " AND transferencias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_transferencias.producto)ts ON ts.codigo=a.codigo "
                        + " WHERE a.estado=1 and a.tipo_producto<6 "
                        + " AND IF(" + nRubro + "<>0,a.rubro=" + nRubro + ",TRUE)"
                        + " GROUP BY a.codigo "
                        + " ORDER BY a.rubro,a.nombre ";
            } else {
                cSql = "SELECT a.rubro AS codrubro,a.medida AS codunidad,unidades.nombre AS nombreunid,COALESCE(a.costo,0) AS costo,"
                        + "COALESCE(a.precio_minimo,0) AS precio_minimo,a.stock,a.codigo,a.nombre,"
                        + "COALESCE(c.compras,0) AS compras, COALESCE(v.ventas,0) AS ventas,"
                        + "COALESCE(e.entradas,0) AS entradas,COALESCE(s.salidas,0) as salidas,"
                        + "0.00 AS transEntrada,0.00 AS  transSalida,"
                        + "a.costo AS costopromedio,rubros.nombre AS nombrerubro "
                        + "FROM productos a "
                        + "LEFT JOIN unidades "
                        + "ON unidades.codigo=a.medida "
                        + "LEFT JOIN rubros "
                        + "ON rubros.codigo=a.rubro "
                        + "LEFT JOIN (SELECT cabecera_compras.fecha,detalle_compras.codprod AS codigo, SUM(detalle_compras.cantidad) AS compras "
                        + "FROM detalle_compras "
                        + "INNER JOIN cabecera_compras "
                        + "ON cabecera_compras.creferencia=detalle_compras.dreferencia "
                        + " WHERE  cabecera_compras.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_compras.codprod) c ON c.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_ventas.fecha,detalle_ventas.codprod AS codigo, SUM(detalle_ventas.cantidad*-1) AS ventas "
                        + "FROM detalle_ventas "
                        + "INNER JOIN cabecera_ventas "
                        + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                        + " WHERE cabecera_ventas.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ventas.codprod) v ON v.codigo=a.codigo "
                        + "LEFT JOIN (SELECT ajuste_mercaderias.fecha,detalle_ajuste_mercaderias.producto AS codigo, SUM(detalle_ajuste_mercaderias.cantidad) AS entradas "
                        + "FROM detalle_ajuste_mercaderias "
                        + "INNER JOIN ajuste_mercaderias "
                        + "ON ajuste_mercaderias.idreferencia=detalle_ajuste_mercaderias.dreferencia "
                        + " WHERE ajuste_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_ajuste_mercaderias.producto) e ON e.codigo=a.codigo "
                        + "LEFT JOIN (SELECT cabecera_salida_mercaderias.fecha,detalle_salida_mercaderias.producto AS codigo, SUM(detalle_salida_mercaderias.cantidad*-1) AS salidas "
                        + "FROM detalle_salida_mercaderias "
                        + "INNER JOIN cabecera_salida_mercaderias "
                        + "ON cabecera_salida_mercaderias.idreferencia=detalle_salida_mercaderias.dreferencia "
                        + " WHERE  cabecera_salida_mercaderias.fecha<='" + fechafin + "'"
                        + " GROUP BY detalle_salida_mercaderias.producto)s ON s.codigo=a.codigo "
                        + " WHERE a.estado=1  and a.tipo_producto<6 "
                        + " AND IF(" + nRubro + "<>0,a.rubro=" + nRubro + ",TRUE)"
                        + " GROUP BY a.codigo "
                        + " ORDER BY a.rubro,a.nombre ";
            }
            System.out.println(cSql);
            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    medida medida = new medida();
                    rubro rubro = new rubro();
                    producto pro = new producto();

                    pro.setMedida(medida);
                    pro.setRubro(rubro);

                    pro.getRubro().setCodigo(rs.getInt("codrubro"));
                    pro.getRubro().setNombre(rs.getString("nombrerubro"));
                    pro.getMedida().setCodigo(rs.getInt("codunidad"));
                    pro.getMedida().setNombre(rs.getString("nombreunid"));
                    pro.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                    pro.setCodigo(rs.getString("codigo"));
                    pro.setNombre(rs.getString("nombre"));
                    pro.setCosto(rs.getBigDecimal("costopromedio"));
                    if (pro.getCosto().doubleValue() == 0) {
                        pro.setCosto(rs.getBigDecimal("costo"));
                    }
                    pro.setCompras(rs.getDouble("compras"));
                    pro.setVentas(rs.getDouble("ventas"));
                    pro.setEntrada(rs.getDouble("entradas"));
                    pro.setSalida(rs.getDouble("salidas"));
                    pro.setTransEntrada(rs.getDouble("transEntrada"));
                    pro.setTransSalida(rs.getDouble("transSalida"));

                    pro.setStock(BigDecimal.valueOf(pro.getCompras())
                            .add(BigDecimal.valueOf(pro.getVentas())
                                    .add(BigDecimal.valueOf(pro.getEntrada()))
                                    .add(BigDecimal.valueOf(pro.getSalida()))
                                    .add(BigDecimal.valueOf(pro.getTransEntrada()))
                                    .add(BigDecimal.valueOf(pro.getTransSalida()))));

                    System.out.println(pro.getStock());
                    if (ntipo == 1) {
                        lista.add(pro);
                    } else {
                        if (pro.getStock().doubleValue() != 0) {
                            lista.add(pro);
                        }
                    }
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

    public boolean ActualizarCuentaDebe(producto p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE productos SET "
                + " ctadebe=?  WHERE codigo='" + p.getCodigo() + "'");
        ps.setString(1, p.getCtadebe().getCodigo());
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

    public producto BuscarProductoCuenta(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre,productos.ctadebe,"
                + "plan.nombre AS nombrecuenta "
                + " FROM productos "
                + " LEFT JOIN plan"
                + " ON plan.codigo=productos.ctadebe "
                + " WHERE productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";
        System.out.println(sql);

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                plan pl = new plan();
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCtadebe(pl);
                pr.getCtadebe().setCodigo(rs.getString("ctadebe"));
                if (rs.getString("ctadebe").isEmpty()) {
                    pr.getCtadebe().setNombre("");
                } else {
                    pr.getCtadebe().setNombre(rs.getString("nombrecuenta"));
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }

    
    
        public ArrayList<producto> todoServicio() throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT productos.codigo,productos.nombre "
                + " FROM productos "
                + " WHERE productos.estado=1 "
                +" AND tipo_producto=6 "
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        return lista;
    }

    
    public producto BuscarProductoServicio(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre "
                + " FROM productos "
                + " WHERE productos.estado=1 AND tipo_producto=6 "
                + " AND productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pr;
    }


    public ArrayList<producto> todosxrubromini(int nrubro) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT productos.codigo,productos.nombre,productos.costo,"
                + "productos.precio_maximo,productos.incremento1,"
                + "productos.precio_minimo,productos.incremento2,productos.stock,"
                + "productos.ivaporcentaje,productos.estado,"
                + "productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.tipo_producto=1 AND "
                + " productos.estado=1 AND productos.rubro=?"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, nrubro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto pr = new producto();
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.setCosto(rs.getBigDecimal("costo"));
                pr.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setIncremento1(rs.getBigDecimal("incremento1"));
                pr.setIncremento2(rs.getBigDecimal("incremento2"));
                pr.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                pr.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                pr.setCambiarprecio(rs.getInt("cambiarprecio"));
                pr.setStock(rs.getBigDecimal("stock"));
                lista.add(pr);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        return lista;
    }


    public ArrayList<producto> BuscarProductoUbicacion(String id) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        producto pr = new producto();
        String sql = "SELECT productos.codigo,productos.nombre,"
                + "productos.ubicacion,ubicaciones.nombre AS nombreubicacion "
                + " FROM productos "
                + " LEFT OUTER JOIN ubicaciones "
                + " ON ubicaciones.codigo=productos.ubicacion "
                + " WHERE productos.codigo='" + id + "'"
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            System.out.println("--> CONSULTA UBICACION " );

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ubicacion ubi = new ubicacion();
                pr.setUbicacion(ubi);
                pr.setCodigo(rs.getString("codigo"));
                pr.setNombre(rs.getString("nombre"));
                pr.getUbicacion().setCodigo(rs.getInt("ubicacion"));
                pr.getUbicacion().setNombre(rs.getString("nombreubicacion"));
                lista.add(pr);
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
    

    public boolean actualizarPrecios(producto p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE productos SET costo=?,"
                + "precio_maximo=?,precio_minimo=?,"
                + "incremento1=?,incremento2=? "
                + " WHERE codigo= '" + p.getCodigo() + "'");
        ps.setBigDecimal(1, p.getCosto());
        ps.setBigDecimal(2, p.getPrecio_maximo()); //Precio Minorista
        ps.setBigDecimal(3, p.getPrecio_minimo()); //Precio Mayorista
        ps.setBigDecimal(4, p.getIncremento1()); //
        ps.setBigDecimal(5, p.getIncremento2()); //
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



}
