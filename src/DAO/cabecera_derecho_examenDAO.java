/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cabecera_derecho_examen;
import Modelo.ficha_empleado;
import Modelo.semestres;
import Modelo.sucursal;
import Modelo.periodo_lectivo;
import Modelo.materias;
import Modelo.carrera;
import Modelo.tipo_examen;

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
public class cabecera_derecho_examenDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cabecera_derecho_examen> MostrarxFecha(Date fechaini, Date fechafin) throws SQLException {
        ArrayList<cabecera_derecho_examen> lista = new ArrayList<cabecera_derecho_examen>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT idcertificado,sede,periodo,cabecera_derecho_examen.semestre,"
                    + "codturno,codcarrera,codprofesor,tipoexamen,cabecera_derecho_examen.importe,"
                    + "fechaexamen,acta,materia,"
                    + "sucursales.nombre AS nombresede,"
                    + "carreras.nombre AS nombrecarrera,"
                    + "materias.nombre AS nombremateria,"
                    + "tipo_examen.nombre AS nombretipoexamen,"
                    + "CONCAT(empleados.nombres,empleados.apellidos) AS nombreprofesor "
                    + "FROM cabecera_derecho_examen,carreras,materias,"
                    + "tipo_examen,empleados,sucursales "
                    + "WHERE cabecera_derecho_examen.codcarrera=carreras.codigo "
                    + "AND materias.codigo=cabecera_derecho_examen.materia  "
                    + "AND materias.idcarrera=carreras.codigo "
                    + "AND tipo_examen.codigo=cabecera_derecho_examen.tipoexamen "
                    + "AND sucursales.codigo=cabecera_derecho_examen.sede "
                    + "AND empleados.codigo=cabecera_derecho_examen.codprofesor "
                    + "AND cabecera_derecho_examen.fechaexamen>= ?  AND cabecera_derecho_examen.fechaexamen<= ? "
                    + " ORDER BY cabecera_derecho_examen.idcertificado";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDate(1, fechaini);
                ps.setDate(2, fechafin);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cabecera_derecho_examen ce = new cabecera_derecho_examen();
                    ficha_empleado ficha = new ficha_empleado();
                    semestres sem = new semestres();
                    sucursal suc = new sucursal();
                    periodo_lectivo per = new periodo_lectivo();
                    tipo_examen tipo = new tipo_examen();
                    materias mat = new materias();
                    carrera carre = new carrera();

                    ce.setIdcertificado(rs.getDouble("idcertificado"));
                    ce.setFechaexamen(rs.getDate("fechaexamen"));
                    ce.setActa(rs.getInt("acta"));
                    ce.setCodturno(rs.getString("codturno"));

                    ce.setCodcarrera(carre);
                    ce.setMateria(mat);
                    ce.setCodprofesor(ficha);
                    ce.setSede(suc);
                    ce.setTipoexamen(tipo);
                    ce.setPeriodo(per);
                    ce.setSemestre(sem);

