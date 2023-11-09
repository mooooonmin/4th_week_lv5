package com.level5.basket.carts;

import com.level5.basket.products.Product;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {

    // 카트에 넣는 요청
    private Long productId;
    private int itemQuantity;

}
