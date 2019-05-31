package ru.yummy.eat.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.model.enums.PayStatus;
import ru.yummy.eat.service.impl.OrderServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantController.class);

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    @Autowired
    OrderServiceImpl orderService;

    @RequestMapping(value = "/getWalleteBalance", method = RequestMethod.GET)
    public String getWalleteBalance(){
        String result = null;

        return result;
    }

    @RequestMapping(value = "/successMerchant", method = RequestMethod.GET)
    public String merchantSuccess(@RequestParam(value = "m_operation_id", required = false) String mOperationId,
                                  @RequestParam(value = "m_operation_ps", required = false) String operPs,
                                  @RequestParam(value = "m_operation_date", required = false) String operDate,
                                  @RequestParam(value = "m_operation_pay_date", required = false) String operPayDate,
                                  @RequestParam(value = "m_shop", required = false) String shop,
                                  @RequestParam(value = "m_orderid", required = false) String orderId,
                                  @RequestParam(value = "m_amount", required = false) String amount,
                                  @RequestParam(value = "m_curr", required = false) String currency,
                                  @RequestParam(value = "m_desc", required = false) String desc,
                                  @RequestParam(value = "m_status", required = false) String paymentStatus,
                                  @RequestParam(value = "m_sign", required = false) String sign,
                                  @RequestParam(value = "client_email", required = false) String clientEmail,
                                  @RequestParam(value = "client_account", required = false) String clientAccount,
                                  @RequestParam(value = "transfer_id", required = false) String transferId,
                                  @RequestParam(value = "summa_out", required = false) String summaOut,
                                  @RequestParam(value = "m_params", required = false) String dopParams) {
        LOG.info("-------------------- SUCCESS MERCHANT ---------------------------- ");
        LOG.info("m_operation_id: "+mOperationId );
        LOG.info("m_operation_ps: "+operPs );
        LOG.info("m_operation_date: "+operDate );
        LOG.info("m_operation_pay_date: "+operPayDate );
        LOG.info("m_shop: "+shop );
        LOG.info("m_orderid: "+orderId );
        LOG.info("m_amount: "+amount );
        LOG.info("m_curr: "+currency );
        LOG.info("m_desc: "+desc );
        LOG.info("m_status: "+paymentStatus );
        LOG.info("m_sign: "+sign );
        LOG.info("client_email: "+clientEmail );
        LOG.info("client_account: "+clientAccount );
        LOG.info("transfer_id: "+transferId );
        LOG.info("summa_out: "+summaOut );
        LOG.info("m_params: "+dopParams );

        String result = SUCCESS;
        if (SUCCESS.equals( paymentStatus ) ) {
            try {
                orderService.updateOrderStatus( orderId, amount, PayStatus.SUCCESS.name() );
            } catch (Exception e) {
                result = ERROR;
            }
        }
        return result+"-"+mOperationId+", m_orderid: "+orderId ;
    }

    @RequestMapping(value = "/failMerchant", method = RequestMethod.GET)
    public String merchantFail(@RequestParam(value = "m_operation_id", required = false) String mOperationId,
                               @RequestParam(value = "m_operation_ps", required = false) String operPs,
                               @RequestParam(value = "m_operation_date", required = false) String operDate,
                               @RequestParam(value = "m_operation_pay_date", required = false) String operPayDate,
                               @RequestParam(value = "m_shop", required = false) String shop,
                               @RequestParam(value = "m_orderid", required = false) String orderId,
                               @RequestParam(value = "m_amount", required = false) String amount,
                               @RequestParam(value = "m_curr", required = false) String currency,
                               @RequestParam(value = "m_desc", required = false) String desc,
                               @RequestParam(value = "m_status", required = false) String paymentStatus,
                               @RequestParam(value = "m_sign", required = false) String sign,
                               @RequestParam(value = "client_email", required = false) String clientEmail,
                               @RequestParam(value = "client_account", required = false) String clientAccount,
                               @RequestParam(value = "transfer_id", required = false) String transferId,
                               @RequestParam(value = "summa_out", required = false) String summaOut,
                               @RequestParam(value = "m_params", required = false) String dopParams) {
        LOG.info("-------------------- FAIL MERCHANT ---------------------------- ");
        LOG.info("m_operation_id: "+mOperationId );
        LOG.info("m_operation_ps: "+operPs );
        LOG.info("m_operation_date: "+operDate );
        LOG.info("m_operation_pay_date: "+operPayDate );
        LOG.info("m_shop: "+shop );
        LOG.info("m_orderid: "+orderId );
        LOG.info("m_amount: "+amount );
        LOG.info("m_curr: "+currency );
        LOG.info("m_desc: "+desc );
        LOG.info("m_status: "+paymentStatus );
        LOG.info("m_sign: "+sign );
        LOG.info("client_email: "+clientEmail );
        LOG.info("client_account: "+clientAccount );
        LOG.info("transfer_id: "+transferId );
        LOG.info("summa_out: "+summaOut );
        LOG.info("m_params: "+dopParams );
        String result = SUCCESS;
        try {
            orderService.updateOrderStatus( orderId, amount, PayStatus.FAIL.name() );
        } catch (Exception e) {
            result = ERROR;
        }
        return result;
    }

    @GetMapping("/handlerMerchant")
    public String merchantHandler(@RequestParam(value = "m_operation_id", required = false) String mOperationId,
                                  @RequestParam(value = "m_operation_ps", required = false) String operPs,
                                  @RequestParam(value = "m_operation_date", required = false) String operDate,
                                  @RequestParam(value = "m_operation_pay_date", required = false) String operPayDate,
                                  @RequestParam(value = "m_shop", required = false) String shop,
                                  @RequestParam(value = "m_orderid", required = false) String orderId,
                                  @RequestParam(value = "m_amount", required = false) String amount,
                                  @RequestParam(value = "m_curr", required = false) String currency,
                                  @RequestParam(value = "m_desc", required = false) String desc,
                                  @RequestParam(value = "m_status", required = false) String paymentStatus,
                                  @RequestParam(value = "m_sign", required = false) String sign,
                                  @RequestParam(value = "client_email", required = false) String clientEmail,
                                  @RequestParam(value = "client_account", required = false) String clientAccount,
                                  @RequestParam(value = "transfer_id", required = false) String transferId,
                                  @RequestParam(value = "summa_out", required = false) String summaOut,
                                  @RequestParam(value = "m_params", required = false) String dopParams) {
        LOG.info("-------------------- HANDLER MERCHANT ---------------------------- ");
        LOG.info("m_operation_id: "+mOperationId );
        LOG.info("m_operation_ps: "+operPs );
        LOG.info("m_operation_date: "+operDate );
        LOG.info("m_operation_pay_date: "+operPayDate );
        LOG.info("m_shop: "+shop );
        LOG.info("m_orderid: "+orderId );
        LOG.info("m_amount: "+amount );
        LOG.info("m_curr: "+currency );
        LOG.info("m_desc: "+desc );
        LOG.info("m_status: "+paymentStatus );
        LOG.info("m_sign: "+sign );
        LOG.info("client_email: "+clientEmail );
        LOG.info("client_account: "+clientAccount );
        LOG.info("transfer_id: "+transferId );
        LOG.info("summa_out: "+summaOut );
        LOG.info("m_params: "+dopParams );
        return SUCCESS;
    }
}
