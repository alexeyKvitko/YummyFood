package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.ClientOrder;
import ru.yummy.eat.entity.OrderEntity;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.BasketModel;
import ru.yummy.eat.model.ClientOrderModel;
import ru.yummy.eat.model.enums.PayStatus;
import ru.yummy.eat.model.enums.PayType;
import ru.yummy.eat.repo.ClientOrderRepository;
import ru.yummy.eat.repo.CompanyRepository;
import ru.yummy.eat.repo.OrderEntityRepository;
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


    public ApiResponse createClientOrder(ClientOrderModel clientOrderModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            ClientOrder clientOrder = convertUtils.convertModelToClientOrder(clientOrderModel);
            if (PayType.WALLET.name().equals(clientOrder.getPayType())) {
                clientOrder.setPayStatus(PayStatus.EXPECTED.name());
            }
            List<OrderEntity> orderEntities = convertUtils.convertModelsToOrderEntityList(clientOrder.getId(), clientOrderModel.getOrders());
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
                                                                            .findFirst().get().getCompany().getLogo();
                    clientOrder.setCompanyLogo( logo );
                }
                if( idx == 1 ){
                    clientOrder.setCompanyTwoId( key );
                    clientOrder.setCompanyTwoName( orderCompanies.get( key ) );
                    clientOrder.setCompanyLogo( AppConstants.COMPANY_MIX_LOGO );
                }
                idx++;
            }

            clientOrderRepo.save(clientOrder);
            orderEntityRepo.saveAll(orderEntities);
            response.setResult(clientOrder.getId());
            LOG.info("Creare Order with Id: " + clientOrder.getId());
        } catch (Exception e) {
            LOG.error("Exception got when save client order: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public void updateOrderStatus(String orderId, String payAmount, String payStatus) {
        try {
            Integer id = Integer.valueOf(orderId);
            ClientOrder clientOrder = clientOrderRepo.findById(id).get();
            clientOrder.setPayAmount(payAmount);
            clientOrder.setPayStatus(payStatus);
            clientOrderRepo.save(clientOrder);
        } catch (Exception e) {
            LOG.error("Exception when update order status: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ApiResponse getClientOrders( String uuid ) {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            List<ClientOrder> clientOrders = clientOrderRepo.findAllByClientUuidOrderByOrderDateDesc( uuid );
            response.setResult( convertUtils.convertClientOrdersToModels( clientOrders ));
        } catch (Exception e) {
            LOG.error("Exception got when get client orders: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }


}
