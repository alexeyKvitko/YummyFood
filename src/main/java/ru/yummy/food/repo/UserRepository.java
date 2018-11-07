package ru.yummy.food.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yummy.food.entity.User;

@Repository
public interface UserRepository  extends CrudRepository<User,Integer> {

    User findByUsername(String username);
}
