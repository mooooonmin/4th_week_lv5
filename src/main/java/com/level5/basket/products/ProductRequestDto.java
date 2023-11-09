package com.level5.basket.products;

import com.level5.basket.enumType.CategoryTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "내용을 입력해주세요")
    private String productName;

    @Min(100)
    private int price;

    @Min(1)
    private int quantity;

    private String productInfo;

    private CategoryTypeEnum category;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .productInfo(productInfo)
                .category(category)
                .build();
    }

}
