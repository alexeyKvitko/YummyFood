package ru.yummy.eat.service;

import ru.yummy.eat.entity.User;
import ru.yummy.eat.model.UserModel;

import java.util.List;

public interface UserService {

    User save(UserModel user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserModel update(UserModel userDto);
}
