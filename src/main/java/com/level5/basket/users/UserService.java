package com.level5.basket.users;

import com.level5.basket.enumType.UserRoleEnum;
import com.level5.basket.global.CustomException;
import com.level5.basket.global.ErrorMessage;
import com.level5.basket.jwt.JwtUtil;
import com.level5.basket.users.joinDto.UserJoinRequestDto;
import com.level5.basket.users.joinDto.UserJoinResponseDto;
import com.level5.basket.users.loginDto.UserLoginRequestDto;
import com.level5.basket.users.loginDto.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret.key}")
    private String secretKey;

    // 회원가입
    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto requestDto) {

        // 이메일 중복 확인
        String email = requestDto.getEmail();
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS.getMessage());
        }

        // 비밀번호 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 역할 설정
        UserRoleEnum role;
        String message;
        if (secretKey.equals(requestDto.getAdminKey())) {
            role = UserRoleEnum.ADMIN;
            message = "성공적으로 관리자로 가입되었습니다";
        } else {
            role = UserRoleEnum.USER;
            message = "AdminKey 불일치로 사용자로 자동 가입되었습니다";
        }

        // 사용자 등록
        User user = User.builder()
                .address(requestDto.getAddress())
                .email(email)
                .gender(requestDto.getGender())
                .password(password) // 인코딩된 비밀번호 설정
                .phoneNum(requestDto.getPhoneNum())
                .role(role)
                .build();

        userRepository.save(user);

        // 응답 반환
        return new UserJoinResponseDto(user, message);
    }

    // 로그인
    // 이메일 입력받아서 -> 사용자 조회 -> 비밀번호 검증 -> jwt 토큰 생성 -> 응답 반환
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {

        // 사용자 조회
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(CustomException.UserNotFoundException::new);

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {

            log.error("비밀번호 불일치: {}", requestDto.getEmail());

            throw new CustomException.InvalidPasswordException();
        }

        log.info("이메일에 맞는 비밀번호 확인: {}", requestDto.getEmail());

        // 토큰 생성
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        log.info("사용자를 위한 토큰생성: {}", requestDto.getEmail());

        // 응답 객체 생성 및 반환
        return new UserLoginResponseDto(user, "로그인 성공", token);
    }
}