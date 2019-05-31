package ru.yummy.eat.repo;

import org.springframework.data.repository.CrudRepository;
import ru.yummy.eat.entity.ClientOrder;

import java.util.List;

public interface ClientOrderRepository extends CrudRepository<ClientOrder,Integer> {

    List<ClientOrder> findAllByClientUuidOrderByOrderDateDesc(String clientUuid );
}
