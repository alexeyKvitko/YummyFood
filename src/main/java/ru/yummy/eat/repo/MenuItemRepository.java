package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yummy.eat.entity.MenuItem;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem,Integer> {

    String SELECT_SQL = "select id from menu_item where company_id = :companyId " +
                                                "and type_id = :typeId and category_id = :categoryId and entity_id=:entityId";

    String SELECT_COUNT_TYPE_SQL = "select count(id) from menu_item where type_id = :typeId";

    String DELETE_SQL = "delete from menu_item where company_id = :companyId " +
            "and type_id = :typeId and category_id = :categoryId and entity_id > 0";

    String SELECT_COUNT_CATEGORY_SQL = "select count(id) from menu_item where category_id = :categoryId";

    @Query(value=SELECT_SQL,nativeQuery = true)
    Integer getMenuItemId(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId,
                                @Param("categoryId") Integer categoryId, @Param("entityId") Integer entityId);

    @Query(value=SELECT_COUNT_TYPE_SQL,nativeQuery = true)
    Integer getCountByTypeId(@Param("typeId") Integer typeId);

    @Query(value=SELECT_COUNT_CATEGORY_SQL,nativeQuery = true)
    Integer getCountByCategoryId(@Param("categoryId") Integer categoryId);

    List<MenuItem> findAllByCompanyIdAndTypeIdAndCategoryId(Integer companyId, Integer typeId, Integer categoryId);

    @Transactional
    @Modifying
    @Query(value=DELETE_SQL,nativeQuery = true)
    Integer deleteMenuItems(@Param("companyId") Integer companyId, @Param("typeId") Integer typeId,
                               @Param("categoryId") Integer categoryId);


}
