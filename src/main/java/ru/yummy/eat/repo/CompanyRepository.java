package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.Company;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    List<Company> findAllByCityId(Integer cityId);

}
