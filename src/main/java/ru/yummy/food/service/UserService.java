package ru.yummy.food.service;

import ru.yummy.food.entity.User;
import ru.yummy.food.model.UserModel;

import java.util.List;

public interface UserService {

    User save(UserModel user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserModel update(UserModel userDto);
}
