/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.concurso;
import Modelo.postulante;
import Modelo.vacancias;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class postulanteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<postulante> Todos() throws SQLException {
        ArrayList<postulante> lista = new ArrayList<postulante>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT codigo,nombres,apellidos,cedula,concurso,vacancia,"
                    + "fechanacimiento,direccion,telefono,estado_civil,"
                    + "conyugue,postulantes.sexo,estado,vacancias.nombrepuesto,"
                    + "postulantes.objetivos_laborales, "
                    + "postulantes.experiencia_profesional, "
                    + "postulantes.preparacion_academica "
                    + "FROM postulantes "
                    + "LEFT JOIN concursos "
                    + "ON concursos.idconcurso=postulantes.concurso "
                    + "LEFT JOIN vacancias "
                    + "ON vacancias.numero=concursos.idvacancia "
                    + "ORDER BY postulantes.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    postulante fi = new postulante();
                    vacancias vac = new vacancias();
                    concurso co = new concurso();

                    fi.setVacancia(vac);
                    fi.setConcurso(co);
                    fi.getVacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                    fi.getConcurso().setIdconcurso(rs.getDouble("concurso"));
                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setEstado(rs.getInt("estado"));
                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(fi);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public postulante MostrarxEstado(int nestado) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        postulante fi = new postulante();
        vacancias vac = new vacancias();
        concurso co = new concurso();
        try {
            String sql = "SELECT codigo,nombres,apellidos,cedula,concurso,vacancia,"
                    + "fechanacimiento,direccion,telefono,estado_civil,"
                    + "conyugue,postulantes.sexo,estado,vacancias.nombrepuesto,"
                    + "postulantes.objetivos_laborales, "
                    + "postulantes.experiencia_profesional, "
                    + "postulantes.preparacion_acedemica, "
                    + "objetivos_laborales,experiencia_profesional,preparacion_academica "
                    + " FROM postulantes "
                    + "LEFT JOIN concursos "
                    + "ON concursos.idconcurso=postulantes.concurso "
                    + "LEFT JOIN vacancias "
                    + "ON vacancias.numero=concursos.idvacancia "
                    + " WHERE postulantes.estado=?"
                    + "ORDER BY postulantes.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nestado);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    fi.setVacancia(vac);
                    fi.setConcurso(co);
                    fi.getVacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                    fi.getConcurso().setIdconcurso(rs.getDouble("concurso"));
                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));

                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return fi;
    }

    public postulante insertarPostulante(postulante ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        int id = 0;
        PreparedStatement fi = null;

        fi = st.getConnection().prepareStatement("INSERT INTO postulantes"
                + " (nombres,apellidos,sexo,"
                + "cedula,fechanacimiento,concurso,"
                + "vacancia,estado_civil,conyugue,"
                + "direccion,telefono,estado"
                + ",objetivos_laborales,experiencia_profesional,preparacion_academica) " //6
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        fi.setString(1, ocr.getNombres());
        fi.setString(2, ocr.getApellidos());
        fi.setInt(3, ocr.getSexo());
        fi.setInt(4, ocr.getCedula());
        fi.setDate(5, ocr.getFechanacimiento());
        fi.setDouble(6, ocr.getConcurso().getIdconcurso());
        fi.setDouble(7, ocr.getVacancia().getNumero());
        fi.setString(8, ocr.getEstado_civil());
        fi.setString(9, ocr.getConyugue());
        fi.setString(10, ocr.getDireccion());
        fi.setString(11, ocr.getTelefono());
        fi.setInt(12, ocr.getEstado());
        fi.setString(13, ocr.getObjetivos_laborales());
        fi.setString(14, ocr.getExperiencia_laboral());
        fi.setString(15, ocr.getPreparacion_academica());
        try {
            fi.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE POSTULANTES " + ex.getLocalizedMessage());
        }
        st.close();
        fi.close();
        return ocr;
    }

    public boolean actualizarPostulante(postulante ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement fi = null;

        fi = st.getConnection().prepareStatement("UPDATE postulantes SET nombres=?,"
                + "apellidos=?,sexo=?,cedula=?,fechanacimiento=?,concurso=?,vacancia=?,"
                + "estado_civil=?,conyugue=?,direccion=?,telefono=?,"
                + "estado=?,"
                + "objetivos_laborales=?, "
                + "experiencia_profesional=?,"
                + "preparacion_academica=? "
                + " WHERE codigo=" + ocr.getCodigo());

        fi.setString(1, ocr.getNombres());
        fi.setString(2, ocr.getApellidos());
        fi.setInt(3, ocr.getSexo());
        fi.setInt(4, ocr.getCedula());
        fi.setDate(5, ocr.getFechanacimiento());
        fi.setDouble(6, ocr.getConcurso().getIdconcurso());
        fi.setDouble(7, ocr.getVacancia().getNumero());
        fi.setString(8, ocr.getEstado_civil());
        fi.setString(9, ocr.getConyugue());
        fi.setString(10, ocr.getDireccion());
        fi.setString(11, ocr.getTelefono());
        fi.setInt(12, ocr.getEstado());
        fi.setString(13, ocr.getObjetivos_laborales());
        fi.setString(14, ocr.getExperiencia_laboral());
        fi.setString(15, ocr.getPreparacion_academica());

        try {
            int rowsUpdated = fi.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> CARGA DE POSTULANTES " + ex.getLocalizedMessage());
        }

        int rowsUpdated = fi.executeUpdate();
        st.close();
        fi.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean borrarFicha(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM postulantes WHERE codigo=?");
        ps.setInt(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public postulante buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        postulante fi = new postulante();
        vacancias vac = new vacancias();
        concurso co = new concurso();
        try {

            String sql = "SELECT codigo,nombres,apellidos,cedula,concurso,vacancia,"
                    + "fechanacimiento,direccion,telefono,estado_civil,"
                    + "conyugue,postulantes.sexo,estado,vacancias.nombrepuesto, "
                    + "objetivos_laborales,experiencia_profesional,preparacion_academica "
                    + "FROM postulantes "
                    + "LEFT JOIN concursos "
                    + "ON concursos.idconcurso=postulantes.concurso "
                    + "LEFT JOIN vacancias "
                    + "ON vacancias.numero=concursos.idvacancia "
                    + " WHERE postulantes.codigo=? "
                    + " ORDER BY postulantes.codigo ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    fi.setVacancia(vac);
                    fi.setConcurso(co);
                    fi.getVacancia().setNombrepuesto(rs.getString("nombrepuesto"));
                    fi.getConcurso().setIdconcurso(rs.getDouble("concurso"));
                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return fi;
    }

    public ArrayList<postulante> ListaAprobados() throws SQLException {
        ArrayList<postulante> lista = new ArrayList<postulante>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT codigo,nombres,apellidos,cedula,concurso,vacancia,"
                    + "fechanacimiento,direccion,telefono,estado_civil,"
                    + "conyugue,x.sexo,estado,"
                    + "x.objetivos_laborales,"
                    + "x.experiencia_profesional,"
                    + "x.preparacion_academica "
                    + " FROM postulantes X"
                    + " WHERE x.estado=4 "
                    + " AND NOT EXISTS "
                    + " (SELECT postulante "
                    + " FROM empleados Y "
                    + " WHERE x.codigo=y.postulante) "
                    + " ORDER BY x.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    postulante fi = new postulante();

                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setEstado(rs.getInt("estado"));
                    // System.out.println("Unidad Get " + ed. getIdunidad());
                    lista.add(fi);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

}
