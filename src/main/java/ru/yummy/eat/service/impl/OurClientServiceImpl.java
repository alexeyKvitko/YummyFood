package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.ClientOrder;
import ru.yummy.eat.entity.OrderEntity;
import ru.yummy.eat.entity.OurClient;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.ClientOrderModel;
import ru.yummy.eat.model.OurClientModel;
import ru.yummy.eat.repo.ClientOrderRepository;
import ru.yummy.eat.repo.OrderEntityRepository;
import ru.yummy.eat.repo.OurClientRepository;
import ru.yummy.eat.util.ConvertUtils;

import java.util.List;

@Service("clientService")
public class OurClientServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(OurClientServiceImpl.class);

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    OurClientRepository clientRepo;

    @Autowired
    ClientOrderRepository clientOrderRepo;

    @Autowired
    OrderEntityRepository orderEntityRepo;


    public String registerClient( OurClientModel ourClientModel ) {
        String result = null;
        OurClient ourClient = convertUtils.convertModelToOurClient( ourClientModel );
        result = checkForExistingClient( ourClient );
        if( result == null ){
            try {
                clientRepo.save( ourClient );
                result = ourClient.getUuid();
            } catch ( Exception e ){
                LOG.error( "Exception got when register client: "+e.getMessage() );
                result = e.getMessage();
            }

        }
        return result;
    }

    public String authorizationClient( OurClientModel ourClientModel ) {
        String result = null;
        OurClient ourClient = convertUtils.convertModelToOurClient(ourClientModel);
        OurClient existClient = null;
        try {
            if (ourClientModel.getEmail() != null) {
                existClient = clientRepo.findByEmail(ourClient.getEmail());
                result = existClient == null ? AppConstants.EMAIL_NOT_FOUND : null;
            } else {
                existClient = clientRepo.findByPhone(ourClient.getPhone());
                result = existClient == null ? AppConstants.PHONE_NOT_FOUND : null;
            }
            if (existClient != null && !convertUtils.isPasswordMatches( ourClientModel.getPassword(), existClient.getPassword() ) ) {
                result = AppConstants.WRONG_PASSWORD;
            }
            if (result == null) {
                result = existClient.getUuid();
            }
        } catch (Exception e) {
            LOG.error("Exception got when register client: " + e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    public String checkForExistingClient( OurClient ourClient ){
        String result = null;
        OurClient existClient = clientRepo.findByPhone( ourClient.getPhone() );
        if ( existClient == null && ourClient.getEmail() != null ){
            existClient = clientRepo.findByEmail( ourClient.getEmail() );
            if( existClient != null ){
                result = AppConstants.EMAIL_EXIST;
            }
        } else {
            result = AppConstants.PHONE_EXIST;
        }
        return result;
    }

    public ApiResponse createClientOrder(ClientOrderModel clientOrderModel){
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            ClientOrder clientOrder = convertUtils.convertModelToClientOrder( clientOrderModel );
            clientOrderRepo.save( clientOrder );
            List<OrderEntity> orderEntities = convertUtils.convertModelsToOrderEntityList( clientOrder.getId(), clientOrderModel.getOrders() );
            orderEntityRepo.saveAll( orderEntities );
            response.setResult( clientOrder.getId() );
        } catch ( Exception e ){
            LOG.error( "Exception got when save client order: "+e.getMessage() );
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    public ApiResponse getClientByUUID(String uuid){
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            OurClientModel ourClientModel = convertUtils.convertOurClientToModel( clientRepo.findByUuid( uuid) );
            response.setResult( ourClientModel );
        } catch ( Exception e ){
            LOG.error( "Exception got when register client: "+e.getMessage() );
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
       return response;
    }


}
