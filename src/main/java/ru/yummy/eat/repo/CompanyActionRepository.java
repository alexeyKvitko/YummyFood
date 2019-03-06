package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.CompanyAction;

import java.util.List;

public interface CompanyActionRepository extends CrudRepository<CompanyAction,Integer> {

    List<CompanyAction> findAllByCityId( Integer cityId );
}
