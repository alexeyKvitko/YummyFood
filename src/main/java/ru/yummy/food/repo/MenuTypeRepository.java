package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.MenuType;

public interface MenuTypeRepository extends CrudRepository<MenuType,Integer> {
}
