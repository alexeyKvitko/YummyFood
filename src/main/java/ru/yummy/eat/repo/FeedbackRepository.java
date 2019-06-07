package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.Feedback;

import java.util.List;

public interface FeedbackRepository extends CrudRepository<Feedback,Integer> {

    List<Feedback> findAllByCompanyId(Integer companyId);

}
