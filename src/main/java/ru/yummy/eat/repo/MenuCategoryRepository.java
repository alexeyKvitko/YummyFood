package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yummy.eat.entity.MenuCategory;

import java.util.List;

public interface MenuCategoryRepository extends CrudRepository<MenuCategory, Integer> {

    String GET_MENU_CATEGORY_IDS_SQL = "select DISTINCT mi.category_id from menu_item mi" +
                                        " where mi.company_id = :companyId ORDER BY mi.category_id";

    String GET_FAST_MENU_IDS_SQL = "select mc.id from menu_category mc where name in (:names)";

    @Query("SELECT DISTINCT mc from MenuCategory mc " +
            "INNER JOIN MenuItem mi on mi.categoryId = mc.id and mi.companyId = :companyId and mi.typeId = :typeId order by mc.displayName")
    List<MenuCategory> findCategoriesByCompanyAndTypeIdOrderByDisplayName(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId );

    @Query("SELECT  mc from MenuCategory mc " +
            "INNER JOIN MenuItem mi on mi.categoryId = mc.id GROUP BY mi.categoryId" +
            " ORDER BY count(mi.categoryId) DESC")
    List<MenuCategory> findAllOrderByCategoryCount();

    List<MenuCategory> findAllByOrderByDisplayName();

    @Query(value=GET_MENU_CATEGORY_IDS_SQL,nativeQuery = true)
    List<Integer> findCategoryIdsByCompanyId(@Param("companyId") Integer companyId);

    @Query(value=GET_FAST_MENU_IDS_SQL,nativeQuery = true)
    List<Integer> findFastMenuByNames(@Param("names") String names);

    @Query( "select mc.id from MenuCategory mc where mc.name in :names order by mc.id desc" )
    List<Integer> findAllByNames(@Param("names") List<String> names);
}
