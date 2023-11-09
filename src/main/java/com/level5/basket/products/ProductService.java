package com.level5.basket.products;

import com.level5.basket.global.CustomException;
import com.level5.basket.global.ErrorMessage;
import com.level5.basket.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.IMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;

    // TODO 상품 등록
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        String message;

        // 상품 중복 확인
        String productName = requestDto.getProductName();
        if (productRepository.existsByProductName(requestDto.getProductName())) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS.getMessage());
        } else {
            message = "상품이 등록 되었습니다";
        }

        // 상품 등록
        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .price(requestDto.getPrice())
                .quantity(requestDto.getQuantity())
                .productInfo(requestDto.getProductInfo())
                .category(requestDto.getCategory())
                .build();

        // 저장
        productRepository.save(product);

        // 반환
        return new ProductResponseDto(product, message);

    }

    // TODO 상품 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProductByProductName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(CustomException.ProductNotFoundException::new);
        ProductResponseDto responseDto = new ProductResponseDto(product);

        // 반환
        return responseDto;
    }

    // TODO 상품 목록 조회(전체)
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductResponseDto(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getProductInfo(),
                product.getCategory()
        )).collect(Collectors.toList());
    }

    // TODO 상품 수정
}
