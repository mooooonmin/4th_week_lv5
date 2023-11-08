package com.level5.basket.security;

import com.level5.basket.users.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    // 스프링 시큐리티에서 사용자의 인증정보를 담기위한 UserDetails의 구현체

    private final User user;

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() { // 이메일 반환
        return user.getEmail();
    }

    public Long getUserId() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = user.getRole().getAuthority();
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    // 여기 아래부븐은 계정의 활성상태를 확인해줌
    // 모두 true 로 반환되면 -> 활성상태
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
