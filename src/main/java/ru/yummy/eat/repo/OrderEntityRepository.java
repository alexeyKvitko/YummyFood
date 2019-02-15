package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.OrderEntity;

public interface OrderEntityRepository extends CrudRepository<OrderEntity,Integer> {
}
