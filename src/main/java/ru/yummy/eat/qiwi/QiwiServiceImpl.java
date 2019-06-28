package ru.yummy.eat.qiwi;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service("qiwiService")
public class QiwiServiceImpl {

    private static final String CALLBACK_URL = "http://edanyma.ru/paymentResult";

    @Value("${qiwi.public.key}")
    private String publicKey;

    @Value("${qiwi.secret.key}")
    private String secretKey;

    private static final Logger LOG = LoggerFactory.getLogger(QiwiServiceImpl.class);

    public String generatePaymentUrl(Integer orderId, String amount) {
        LOG.info("Create QIWI PAYMENT: orderId[" + orderId + "], amount[" + amount + "]");
        String paymentUrl = null;
        try {
            BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);
            LOG.info("Bill Client Verison [" + client.getAppVersion() + "]");
            MoneyAmount moneyAmount = new MoneyAmount(
                    BigDecimal.valueOf(Long.valueOf(amount)),
                    Currency.getInstance("RUB")
            );
            LOG.info("Money amount [" + moneyAmount.toString() + "]");
            paymentUrl = client.createPaymentForm(new PaymentInfo(publicKey, moneyAmount, orderId.toString(), CALLBACK_URL));
            LOG.info("Payment URL [" + paymentUrl + "]");
        } catch (Exception e) {
            LOG.error("Can not create qiwi url: " + e.getMessage());
            e.printStackTrace();
        }
        return paymentUrl;
    }

}
