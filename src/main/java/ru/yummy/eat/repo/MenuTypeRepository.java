package ru.yummy.eat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yummy.eat.entity.MenuType;

import java.util.List;

@Repository
public interface MenuTypeRepository extends CrudRepository<MenuType,Integer> {

    @Query("SELECT DISTINCT mt FROM MenuType mt INNER JOIN MenuItem mi " +
                    "ON mi.typeId = mt.id and  mi.companyId = :companyId group by mi.typeId order by mt.displayOrder")
    List<MenuType> findTypesByCompanyId(@Param("companyId") Integer companyId);

    List<MenuType> findAllByOrderByDisplayOrder();

}
