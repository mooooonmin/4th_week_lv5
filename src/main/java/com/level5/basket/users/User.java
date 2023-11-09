package com.level5.basket.users;

import com.level5.basket.carts.Cart;
import com.level5.basket.enumType.GenderTypeEnum;
import com.level5.basket.enumType.UserRoleEnum;
import com.level5.basket.users.joinDto.UserJoinRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Enumerated(EnumType.STRING)
    private GenderTypeEnum gender;

    private String phoneNum;

    private String address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @Builder
    public User(String email, String password,
                UserRoleEnum role, GenderTypeEnum gender,
                String phoneNum, String address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    public static User from(UserJoinRequestDto requestDto) {
        return User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .role(UserRoleEnum.USER) // 기본값
                .gender(requestDto.getGender())
                .phoneNum(requestDto.getPhoneNum())
                .address(requestDto.getAddress())
                .build();
    }

}
