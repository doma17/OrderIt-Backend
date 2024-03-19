package inu.amigo.order_it.item.entity;

public enum Category {
    Hot,
    Cold,
    Decaff,
    Dessert
}

/**
 * package inu.amigo.order_it.item.entity;
 *
 *
 * import jakarta.persistence.Entity;
 * import jakarta.persistence.GeneratedValue;
 * import jakarta.persistence.GenerationType;
 * import jakarta.persistence.Id;
 * import lombok.*;
 *
 * @Getter
 * @AllArgsConstructor
 * @NoArgsConstructor(access = AccessLevel.PROTECTED)
 * @Builder
 * @Entity
 * public class Category {
 *     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     private Long name;
 * }
 */