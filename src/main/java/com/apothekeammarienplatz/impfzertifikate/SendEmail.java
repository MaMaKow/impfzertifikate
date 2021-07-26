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
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail {

    public static void main(String[] args) {
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        // Recipient's email ID needs to be mentioned.
        String to = "abcd@mail.com";

        // Sender's email ID needs to be mentioned
        String from = readPropertyFile.getEmailFrom();

        // Assuming you are sending email from localhost
        //String host = "localhost";
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", readPropertyFile.getEmailHost());
        properties.setProperty("mail.user", readPropertyFile.getEmailUserName());
        properties.setProperty("mail.password", readPropertyFile.getEmailUserPassword());
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

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
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            String messageText
                    = "Sehr geehrte Damen und Herren, \n"
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
            String filename = "file.txt";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
