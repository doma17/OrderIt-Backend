package inu.amigo.order_it.order.repository;

import inu.amigo.order_it.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
