package com.level5.basket.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    // 현재 인증된 사용자의 유저네임을 반환하는 역할
    // 로그인된 사용자의 식별자로 사용
    // 예) 사용자의 프로필 정보를 조회하거나 사용자에게 특정 작업을 허용할지 결정하는 등의 작업
    public static String getLoginUsername() {
        UserDetails user = (UserDetails) SecurityContextHolder
                // 시큐리티의 핵심 클래스중 하나, 현재 실행 중인 스레드에 대한 보안 컨텍스트를 제공 -> 인증된 사용자의 정보 포함
                .getContext()
                .getAuthentication()
                // 현재 보안 컨텍스트에서 Authentication 객체를 가져옴 -> 인증된 사용자 정보
                .getPrincipal();
        // Principal을 가져오는데, 보통 Principal -> UserDetails 타입의 객체로, 인증된 사용자의 상세 정보

        return user.getUsername();
    }
}