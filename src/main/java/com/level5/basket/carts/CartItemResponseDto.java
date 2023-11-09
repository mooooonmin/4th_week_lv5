package com.level5.basket.carts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartItemResponseDto {
    private Long productId;
    private String productName;
    private int price;
    private int itemQuantity;

    public CartItemResponseDto(Long productId,
                               String productName,
                               int price,
                               int itemQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.itemQuantity = itemQuantity;
    }

}