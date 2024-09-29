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
        String cUrl = "https://api.horuscloud.net/staging/cobrador/bancard/tpago/linkSuscription/crear";
        String cUser = "testing";
        String cPass = "kdghdufkf254";
        String cCJson = "{\"amount\":500000,\n"
                + "\"first_installment_amount\":100000,\n"
                + "\"description\":\"PRESTAMOS\",\n"
                + "\"periodicity\":1,\n"
                + "\"debit_day\":20,\n"
                + "\"unlimited\":true,\n"
                + "\"start_date\":\"24/09/2024\",\n"
                + "\"end_date\":\"24/02/2025\",\n"
                + "\"reference_id\":\"01246400\"}";
        System.out.println(cCJson);
        
        String cJson = "{\"amount\":"+500000+",\n"
                + "\"first_installment_amount\":"+100000+",\n"
                + "\"description\":\"FINANCIACION\",\n"
                + "\"periodicity\":"+1+",\n"
                + "\"debit_day\":"+20+",\n"
                + "\"unlimited\":"+true+",\n"
                + "\"start_date\":\"24/09/2024\",\n"
                + "\"end_date\":\"24/02/2025\",\n"
                + "\"reference_id\":"+"\"01246400\"}";
        //cJson="'"+cJson+"'";
        System.out.println(cJson);
        String cPost="POST";
        String cRespuesta = curl.ApiSuscripcion(cUrl, cUser, cPass,cJson,cPost);

        String url = cRespuesta.substring(cRespuesta.indexOf("https"), cRespuesta.lastIndexOf("\""));

        System.out.println(url);

    }
}
