package Mail_Sender_App;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws MessagingException {
       String to="himantabiswadeka234@gmail.com";
       String from="arindombora_ug@ece.nits.ac.in";
       //check for google SMTP protocol
        //port 465
        //smtp.gmail.com is the google host for mails
        String host="smtp.gmail.com";

        Properties properties=System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port",465);
        properties.put("mail.smtp.ssl.enable",true);
        properties.put("mail.smtp.auth",true);

        Session session=Session.getInstance(properties,new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//PassWordAuthentication is a subclass of Authenticator
                return new PasswordAuthentication("arindombora_ug@ece.nits.ac.in","*******");
            }
        });
        session.setDebug(true);

        MimeMessage message=new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        message.setSubject("Test Mail Java");
        message.setText("Ki Khobor Mama");
        Transport.send(message);
    }
}
