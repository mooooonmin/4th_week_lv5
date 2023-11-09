package com.level5.basket.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품 이름으로 중복채크
    boolean existsByProductName(String productName);

}
