package com.level5.basket.users.loginDto;

import com.level5.basket.enumType.GenderTypeEnum;
import com.level5.basket.enumType.UserRoleEnum;
import com.level5.basket.users.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginResponseDto {

    private String email;
    private GenderTypeEnum gender;
    private String address;
    private UserRoleEnum role;
    private String message;
    private String token;

    public UserLoginResponseDto(User user, String message, String token) {
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.message = message;
        this.token = token;
    }
}