package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.ParseMenu;

import java.util.List;

public interface ParseMenuRepository extends CrudRepository<ParseMenu,Integer> {

    List<ParseMenu> findAllByProcessed( Integer processed );

    ParseMenu findParseMenuByCompanyIdAndTypeIdAndCategoryId(Integer companyId, Integer typeId, Integer categoryId);

}
