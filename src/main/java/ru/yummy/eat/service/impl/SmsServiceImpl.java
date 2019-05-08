package ru.yummy.eat.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.util.AppUtils;

import javax.annotation.PostConstruct;

@Service
public class SmsServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapServiceImpl.class);

    @Value("${sms.from}")
    private String from;
    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;

    @PostConstruct
    public void postConstruct() {
        Twilio.init(username, password);
    }

    public void send(String to, String text) {
        try {
//            Message message = Message.creator(username, new PhoneNumber(to), new PhoneNumber(from), text).create();
            Message message = Message.creator(username, new PhoneNumber(to), from, text).create();
            LOG.info("New message sent: {}, to: {}", message.getSid(), to);
        } catch (Exception e) {
            LOG.error("New message sent failed: to: {}", to, e);
        }
    }

    public String send( String to ) {
        String code = AppUtils.getRandomBetweenRange( 4000,9999 )+"";
        try {
            Message message = Message.creator(username, new PhoneNumber( AppConstants.PHONE_PREFIX + to ),
                                                                     new PhoneNumber(from), "ЕдаНяма.рф, код подтверждения: "+code).create();
            LOG.info("New message sent: {}, to: {}",   message.getSid(), to);
        } catch (Exception e) {
            code = null;
            LOG.error("New message sent failed: to: {}", to, e);
        }
        return code;
    }

}
