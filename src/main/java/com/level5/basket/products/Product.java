package com.level5.basket.products;

import com.level5.basket.enumType.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    private String productInfo;

    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum category;

    @Builder
    public Product(String productName,
                   int price,
                   int quantity,
                   String productInfo,
                   CategoryTypeEnum category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productInfo = productInfo;
        this.category = category;
    }

    public static Product from(ProductRequestDto requestDto) {
        return Product.builder()
                .productName(requestDto.getProductName())
                .price(requestDto.getPrice())
                .quantity(requestDto.getQuantity())
                .productInfo(requestDto.getProductInfo())
                .category(requestDto.getCategory())
                .build();
    }

    public void update(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.quantity = requestDto.getQuantity();
        this.productInfo = requestDto.getProductInfo();
        this.category = requestDto.getCategory();
    }
}
