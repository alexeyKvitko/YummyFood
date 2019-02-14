package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.OurClient;

import java.util.List;

public interface OurClientRepository extends CrudRepository<OurClient,Integer> {

    OurClient findByEmail(String email);

    OurClient findByPhone(String phone);

    OurClient findByUuid(String uuid);
}
