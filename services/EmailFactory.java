package services;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import sql.Sql;

/**
 *
 * @author renan
 */
public class EmailFactory {

    Properties props;

    private final String db_user;
    private final String db_pass;
    private final String email_user;
    private final String email_pass;

    private final Session session;

    public EmailFactory(String db_user, String db_pass) {
        this.db_user = db_user;
        this.db_pass = db_pass;

        email_user = "";
        email_pass = "";
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email_user, email_pass);
            }
        });

        session.setDebug(true);
    }

    public void SendEmail(String assunto, String corpo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email_user));
            Address[] toUser = InternetAddress.parse(get_emails());
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);
            message.setText(corpo);
            Transport.send(message);
            JOptionPane.showMessageDialog(null,
                    "Emails enviados com sucesso !");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void SendEmailAnexo(String from, String to, String subject, String message, String attach) throws AddressException {
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setSubject(subject);
            Address addressFrom = new InternetAddress(from);
            mimeMessage.setFrom(addressFrom);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            Multipart multipart = new MimeMultipart();
            // Texto
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/plain");
            multipart.addBodyPart(messageBodyPart);
            //Anexo
            File file = new File(attach);
            DataSource ds = new FileDataSource(file) {
                public String getContentType() {
                    return "application/octet-stream";
                }
            };
            BodyPart mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(ds));
            mbp.setFileName(file.getName());
            mbp.setDisposition(Part.ATTACHMENT);
            multipart.addBodyPart(mbp);
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String get_emails() {
        try {
            Connection con = Sql.getConnection(db_user, db_pass);
            PreparedStatement stmt = con.prepareStatement("SELECT email FROM clientes;");
            ResultSet rs = stmt.executeQuery();

            String emails = "closer@cloclo.com";

            while (rs.next()) {
                emails += ", " + rs.getString("email");
            }

            return emails;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

}
