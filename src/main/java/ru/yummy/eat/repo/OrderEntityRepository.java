package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.OrderEntity;

import java.util.List;

public interface OrderEntityRepository extends CrudRepository<OrderEntity,Integer> {

    List<OrderEntity> findAllByOrderId( Integer orderId );
}
