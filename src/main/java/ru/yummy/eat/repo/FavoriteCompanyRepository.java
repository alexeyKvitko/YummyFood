package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.FavoriteCompany;

import java.util.List;

public interface FavoriteCompanyRepository extends CrudRepository<FavoriteCompany, Integer> {

    List<FavoriteCompany> findAllByClientId( Integer clientId );
}
