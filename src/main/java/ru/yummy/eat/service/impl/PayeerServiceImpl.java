package ru.yummy.eat.service.impl;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service("payeerService")
public class PayeerServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger( PayeerServiceImpl.class );

    private static final String CURRENCY = "RUB";
    private static final String PAYEER_URL = "https://payeer.com/merchant/?m_curr="+CURRENCY+"&lang=ru";
    private static final String TRANSACTION_DESCRIPTION = "ЕдаНяма.рф. Оплата заказа № %s, от %s";

    private static final String PARAM_SHOP = "&m_shop=";
    private static final String PARAM_ORDER_ID ="&m_orderid=";
    private static final String PARAM_AMOUNT = "&m_amount=";
    private static final String PARAM_DESC = "&m_desc=";
    private static final String PARAM_SIGN= "&m_sign=";

    @Value("${payeer.shop.id}")
    private String shopId;

    @Value("${payeer.secret.key}")
    private String secretKey;

    public String generatePayeerPaymentURL(String orderId, String amount, String date){
        String resultURL = null;
        try {
            String base64Desc = Base64.getEncoder().encodeToString(
                    String.format( TRANSACTION_DESCRIPTION, orderId, date ).getBytes("utf-8"));
            String[] params = new String[]{ shopId, orderId, amount, CURRENCY, base64Desc, secretKey };
            String sign = getSHA256( StringUtils.join( params, ":") ).toUpperCase();
            resultURL = PAYEER_URL
                    + PARAM_SHOP + shopId
                    + PARAM_ORDER_ID + orderId
                    + PARAM_AMOUNT + amount
                    + PARAM_DESC + base64Desc
                    + PARAM_SIGN + sign;
        } catch (Exception e) {
            LOG.error("Couldn't generate payeer payment URL, for order: " + orderId, e);
            resultURL = null;
        }
        return resultURL;
    }


    private static  String getSHA256( String source ) throws NoSuchAlgorithmException {
        String hash = null;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                source.getBytes(StandardCharsets.UTF_8));
        hash = bytesToHex( encodedhash );
        return hash;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
