package com.level5.basket.carts;

import com.level5.basket.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // 장바구니 상품 추가
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    // 장바구니 상품 제거
    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getProductId().equals(productId));
    }

}
