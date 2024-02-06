package inu.amigo.order_it.item.repository;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsById(Long id);

    List<Item> findItemsByCategory(Category category);
}
