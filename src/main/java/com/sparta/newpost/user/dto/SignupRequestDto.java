package com.sparta.newpost.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String nickname;
    private String username;
    private String password;
    private String repeatpassword;
    private String admintoken;
}
