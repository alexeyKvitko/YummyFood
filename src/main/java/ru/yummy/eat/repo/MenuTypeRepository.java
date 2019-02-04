package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yummy.eat.entity.MenuType;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface MenuTypeRepository extends CrudRepository<MenuType,Integer> {

    String GET_MENU_TYPE_IDS_SQL = "select DISTINCT mi.type_id from menu_item mi " +
            "where mi.company_id = :companyId ORDER BY mi.type_id";

    @Query("SELECT DISTINCT mt FROM MenuType mt INNER JOIN MenuItem mi " +
                    "ON mi.typeId = mt.id and  mi.companyId = :companyId group by mi.typeId order by mt.displayName")
    List<MenuType> findTypesByCompanyIdOrderByDisplayName(@Param("companyId") Integer companyId);

    List<MenuType> findAllByOrderByDisplayName();


    @Query(value=GET_MENU_TYPE_IDS_SQL,nativeQuery = true)
    List<Integer> findTypeIdsByCompanyId(@Param("companyId") Integer companyId);

}
