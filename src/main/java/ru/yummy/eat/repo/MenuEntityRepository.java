package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yummy.eat.entity.MenuEntity;

import java.util.List;

public interface MenuEntityRepository extends CrudRepository<MenuEntity,Integer> {

    String UPDATE_SQL = "update menu_entity set status = 'УДАЛИТЬ' where id in " +
            "(select mi.entity_id from menu_item mi where mi.company_id = :companyId " +
                    "and mi.type_id = :typeId and mi.category_id = :categoryId)";

    String DELETE_SQL = "delete from menu_entity where id in " +
            "(select mi.entity_id from menu_item mi where mi.company_id = :companyId " +
            "and mi.type_id = :typeId and mi.category_id = :categoryId)";

    String FIND_BY_CATEGORY_SQL = "select mi.company_id," +
            "mi.type_id," +
            "me.id, " +
            "me.name, " +
            "me.display_name, " +
            "me.description, " +
            "me.image_url, " +
            "me.weight_one, " +
            "me.size_one, " +
            "me.price_one, " +
            "me.weight_two, " +
            "me.size_two, " +
            "me.price_two, " +
            "me.weight_three, " +
            "me.size_three, " +
            "me.price_three, " +
            "me.weight_four, " +
            "me.size_four, " +
            "me.price_four " +
            "from menu_entity me " +
            "inner join menu_item mi on mi.entity_id = me.id " +
            "inner join companies cm ON cm.id = mi.company_id " +
            "inner join cities ct on ct.id = cm.city_id " +
            "where mi.category_id = :categoryId and ct.name = :cityName " +
            "order by me.display_name";

    MenuEntity findByName(String name);

    @Transactional
    @Modifying
    @Query(value=UPDATE_SQL,nativeQuery = true)
    Integer updateMenuEntities(@Param("companyId") Integer companyId, @Param("typeId") Integer typeId,
                                @Param("categoryId") Integer categoryId);

    @Transactional
    @Modifying
    @Query(value=DELETE_SQL,nativeQuery = true)
    Integer deleteMenuEntities(@Param("companyId") Integer companyId, @Param("typeId") Integer typeId,
                               @Param("categoryId") Integer categoryId);

    @Query("FROM MenuEntity me WHERE me.id IN (SELECT mi.entityId from MenuItem mi WHERE " +
            "mi.companyId = :companyId and mi.typeId= :typeId and mi.categoryId= :categoryId)")
    List<MenuEntity> findMenuEntity(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId,
                                    @Param("categoryId") Integer categoryId);

    @Query("FROM MenuEntity me WHERE me.id IN (SELECT mi.entityId from MenuItem mi WHERE " +
            "mi.companyId = :companyId and mi.categoryId= :categoryId)")
    List<MenuEntity> findAllByCompanyIdAndCategoryIdOrderByDisplayName(@Param("companyId") Integer companyId,
                                                                       @Param("categoryId") Integer categoryId);

    @Query("FROM MenuEntity me WHERE me.id IN (SELECT mi.entityId from MenuItem mi WHERE " +
            "mi.categoryId= :categoryId)")
    List<MenuEntity> findAllByCategoryIdOrderByDisplayName( @Param("categoryId") Integer categoryId );

    @Query(value=FIND_BY_CATEGORY_SQL,nativeQuery = true)
    List<Object> findByCategoryIdOrderByDisplayName( @Param("cityName") String cityName,
                                                     @Param("categoryId") Integer categoryId );

}
