package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.FeedbackModel;
import ru.yummy.eat.service.impl.FeedbackServiceImpl;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping({"/api/feedback"})
public class FeedbackController {

    @Autowired
    FeedbackServiceImpl feedbackService;

    @RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
    public ApiResponse saveFeedback(@RequestBody FeedbackModel feedbackModel) {
        ApiResponse response = null;
        response = feedbackService.saveFeedback( feedbackModel );
        return response;
    }

    @GetMapping("/getCompanyFeedbacks/{companyId}")
    public ApiResponse<List<FeedbackModel>> getCompanyFeedbacks(@PathVariable Integer companyId) {
        ApiResponse<List<FeedbackModel>> response =  feedbackService.getFeedbackListByCompanyId( companyId );
        return response;
    }


}
