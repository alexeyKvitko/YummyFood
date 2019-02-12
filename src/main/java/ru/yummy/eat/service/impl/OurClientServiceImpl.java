package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.OurClient;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.OurClientModel;
import ru.yummy.eat.repo.OurClientRepository;
import ru.yummy.eat.util.ConvertUtils;

@Service("clientService")
public class OurClientServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(OurClientServiceImpl.class);

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    OurClientRepository clientRepository;

    public String registerClient( OurClientModel ourClientModel ) {
        String result = null;
        OurClient ourClient = convertUtils.convertModelToOurClient( ourClientModel );
        result = checkForExistingClient( ourClient );
        if( result == null ){
            try {
                clientRepository.save( ourClient );
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
                existClient = clientRepository.findByEmail(ourClient.getEmail());
                result = existClient == null ? AppConstants.EMAIL_NOT_FOUND : null;
            } else {
                existClient = clientRepository.findByPhone(ourClient.getPhone());
                result = existClient == null ? AppConstants.PHONE_NOT_FOUND : null;
            }
            if (existClient != null && !convertUtils.isPasswordMatches( ourClientModel.getPassword(), existClient.getPassword() ) ) {
                result = AppConstants.WRONG_PASSWORD;
            }
            if (result == null) {
                result = ourClient.getUuid();
            }
        } catch (Exception e) {
            LOG.error("Exception got when register client: " + e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    public String checkForExistingClient( OurClient ourClient ){
        String result = null;
        OurClient existClient = clientRepository.findByPhone( ourClient.getPhone() );
        if ( existClient == null && ourClient.getEmail() != null ){
            existClient = clientRepository.findByEmail( ourClient.getEmail() );
            if( existClient != null ){
                result = AppConstants.EMAIL_EXIST;
            }
        } else {
            result = AppConstants.PHONE_EXIST;
        }
        return result;
    }


}
