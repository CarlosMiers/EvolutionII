/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOBACKUP;

import Conexion.ConexionEspejo;
import Modelo.familia;
import Modelo.marca;
import Modelo.medida;
import Modelo.pais;
import Modelo.producto;
import Modelo.proveedor;
import Modelo.rubro;
import Modelo.ubicacion;
import java.sql.Connection;
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
public class ImportaProductoDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<producto> TodosxFecha(Date dFecha, Integer nsuc) throws SQLException {
        ArrayList<producto> lista = new ArrayList<producto>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }
        Connection conne = stEspejo.getConnection();

        String sql = "SELECT productos.codigo,productos.nombre,paises,productos.rubro,marca,medida,ubicacion,proveedor,costo,precio_maximo,precio_minimo,precioventa,stockminimo,"
                + "incremento1,incremento2,incremento3,sugerido1,sugerido2,sugerido3,fechacompra,tipo_producto,conteomayorista,kit,codigobarra,"
                + "fecha_ingreso,productos.estado,conversion,stockactual,stock,observacion,stocksistema,ivaporcentaje,verificado,fechahecho,"
                + "productos.codfamilia,productos.cambiarprecio "
                + " FROM productos "
                + " WHERE productos.fecha_ingreso=? "
                + " ORDER BY productos.codigo ";

        try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dFecha);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto prorigen = new producto();
                pais pa = new pais();
                marca m = new marca();
                medida un = new medida();
                familia f = new familia();
                ubicacion ub = new ubicacion();
                proveedor pv = new proveedor();
                rubro ru = new rubro();

                prorigen.setRubro(ru);
                prorigen.setPaises(pa);
                prorigen.setMarca(m);
                prorigen.setMedida(un);
                prorigen.setFamilia(f);
                prorigen.setUbicacion(ub);
                prorigen.setProveedor(pv);

                prorigen.setCodigo(rs.getString("codigo"));
                prorigen.setNombre(rs.getString("nombre"));
                prorigen.getRubro().setCodigo(rs.getInt("rubro"));
                prorigen.getFamilia().setCodigo(rs.getInt("codfamilia"));
                prorigen.getMarca().setCodigo(rs.getInt("marca"));
                prorigen.getMedida().setCodigo(rs.getInt("medida"));
                prorigen.getUbicacion().setCodigo(rs.getInt("ubicacion"));
                prorigen.getProveedor().setCodigo(rs.getInt("proveedor"));
                prorigen.getPaises().setCodigo(rs.getInt("paises"));
                prorigen.setCosto(rs.getBigDecimal("costo"));
                prorigen.setPrecio_maximo(rs.getBigDecimal("precio_maximo"));
                prorigen.setPrecio_minimo(rs.getBigDecimal("precio_minimo"));
                prorigen.setPrecioventa(rs.getBigDecimal("precioventa"));
                prorigen.setStockminimo(rs.getBigDecimal("stockminimo"));
                prorigen.setIncremento1(rs.getBigDecimal("incremento1"));
                prorigen.setIncremento2(rs.getBigDecimal("incremento2"));
                prorigen.setIncremento3(rs.getBigDecimal("incremento3"));
                prorigen.setSugerido1(rs.getBigDecimal("sugerido1"));
                prorigen.setSugerido2(rs.getBigDecimal("sugerido2"));
                prorigen.setSugerido3(rs.getBigDecimal("sugerido3"));
                prorigen.setFechacompra(rs.getDate("fechacompra"));
                prorigen.setTipo_producto(rs.getInt("tipo_producto"));
                prorigen.setConteomayorista(rs.getInt("conteomayorista"));
                prorigen.setKit(rs.getInt("kit"));
                prorigen.setCodigobarra(rs.getString("codigobarra"));
                prorigen.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                prorigen.setEstado(rs.getInt("estado"));
                prorigen.setConversion(rs.getBigDecimal("conversion"));
                prorigen.setObservacion(rs.getString("observacion"));
                prorigen.setIvaporcentaje(rs.getBigDecimal("ivaporcentaje"));
                prorigen.setCambiarprecio(rs.getInt("cambiarprecio"));
                lista.add(prorigen);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        stEspejo.close();
        conne.close();
        return lista;
    }

}
