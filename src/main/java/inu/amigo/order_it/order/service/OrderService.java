package inu.amigo.order_it.order.service;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.repository.ItemRepository;
import inu.amigo.order_it.order.dto.DetailDto;
import inu.amigo.order_it.order.dto.OrderRequestDto;
import inu.amigo.order_it.order.dto.OrderResponseDto;
import inu.amigo.order_it.order.entity.Detail;
import inu.amigo.order_it.order.entity.Order;
import inu.amigo.order_it.order.repository.DetailRepository;
import inu.amigo.order_it.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final DetailRepository detailRepository;

//    private final PrintService printService;


    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, DetailRepository detailRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.detailRepository = detailRepository;
    }

    public void createOrder(OrderRequestDto orderRequestDto) {
        log.info("[createOrder] orderDto : {}", orderRequestDto.getOrderType());
        List<DetailDto> detailDtoList = orderRequestDto.getDetailDtoList();

        // Need Dto Validation Process !!

        if (detailDtoList == null || detailDtoList.isEmpty()) {
            log.error("[createOrder] detailDtoList is empty");
            throw new IllegalArgumentException("[createOrder] detailDtoList is empty");
        }

        int totalPrice = 0;
        List<Detail> detailList = new ArrayList<>();

        for (DetailDto detailDto : detailDtoList) {
            if (!itemRepository.existsById(detailDto.getItemId())) {
                log.error("[createOrder] item is not found : {}", detailDto.getItemId());
                throw new EntityNotFoundException("[createOrder] item is not found");
            }
            Item item = itemRepository.findById(detailDto.getItemId()).get();

            if (detailDto.getQuantity() <= 0) {
                log.error("[createOrder] quantity element need to be natural number");
                throw new IllegalArgumentException("[createOrder] quantity element need to be natural number");
            }

            totalPrice += item.getPrice() * detailDto.getQuantity();

            Detail detail = Detail.builder()
                    .item(item)
                    .quantity(detailDto.getQuantity())
                    .build();
            detail = detailRepository.save(detail);

            log.info("[createOrder] 1 detail id : {}", detail.getId());
            log.info("[createOrder] 2 detail quantity : {}", detail.getQuantity());
            log.info("[createOrder] 3 detail item : {}", detail.getItem());

            detailList.add(detail);
        }

        Order order = Order.builder()
                .orderType(orderRequestDto.getOrderType())
                .details(detailList)
                .totalPrice(totalPrice)
                .build();
        log.info("[createOrder] totalPrice = {}", totalPrice);

        orderRepository.save(order);
    }

    public List<OrderResponseDto> getOrderList() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order order : orders) {
            log.info("[getOrderList] order details size : {}", order.getDetails().size());
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setTotalPrice(order.getTotalPrice());
            orderResponseDto.setOrderType(order.getOrderType());

            List<DetailDto> detailDtoList = new ArrayList<>();
            for (Detail detail : order.getDetails()) {
                DetailDto detailDto = new DetailDto();
                detailDto.setItemId(detail.getItem().getId());
                detailDto.setQuantity(detail.getQuantity());
                detailDtoList.add(detailDto);
            }
            orderResponseDto.setDetailDtoList(detailDtoList);
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }

    public String getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("[getOrder] order is not found"));
        return order.toString();
    }

    public void printReceipt(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("[printReceipt] order is not found"));

//        printService.print(order);
    }
}
