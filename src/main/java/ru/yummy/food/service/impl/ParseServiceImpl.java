package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yummy.food.entity.City;
import ru.yummy.food.repo.CityRepository;
import ru.yummy.food.service.ParseService;
import ru.yummy.food.util.AppUtils;

import java.util.List;

@Component
public abstract class ParseServiceImpl implements ParseService {

    @Autowired
    CityRepository cityRepo;




    protected City getCustomerCity(double sourceLatitude, double sourceLongitude) {
        List<City> cities = (List<City>) cityRepo.findAll();
        City nearest = null;
        double minDistance = 1000000;
        for (City city : cities) {
            if (city != null && city.getLatitude() != null) {
                double distance = AppUtils.HaversineInM(sourceLatitude, sourceLongitude, city.getLatitude(), city.getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = city;
                }
            }
        }
        System.out.println("City: " + nearest.getName());
        System.out.println("Distance: " + minDistance);
        return nearest;
    }



}
