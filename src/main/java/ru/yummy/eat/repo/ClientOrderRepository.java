package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.ClientOrder;

public interface ClientOrderRepository extends CrudRepository<ClientOrder,Integer> {
}
