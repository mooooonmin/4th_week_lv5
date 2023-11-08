package com.level5.basket.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 찾기
    Optional<User> findByEmail(String email);

    // 이메일 중복확인
    boolean existsByEmail(String email);

}
