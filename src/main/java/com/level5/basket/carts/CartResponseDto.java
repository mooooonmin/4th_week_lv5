package com.level5.basket.carts;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class CartResponseDto {

    private Long cartId;
    private List<CartItemResponseDto> items;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.items = cart.getItems().stream()
                .map(item -> new CartItemResponseDto(
                        item.getProduct().getProductId(),
                        item.getProduct().getProductName(),
                        item.getProduct().getPrice(),
                        item.getItemQuantity()))
                .collect(Collectors.toList());
    }
}
