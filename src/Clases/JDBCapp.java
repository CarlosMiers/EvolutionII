/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Pc_Server
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class JDBCapp {

    static Connection con;

    public static void main(String args[]) {
        ResultSet rs = null;
        Statement stmt = null;
        String sql;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element results = doc.createElement("Informe_Personas");
            results.setAttribute("Informe_Personas", "1");
            doc.appendChild(results);
            // connection to an ACCESS MDB
            con = AccessCon.getConnection();

            sql = "select * from bicsa WHERE Telefono='0982872017'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            String columnName = null;
            while (rs.next()) {
                //              for (int ii = 1; ii <= colCount; ii++) {
                Element row = doc.createElement("Persona");
                results.appendChild(row);
                for (int ii = 1; ii <= colCount; ii++) {
                    columnName = rsmd.getColumnName(ii);
                        results.appendChild(row);
                        columnName = rsmd.getColumnName(ii);
                        Object value = rs.getObject(ii);
                        Element node = doc.createElement(columnName);
                        node.appendChild(doc.createTextNode(value.toString()));
                        row.appendChild(node);
                }
            }

            System.out.println(getDocumentAsXml(doc));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static String getDocumentAsXml(Document doc)
            throws TransformerConfigurationException, TransformerException {
        DOMSource domSource = new DOMSource(doc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        // we want to pretty format the XML output
        // note : this is broken in jdk1.5 beta!
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //
        java.io.StringWriter sw = new java.io.StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        return sw.toString();
    }
}

class AccessCon {

    public static Connection getConnection() throws Exception {
        Driver d = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://181.120.121.204/evolution";
        Connection c = DriverManager.getConnection(url, "root", "1541947");
        return c;
        /*
   To use an already defined ODBC Datasource :

     String URL = "jdbc:odbc:myDSN";
     Connection c = DriverManager.getConnection(URL, "user", "pwd");

         */
    }
}
