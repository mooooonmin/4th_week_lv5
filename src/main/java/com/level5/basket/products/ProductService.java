package com.level5.basket.products;

import com.level5.basket.exception.CustomException;
import com.level5.basket.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
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

    // 상품 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProductByProductName(String productName) {
        // 상품이름으로 조회 -> 있나 확인
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(CustomException.ProductNotFoundException::new);
        ProductResponseDto responseDto = new ProductResponseDto(product, "상품 조회 완료");

        // 반환
        return responseDto;
    }


    // 상품 목록 조회(조건 추가)
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String direction) {
        // 정렬 기준이 상품명 또는 가격인지 확인
        if (!sortBy.equalsIgnoreCase("productName") && !sortBy.equalsIgnoreCase("price")) {
            throw new IllegalArgumentException("정렬 기준은 상품명 또는 가격이어야 합니다.");
        }

        // 정렬 방향이 유효한지 확인
        if (!direction.equalsIgnoreCase("ASC") && !direction.equalsIgnoreCase("DESC")) {
            throw new IllegalArgumentException("정렬 방향은 ASC 또는 DESC 이어야 합니다.");
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.stream()
                .map(product -> new ProductResponseDto(product, "상품 조회 완료"))
                .collect(Collectors.toList());

    }

    // 상품 수정
    @Transactional
    public void updateProduct(String productName, ProductRequestDto requestDto) {
        // 상품이름으로 조회 -> 있나 확인
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(CustomException.ProductNotFoundException::new);

        // 있으면 수정해서 반환
        product.update(requestDto);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(String productName) {
        // 상품이름으로 조회 -> 있나 확인
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(CustomException.ProductNotFoundException::new);

        // 있으면 삭제
        productRepository.delete(product);
    }
}
