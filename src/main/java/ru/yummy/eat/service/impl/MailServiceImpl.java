package ru.yummy.eat.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.util.AppUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service("mailService")
public class MailServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private Configuration freemarkerConfig;

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
        } catch (Exception e) {
            LOG.error("Can't send email" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean sendConfirmCodeEmail(String mailTo, String code) {
        boolean result = true;
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties("mail.properties");
            Session mailSession = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(mailSession);
            MimeMessageHelper helper = new MimeMessageHelper( message, true, "UTF-8" );
            helper.setFrom( new InternetAddress( "alexey.kvitko.15@gmail.com", false ) );
            helper.setTo( new InternetAddress( mailTo ) );
            helper.setSubject("Крымская служба доставки еды");
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("<html><body><H2><b>ЕдаНяма</b></H2><br>" );
            bodyBuilder.append("<H3><p>Код подтверждения регистрации</p></H3>" );
            bodyBuilder.append("<h1><b style='font-size:28px;color:#F53240'>"+code.charAt(0)+"&nbsp;"+code.charAt(1)+"&nbsp;"
                                    +code.charAt(2)+"&nbsp;"+code.charAt(3)+"&nbsp;</b></h1><br><br>" );
            bodyBuilder.append("<br><br><p style='font-size:10px;color:#5E5E5E'>Письмо сгенерировано автоматически.Не отвечайте</p>");
            bodyBuilder.append("</body></html>");
            helper.setText( bodyBuilder.toString(), true );
            Transport tr = mailSession.getTransport();
            tr.connect(null, "alex09079901");
            tr.sendMessage( message,message.getAllRecipients() );
            tr.close();
        } catch (Exception e) {
            LOG.error("Can't send email" + e.getMessage());
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    public void sendEmailWithTemplate() {
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties("mail.properties");

            Session mailSession = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(mailSession);

            MimeMessageHelper helper = new MimeMessageHelper( message, true, "UTF-8" );
            helper.setFrom( new InternetAddress( "alexey.kvitko.15@gmail.com", false ) );
            helper.setTo( new InternetAddress("alexey.kvitko.15@gmail.com") );
            Map model = new HashMap();
            model.put("name", "yummyeat.com");
            model.put("location", "Crimea");
            model.put("signature", "https://edanyma.ru");
            Template t = freemarkerConfig.getTemplate("email.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setText(html, true);

            Transport tr = mailSession.getTransport();
            tr.connect(null, "alex09079901");
            tr.sendMessage( message,message.getAllRecipients() );
            tr.close();
        } catch (Exception e) {
            LOG.error("Can't send email" + e.getMessage());
            e.printStackTrace();
        }
    }

}
