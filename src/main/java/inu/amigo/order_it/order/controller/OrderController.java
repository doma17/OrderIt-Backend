package inu.amigo.order_it.order.controller;

import inu.amigo.order_it.order.service.OrderService;
import inu.amigo.order_it.order.dto.OrderDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Order API")
@RequestMapping("/api/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> postOrderItem(OrderDto orderDto) {

        try {
            orderService.createOrder(orderDto);
            return new ResponseEntity<>("order success", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("order failed", HttpStatus.BAD_REQUEST);
        }
    }
}
