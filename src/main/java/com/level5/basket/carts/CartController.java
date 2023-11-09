package com.level5.basket.carts;

import com.level5.basket.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping("/{userId}/items")
    public ResponseEntity<?> addItemToCart(@PathVariable Long userId,
                                           @RequestBody CartItemRequestDto cartItemRequest) {
        cartService.addItemToCart(userId, cartItemRequest);
        return ResponseEntity.ok().build();
    }

    // 특정 사용자의 장바구니 조회
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) {
        CartResponseDto cartResponse = cartService.getCart(userId);
        return ResponseEntity.ok(cartResponse);
    }

    // 장바구니 내 상품 제거
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long userId,
                                                @PathVariable Long productId) {
        cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    // 장바구니 내 상품 수량 변경
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<?> updateItemQuantity(@PathVariable Long userId,
                                                @PathVariable Long productId,
                                                @RequestBody int itemQuantity) {
        cartService.updateItemQuantity(userId, productId, itemQuantity);
        return ResponseEntity.ok().build();
    }

}
