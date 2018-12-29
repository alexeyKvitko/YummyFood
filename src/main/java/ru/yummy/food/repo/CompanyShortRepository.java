package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.CompanyShort;

import java.util.List;

public interface CompanyShortRepository extends CrudRepository<CompanyShort,Integer> {

    List<CompanyShort> findAllByCityId(Integer cityId);
}
