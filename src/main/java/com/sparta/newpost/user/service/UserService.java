package com.sparta.newpost.user.service;

import com.sparta.newpost.user.dto.SignupRequestDto;
import com.sparta.newpost.user.entity.User;
import com.sparta.newpost.user.entity.UserRoleEnum;
import com.sparta.newpost.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void userSignup(SignupRequestDto requestDto) {

        // 중복되는 닉네임/아이디가 있는지검사
        Optional<User> checkNickname = userRepository.findByNickname(requestDto.getUsername());

        if(checkNickname.isPresent()){
             throw new IllegalArgumentException("중복되는 닉네임이 이미 있습니다.");
        }

        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());

        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복되는 아이디가 이미 있습니다.");
        }

        // 입력받은 비밀번호 확인
        if(!requestDto.getPassword().equals(requestDto.getRepeatpassword())){
            throw new IllegalArgumentException("비밀번호가 맞는지 확인하세요");
        }

        // 비밀번호는 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
        if(requestDto.getPassword().contains(requestDto.getNickname())&&requestDto.getPassword().contains(requestDto.getUsername())){
            throw new IllegalArgumentException("닉네임&아이디 값과 비슷한 비밀번호를 사용할 수 없습니다.");
        }

        //관리자 인지 아닌지 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(StringUtils.hasText(requestDto.getAdmintoken())){
            if (!ADMIN_TOKEN.equals(requestDto.getAdmintoken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getNickname(), requestDto.getUsername(), password, role);
        userRepository.save(user);
    }
}
