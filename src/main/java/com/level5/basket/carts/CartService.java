package com.level5.basket.carts;

import com.level5.basket.global.CustomException;
import com.level5.basket.global.ErrorMessage;
import com.level5.basket.products.Product;
import com.level5.basket.products.ProductRepository;
import com.level5.basket.users.User;
import com.level5.basket.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 장바구니 추가
    @Transactional
    public void addItemToCart(Long userId, CartItemRequestDto cartItemRequest) {

        // 유저 검색 -> 없으면 예외처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        // 장바구나에서 유저정보 찾기 -> 없으면 새로운 장바구니 만들어주기
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseGet(() -> {Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });

        // 상품 찾기 -> 없으면 애러
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        // 상품 넣기
        cart.addItem(new CartItem(cart, product, cartItemRequest.getItemQuantity()));

        cartRepository.save(cart);
    }

    // 장바구니 조회
    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(NoSuchElementException::new);
        return new CartResponseDto(cart);
    }

    // 장바구니 제거
    @Transactional
    public void removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(NoSuchElementException::new);

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    // 장바구니 수정
    @Transactional
    public void updateItemQuantity(Long userId, Long productId, int itemQuantity) {
        if (itemQuantity < 1) {
            throw new IllegalArgumentException("상품의 수량은 1개 이상만 가능합니다");
        }

        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        CartItem itemToUpdate = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        itemToUpdate.setItemQuantity(itemQuantity);

        cartRepository.save(cart);
    }
}