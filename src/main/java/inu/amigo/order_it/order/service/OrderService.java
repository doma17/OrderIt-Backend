package inu.amigo.order_it.order.service;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.repository.ItemRepository;
import inu.amigo.order_it.order.dto.DetailDto;
import inu.amigo.order_it.order.dto.OrderDto;
import inu.amigo.order_it.order.entity.Detail;
import inu.amigo.order_it.order.entity.Order;
import inu.amigo.order_it.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public void createOrder(OrderDto orderDto) {
        log.info("[createOrder]");
        List<DetailDto> detailDtoList = orderDto.getDetailDtoList();

        // Need Dto Validation Process !!

        int totalPrice = 0;
        List<Detail> details = new ArrayList<>();

        for (DetailDto detailDto : detailDtoList) {
            Item item = itemRepository.findById(detailDto.getItemId()).orElseThrow(() -> new EntityNotFoundException("[createOrder] item is not found"));

            if (detailDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("[createOrder] quantity element need to be natural number");
            }

            totalPrice += item.getPrice() * detailDto.getQuantity();

            Detail detail = Detail.builder()
                    .item(item)
                    .quantity(detailDto.getQuantity())
                    .build();

            details.add(detail);
        }

        Order order = Order.builder()
                .details(details)
                .totalPrice(totalPrice)
                .build();
        log.info("[createOrder] totalPrice = {}", totalPrice);

        orderRepository.save(order);
    }
}
