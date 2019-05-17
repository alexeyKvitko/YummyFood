package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.ClientOrder;
import ru.yummy.eat.entity.FavoriteCompany;
import ru.yummy.eat.entity.OrderEntity;
import ru.yummy.eat.entity.OurClient;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.ClientOrderModel;
import ru.yummy.eat.model.FavoriteCompanyModel;
import ru.yummy.eat.model.OurClientModel;
import ru.yummy.eat.repo.ClientOrderRepository;
import ru.yummy.eat.repo.FavoriteCompanyRepository;
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

    @Autowired
    FavoriteCompanyRepository favoriteCompanyRepo;

    @Autowired
    MailServiceImpl mailService;

    @Autowired
    SmsServiceImpl smsService;


    public String registerClient(OurClientModel ourClientModel) {
        String result = null;
        OurClient ourClient = convertUtils.convertModelToOurClient(ourClientModel);
        result = checkForExistingClient(ourClient);
        if (result == null) {
            try {
                clientRepo.save(ourClient);
                result = ourClient.getUuid();
            } catch (Exception e) {
                LOG.error("Exception got when register client: " + e.getMessage());
                result = e.getMessage();
            }

        }
        return result;
    }

    public OurClientModel registerMobileClient(OurClientModel ourClientModel) {
        OurClient ourClient = convertUtils.convertModelToOurClient(ourClientModel);
        String checkResult = null;
        if (AppConstants.FORGOT_PASSWORD.equals(ourClientModel.getAdditionalMessage())) {
            ourClient = updateClientPassword(ourClient);
        } else {
            checkResult = checkForExistingClient(ourClient);
        }
        if (checkResult == null) {
            try {
                clientRepo.save(ourClient);
                ourClientModel = convertUtils.convertOurClientToModel(ourClient, null);
            } catch (Exception e) {
                LOG.error("Exception got when register client: " + e.getMessage());
                ourClientModel.setId(AppConstants.FAKE_ID);
                ourClientModel.setAdditionalMessage(e.getMessage());
            }
        } else {
            ourClientModel.setId(AppConstants.FAKE_ID);
            ourClientModel.setAdditionalMessage(checkResult);
        }
        return ourClientModel;
    }

    public OurClientModel authorizationMobileClient(OurClientModel ourClientModel) {
        OurClient ourClient = convertUtils.convertModelToOurClient(ourClientModel);
        OurClient existClient = null;
        String result = null;
        try {
            if (ourClientModel.getEmail() != null) {
                existClient = clientRepo.findByEmail(ourClient.getEmail());
                result = existClient == null ? AppConstants.EMAIL_NOT_FOUND : null;
            } else {
                existClient = clientRepo.findByPhone(ourClient.getPhone());
                result = existClient == null ? AppConstants.PHONE_NOT_FOUND : null;
            }
            if (existClient != null && !convertUtils.isPasswordMatches(ourClientModel.getPassword(), existClient.getPassword())) {
                result = AppConstants.WRONG_PASSWORD;
            }
            if (result == null) {
                ourClientModel = convertUtils.convertOurClientToModel(existClient,
                        favoriteCompanyRepo.findAllByClientId(existClient.getId()));
            } else {
                ourClientModel.setId(AppConstants.FAKE_ID);
                ourClientModel.setAdditionalMessage(result);
            }
        } catch (Exception e) {
            LOG.error("Exception got when register client: " + e.getMessage());
            ourClientModel.setId(AppConstants.FAKE_ID);
            ourClientModel.setAdditionalMessage(e.getMessage());
        }
        return ourClientModel;
    }

    public OurClientModel authorizationClient(OurClientModel ourClientModel) {
        OurClientModel result = authorizationMobileClient(ourClientModel);
        return result;
    }

    public String validateAndSendEmailConfirmCode(OurClientModel ourClientModel) {
        String result = checkForExistingClient(convertUtils.convertModelToOurClient(ourClientModel));
        String searchCriteria = ourClientModel.getEmail() != null ? ourClientModel.getEmail() : ourClientModel.getPhone();
        if (AppConstants.FORGOT_PASSWORD.equals(ourClientModel.getAdditionalMessage())) {
            if (result == null) {
                result = String.format(AppConstants.USER_NOT_EXIST, searchCriteria);
                return result;
            } else {
                result = null;
            }
        }
        if (result == null) {
            if (ourClientModel.getEmail() != null) {
                result = mailService.sendConfirmCodeEmail(ourClientModel.getEmail());
            } else if (ourClientModel.getPhone() != null) {
                result = smsService.send(ourClientModel.getPhone());
            }
            if (result == null) {
                result = AppConstants.CODE_NOT_SEND;
            }
        }
        return result;
    }

    private String checkForExistingClient(OurClient ourClient) {
        String result = null;
        OurClient existClient = null;
        if (ourClient.getPhone() != null) {
            existClient = clientRepo.findByPhone(ourClient.getPhone());
            if (existClient != null) {
                result = String.format(AppConstants.USER_EXIST, ourClient.getPhone());
            }
        } else if (ourClient.getEmail() != null) {
            existClient = clientRepo.findByEmail(ourClient.getEmail());
            if (existClient != null) {
                result = String.format(AppConstants.USER_EXIST, ourClient.getEmail());
            }
        }
        return result;
    }

    private OurClient updateClientPassword(OurClient ourClient) {
        OurClient existClient = null;
        if (ourClient.getPhone() != null) {
            existClient = clientRepo.findByPhone(ourClient.getPhone());

        } else if (ourClient.getEmail() != null) {
            existClient = clientRepo.findByEmail(ourClient.getEmail());
        }
        existClient.setPassword(ourClient.getPassword());
        return existClient;
    }

    public ApiResponse updateClientInfo(OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String success = null;
        String error = null;
        try {
            OurClient existClient = clientRepo.findByUuid(ourClientModel.getUuid());
            if (existClient != null) {
                existClient.setNickName(ourClientModel.getNickName());
                existClient.setPhone(ourClientModel.getPhone());
                existClient.setEmail(ourClientModel.getEmail());
                clientRepo.save(existClient);
                success = AppConstants.USER_UPDATED;
            } else {
                error = String.format(AppConstants.USER_NOT_EXIST, ourClientModel.getUuid());
            }
        } catch (Exception e) {
            LOG.error("Exception when update exist client: " + e.getMessage());
            e.printStackTrace();
            error = AppConstants.UNEXPECTED_ERROR;
        }
        if ( success != null ){
            response.setMessage( success );
        } else if( error != null ){
            response.setMessage( error );
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
        }
        return response;
    }

    public ApiResponse createClientOrder(ClientOrderModel clientOrderModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            ClientOrder clientOrder = convertUtils.convertModelToClientOrder(clientOrderModel);
            clientOrderRepo.save(clientOrder);
            List<OrderEntity> orderEntities = convertUtils.convertModelsToOrderEntityList(clientOrder.getId(), clientOrderModel.getOrders());
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

    public ApiResponse getClientByUUID(String uuid) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            OurClient ourClient = clientRepo.findByUuid(uuid);
            OurClientModel ourClientModel = convertUtils.convertOurClientToModel(ourClient
                    , favoriteCompanyRepo.findAllByClientId(ourClient.getId()));
            response.setResult(ourClientModel);
        } catch (Exception e) {
            LOG.error("Exception got when register client: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ApiResponse addToFavorite(FavoriteCompanyModel favoriteCompanyModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        FavoriteCompany favoriteCompany = convertUtils.convertModelToFavoriteCompany(favoriteCompanyModel);
        try {
            favoriteCompanyRepo.save(favoriteCompany);
            response.setMessage(favoriteCompany.getId().toString());
        } catch (Exception e) {
            LOG.error("Exception got when add to favorite: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ApiResponse removeFromFavorite(FavoriteCompanyModel favoriteCompanyModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            favoriteCompanyRepo.delete(convertUtils.convertModelToFavoriteCompany(favoriteCompanyModel));
        } catch (Exception e) {
            LOG.error("Exception got when delete from favorite: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public String sendEmailToUs(String message) {
        String result = AppConstants.EMAIL_SEND_SUCCESS;
        if (!mailService.sendEmail("Сообщение от пользователя", message)) {
            result = AppConstants.UNEXPECTED_ERROR;
        }
        return result;
    }

    public ApiResponse removeClientFully( String uuid ){
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
             OurClient ourClient = clientRepo.findByUuid( uuid );
             if( ourClient == null ){
                 throw new BusinessLogicException("Client not found");
             }
             ourClient.setNickName("_"+ourClient.getNickName() );
             ourClient.setPhone( "_"+ourClient.getPhone() );
             ourClient.setEmail( "_"+ourClient.getEmail() );
             ourClient.setPassword( null );
             ourClient.setUuid( null );
            clientRepo.save( ourClient );
        } catch ( Exception e ) {
            LOG.error("Exception got when delete client: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }


}
