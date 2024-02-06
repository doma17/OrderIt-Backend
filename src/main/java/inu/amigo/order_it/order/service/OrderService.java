package inu.amigo.order_it.order.service;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Option;
import inu.amigo.order_it.item.repository.ItemRepository;
import inu.amigo.order_it.item.repository.OptionRepository;
import inu.amigo.order_it.order.dto.OrderInfoDto;
import inu.amigo.order_it.order.dto.OrderItemDto;
import inu.amigo.order_it.order.dto.ReceiptDto;
import inu.amigo.order_it.order.entity.OrderEntity;
import inu.amigo.order_it.order.entity.OrderItem;
import inu.amigo.order_it.order.entity.OrderStatus;
import inu.amigo.order_it.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, OptionRepository optionRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public ReceiptDto createOrder(List<OrderItemDto> orderItems) {
        int totalPrice = 0;

        // OrderEntity 및 OrderItem 엔티티 생성
        OrderEntity orderEntity = new OrderEntity(); // OrderEntity 초기화
        orderEntity.setItems(createOrderItems(orderItems, orderEntity)); // createOrderItems에 orderEntity 전달

        // 각 주문 항목에 대한 총 가격 및 옵션 총 가격 계산
        for (OrderItemDto orderItemDto : orderItems) {
            int itemPrice = itemRepository.findById(orderItemDto.getItemId()).get().getPrice();
            int optionsPrice = orderItemDto.getSelectedOptions().values().stream().mapToInt(Integer::intValue).sum();
            int itemTotalPrice = itemPrice + optionsPrice;

            orderItemDto.setOptionTotalPrice(optionsPrice);
            totalPrice += itemTotalPrice;
        }

        // OrderEntity에 대한 추가 속성 설정
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setStatus(OrderStatus.ORDER);

        // OrderEntity 및 OrderItems 저장
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        // ReceiptDto 생성
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        ReceiptDto receiptDto = new ReceiptDto(orderInfoDto);

        // ReceiptDto 반환
        return receiptDto;
    }

    @Transactional
    public void changeOrderStatus(Long orderId, OrderStatus newStatus) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 주문을 찾을 수 없습니다: " + orderId));

        // 현재 상태 확인 및 무효한 전이인 경우 예외 발생
        if (!isValidStatusTransition(orderEntity.getStatus(), newStatus)) {
            throw new IllegalArgumentException("현재 상태에서 " +
                    newStatus + "(으)로의 무효한 상태 전이입니다.");
        }

        // OrderEntity 상태 업데이트
        orderEntity.setStatus(newStatus);

        // OrderEntity 저장
        orderRepository.save(orderEntity);
    }

    private List<OrderItem> createOrderItems(List<OrderItemDto> orderItemDtoList, OrderEntity orderEntity) {
        // OrderItemDto를 OrderItem 엔티티로 변환
        return orderItemDtoList.stream()
                .map(orderItemDto -> {
                    Item item = itemRepository.findById(orderItemDto.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 상품을 찾을 수 없습니다: " + orderItemDto.getItemId()));

                    Map<Option, Integer> options = orderItemDto.getSelectedOptions().entrySet().stream()
                            .collect(Collectors.toMap(
                                    entry -> optionRepository.findById(entry.getKey())
                                            .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 옵션을 찾을 수 없습니다: " + entry.getKey())),
                                    Map.Entry::getValue
                            ));

                    OrderItem orderItem = OrderItem.builder()
                            .order(orderEntity)
                            .item(item)
                            .quantity(orderItemDto.getQuantity())
                            .selectedOptions(options)
                            .optionTotalPrice(orderItemDto.getOptionTotalPrice())
                            .build();

                    return orderItem;
                })
                .toList();
    }

    private boolean isValidStatusTransition(OrderStatus status, OrderStatus newStatus) {
        // 유효한 상태 전이인지 여부를 확인하는 로직
        // newStatus == status -> false
        return !status.equals(newStatus);
    }
}
