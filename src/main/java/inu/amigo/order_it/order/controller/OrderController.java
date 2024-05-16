package inu.amigo.order_it.order.controller;

import inu.amigo.order_it.order.dto.OrderRequestDto;
import inu.amigo.order_it.order.dto.OrderResponseDto;
import inu.amigo.order_it.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "주문하기")
    @PostMapping
    public OrderResponseDto postOrderItem(
            @Parameter(
                    name = "orderDto",
                    description = "주문할 Item 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequestDto.class)
                    )
            ) @RequestBody OrderRequestDto orderRequestDto) {

        return orderService.createOrder(orderRequestDto);
    }

    @Operation(summary = "주문 목록 조회")
    @GetMapping
    public List<OrderResponseDto> getOrderList() {
        return orderService.getOrderList();
    }

    @Operation(summary = "주문 조회")
    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(@Parameter(
            name = "orderId",
            description = "주문 ID",
            required = true
    ) @PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @Operation(summary = "주문 영수증 출력")
    @PostMapping("/{orderId}")
    public ResponseEntity<String> printReceipt(@PathVariable Long orderId) {
        orderService.printReceipt(orderId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
