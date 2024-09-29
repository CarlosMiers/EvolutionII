/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.MailcapCommandMap;
import javax.activation.CommandMap;

public class EnviarCorreoFais {

    private final Properties properties = new Properties();

    private void setupMail() {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        //  mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        //  mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        //  mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
    }

    private Session session;

    private void init() {
        setupMail();
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        properties.put("mail.imap.socketFactory.class", SSL_FACTORY);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");        
        properties.put("mail.imap.socketFactory.fallback", "false");
        properties.put("mail.smtp.host", Config.ServidorCorreo.trim());  //El servidor SMTP 
        properties.put("mail.smtp.user", Config.CorreoPrincipal.trim());
        properties.put("mail.smtp.clave", Config.PaseServidorCorreo.trim());    //La clave de la cuenta
        properties.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        properties.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        properties.put("mail.smtp.port", Config.PuertoCorreo.trim()); //El puerto SMTP seguro de Google        
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.debug", "true");  //false

        session = Session.getDefaultInstance(properties);
        System.out.println("CORREO");
    }

    public void sendEmail(String correo, String cAsunto, String cMensaje, String reporte) {

        init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Config.CorreoPrincipal.trim()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.toString()));   //Se podrían añadir varios de la misma manera
            message.setSentDate(new Date());

            // set plain text message
            message.setSubject(cAsunto);
            message.setText(cMensaje);
            BodyPart texto = new MimeBodyPart();
            texto.setText(cAsunto);

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(reporte)));

            //  adjunto.setContent(attachmentDataStream, "application/pdf");
            adjunto.setHeader("Content-Type", "application/pdf; name=\"+reporte\"");
            adjunto.setFileName(reporte.trim());

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();

            multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            message.setContent(multiParte);
            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect(Config.CorreoPrincipal.trim(), Config.PaseServidorCorreo.trim());
            t.sendMessage(message, message.getAllRecipients());
            t.close();

            System.out.println("Email sent successfully!");
            System.out.println("CORREO");
        } catch (MessagingException me) {
            System.out.println(me);
            return;
        }

    }
}
