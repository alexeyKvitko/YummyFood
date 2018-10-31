package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.MenuEntity;

public interface MenuEntityRepository extends CrudRepository<MenuEntity,Integer> {

    MenuEntity findByName(String name);
}
