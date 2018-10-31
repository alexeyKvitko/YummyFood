package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
}
