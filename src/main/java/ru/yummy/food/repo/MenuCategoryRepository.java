package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.MenuCategory;

public interface MenuCategoryRepository extends CrudRepository<MenuCategory, Integer> {
}
