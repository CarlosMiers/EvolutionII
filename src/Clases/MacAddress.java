package Clases;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Autor: Adwind
* Descripcion: Codigo para obtener la MAC de cualquier PC
* funciona en todas las versiones de Windows y Linux
* Fecha: 16-Noviembre-2011
* NOTA: Las versiones que existen en la red hacen uso de "ipconfig" para obtener
* la MAC, haciendo mas extenso el codigo. Este codigo es corto y flexible XD
* NOTA2: Lenguaje JAVA XD para los N00BS XD
* Pagina: http://www.indetectables.net
* Derechos Reservados: Este codigo es de uso educativo y esta prohibido
* sea distribuido fuera de http://www.indetectables.net a menos que me pidas permiso
*
* NOTA3: No se crean XD
 */
public class MacAddress {

    public static void main(String[] args) throws UnknownHostException, SocketException {
        NetworkInterface a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        InetAddress ip;
        ip = InetAddress.getLocalHost();

        System.out.println("Current IP address : " + ip.getHostAddress());
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        System.out.print("Current MAC address : ");
        StringBuilder sb = new StringBuilder();

        System.out.println("INTERFASES " + a.getInterfaceAddresses());
        System.out.println("display " + a.getDisplayName());
        System.out.println("PLACA " + a.getHardwareAddress());

        byte[] b = a.getHardwareAddress();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());
    }
}
