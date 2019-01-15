package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.Company;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    List<Company> findAllByCityId(Integer cityId);

}
