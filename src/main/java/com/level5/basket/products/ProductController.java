package com.level5.basket.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @Secured("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 상품 단일 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // 상품 목록 조회 - 페이징 및 정렬 추가
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page, // 페이지 번호
            @RequestParam(defaultValue = "10") int size, // 나오는 상품 갯수
            @RequestParam(defaultValue = "productName") String sortBy, // 정렬 조건 productName, price
            @RequestParam(defaultValue = "ASC") String direction) { // 오름차순, 내림차순

        List<ProductResponseDto> products = productService.getAllProducts(page, size, sortBy, direction);
        return ResponseEntity.ok(products);
    }


    // 선택 상품 수정
    @Secured("ROLE_ADMIN")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                          @RequestBody ProductRequestDto requestDto) {
        try {
            productService.updateProduct(productId, requestDto);
            return ResponseEntity.ok("상품 정보가 수정되었습니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("상품 정보 수정에 실패했습니다");
        }
    }

    // 선택 상품 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().body("상품 정보 삭제에 성공했습니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("상품 정보 삭제에 실패했습니다");
        }
    }

}