                    ce.getCodcarrera().setCodigo(rs.getInt("codcarrera"));
                    ce.getCodcarrera().setNombre(rs.getString("nombrecarrera"));
                    ce.getMateria().setCodigo(rs.getInt("materia"));
                    ce.getMateria().setNombre(rs.getString("nombremateria"));
                    ce.getCodprofesor().setCodigo(rs.getInt("codprofesor"));
                    ce.getCodprofesor().setNombres(rs.getString("nombreprofesor"));
                    ce.getSede().setCodigo(rs.getInt("sede"));
                    ce.getSede().setNombre(rs.getString("nombresede"));
                    ce.getTipoexamen().setCodigo(rs.getString("tipoexamen"));
                    ce.getTipoexamen().setNombre(rs.getString("nombretipoexamen"));
                    ce.getPeriodo().setCodigo(rs.getInt("periodo"));
                    ce.getSemestre().setSemestre(rs.getInt("semestre"));
                    ce.setImporte(rs.getDouble("importe"));
                    lista.add(ce);
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return lista;
    }

    public cabecera_derecho_examen BuscarId(Double nnumero) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        cabecera_derecho_examen ce = new cabecera_derecho_examen();
        try {

            String sql = "SELECT idcertificado,sede,periodo,cabecera_derecho_examen.semestre,"
                    + "codturno,codcarrera,codprofesor,tipoexamen,"
                    + "fechaexamen,acta,materia,cabecera_derecho_examen.importe,"
                    + "sucursales.nombre AS nombresede,"
                    + "carreras.nombre AS nombrecarrera,"
                    + "materias.nombre AS nombremateria,"
                    + "tipo_examen.nombre AS nombretipoexamen,"
                    + "CONCAT(empleados.nombres,empleados.apellidos) AS nombreprofesor "
                    + "FROM cabecera_derecho_examen,carreras,materias,"
                    + "tipo_examen,empleados,sucursales "
                    + "WHERE cabecera_derecho_examen.codcarrera=carreras.codigo "
                    + "AND materias.codigo=cabecera_derecho_examen.materia  "
                    + "AND materias.idcarrera=carreras.codigo "
                    + "AND tipo_examen.codigo=cabecera_derecho_examen.tipoexamen "
                    + "AND sucursales.codigo=cabecera_derecho_examen.sede "
                    + "AND empleados.codigo=cabecera_derecho_examen.codprofesor "
                    + "AND cabecera_derecho_examen.idcertificado=? "
                    + " ORDER BY cabecera_derecho_examen.idcertificado";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, nnumero);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    ficha_empleado ficha = new ficha_empleado();
                    semestres sem = new semestres();
                    sucursal suc = new sucursal();
                    periodo_lectivo per = new periodo_lectivo();
                    tipo_examen tipo = new tipo_examen();
                    materias mat = new materias();
                    carrera carre = new carrera();

                    ce.setIdcertificado(rs.getDouble("idcertificado"));
                    ce.setFechaexamen(rs.getDate("fechaexamen"));
                    ce.setActa(rs.getInt("acta"));
                    ce.setCodturno(rs.getString("codturno"));

                    ce.setCodcarrera(carre);
                    ce.setMateria(mat);
                    ce.setCodprofesor(ficha);
                    ce.setSede(suc);
                    ce.setTipoexamen(tipo);
                    ce.setPeriodo(per);
                    ce.setSemestre(sem);

                    ce.getCodcarrera().setCodigo(rs.getInt("codcarrera"));
                    ce.getCodcarrera().setNombre(rs.getString("nombrecarrera"));
                    ce.getMateria().setCodigo(rs.getInt("materia"));
                    ce.getMateria().setNombre(rs.getString("nombremateria"));
                    ce.getCodprofesor().setCodigo(rs.getInt("codprofesor"));
                    ce.getCodprofesor().setNombres(rs.getString("nombreprofesor"));
                    ce.getSede().setCodigo(rs.getInt("sede"));
                    ce.getSede().setNombre(rs.getString("nombresede"));
                    ce.getTipoexamen().setCodigo(rs.getString("tipoexamen"));
                    ce.getTipoexamen().setNombre(rs.getString("nombretipoexamen"));
                    ce.getPeriodo().setCodigo(rs.getInt("periodo"));
                    ce.getSemestre().setSemestre(rs.getInt("semestre"));
                    ce.setImporte(rs.getDouble("importe"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ce;
    }

    public cabecera_derecho_examen InsertarActa(cabecera_derecho_examen anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cabecera_derecho_examen "
                + " (sede,periodo,semestre,"
                + "codturno,codcarrera,codprofesor,"
                + "tipoexamen,fechaexamen,importe,"
                + "materia)"
                + "  VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, anti.getSede().getCodigo());
        ps.setInt(2, anti.getPeriodo().getCodigo());
        ps.setInt(3, anti.getSemestre().getSemestre());
        ps.setString(4, anti.getCodturno());
        ps.setInt(5, anti.getCodcarrera().getCodigo());
        ps.setInt(6, anti.getCodprofesor().getCodigo());
        ps.setString(7, anti.getTipoexamen().getCodigo());
        ps.setDate(8, anti.getFechaexamen());
        ps.setDouble(9, anti.getImporte());
        ps.setInt(10, anti.getMateria().getCodigo());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return anti;
    }

    public boolean ActualizarActa(cabecera_derecho_examen anti) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cabecera_derecho_examen SET "
                + "sede=?,periodo=?,semestre=?,"
                + "codturno=?,codcarrera=?,codprofesor=?,"
                + "tipoexamen=?,fechaexamen=?,importe=?,"
                + "materia=? WHERE idcertificado=" + anti.getIdcertificado());
        ps.setInt(1, anti.getSede().getCodigo());
        ps.setInt(2, anti.getPeriodo().getCodigo());
        ps.setInt(3, anti.getSemestre().getSemestre());
        ps.setString(4, anti.getCodturno());
        ps.setInt(5, anti.getCodcarrera().getCodigo());
        ps.setInt(6, anti.getCodprofesor().getCodigo());
        ps.setString(7, anti.getTipoexamen().getCodigo());
        ps.setDate(8, anti.getFechaexamen());
        ps.setDouble(9, anti.getImporte());
        ps.setInt(10, anti.getMateria().getCodigo());

        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarActa(double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cabecera_derecho_examen"
                + " WHERE idcertificado=?");
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

    public cabecera_derecho_examen GenerarActa() throws SQLException {

        cabecera_derecho_examen anti = new cabecera_derecho_examen();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT MAX(acta)+1 AS nacta "
                    + " FROM cabecera_derecho_examen ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    anti.setActa(rs.getInt("nacta"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return anti;
    }

}
