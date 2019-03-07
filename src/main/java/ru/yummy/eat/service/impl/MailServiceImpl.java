package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.util.AppUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service("mailService")
public class MailServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    public void sendEmail(String result) {
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties("mail.properties");

            Session mailSession = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(mailSession);

            MimeMessageHelper helper = new MimeMessageHelper( message, true, "UTF-8" );
            helper.setFrom( new InternetAddress( "alexey.kvitko.15@gmail.com", false ) );
            helper.setTo( new InternetAddress("alexey.kvitko.15@gmail.com") );
            helper.setSubject("Обновление базы предприятий");
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("<html><body><H1><b>"+ AppUtils.formatDate(AppConstants.UPDATE_DATE_FORMAT, new Date() ) +"</b></H1><br>" );
            bodyBuilder.append(result);
            bodyBuilder.append("<br><br><p style='font-size:10sp;color:#5E5E5E'>Письмо сгенерировано автоматически.Не отвечайте</p>");
            bodyBuilder.append("</body></html>");
            helper.setText( bodyBuilder.toString(), true );
            Transport tr = mailSession.getTransport();
            tr.connect(null, "alex09079901");
            tr.sendMessage( message,message.getAllRecipients() );
            tr.close();

//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//        MimeBodyPart attachPart = new MimeBodyPart();
//
//        attachPart.attachFile("/var/tmp/image19.png");
//        multipart.addBodyPart(attachPart);
//        msg.setContent(multipart);
//            Transport.send(msg);
        } catch (Exception e) {
            LOG.error("Can't send email" + e.getMessage());
            e.printStackTrace();
        }

    }
}
