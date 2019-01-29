package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.yummy.eat.entity.MenuOrder;

import java.util.List;

public interface MenuOrderRepository extends CrudRepository<MenuOrder,Integer> {

    String GET_MAX_TYPE_ORDER = "select max(menu_order) from menu_order " +
            "where company_id = :companyId and menu_category_id = -1";
    String GET_MAX_CATEGORY_ORDER = "select max(menu_order) from menu_order " +
            "where company_id = :companyId and menu_type_id = :menuTypeId and menu_category_id <> -1";

    List<MenuOrder> findAllByCompanyId( Integer companyId );

    MenuOrder findByCompanyIdAndMenuTypeIdAndMenuCategoryId( Integer companyId, Integer menuTypeId, Integer categoryId );

    @Query(value=GET_MAX_TYPE_ORDER,nativeQuery = true)
    Integer getMaxTypeOrder(@Param("companyId") Integer companyId);

    @Query(value=GET_MAX_CATEGORY_ORDER,nativeQuery = true)
    Integer getMaxCategoryOrder(@Param("companyId") Integer companyId, @Param("menuTypeId") Integer menuTypeId);

}
