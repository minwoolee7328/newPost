package com.sparta.newpost.user.controller;

import com.sparta.newpost.user.dto.SignupRequestDto;
import com.sparta.newpost.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public void userSignup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.userSignup(requestDto);
    }

}
