/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author hp
 */
public class UUID {

    public static String crearUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
