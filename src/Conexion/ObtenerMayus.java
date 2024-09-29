/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

/**
 *
 * @author Administrador
 */
public class ObtenerMayus {

    // TODO code application logic here
    public static String minusculas = "abcdefghijklmnopqrstuvwxyz";
    private static String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Indica si el char es o no una minuscula.
     */
    public static boolean esMinuscula(char c) {
        for (int i = 0; i < minusculas.length(); i++) {
            if (c == minusculas.charAt(i)) {
                return true;
            }
        }
// si no la encontro es por que no es minuscula.
        return false;
    }

    /**
     * Regresa el caracter que este en la misma posicion que el character
     * minuscula.
     */
    public static char aMayuscula(char c) {
        for (int i = 0; i < minusculas.length(); i++) {
            if (c == minusculas.charAt(i)) {
// cuando la encuentre, usar esa posicion
// pero en la cadena de mayusculas
                return mayusculas.charAt(i);
            }
        }
        throw new IllegalStateException("Jamás deberia de llegar aqui.. algo estuvo mal");
    }

    public String pasar(String mayus) {
        String cadena = mayus;
        char[] content = cadena.toCharArray();
        boolean elUltimoLeidoFueEspacio = true; // nomas para que jale.


        for (int i = 0; i < content.length; i++) {
            char c = content[i];
            if (c == ' ') {
                elUltimoLeidoFueEspacio = true;
            } else if (elUltimoLeidoFueEspacio && esMinuscula(c)) {
                content[i] = aMayuscula(c);
                elUltimoLeidoFueEspacio = false;
            }
// si no esta en las dos condiciones anterior
// entonces ignorar
        }
// crear una nueva cadena con el contenido reemplazado
        cadena = new String(content);
        System.out.println(cadena);
        return cadena;

    }
}
