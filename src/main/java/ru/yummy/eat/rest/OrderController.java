package ru.yummy.eat.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.ClientOrderModel;
import ru.yummy.eat.model.ExistOrders;
import ru.yummy.eat.service.impl.OrderServiceImpl;

@CrossOrigin
@RestController
@RequestMapping({"/api/client/order"})
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderServiceImpl orderService;

    @RequestMapping(value = "/createClientOrder", method = RequestMethod.POST)
    public ApiResponse createClientOrder(@RequestBody ClientOrderModel clientOrderModel) {
        ApiResponse response = null;
        response = orderService.createClientOrder(clientOrderModel);
        return response;
    }

    @GetMapping("/getClientOrders/{uuid}")
    public ApiResponse<ExistOrders> getClientOrders(@PathVariable String uuid) {
        ApiResponse<ExistOrders> response = null;
        response = orderService.getClientOrders( uuid );
        return response;
    }

}
