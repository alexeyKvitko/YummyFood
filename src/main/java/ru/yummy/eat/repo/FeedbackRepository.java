package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yummy.eat.entity.Feedback;

import java.util.List;

public interface FeedbackRepository extends CrudRepository<Feedback,Integer> {

    String GET_TOTAL_FEEDBACK_RATE_SQL = "select round(sum( rate )/count(rate)) as total_rate from feedback where company_id = :companyId";

    List<Feedback> findAllByCompanyIdOrderByCreateDateDesc(Integer companyId);


    @Query(value=GET_TOTAL_FEEDBACK_RATE_SQL,nativeQuery = true)
    Integer getTotalRateByCompanyId( @Param("companyId") Integer companyId );
}
