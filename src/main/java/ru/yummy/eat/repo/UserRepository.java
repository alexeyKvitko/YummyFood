package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yummy.eat.entity.User;

@Repository
public interface UserRepository  extends CrudRepository<User,Integer> {

    User findByUsername(String username);
}
