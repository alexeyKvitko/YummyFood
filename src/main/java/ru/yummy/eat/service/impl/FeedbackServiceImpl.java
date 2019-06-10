package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yummy.eat.entity.Feedback;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.FeedbackModel;
import ru.yummy.eat.repo.FeedbackRepository;
import ru.yummy.eat.util.ConvertUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.LinkedList;
import java.util.List;

@Service("feedbackService")
public class FeedbackServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger( FeedbackServiceImpl.class );

    @Autowired
    FeedbackRepository feedbackRepo;

    @Autowired
    ConvertUtils convertUtils;

    public ApiResponse saveFeedback(FeedbackModel model){
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            Feedback feedback= convertUtils.convertFeedbackModelToEntity( model );
            feedbackRepo.save( feedback );
        } catch (Exception e) {
            LOG.error("Exception got when save feedback: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ApiResponse<List<FeedbackModel>> getFeedbackListByCompanyId( Integer companyId ){
        ApiResponse<List<FeedbackModel>> response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        try {
            List<Feedback> feedbacks = feedbackRepo.findAllByCompanyIdOrderByCreateDateDesc( companyId );
            List<FeedbackModel> feedbackModels = new LinkedList<>();
            for(Feedback feedback : feedbacks ){
                feedbackModels.add( convertUtils.convertFeedbackToModel( feedback ) );
            }
            response.setResult( feedbackModels );
        } catch (Exception e) {
            LOG.error("Exception got when save feedback: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
