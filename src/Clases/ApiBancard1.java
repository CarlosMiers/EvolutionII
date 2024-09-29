package Clases;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ApiBancard1 {

    private void doTrustToCertificates() throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    return;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    return;
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public static void main(String[] args) {
        try {
            // URL del endpoint
            new ApiBancard1().doTrustToCertificates();
            String url = "https://api.horuscloud.net/staging/cobrador/bancard/tpago/linkSuscription/crear";

            // Datos de la solicitud en formato JSON
            String json = "{\"amount\":500000,\n"
                    + "\"first_installment_amount\":100000,\n"
                    + "\"description\":\"PRESTAMOS\",\n"
                    + "\"periodicity\":1,\n"
                    + "\"debit_day\":20,\n"
                    + "\"unlimited\":true,\n"
                    + "\"start_date\":\"24/09/2024\",\n"
                    + "\"end_date\":\"24/02/2025\",\n"
                    + "\"reference_id\":\"01246400\"}";

            /*            String json = "{amount:" + 500000 + ","
                    + "first_installment_amount:" + 100000 + ","
                    + "description:" + "PRESTAMOS" + ","
                    + "periodicity:" + 1 + ","
                    + "debit_day:" + 20 + ","
                    + "unlimited:" + false + ","
                    + "start_date:" + "24/09/2024" + ","
                    + "end_date:" + "24/02/2025" + ","
                    + "reference_id:" + "01246400" + "}";*/
            // Codificación de la autorización
            String username = "testing";
            String password = "kdghdufkf254";
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            // Crear la conexión
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Configurar la solicitud
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Basic " + encodedAuth);

            // Habilitar el envío de datos
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(json);
                wr.flush();
            }

            // Leer la respuesta
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Imprimir la respuesta
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
