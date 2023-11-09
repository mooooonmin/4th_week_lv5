package com.level5.basket.products;

import com.level5.basket.enumType.CategoryTypeEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductResponseDto {
    private String productName;
    private int price;
    private int quantity;
    private String productInfo;
    private CategoryTypeEnum category;
    private String message;

    public ProductResponseDto(Product product, String message) {
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.productInfo = product.getProductInfo();
        this.category = product.getCategory();
        this.message = message;
    }

}
