package inu.amigo.order_it.order.controller;

import inu.amigo.order_it.order.dto.OrderItemDto;
import inu.amigo.order_it.order.dto.ReceiptDto;
import inu.amigo.order_it.order.entity.OrderStatus;
import inu.amigo.order_it.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order API")
@RequestMapping("/api/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "주문 생성")
    @PostMapping
    public ResponseEntity<ReceiptDto> createOrder(
            @Parameter(
                    name = "orderItems",
                    description = "주문할 상품 목록",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = List.class, allowableValues = "orderItemDto")
                    )
            )
            @RequestBody List<OrderItemDto> orderItems) {
        try {
            ReceiptDto receiptDto = orderService.createOrder(orderItems);
            return new ResponseEntity<>(receiptDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "주문 상태 변경")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> changeOrderStatus(
            @Parameter(
                    name = "orderId",
                    description = "주문의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long orderId,
            @Parameter(
                    name = "newStatus",
                    description = "변경할 주문 상태",
                    required = true,
                    schema = @Schema(implementation = OrderStatus.class)
            )
            @RequestParam OrderStatus newStatus) {
        try {
            orderService.changeOrderStatus(orderId, newStatus);
            return new ResponseEntity<>("Order status is updated", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Order status update failed", HttpStatus.BAD_REQUEST);
        }
    }
}
