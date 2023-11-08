package com.level5.basket.users.joinDto;

import com.level5.basket.enumType.GenderTypeEnum;
import com.level5.basket.enumType.UserRoleEnum;
import com.level5.basket.users.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserJoinResponseDto {
    private String email;
    private GenderTypeEnum gender;
    private String address;
    private UserRoleEnum role;
    private String message;

    public UserJoinResponseDto(User user, String message) {
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.message = message;
    }
}