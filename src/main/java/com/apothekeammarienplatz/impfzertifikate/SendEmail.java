/*
 * Copyright (C) 2021 Mandelkow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.apothekeammarienplatz.impfzertifikate;

/**
 * taken from https://www.tutorialspoint.com/java/java_sending_email.htm
 */
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;

public class SendEmail {

    SendEmail(String vorname, String nachname, String email_to) throws IOException, InterruptedException {

        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        // Recipient's email ID needs to be mentioned.
        //String to = "impf-test@martin-mandelkow.de";
        String to = email_to;

        // Sender's email ID needs to be mentioned
        String from = readPropertyFile.getEmailFrom();

        // Assuming you are sending email from localhost
        //String host = "localhost";
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", readPropertyFile.getEmailHost());
        properties.put("mail.smtp.port", Integer.valueOf(readPropertyFile.getEmailPort()));
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.dsn.notify", "SUCCESS");
        properties.put("mail.smtp.dsn.ret", "FULL");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Impfzertifikate");

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            String messageText
                    = "Hallo " + vorname + " " + nachname + ", \n"
                    + "\n"
                    + "wir haben Ihre Impfzertifikate fertiggestellt. Sie sind als PDF dieser Email beigefügt.\n"
                    + "Wenn Sie noch Fragen haben, antworten Sie auf diese Mail oder kommen Sie persönlich vorbei.\n"
                    + "\n"
                    + "Mit freundlichen Grüßen\n"
                    + "\n"
                    + "Ihre Apotheke\n"
                    + "\n";
            messageBodyPart.setText(messageText);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "C:\\Users\\Apothekenadmin\\Desktop\\Zertifikate\\Impfzertifikat_" + replaceUmlaut(vorname) + "_" + replaceUmlaut(nachname) + ".pdf";
            messageBodyPart.attachFile(filename);
            multipart.addBodyPart(messageBodyPart);
            try {
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                String filename2 = "C:\\Users\\Apothekenadmin\\Desktop\\Zertifikate\\Impfzertifikat_" + replaceUmlaut(vorname) + "_" + replaceUmlaut(nachname) + " (1).pdf";
                messageBodyPart2.attachFile(filename2);
                multipart.addBodyPart(messageBodyPart2);
            } catch (Exception e) {
                System.out.println("Es gab nur eine Datei zu senden.");
            }
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect(readPropertyFile.getEmailHost(), readPropertyFile.getEmailUserName(), readPropertyFile.getEmailUserPassword());
            message.saveChanges();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            Thread.sleep(1000); //Für den Fall von rate limiting möchten wir dem Mailserver etwas Pause gönnen.
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            Thread.sleep(1000);
        }
    }

    private String replaceUmlaut(String input) {

        //Die dateien werden mit gekürzten Umlauten gespeichert:
        String output = input.replace("ü", "u")
                .replace("ö", "o")
                .replace("ä", "a")
                .replace("ß", "s")
                .replace("Ü", "U")
                .replace("Ö", "O")
                .replace("Ä", "A");

        return output;
    }
}
