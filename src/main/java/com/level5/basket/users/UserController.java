package com.level5.basket.users;

import com.level5.basket.users.joinDto.UserJoinRequestDto;
import com.level5.basket.users.joinDto.UserJoinResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponseDto> createUser(@Valid @RequestBody UserJoinRequestDto requestDto) {
        UserJoinResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}