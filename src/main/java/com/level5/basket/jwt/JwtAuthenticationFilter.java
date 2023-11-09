package com.level5.basket.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level5.basket.enumType.UserRoleEnum;
import com.level5.basket.exception.CustomException;
import com.level5.basket.security.UserDetailsImpl;
import com.level5.basket.users.loginDto.UserLoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    // JSON 변환을 위한 ObjectMapper 인스턴스 추가
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("로그인 시도");

        try {
            UserLoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(), UserLoginRequestDto.class);

            log.info("Request Data - Email: {}, Password: {}", requestDto.getEmail(), requestDto.getPassword());

            // Json 형태의 String 데이터를 -> object로 바꾸기
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());

        } catch (AuthenticationException e) {
            throw new CustomException.InvalidPasswordException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        log.info("로그인 성공 및 JWT 생성");

        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(email, role);
        jwtUtil.addJwtToCookie(token, response);

        // 성공 메시지와 토큰을 JSON 형식으로 응답에 추가
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("로그인 성공")));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        log.info("로그인 실패: {}", failed.getMessage());

        // 실패 메시지를 JSON 형식으로 응답에 추가
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("로그인 실패")));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    // 간단한 응답 객체 - 토큰 반환 없앰
    @Getter @Setter
    private static class SimpleResponse {
        private String message;


        public SimpleResponse(String message) {
            this.message = message;

        }
    }
}