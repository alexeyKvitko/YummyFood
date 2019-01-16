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
                    "and mi.type_id = :typeId and mi.category_id = :categoryId);";

    MenuEntity findByName(String name);

    @Transactional
    @Modifying
    @Query(value=UPDATE_SQL,nativeQuery = true)
    Integer updateMenuEntities(@Param("companyId") Integer companyId, @Param("typeId") Integer typeId,
                                @Param("categoryId") Integer categoryId);

    @Query("FROM MenuEntity me WHERE me.id IN (SELECT mi.entityId from MenuItem mi WHERE " +
            "mi.companyId = :companyId and mi.typeId= :typeId and mi.categoryId= :categoryId)")
    List<MenuEntity> findMenuEntity(@Param("companyId") Integer companyId,@Param("typeId") Integer typeId,
                                    @Param("categoryId") Integer categoryId);

    @Query("FROM MenuEntity me WHERE me.id IN (SELECT mi.entityId from MenuItem mi WHERE " +
                                                                                "mi.companyId = :companyId)")
    List<MenuEntity> findMenuEntity( @Param("companyId") Integer companyId );

}
