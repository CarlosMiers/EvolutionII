package DAO;

import Clases.Config;
import Conexion.Conexion;
import Conexion.ConexionEspejo;
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

public class ExportarProductosDAO {

    Conexion con = null;
    Statement st = null;

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;

    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";
    
    public boolean EnviarProductos(java.util.Date desde, java.util.Date hasta,int nsuc) throws SQLException {
        System.out.println("Generando Consulta Transferencias");
        Date dFecha;
        boolean valor = true;
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fDesde = formateador.format(desde);
        String fHasta = formateador.format(hasta);
        System.out.println(fDesde + " - " + fHasta);

        try {
            con = new Conexion();
            st = con.conectar();
            Connection conn = st.getConnection();

            conEsp = new ConexionEspejo();
            if (nsuc == 2) {
                stEspejo = conEsp.conectarEspejo(ip2);
            } else if (nsuc == 3) {
                stEspejo = conEsp.conectarEspejo(ip3);
            }
            System.out.println("Espejo "+stEspejo);
            Connection conne = stEspejo.getConnection();

            PreparedStatement psdestino = conne.prepareStatement("");

            // Verificar que existen ventas
            String sqlCabecera = "SELECT codigo,nombre,paises,rubro,medida,ubicacion,"
                    + "costo,precio_maximo,precio_minimo,incremento1,"
                    + "incremento2,tipo_producto,codigobarra,fecha_ingreso,"
                    + "cambiarprecio,ivaporcentaje,codfamilia,estado "
                    + "FROM productos "
                    + " WHERE  fecha_ingreso BETWEEN'" + fDesde + "' AND '" + fHasta + "'"
                    + " ORDER BY codigo ";

            PreparedStatement ps = conn.prepareStatement(sqlCabecera);
            ResultSet cabecera = ps.executeQuery(sqlCabecera);

            while (cabecera.next()) {
                String codigo = cabecera.getString("codigo");
                String nombre = cabecera.getString("nombre");
                int paises = cabecera.getInt("paises");
                int rubro = cabecera.getInt("rubro");
                int medida = cabecera.getInt("medida");
                int ubicacion = cabecera.getInt("ubicacion");
                double costo = cabecera.getDouble("costo");
                double precio_maximo = cabecera.getDouble("precio_maximo");
                double precio_minimo = cabecera.getDouble("precio_minimo");
                double incremento1 = cabecera.getDouble("incremento1");
                double incremento2 = cabecera.getDouble("incremento2");
                int tipo_producto = cabecera.getInt("tipo_producto");
                String codigobarra = cabecera.getString("codigobarra");
                int cambiarprecio = cabecera.getInt("cambiarprecio");
                double ivaporcentaje = cabecera.getDouble("ivaporcentaje");
                int codfamilia = cabecera.getInt("codfamilia");
                int estado = cabecera.getInt("estado");
                Date fecha_ingreso = cabecera.getDate("fecha_ingreso");

                System.out.println("Fecha Transferencia " + cabecera.getDate("fecha_ingreso"));
                System.out.println("Precio  " + cabecera.getDouble("precio_minimo"));
                System.out.println("Precio 2  " + cabecera.getDouble("precio_maximo"));
                // BORRAMOS CABECERA DESTINO
                String sqlConsultar = "SELECT * FROM productos WHERE codigo='" + codigo + "'";
                psdestino = conne.prepareStatement(sqlConsultar);

//              psdestino.executeQuery(sqlConsultar);
                ResultSet pExiste = psdestino.executeQuery(sqlConsultar);

                pExiste.last();
                int encontrado = pExiste.getRow();                
                if (encontrado==1) {
                    String cCodProd = pExiste.getString("codigo");
                    System.out.println("EXISTE " + cCodProd);
                    String sqlAgregarProducto = "UPDATE productos "
                            + "SET nombre=?,"
                            + "paises=?,"
                            + "rubro=?,"
                            + "medida=?,"
                            + "ubicacion=?,"
                            + "costo=?,"
                            + "precio_maximo=?,"
                            + "precio_minimo=?,"
                            + "incremento1=?,"
                            + "incremento2=?,"
                            + "tipo_producto=?,"
                            + "codigobarra=?,"
                            + "fecha_ingreso=?,"
                            + "ivaporcentaje=?,"
                            + "codfamilia=?,"
                            + "estado=? "
                            + " WHERE codigo='" + cCodProd + "'";
                    psdestino = conne.prepareStatement(sqlAgregarProducto);
                    psdestino.setString(1,cabecera.getString("nombre"));
                    psdestino.setInt(2, cabecera.getInt("paises"));
                    psdestino.setInt(3, cabecera.getInt("rubro"));
                    psdestino.setInt(4, cabecera.getInt("medida"));
                    psdestino.setInt(5, cabecera.getInt("ubicacion"));
                    psdestino.setDouble(6, cabecera.getDouble("costo"));
                    psdestino.setDouble(7, cabecera.getDouble("precio_maximo"));
                    psdestino.setDouble(8, cabecera.getDouble("precio_minimo"));
                    psdestino.setDouble(9, cabecera.getDouble("incremento1"));
                    psdestino.setDouble(10, cabecera.getDouble("incremento2"));
                    psdestino.setInt(11, cabecera.getInt("tipo_producto"));
                    psdestino.setString(12, cabecera.getString("codigobarra"));
                    psdestino.setDate(13,  cabecera.getDate("fecha_ingreso"));
                    psdestino.setDouble(14, cabecera.getDouble("ivaporcentaje"));
                    psdestino.setInt(15, cabecera.getInt("codfamilia"));
                    psdestino.setInt(16, cabecera.getInt("estado"));
                    try {
                      //    stEspejo.executeUpdate(sqlAgregarProducto);
                        psdestino.executeUpdate();
                        System.out.println("--> CARGA DE PRODUCTOS " +cabecera.getString("nombre"));
                    } catch (SQLException ex) {
                        System.out.println("--> CARGA DE PRODUCTOS " + ex.getLocalizedMessage());
                    }

                } else {
                    System.out.println("NO EXISTE ");
                    String sqlAgregarProducto = "INSERT INTO productos(codigo,nombre,paises,"
                            + "rubro,medida,ubicacion,"
                            + "costo,precio_maximo,precio_minimo,incremento1,"
                            + "incremento2,tipo_producto,codigobarra,fecha_ingreso,"
                            + "ivaporcentaje,codfamilia,estado) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    psdestino = conne.prepareStatement(sqlAgregarProducto);
                    psdestino.setString(1,cabecera.getString("codigo"));
                    psdestino.setString(2,cabecera.getString("nombre"));
                    psdestino.setInt(3, cabecera.getInt("paises"));
                    psdestino.setInt(4, cabecera.getInt("rubro"));
                    psdestino.setInt(5, cabecera.getInt("medida"));
                    psdestino.setInt(6, cabecera.getInt("ubicacion"));
                    psdestino.setDouble(7, cabecera.getDouble("costo"));
                    psdestino.setDouble(8, cabecera.getDouble("precio_maximo"));
                    psdestino.setDouble(9, cabecera.getDouble("precio_minimo"));
                    psdestino.setDouble(10, cabecera.getDouble("incremento1"));
                    psdestino.setDouble(11, cabecera.getDouble("incremento2"));
                    psdestino.setInt(12, cabecera.getInt("tipo_producto"));
                    psdestino.setString(13, cabecera.getString("codigobarra"));
                    psdestino.setDate(14,  cabecera.getDate("fecha_ingreso"));
                    psdestino.setDouble(15, cabecera.getDouble("ivaporcentaje"));
                    psdestino.setInt(16, cabecera.getInt("codfamilia"));
                    psdestino.setInt(17, cabecera.getInt("estado"));
                    try {
                        psdestino.executeUpdate();
                        System.out.println("--> GRABANDO PRODUCTO " +cabecera.getString("nombre") );
                    } catch (SQLException ex) {
                        System.out.println("--> CARGA DE PRODUCTOS " + ex.getLocalizedMessage());
                    }
                }
            }
            System.out.println("Generando Transferencia de Mercaderias - Fin");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        st.close();
        stEspejo.close();

        return valor;

    }

}
