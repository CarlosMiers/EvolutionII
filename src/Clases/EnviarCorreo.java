/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarCorreo {

    private final Properties properties = new Properties();

    private String password;

    private Session session;

    private void init() {
        sendEmail();
        properties.put("mail.smtp.host", "mail.asociacionfapasa.com");  //El servidor SMTP de Google
        properties.put("mail.smtp.user", "extractos@asociacionfapasa.com");
        properties.put("mail.smtp.clave", "Asofapasa456");    //La clave de la cuenta
        properties.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        properties.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        //properties.put("mail.smtp.port", "25"); //El puerto SMTP seguro de Google        
        properties.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google        

        session = Session.getDefaultInstance(properties);
        System.out.println("CORREO");
    }

    public void sendEmail() {
        init();
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("extractos@asociacionfapasa.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("carlosgmiers@gmail.com"));   //Se podrían añadir varios de la misma manera
            message.setHeader("X-Mailer", "correo");
            message.setSentDate(new Date());
            message.setSubject("asunto");
            message.setText("cuerpo");

            Transport transport = session.getTransport("smtp");
            transport.connect("mail.asociacionfapasa.com", "extractos@asociacionfapasa.com", "Asofapasa456");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("CORREO2");
        } catch (MessagingException me) {

            System.out.println(me);
            return;
        }

    }
}
