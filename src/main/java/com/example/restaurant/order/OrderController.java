package com.example.restaurant.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/request")
    public OrderResponseDto registerOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.order(orderRequestDto);
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> showOrder(){
        return orderService.showOrders();
    }

}
