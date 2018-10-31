package ru.yummy.food.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yummy.food.entity.MenuItem;

public interface MenuItemRepository extends CrudRepository<MenuItem,Integer> {

    static final String SELECT_SQL = "select id from menu_item where company_id = :companyId " +
                                                "and type_id = :typeId and category_id = :categoryId and entity_id=:entityId";

    @Query(value=SELECT_SQL,nativeQuery = true)
    Integer getMenuItemId(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId,
                                @Param("categoryId") Integer categoryId, @Param("entityId") Integer entityId);
}
