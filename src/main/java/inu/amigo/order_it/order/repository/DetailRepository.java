package inu.amigo.order_it.order.repository;

import inu.amigo.order_it.order.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail, Long> {
}
