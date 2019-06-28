package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.ClientOrder;
import ru.yummy.eat.entity.OrderEntity;
import ru.yummy.eat.model.*;
import ru.yummy.eat.model.enums.PayStatus;
import ru.yummy.eat.model.enums.PayType;
import ru.yummy.eat.qiwi.QiwiServiceImpl;
import ru.yummy.eat.repo.ClientOrderRepository;
import ru.yummy.eat.repo.CompanyRepository;
import ru.yummy.eat.repo.OrderEntityRepository;
import ru.yummy.eat.util.AppUtils;
import ru.yummy.eat.util.ConvertUtils;

import java.util.*;

@Service("orderService")
public class OrderServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    ClientOrderRepository clientOrderRepo;

    @Autowired
    OrderEntityRepository orderEntityRepo;

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    QiwiServiceImpl qiwiService;

    @Autowired
    PayeerServiceImpl payeerService;


    public ApiResponse<ClientOrderResponse> createClientOrder(ClientOrderModel clientOrderModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            ClientOrder clientOrder = convertUtils.convertModelToClientOrder(clientOrderModel);

            Map<Integer, String> orderCompanies = new HashMap<>();
            for(BasketModel basketModel : clientOrderModel.getOrders() ){
                orderCompanies.put( basketModel.getCompany().getId(), basketModel.getCompany().getDisplayName() );
            }
            int idx = 0;
            for(Integer key: orderCompanies.keySet() ){
                if( idx == 0 ){
                    clientOrder.setCompanyOneId( key );
                    clientOrder.setCompanyOneName( orderCompanies.get( key ) );
                    String logo = clientOrderModel.getOrders().stream()
                                                    .filter( model -> model.getCompany().getId().equals( key ) )
                                                                            .findFirst().get().getCompany().getThumb();
                    clientOrder.setCompanyLogo( logo+AppConstants.PNG_EXT );
                }
                if( idx == 1 ){
                    clientOrder.setCompanyTwoId( key );
                    clientOrder.setCompanyTwoName( orderCompanies.get( key ) );
                    clientOrder.setCompanyLogo( AppConstants.COMPANY_MIX_LOGO );
                }
                idx++;
            }
            clientOrderRepo.save( clientOrder );
            List<OrderEntity> orderEntities = convertUtils.convertModelsToOrderEntityList(clientOrder.getId(), clientOrderModel.getOrders());
            orderEntityRepo.saveAll(orderEntities);
            String payUrl = null;
            if ( PayType.QIWI.name().equals(clientOrder.getPayType() ) )   {
                payUrl = qiwiService.generatePaymentUrl( clientOrder.getId(), clientOrder.getOrderPrice().toString() );

            } else  if ( PayType.PAYEER.name().equals(clientOrder.getPayType()) ) {
                String now = AppUtils.formatDate( AppConstants.DATE_FORMAT, new Date(), Locale.ENGLISH );
                payUrl = payeerService.generatePayeerPaymentURL( clientOrder.getId().toString(), clientOrder.getOrderPrice().toString(), now );
            }
            if ( payUrl != null ){
                clientOrder.setPayStatus( PayStatus.EXPECTED.name() );
                clientOrder.setPayUrl( payUrl );
                clientOrderRepo.save( clientOrder );
            }
            ClientOrderResponse orderResponse = new ClientOrderResponse();
            orderResponse.setOrderId( clientOrder.getId() );
            orderResponse.setPayUrl( payUrl );
            response.setResult( orderResponse );
            LOG.info("Creare Order: " + orderResponse.toString());
        } catch (Exception e) {
            LOG.error("Exception got when save client order: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public void updateOrderStatus(String orderId, String payAmount, String payStatus) {
        try {
            LOG.info("START UPDATE ORDER ["+orderId+"], amount ["+payAmount+"], status ["+payStatus+"]");
            Integer id = Integer.valueOf(orderId);
            ClientOrder clientOrder = clientOrderRepo.findById(id).get();
            clientOrder.setPayAmount(payAmount);
            clientOrder.setPayStatus(payStatus);
            clientOrderRepo.save(clientOrder);
            LOG.info(" OPRDER ["+orderId+"], updated");
        } catch (Exception e) {
            LOG.error("Exception when update order status: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ApiResponse<ExistOrders> getClientOrders( String uuid ) {
        ApiResponse<ExistOrders> response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            List<ClientOrder> clientOrders = clientOrderRepo.findAllByClientUuidOrderByOrderDateDesc( uuid );
            List<ClientOrderModel> orderModels = new ArrayList<>();
            for(ClientOrder order: clientOrders ){
                List<OrderEntity> orderEntities = orderEntityRepo.findAllByOrderId( order.getId() );
                List<BasketModel> basketModels = convertUtils.convertOrderEntitiesToBasketModel( orderEntities );
                ClientOrderModel orderModel = convertUtils.convertClientOrderToModel( order );
                orderModel.setOrders( basketModels );
                orderModels.add( orderModel );
            }
            response.setResult( new ExistOrders( orderModels ) );
        } catch (Exception e) {
            LOG.error("Exception got when get client orders: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }


}
