package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.ParseMenu;

import java.util.List;

public interface ParseMenuRepository extends CrudRepository<ParseMenu,Integer> {

    List<ParseMenu> findAllByProcessed( Integer processed );
}
