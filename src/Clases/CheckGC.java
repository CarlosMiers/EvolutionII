/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author Pc_Server
 */
public class CheckGC {

    public static void main(String[] args) {
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = "https://api.horuscloud.net/staging/cobrador/bancard/tpago/linkSuscripcion/recuperar/1/10";
        String cUser = "testing";
        String cPass = "kdghdufkf254";
        String cJson = "{}";
        System.out.println(cJson);
        String cPost="GET";
        String cRespuesta = curl.ApiSuscripcion(cUrl, cUser, cPass,cJson,cPost);

        String url = cRespuesta;

        System.out.println(url);

    }
}
