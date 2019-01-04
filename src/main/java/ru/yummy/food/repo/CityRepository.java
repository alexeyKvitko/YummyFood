package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.food.entity.City;

import java.util.List;

public interface CityRepository extends CrudRepository<City,Integer> {

    List<City> findAllByRegionIdOrderByName(Integer regionId);

    City findByRegionIdAndUrl( Integer regionId, String url );

    City findByNameEn( String nameEn );

    List<City> findAllByUrl( String url );

    List<City> findAllByRegionIdAndProcessed(Integer regionId,Integer processed);

    List<City> findAllByProcessed( Integer processed );

}
