package Clases;

import java.util.regex.Pattern;

public class numero_a_letras {

    private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciséis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trescientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};

    public numero_a_letras() {
    }

    public String Convertir(String numero, boolean mayusculas, int moneda) {
        String literal = "";
        String parteDecimal;

        // Si el número utiliza (.) en lugar de (,), se reemplaza
        numero = numero.replace(".", ",");

        // Si el número no tiene parte decimal, se le agrega ,00
        if (numero.indexOf(",") == -1) {
            numero = numero + ",00";
        }

        // Se valida el formato de entrada -> 0,00 y 999 999 999 999,00
        if (Pattern.matches("\\d{1,12},\\d{1,2}", numero)) {
            // Se divide el número 000000000000,00 -> entero y decimal
            String[] Num = numero.split(",");
            // Se da formato al número decimal
            parteDecimal = Num[1] + "/100";
            // Se convierte el número a literal
            if (Long.parseLong(Num[0]) == 0) { // Si el valor es cero
                literal = "cero ";
            } else if (Long.parseLong(Num[0]) >= 1000000000) { // Si es miles de millones
                literal = getMilesMillones(Num[0]);
            } else if (Long.parseLong(Num[0]) >= 1000000) { // Si es millones
                literal = getMillones(Num[0]);
            } else if (Long.parseLong(Num[0]) >= 1000) { // Si es miles
                literal = getMiles(Num[0]);
            } else if (Long.parseLong(Num[0]) >= 100) { // Si es centena
                literal = getCentenas(Num[0]);
            } else if (Long.parseLong(Num[0]) >= 10) { // Si es decena
                literal = getDecenas(Num[0]);
            } else { // Sino unidades -> 9
                literal = getUnidades(Num[0]);
            }

            // Devuelve el resultado en mayúsculas o minúsculas
            if (moneda == 1) {
                if (mayusculas) {
                    return literal.toUpperCase();
                } else {
                    return literal;
                }
            } else {
                if (mayusculas) {
                    return (literal + parteDecimal).toUpperCase();
                } else {
                    return (literal + parteDecimal);
                }
            }
        } else { // Error, no se puede convertir
            return null;
        }
    }

    /* funciones para convertir los numeros a literales */
    private String getUnidades(String numero) { // 1 - 9
        // Si tuviera algún 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }

    private String getDecenas(String num) { // 99
        int n = Integer.parseInt(num);
        if (n < 10) { // Para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) { // Para 20...99
            String u = getUnidades(num);
            if (u.equals("")) { // Para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else { // Números entre 10 y 19
            return DECENAS[n - 10];
        }
    }

    private String getCentenas(String num) { // 999 o 099
        if (Integer.parseInt(num) > 99) { // Es centena
            if (Integer.parseInt(num) == 100) { // Caso especial
                return "cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        } else { // Por ejemplo 099
            // Se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num) + "");
        }
    }

    private String getMiles(String numero) { // 999 999
        // Obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        // Obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n = "";
        // Se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "mil " + getCentenas(c);
        } else {
            return getCentenas(c);
        }
    }

    private String getMillones(String numero) { // 000 000 000
        // Se obtienen los miles
        String miles = numero.substring(numero.length() - 6);
        // Se obtienen los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if (Integer.parseInt(millon) > 1) {
            n = getCentenas(millon) + "millones ";
        } else {
            n = getUnidades(millon) + "millón ";
        }
        return n + getMiles(miles);
    }

    private String getMilesMillones(String numero) { // 000 000 000 000
        // Se obtienen los millones
        String millones = numero.substring(numero.length() - 9);
        // Se obtienen los miles de millones
        String milMillones = numero.substring(0, numero.length() - 9);
        String n = "";
        if (Integer.parseInt(milMillones) > 1) {
            n = getCentenas(milMillones) + "mil millones ";
        } else {
            n = getUnidades(milMillones) + "mil millones ";
        }
        return n + getMillones(millones).replace("millón", "").trim();
    }
}
