package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yummy.eat.entity.MenuCategory;

import java.util.List;

public interface MenuCategoryRepository extends CrudRepository<MenuCategory, Integer> {

    String GET_MENU_CATEGORY_IDS_SQL = "select DISTINCT mi.category_id from menu_item mi" +
                                        " where mi.company_id = :companyId ORDER BY mi.category_id";

    @Query("SELECT DISTINCT mc from MenuCategory mc " +
            "INNER JOIN MenuItem mi on mi.categoryId = mc.id and mi.companyId = :companyId and mi.typeId = :typeId order by mc.displayName")
    List<MenuCategory> findCategoriesByCompanyAndTypeIdOrderByDisplayName(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId );

    List<MenuCategory> findAllByOrderByDisplayName();

    @Query(value=GET_MENU_CATEGORY_IDS_SQL,nativeQuery = true)
    List<Integer> findCategoryIdsByCompanyId(@Param("companyId") Integer companyId);
}
