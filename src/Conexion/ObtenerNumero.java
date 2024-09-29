/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 */
public class ObtenerNumero {

    public String getNumero(int f1) {

        //   int f1 = 1234567;
        Locale locFR = new Locale("fr");    // Francia

        NumberFormat[] nfa = new NumberFormat[4];

        nfa[0] = NumberFormat.getInstance();
        String numero = String.valueOf(nfa[0].format(f1));

        nfa[1] = NumberFormat.getInstance(locFR);

        nfa[2] = NumberFormat.getCurrencyInstance();

        nfa[3] = NumberFormat.getCurrencyInstance(locFR);

        return numero;

    }

    public JTable obtener_num(JTable tabla, int col) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String numero = String.valueOf(tabla.getValueAt(i, col));
            int num = Integer.valueOf(numero);
            numero = this.getNumero(num);
            tabla.setValueAt(numero, i, col);
        }
        return tabla;
    }

    public JTable agregar_cosa(JTable tabla, int col, String men) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String numero = String.valueOf(tabla.getValueAt(i, col));
            numero = numero + men;
            tabla.setValueAt(numero, i, col);
        }
        return tabla;
    }

    public int point_to_number(String numero) {
        String nuevo = "";
        for (int i = 0; i < numero.length(); i++) {
            char hola = numero.charAt(i);
            if (Character.isDigit(hola)) {
                String nue = String.valueOf(hola);
                nuevo = nuevo + nue;
            }
        }
        int med = Integer.valueOf(nuevo);
        return med;
    }

    public String point_to_number_concoma(String numero) {
        String nuevo = "";
        int cant = 0;
        for (int i = 0; i < numero.length(); i++) {
            char caracter = numero.charAt(i);
            if (cant == 0) {
                if (".".equals(String.valueOf(caracter))) {
                    String nue = String.valueOf(caracter);
                    nuevo = nuevo + nue;
                    cant++;
                }
            }
            if (Character.isDigit(caracter)) {
                String nue = String.valueOf(caracter);
                nuevo = nuevo + nue;
            }

        }
        //int med = Integer.valueOf(nuevo);
        return nuevo;
    }

    public void solonumero(JTextField texto) {
        texto.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c)
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    //  getToolkit().beep();
                    e.consume();
                }
            }
        });
    }

    public boolean esDecimal(String cad) {
        try {
            Double.parseDouble(cad);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
