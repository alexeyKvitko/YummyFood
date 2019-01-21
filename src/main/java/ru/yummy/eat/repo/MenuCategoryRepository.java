package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yummy.eat.entity.MenuCategory;

import java.util.List;

public interface MenuCategoryRepository extends CrudRepository<MenuCategory, Integer> {

    @Query("SELECT DISTINCT mc from MenuCategory mc " +
            "INNER JOIN MenuItem mi on mi.categoryId = mc.id and mi.companyId = :companyId and mi.typeId = :typeId order by mc.displayOrder")
    List<MenuCategory> findCategoriesByCompanyAndTypeId(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId );

    List<MenuCategory> findAllByOrderByDisplayOrder();
}