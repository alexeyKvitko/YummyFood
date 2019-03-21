package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.City;

import java.util.List;

public interface CityRepository extends CrudRepository<City,Integer> {

    List<City> findAllByRegionIdOrderByName(Integer regionId);

    City findByRegionIdAndUrl( Integer regionId, String url );

    City findByNameEn( String nameEn );

    List<City> findAllByUrl( String url );

    List<City> findAllByRegionIdAndProcessed(Integer regionId,Integer processed);

    List<City> findAllByProcessed( Integer processed );

    List<City> findByOrderByName();
}
