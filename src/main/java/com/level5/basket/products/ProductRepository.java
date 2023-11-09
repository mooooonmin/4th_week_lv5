package com.level5.basket.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품 중복확인
    boolean existsByProductName(String productName);

    Optional<Product> findByProductName(String productName);

}
