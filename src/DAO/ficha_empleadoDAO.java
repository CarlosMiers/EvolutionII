/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cargo;
import Modelo.departamento_laboral;
import Modelo.ficha_empleado;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.pais;
import Modelo.profesion;
import Modelo.seccion;
import Modelo.sucursal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class ficha_empleadoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<ficha_empleado> Todos() throws SQLException {
        ArrayList<ficha_empleado> lista = new ArrayList<ficha_empleado>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT empleados.codigo,empleados.nombres,CONCAT(nombres,' ',apellidos) AS nombreempleado,empleados.apellidos,empleados.sexo,empleados.cedula,empleados.fechanacimiento, "
                    + "empleados.nacionalidad, paises.nombre AS nombrenacionalidad,empleados.estado_civil,empleados.conyugue,empleados.localidad, "
                    + "localidades.nombre AS nombrelocalidad,empleados.direccion,empleados.telefono,empleados.celular,empleados.telefono_urgencia, "
                    + "empleados.e_mail,empleados.estado,empleados.sucursal,sucursales.nombre AS nombresuc,empleados.profesion,profesiones.Nombre AS nombreprofesion, "
                    + "empleados.cargo,cargos.Nombre AS nombrecargo,empleados.departamento,departamento_laboral.Nombre AS nombredepartamento,empleados.seccion, "
                    + "secciones.Nombre AS nombreseccion,empleados.giraduria,giradurias.nombre AS nombregiraduria,empleados.fecha_ingreso,empleados.salario, "
                    + "empleados.adicionalxformacion,empleados.sistema_cobro,empleados.tipo_salario,empleados.ips,empleados.bonificacion,empleados.academia, "
                    + "empleados.espanol,empleados.guarani,empleados.portugues,empleados.aleman,empleados.otros, "
                    + "empleados.nroseguroips,empleados.estatura,empleados.peso,empleados.grupo_sanguineo,"
                    + "empleados.bebe,empleados.fuma,empleados.anteojos,empleados.pantalon,empleados.casaca,empleados.zapatos,"
                    + "empleados.capacidad_auditiva,empleados.medicamentos_contraindicados,empleados.accidentes,empleados.enfermedades,"
                    + "empleados.objetivos_laborales,empleados.experiencia_profesional,empleados.preparacion_academica,"
                    + "empleados.operaciones,nrohijos "
                    + " FROM empleados "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=empleados.sucursal "
                    + " LEFT JOIN secciones "
                    + " ON secciones.Codigo=empleados.seccion "
                    + " LEFT JOIN cargos"
                    + " ON cargos.Codigo=empleados.cargo "
                    + " LEFT JOIN paises"
                    + " ON paises.codigo=empleados.nacionalidad "
                    + " LEFT JOIN localidades"
                    + " ON localidades.codigo=empleados.localidad "
                    + " LEFT JOIN profesiones"
                    + " ON profesiones.Codigo=empleados.profesion "
                    + " LEFT JOIN departamento_laboral"
                    + " ON departamento_laboral.Codigo=empleados.departamento "
                    + " LEFT JOIN giradurias"
                    + " ON giradurias.codigo=empleados.giraduria "
                    + " ORDER BY empleados.codigo ";
            
            System.out.println(sql);
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    ficha_empleado fi = new ficha_empleado();
                    seccion secc = new seccion();
                    cargo car = new cargo();
                    sucursal suc = new sucursal();
                    pais pa = new pais();
                    localidad loc = new localidad();
                    profesion pro = new profesion();
                    departamento_laboral dep = new departamento_laboral();
                    giraduria gira = new giraduria();

                    fi.setSeccion(secc);
                    fi.setCargo(car);
                    fi.setSucursal(suc);
                    fi.setNacionalidad(pa);
                    fi.setLocalidad(loc);
                    fi.setProfesion(pro);
                    fi.setDepartamento(dep);
                    fi.setGiraduria(gira);

                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombreempleado"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setNombreempleado(rs.getString("nombreempleado"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                    fi.getNacionalidad().setNombre(rs.getString("nombrenacionalidad"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.getLocalidad().setCodigo(rs.getInt("localidad"));
                    fi.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setCelular(rs.getString("celular"));
                    fi.setTelefono_urgencia(rs.getString("telefono_urgencia"));
                    fi.setE_mail(rs.getString("e_mail"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.getSucursal().setCodigo(rs.getInt("sucursal"));
                    fi.getSucursal().setNombre(rs.getString("nombresuc"));
                    fi.getProfesion().setCodigo(rs.getInt("profesion"));
                    fi.getProfesion().setNombre(rs.getString("nombreprofesion"));
                    fi.getCargo().setCodigo(rs.getInt("cargo"));
                    fi.getCargo().setNombre(rs.getString("nombrecargo"));
                    fi.getDepartamento().setCodigo(rs.getInt("departamento"));
                    fi.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                    fi.getSeccion().setCodigo(rs.getInt("seccion"));
                    fi.getSeccion().setNombre(rs.getString("nombreseccion"));
                    fi.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    fi.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    fi.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                    fi.setSalario(rs.getBigDecimal("salario"));
                    fi.setAdicionalxformacion(rs.getBigDecimal("adicionalxformacion"));
                    fi.setSistema_cobro(rs.getInt("sistema_cobro"));
                    fi.setTipo_salario(rs.getInt("tipo_salario"));
                    fi.setIps(rs.getInt("ips"));
                    fi.setBonificacion(rs.getInt("bonificacion"));
                    fi.setAcademia(rs.getInt("academia"));
                    fi.setEspanol(rs.getInt("espanol"));
                    fi.setGuarani(rs.getInt("guarani"));
                    fi.setPortugues(rs.getInt("portugues"));
                    fi.setAleman(rs.getInt("aleman"));
                    fi.setOtros(rs.getInt("otros"));
                    fi.setNrohijos(rs.getInt("nrohijos"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));
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

    public ficha_empleado MostrarxEstado(int nestado) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        ficha_empleado fi = new ficha_empleado();
        try {

            String sql = "SELECT empleados.codigo,empleados.nombres,empleados.apellidos,empleados.sexo,empleados.cedula,empleados.fechanacimiento, "
                    + "empleados.nacionalidad, paises.nombre AS nombrenacionalidad,empleados.estado_civil,empleados.conyugue,empleados.localidad, "
                    + "localidades.nombre AS nombrelocalidad,empleados.direccion,empleados.telefono,empleados.celular,empleados.telefono_urgencia, "
                    + "empleados.e_mail,empleados.estado,empleados.sucursal,sucursales.nombre AS nombresuc,empleados.profesion,profesiones.Nombre AS nombreprofesion, "
                    + "empleados.cargo,cargos.Nombre AS nombrecargo,empleados.departamento,departamento_laboral.Nombre AS nombredepartamento,empleados.seccion, "
                    + "secciones.Nombre AS nombreseccion,empleados.giraduria,giradurias.nombre AS nombregiraduria,empleados.fecha_ingreso,empleados.salario, "
                    + "empleados.adicionalxformacion,empleados.sistema_cobro,empleados.tipo_salario,empleados.ips,empleados.bonificacion,empleados.academia, "
                    + "empleados.espanol,empleados.guarani,empleados.portugues,empleados.aleman,empleados.otros,"
                    + "empleados.nroseguroips,empleados.estatura,empleados.peso,empleados.grupo_sanguineo,"
                    + "empleados.bebe,empleados.fuma,empleados.anteojos,empleados.pantalon,empleados.casaca,empleados.zapatos,"
                    + "empleados.capacidad_auditiva,empleados.medicamentos_contraindicados,empleados.accidentes,empleados.enfermedades,"
                    + "empleados.operaciones,empleados.foto,empleados.nrohijos "
                    + " FROM empleados "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=empleados.sucursal "
                    + " LEFT JOIN secciones "
                    + " ON secciones.Codigo=empleados.seccion "
                    + " LEFT JOIN cargos"
                    + " ON cargos.Codigo=empleados.cargo "
                    + " LEFT JOIN paises"
                    + " ON paises.codigo=empleados.nacionalidad "
                    + " LEFT JOIN localidades"
                    + " ON localidades.codigo=empleados.localidad "
                    + " LEFT JOIN profesiones"
                    + " ON profesiones.Codigo=empleados.profesion "
                    + " LEFT JOIN departamento_laboral"
                    + "  ON departamento_laboral.Codigo=empleados.departamento "
                    + " LEFT JOIN giradurias"
                    + " ON giradurias.codigo=empleados.giraduria "
                    + " WHERE empleados.codigo = ? "
                    + " ORDER BY empleados.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, nestado);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    seccion secc = new seccion();
                    cargo car = new cargo();
                    sucursal suc = new sucursal();
                    pais pa = new pais();
                    localidad loc = new localidad();
                    profesion pro = new profesion();
                    departamento_laboral dep = new departamento_laboral();
                    giraduria gira = new giraduria();

                    fi.setSeccion(secc);
                    fi.setCargo(car);
                    fi.setSucursal(suc);
                    fi.setNacionalidad(pa);
                    fi.setLocalidad(loc);
                    fi.setProfesion(pro);
                    fi.setDepartamento(dep);
                    fi.setGiraduria(gira);

                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                    fi.getNacionalidad().setNombre(rs.getString("nombrenacionalidad"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.getLocalidad().setCodigo(rs.getInt("localidad"));
                    fi.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setCelular(rs.getString("celular"));
                    fi.setTelefono_urgencia(rs.getString("telefono_urgencia"));
                    fi.setE_mail(rs.getString("e_mail"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.getSucursal().setCodigo(rs.getInt("sucursal"));
                    fi.getSucursal().setNombre(rs.getString("nombresuc"));
                    fi.getProfesion().setCodigo(rs.getInt("profesion"));
                    fi.getProfesion().setNombre(rs.getString("nombreprofesion"));
                    fi.getCargo().setCodigo(rs.getInt("cargo"));
                    fi.getCargo().setNombre(rs.getString("nombrecargo"));
                    fi.getDepartamento().setCodigo(rs.getInt("departamento"));
                    fi.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                    fi.getSeccion().setCodigo(rs.getInt("seccion"));
                    fi.getSeccion().setNombre(rs.getString("nombreseccion"));
                    fi.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    fi.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    fi.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                    fi.setSalario(rs.getBigDecimal("salario"));
                    fi.setAdicionalxformacion(rs.getBigDecimal("adicionalxformacion"));
                    fi.setSistema_cobro(rs.getInt("sistema_cobro"));
                    fi.setTipo_salario(rs.getInt("tipo_salario"));
                    fi.setIps(rs.getInt("ips"));
                    fi.setBonificacion(rs.getInt("bonificacion"));
                    fi.setAcademia(rs.getInt("academia"));
                    fi.setEspanol(rs.getInt("espanol"));
                    fi.setGuarani(rs.getInt("guarani"));
                    fi.setPortugues(rs.getInt("portugues"));
                    fi.setAleman(rs.getInt("aleman"));
                    fi.setOtros(rs.getInt("otros"));
                    fi.setFoto(rs.getBytes("foto"));
                    fi.setNrohijos(rs.getInt("nrohijos"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return fi;
    }

    public ficha_empleado insertarficha(ficha_empleado ocr, Boolean isFoto) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        String foto = ", foto";
        String fotoP = ",?";
        if (!isFoto) {
            foto = "";
            fotoP = "";
        }
        boolean guardado = false;
        int id = 0;
        PreparedStatement fi = null;

        fi = st.getConnection().prepareStatement("INSERT INTO empleados (nombres,apellidos,sexo,cedula,fechanacimiento," //5
                + "nacionalidad,estado_civil,conyugue,localidad,direccion,telefono,celular," //7
                + "telefono_urgencia,e_mail,estado,sucursal,profesion,cargo,departamento,seccion," //8
                + "giraduria,fecha_ingreso,salario,adicionalxformacion,sistema_cobro," //5
                + "tipo_salario,ips,bonificacion,academia,espanol,guarani,"//6
                + "portugues,aleman,otros,nroseguroips,nrohijos,postulante,foto,"
                + "objetivos_laborales,experiencia_profesional,preparacion_academica) " //6
                + " VALUES (?,?,?,?,?,?,?,?,?,?,"//10
                        + "?,?,?,?,?,?,?,?,?,?,"//10
                        + "?,?,?,?,?,?,?,?,?,?,"//10
                        + "?,?,?,?,?,?,?" + fotoP + ",?,?,?)", Statement.RETURN_GENERATED_KEYS);
//36                        1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6
        fi.setString(1, ocr.getNombres());
        fi.setString(2, ocr.getApellidos());
        fi.setInt(3, ocr.getSexo());
        fi.setInt(4, ocr.getCedula());
        fi.setDate(5, ocr.getFechanacimiento());
        fi.setInt(6, ocr.getNacionalidad().getCodigo());
        fi.setString(7, ocr.getEstado_civil());
        fi.setString(8, ocr.getConyugue());
        fi.setInt(9, ocr.getLocalidad().getCodigo());
        fi.setString(10, ocr.getDireccion());
        fi.setString(11, ocr.getTelefono());
        fi.setString(12, ocr.getCelular());
        fi.setString(13, ocr.getTelefono_urgencia());
        fi.setString(14, ocr.getE_mail());
        fi.setInt(15, ocr.getEstado());
        fi.setInt(16, ocr.getSucursal().getCodigo());
        fi.setInt(17, ocr.getProfesion().getCodigo());
        fi.setInt(18, ocr.getCargo().getCodigo());
        fi.setInt(19, ocr.getDepartamento().getCodigo());
        fi.setInt(20, ocr.getSeccion().getCodigo());
        fi.setInt(21, ocr.getGiraduria().getCodigo());
        fi.setDate(22, ocr.getFecha_ingreso());
        fi.setBigDecimal(23, ocr.getSalario());
        fi.setBigDecimal(24, ocr.getAdicionalxformacion());
        fi.setInt(25, ocr.getSistema_cobro());
        fi.setInt(26, ocr.getTipo_salario());
        fi.setInt(27, ocr.getIps());
        fi.setInt(28, ocr.getBonificacion());
        fi.setInt(29, ocr.getAcademia());
        fi.setInt(30, ocr.getEspanol());
        fi.setInt(31, ocr.getGuarani());
        fi.setInt(32, ocr.getPortugues());
        fi.setInt(33, ocr.getAleman());
        fi.setInt(34, ocr.getOtros());
        fi.setString(35, ocr.getNroseguroips());
        fi.setInt(36, ocr.getNrohijos());
        fi.setInt(37, ocr.getPostulante());
        fi.setBytes(38, ocr.getFoto());
        fi.setString(39, ocr.getObjetivos_laborales());
        fi.setString(40, ocr.getExperiencia_laboral());
        fi.setString(41, ocr.getPreparacion_academica());
        
        fi.executeUpdate();
        st.close();
        fi.close();
        return ocr;
    }

    public boolean actualizarFicha(ficha_empleado ocr, Boolean isFoto) throws SQLException {
        String foto = ", foto";
        String fotoP = ",?";
        if (!isFoto) {
            foto = "";
            fotoP = "";
        }
        con = new Conexion();
        st = con.conectar();
        PreparedStatement fi = null;

        fi = st.getConnection().prepareStatement("UPDATE empleados SET nombres=?,apellidos=?,sexo=?,cedula=?,fechanacimiento=?,"
                + "nacionalidad=?,estado_civil=?,conyugue=?,localidad=?,direccion=?,telefono=?,celular=?,"
                + "telefono_urgencia=?,e_mail=?,estado=?,sucursal=?,profesion=?,cargo=?,"
                + "departamento=?,seccion=?,giraduria=?,fecha_ingreso=?,salario=?,adicionalxformacion=?,sistema_cobro=?,"
                + "tipo_salario=?,ips=?,bonificacion=?,academia=?,espanol=?,"
                + "guarani=?,portugues=?,aleman=?,otros=?,nroseguroips=?,foto=?,nrohijos=?,"
                +"objetivos_laborales=?,experiencia_profesional=?,preparacion_academica=? "
                + " WHERE codigo=" + ocr.getCodigo());

        fi.setString(1, ocr.getNombres());
        fi.setString(2, ocr.getApellidos());
        fi.setInt(3, ocr.getSexo());
        fi.setInt(4, ocr.getCedula());
        fi.setDate(5, ocr.getFechanacimiento());
        fi.setInt(6, ocr.getNacionalidad().getCodigo());
        fi.setString(7, ocr.getEstado_civil());
        fi.setString(8, ocr.getConyugue());
        fi.setInt(9, ocr.getLocalidad().getCodigo());
        fi.setString(10, ocr.getDireccion());
        fi.setString(11, ocr.getTelefono());
        fi.setString(12, ocr.getCelular());
        fi.setString(13, ocr.getTelefono_urgencia());
        fi.setString(14, ocr.getE_mail());
        fi.setInt(15, ocr.getEstado());
        fi.setInt(16, ocr.getSucursal().getCodigo());
        fi.setInt(17, ocr.getProfesion().getCodigo());
        fi.setInt(18, ocr.getCargo().getCodigo());
        fi.setInt(19, ocr.getDepartamento().getCodigo());
        fi.setInt(20, ocr.getSeccion().getCodigo());
        fi.setInt(21, ocr.getGiraduria().getCodigo());
        fi.setDate(22, ocr.getFecha_ingreso());
        fi.setBigDecimal(23, ocr.getSalario());
        fi.setBigDecimal(24, ocr.getAdicionalxformacion());
        fi.setInt(25, ocr.getSistema_cobro());
        fi.setInt(26, ocr.getTipo_salario());
        fi.setInt(27, ocr.getIps());
        fi.setInt(28, ocr.getBonificacion());
        fi.setInt(29, ocr.getAcademia());
        fi.setInt(30, ocr.getEspanol());
        fi.setInt(31, ocr.getGuarani());
        fi.setInt(32, ocr.getPortugues());
        fi.setInt(33, ocr.getAleman());
        fi.setInt(34, ocr.getOtros());
        fi.setString(35, ocr.getNroseguroips());
        if (isFoto) {
            fi.setBytes(36, ocr.getFoto());
        }
        fi.setInt(37,ocr.getNrohijos());
        fi.setString(38, ocr.getObjetivos_laborales());
        fi.setString(39, ocr.getExperiencia_laboral());
        fi.setString(40, ocr.getPreparacion_academica());

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

        ps = st.getConnection().prepareStatement("DELETE FROM empleados WHERE codigo=?");
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

    public ficha_empleado buscarId(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        ficha_empleado fi = new ficha_empleado();
        try {

            String sql = "SELECT empleados.codigo,empleados.nombres,CONCAT(nombres,' ',apellidos) AS nombreempleado,empleados.apellidos,empleados.sexo,empleados.cedula,empleados.fechanacimiento, "
                    + "empleados.nacionalidad, paises.nombre AS nombrenacionalidad,empleados.estado_civil,empleados.conyugue,empleados.localidad, "
                    + "localidades.nombre AS nombrelocalidad,empleados.direccion,empleados.telefono,empleados.celular,empleados.telefono_urgencia, "
                    + "empleados.e_mail,empleados.estado,empleados.sucursal,sucursales.nombre AS nombresuc,empleados.profesion,profesiones.Nombre AS nombreprofesion, "
                    + "empleados.cargo,cargos.Nombre AS nombrecargo,empleados.departamento,departamento_laboral.Nombre AS nombredepartamento,empleados.seccion, "
                    + "secciones.Nombre AS nombreseccion,empleados.giraduria,giradurias.nombre AS nombregiraduria,empleados.fecha_ingreso,empleados.salario, "
                    + "empleados.adicionalxformacion,empleados.sistema_cobro,empleados.tipo_salario,empleados.ips,empleados.bonificacion,empleados.academia, "
                    + "empleados.espanol,empleados.guarani,empleados.portugues,empleados.aleman,empleados.otros,"
                    + "empleados.nroseguroips,empleados.estatura,empleados.peso,empleados.grupo_sanguineo,"
                    + "empleados.bebe,empleados.fuma,empleados.anteojos,empleados.pantalon,empleados.casaca,empleados.zapatos,"
                    + "empleados.capacidad_auditiva,empleados.medicamentos_contraindicados,empleados.accidentes,empleados.enfermedades,"
                    + "empleados.operaciones, empleados.foto,empleados.nrohijos,empleados.postulante,"
                    + "objetivos_laborales,experiencia_profesional,preparacion_academica "
                    + " FROM empleados "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=empleados.sucursal "
                    + " LEFT JOIN secciones "
                    + " ON secciones.Codigo=empleados.seccion "
                    + " LEFT JOIN cargos"
                    + " ON cargos.Codigo=empleados.cargo "
                    + " LEFT JOIN paises"
                    + " ON paises.codigo=empleados.nacionalidad "
                    + " LEFT JOIN localidades"
                    + " ON localidades.codigo=empleados.localidad "
                    + " LEFT JOIN profesiones"
                    + " ON profesiones.Codigo=empleados.profesion "
                    + " LEFT JOIN departamento_laboral"
                    + "  ON departamento_laboral.Codigo=empleados.departamento "
                    + " LEFT JOIN giradurias"
                    + " ON giradurias.codigo=empleados.giraduria "
                    + " WHERE empleados.codigo = ? "
                    + " ORDER BY empleados.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    seccion secc = new seccion();
                    cargo car = new cargo();
                    sucursal suc = new sucursal();
                    pais pa = new pais();
                    localidad loc = new localidad();
                    profesion pro = new profesion();
                    departamento_laboral dep = new departamento_laboral();
                    giraduria gira = new giraduria();

                    fi.setSeccion(secc);
                    fi.setCargo(car);
                    fi.setSucursal(suc);
                    fi.setNacionalidad(pa);
                    fi.setLocalidad(loc);
                    fi.setProfesion(pro);
                    fi.setDepartamento(dep);
                    fi.setGiraduria(gira);

                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setNombreempleado(rs.getString("nombreempleado"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                    fi.getNacionalidad().setNombre(rs.getString("nombrenacionalidad"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.getLocalidad().setCodigo(rs.getInt("localidad"));
                    fi.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setCelular(rs.getString("celular"));
                    fi.setTelefono_urgencia(rs.getString("telefono_urgencia"));
                    fi.setE_mail(rs.getString("e_mail"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.getSucursal().setCodigo(rs.getInt("sucursal"));
                    fi.getSucursal().setNombre(rs.getString("nombresuc"));
                    fi.getProfesion().setCodigo(rs.getInt("profesion"));
                    fi.getProfesion().setNombre(rs.getString("nombreprofesion"));
                    fi.getCargo().setCodigo(rs.getInt("cargo"));
                    fi.getCargo().setNombre(rs.getString("nombrecargo"));
                    fi.getDepartamento().setCodigo(rs.getInt("departamento"));
                    fi.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                    fi.getSeccion().setCodigo(rs.getInt("seccion"));
                    fi.getSeccion().setNombre(rs.getString("nombreseccion"));
                    fi.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    fi.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    fi.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                    fi.setSalario(rs.getBigDecimal("salario"));
                    fi.setAdicionalxformacion(rs.getBigDecimal("adicionalxformacion"));
                    fi.setSistema_cobro(rs.getInt("sistema_cobro"));
                    fi.setTipo_salario(rs.getInt("tipo_salario"));
                    fi.setIps(rs.getInt("ips"));
                    fi.setBonificacion(rs.getInt("bonificacion"));
                    fi.setAcademia(rs.getInt("academia"));
                    fi.setEspanol(rs.getInt("espanol"));
                    fi.setGuarani(rs.getInt("guarani"));
                    fi.setPortugues(rs.getInt("portugues"));
                    fi.setAleman(rs.getInt("aleman"));
                    fi.setOtros(rs.getInt("otros"));
                    fi.setNroseguroips(rs.getString("nroseguroips"));
                    fi.setNrohijos(rs.getInt("nrohijos"));
                    fi.setFoto(rs.getBytes("foto"));
                    fi.setPostulante(rs.getInt("postulante"));
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



        public ArrayList<ficha_empleado> TodosActivo() throws SQLException {
        ArrayList<ficha_empleado> lista = new ArrayList<ficha_empleado>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT empleados.codigo,empleados.nombres,CONCAT(nombres,' ',apellidos) AS nombreempleado,empleados.apellidos,empleados.sexo,empleados.cedula,empleados.fechanacimiento, "
                    + "empleados.nacionalidad, paises.nombre AS nombrenacionalidad,empleados.estado_civil,empleados.conyugue,empleados.localidad, "
                    + "localidades.nombre AS nombrelocalidad,empleados.direccion,empleados.telefono,empleados.celular,empleados.telefono_urgencia, "
                    + "empleados.e_mail,empleados.estado,empleados.sucursal,sucursales.nombre AS nombresuc,empleados.profesion,profesiones.Nombre AS nombreprofesion, "
                    + "empleados.cargo,cargos.Nombre AS nombrecargo,empleados.departamento,departamento_laboral.Nombre AS nombredepartamento,empleados.seccion, "
                    + "secciones.Nombre AS nombreseccion,empleados.giraduria,giradurias.nombre AS nombregiraduria,empleados.fecha_ingreso,empleados.salario, "
                    + "empleados.adicionalxformacion,empleados.sistema_cobro,empleados.tipo_salario,empleados.ips,empleados.bonificacion,empleados.academia, "
                    + "empleados.espanol,empleados.guarani,empleados.portugues,empleados.aleman,empleados.otros, "
                    + "empleados.nroseguroips,empleados.estatura,empleados.peso,empleados.grupo_sanguineo,"
                    + "empleados.bebe,empleados.fuma,empleados.anteojos,empleados.pantalon,empleados.casaca,empleados.zapatos,"
                    + "empleados.capacidad_auditiva,empleados.medicamentos_contraindicados,empleados.accidentes,empleados.enfermedades,"
                    + "empleados.objetivos_laborales,empleados.experiencia_profesional,empleados.preparacion_academica,"
                    + "empleados.operaciones,nrohijos "
                    + " FROM empleados "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=empleados.sucursal "
                    + " LEFT JOIN secciones "
                    + " ON secciones.Codigo=empleados.seccion "
                    + " LEFT JOIN cargos"
                    + " ON cargos.Codigo=empleados.cargo "
                    + " LEFT JOIN paises"
                    + " ON paises.codigo=empleados.nacionalidad "
                    + " LEFT JOIN localidades"
                    + " ON localidades.codigo=empleados.localidad "
                    + " LEFT JOIN profesiones"
                    + " ON profesiones.Codigo=empleados.profesion "
                    + " LEFT JOIN departamento_laboral"
                    + " ON departamento_laboral.Codigo=empleados.departamento "
                    + " LEFT JOIN giradurias"
                    + " ON giradurias.codigo=empleados.giraduria "
                    + " WHERE empleados.estado=1 "
                    + " ORDER BY empleados.codigo ";
            
            System.out.println(sql);
            
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    ficha_empleado fi = new ficha_empleado();
                    seccion secc = new seccion();
                    cargo car = new cargo();
                    sucursal suc = new sucursal();
                    pais pa = new pais();
                    localidad loc = new localidad();
                    profesion pro = new profesion();
                    departamento_laboral dep = new departamento_laboral();
                    giraduria gira = new giraduria();

                    fi.setSeccion(secc);
                    fi.setCargo(car);
                    fi.setSucursal(suc);
                    fi.setNacionalidad(pa);
                    fi.setLocalidad(loc);
                    fi.setProfesion(pro);
                    fi.setDepartamento(dep);
                    fi.setGiraduria(gira);

                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombreempleado"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setNombreempleado(rs.getString("nombreempleado"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                    fi.getNacionalidad().setNombre(rs.getString("nombrenacionalidad"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.getLocalidad().setCodigo(rs.getInt("localidad"));
                    fi.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setCelular(rs.getString("celular"));
                    fi.setTelefono_urgencia(rs.getString("telefono_urgencia"));
                    fi.setE_mail(rs.getString("e_mail"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.getSucursal().setCodigo(rs.getInt("sucursal"));
                    fi.getSucursal().setNombre(rs.getString("nombresuc"));
                    fi.getProfesion().setCodigo(rs.getInt("profesion"));
                    fi.getProfesion().setNombre(rs.getString("nombreprofesion"));
                    fi.getCargo().setCodigo(rs.getInt("cargo"));
                    fi.getCargo().setNombre(rs.getString("nombrecargo"));
                    fi.getDepartamento().setCodigo(rs.getInt("departamento"));
                    fi.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                    fi.getSeccion().setCodigo(rs.getInt("seccion"));
                    fi.getSeccion().setNombre(rs.getString("nombreseccion"));
                    fi.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    fi.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    fi.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                    fi.setSalario(rs.getBigDecimal("salario"));
                    fi.setAdicionalxformacion(rs.getBigDecimal("adicionalxformacion"));
                    fi.setSistema_cobro(rs.getInt("sistema_cobro"));
                    fi.setTipo_salario(rs.getInt("tipo_salario"));
                    fi.setIps(rs.getInt("ips"));
                    fi.setBonificacion(rs.getInt("bonificacion"));
                    fi.setAcademia(rs.getInt("academia"));
                    fi.setEspanol(rs.getInt("espanol"));
                    fi.setGuarani(rs.getInt("guarani"));
                    fi.setPortugues(rs.getInt("portugues"));
                    fi.setAleman(rs.getInt("aleman"));
                    fi.setOtros(rs.getInt("otros"));
                    fi.setNrohijos(rs.getInt("nrohijos"));
                    fi.setObjetivos_laborales(rs.getString("objetivos_laborales"));
                    fi.setExperiencia_laboral(rs.getString("experiencia_profesional"));
                    fi.setPreparacion_academica(rs.getString("preparacion_academica"));
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


        
    public ficha_empleado buscarIdActivo(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        ficha_empleado fi = new ficha_empleado();
        try {

            String sql = "SELECT empleados.codigo,empleados.nombres,CONCAT(nombres,' ',apellidos) AS nombreempleado,empleados.apellidos,empleados.sexo,empleados.cedula,empleados.fechanacimiento, "
                    + "empleados.nacionalidad, paises.nombre AS nombrenacionalidad,empleados.estado_civil,empleados.conyugue,empleados.localidad, "
                    + "localidades.nombre AS nombrelocalidad,empleados.direccion,empleados.telefono,empleados.celular,empleados.telefono_urgencia, "
                    + "empleados.e_mail,empleados.estado,empleados.sucursal,sucursales.nombre AS nombresuc,empleados.profesion,profesiones.Nombre AS nombreprofesion, "
                    + "empleados.cargo,cargos.Nombre AS nombrecargo,empleados.departamento,departamento_laboral.Nombre AS nombredepartamento,empleados.seccion, "
                    + "secciones.Nombre AS nombreseccion,empleados.giraduria,giradurias.nombre AS nombregiraduria,empleados.fecha_ingreso,empleados.salario, "
                    + "empleados.adicionalxformacion,empleados.sistema_cobro,empleados.tipo_salario,empleados.ips,empleados.bonificacion,empleados.academia, "
                    + "empleados.espanol,empleados.guarani,empleados.portugues,empleados.aleman,empleados.otros,"
                    + "empleados.nroseguroips,empleados.estatura,empleados.peso,empleados.grupo_sanguineo,"
                    + "empleados.bebe,empleados.fuma,empleados.anteojos,empleados.pantalon,empleados.casaca,empleados.zapatos,"
                    + "empleados.capacidad_auditiva,empleados.medicamentos_contraindicados,empleados.accidentes,empleados.enfermedades,"
                    + "empleados.operaciones, empleados.foto,empleados.nrohijos,empleados.postulante,"
                    + "objetivos_laborales,experiencia_profesional,preparacion_academica "
                    + " FROM empleados "
                    + " LEFT JOIN sucursales "
                    + " ON sucursales.codigo=empleados.sucursal "
                    + " LEFT JOIN secciones "
                    + " ON secciones.Codigo=empleados.seccion "
                    + " LEFT JOIN cargos"
                    + " ON cargos.Codigo=empleados.cargo "
                    + " LEFT JOIN paises"
                    + " ON paises.codigo=empleados.nacionalidad "
                    + " LEFT JOIN localidades"
                    + " ON localidades.codigo=empleados.localidad "
                    + " LEFT JOIN profesiones"
                    + " ON profesiones.Codigo=empleados.profesion "
                    + " LEFT JOIN departamento_laboral"
                    + "  ON departamento_laboral.Codigo=empleados.departamento "
                    + " LEFT JOIN giradurias"
                    + " ON giradurias.codigo=empleados.giraduria "
                    + " WHERE empleados.codigo = ?  and empleados.estado=1 "
                    + " ORDER BY empleados.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    seccion secc = new seccion();
                    cargo car = new cargo();
                    sucursal suc = new sucursal();
                    pais pa = new pais();
                    localidad loc = new localidad();
                    profesion pro = new profesion();
                    departamento_laboral dep = new departamento_laboral();
                    giraduria gira = new giraduria();

                    fi.setSeccion(secc);
                    fi.setCargo(car);
                    fi.setSucursal(suc);
                    fi.setNacionalidad(pa);
                    fi.setLocalidad(loc);
                    fi.setProfesion(pro);
                    fi.setDepartamento(dep);
                    fi.setGiraduria(gira);

                    fi.setCodigo(rs.getInt("codigo"));
                    fi.setNombres(rs.getString("nombres"));
                    fi.setApellidos(rs.getString("apellidos"));
                    fi.setNombreempleado(rs.getString("nombreempleado"));
                    fi.setSexo(rs.getInt("sexo"));
                    fi.setCedula(rs.getInt("cedula"));
                    fi.setFechanacimiento(rs.getDate("fechanacimiento"));
                    fi.getNacionalidad().setCodigo(rs.getInt("nacionalidad"));
                    fi.getNacionalidad().setNombre(rs.getString("nombrenacionalidad"));
                    fi.setEstado_civil(rs.getString("estado_civil"));
                    fi.setConyugue(rs.getString("conyugue"));
                    fi.getLocalidad().setCodigo(rs.getInt("localidad"));
                    fi.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    fi.setDireccion(rs.getString("direccion"));
                    fi.setTelefono(rs.getString("telefono"));
                    fi.setCelular(rs.getString("celular"));
                    fi.setTelefono_urgencia(rs.getString("telefono_urgencia"));
                    fi.setE_mail(rs.getString("e_mail"));
                    fi.setEstado(rs.getInt("estado"));
                    fi.getSucursal().setCodigo(rs.getInt("sucursal"));
                    fi.getSucursal().setNombre(rs.getString("nombresuc"));
                    fi.getProfesion().setCodigo(rs.getInt("profesion"));
                    fi.getProfesion().setNombre(rs.getString("nombreprofesion"));
                    fi.getCargo().setCodigo(rs.getInt("cargo"));
                    fi.getCargo().setNombre(rs.getString("nombrecargo"));
                    fi.getDepartamento().setCodigo(rs.getInt("departamento"));
                    fi.getDepartamento().setNombre(rs.getString("nombredepartamento"));
                    fi.getSeccion().setCodigo(rs.getInt("seccion"));
                    fi.getSeccion().setNombre(rs.getString("nombreseccion"));
                    fi.getGiraduria().setCodigo(rs.getInt("giraduria"));
                    fi.getGiraduria().setNombre(rs.getString("nombregiraduria"));
                    fi.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                    fi.setSalario(rs.getBigDecimal("salario"));
                    fi.setAdicionalxformacion(rs.getBigDecimal("adicionalxformacion"));
                    fi.setSistema_cobro(rs.getInt("sistema_cobro"));
                    fi.setTipo_salario(rs.getInt("tipo_salario"));
                    fi.setIps(rs.getInt("ips"));
                    fi.setBonificacion(rs.getInt("bonificacion"));
                    fi.setAcademia(rs.getInt("academia"));
                    fi.setEspanol(rs.getInt("espanol"));
                    fi.setGuarani(rs.getInt("guarani"));
                    fi.setPortugues(rs.getInt("portugues"));
                    fi.setAleman(rs.getInt("aleman"));
                    fi.setOtros(rs.getInt("otros"));
                    fi.setNroseguroips(rs.getString("nroseguroips"));
                    fi.setNrohijos(rs.getInt("nrohijos"));
                    fi.setFoto(rs.getBytes("foto"));
                    fi.setPostulante(rs.getInt("postulante"));
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
        
    public boolean DesactivarFicha(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE empleados SET estado=0 WHERE codigo=?");
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



    public boolean ActivarFicha(int id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE empleados SET estado=1 WHERE codigo=?");
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

    
}
